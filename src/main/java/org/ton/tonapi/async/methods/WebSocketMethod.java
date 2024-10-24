package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.TraceEventData;
import org.ton.schema.events.TransactionEventData;
import org.ton.schema.events.mempool.MempoolEventData;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@Slf4j
@ClientEndpoint
public class WebSocketMethod extends AsyncTonapiClientBase {

    private final Map<String, BiConsumer<Map<String, Object>, Object[]>> handlers = new ConcurrentHashMap<>();
    private final AtomicInteger requestIdCounter = new AtomicInteger(1);
    private Session userSession = null;
    private boolean isConnected = false;

    public WebSocketMethod(AsyncTonapiClientBase client) {
        super(client);
        initializeWebSocket();
    }

    /**
     * Initializes the WebSocket connection.
     */
    private void initializeWebSocket() {
        try {
            String endpoint = this.websocketUrl;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(endpoint));
        } catch (URISyntaxException | DeploymentException | IOException e) {
            log.error("Failed to connect to WebSocket: {}", e.getMessage());
            throw new RuntimeException("WebSocket initialization failed", e);
        }
    }

    /**
     * Establishes a WebSocket connection and subscribes to a specific method.
     *
     * @param method  The subscription method.
     * @param params  The parameters for the subscription.
     * @param handler The handler function to process incoming messages.
     * @param args    Additional arguments to pass to the handler.
     * @throws TONAPIError if the connection or subscription fails.
     */
    private synchronized void subscribeWebSocket(
            String method,
            List<String> params,
            BiConsumer<Map<String, Object>, Object[]> handler,
            Object... args) throws TONAPIError {

        if (userSession == null || !userSession.isOpen()) {
            initializeWebSocket();
        }

        int requestId = requestIdCounter.getAndIncrement();

        Map<String, Object> subscriptionMessage = new ConcurrentHashMap<>();
        subscriptionMessage.put("id", requestId);
        subscriptionMessage.put("jsonrpc", "2.0");
        subscriptionMessage.put("method", method);
        subscriptionMessage.put("params", params);

        handlers.put(method, handler);

        try {
            String message = objectMapper.writeValueAsString(subscriptionMessage);
            userSession.getAsyncRemote().sendText(message);
            log.info("Subscribed to method: {} with id: {}", method, requestId);
        } catch (IOException e) {
            throw new TONAPIError("Failed to send subscription message: " + e.getMessage(), e);
        }
    }

    /**
     * Callback method for when a WebSocket connection is established.
     *
     * @param userSession The user session.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        this.isConnected = true;
        log.info("WebSocket connection established");
    }

    /**
     * Callback method for when a message is received from the server.
     *
     * @param message The incoming message.
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {
            });
            String jsonrpc = (String) data.get("jsonrpc");
            if (!"2.0".equals(jsonrpc)) {
                log.warn("Received message with unexpected jsonrpc version: {}", jsonrpc);
                return;
            }

            if (data.containsKey("id")) {
                Integer id = (Integer) data.get("id");
                String result = (String) data.get("result");
                log.info("Received subscription response for id {}: {}", id, result);
            } else {
                String method = (String) data.get("method");
                Map<String, Object> params = (Map<String, Object>) data.get("params");

                if (method == null || params == null) {
                    log.warn("Received message without method or params: {}", message);
                    return;
                }

                BiConsumer<Map<String, Object>, Object[]> handler = handlers.get(method);

                if (handler != null) {
                    handler.accept(params, null);
                } else {
                    log.warn("No handler found for method: {}", method);
                }
            }
        } catch (IOException | ClassCastException e) {
            log.error("Failed to process incoming WebSocket message: {}", e.getMessage());
        }
    }

    /**
     * Callback method for when a WebSocket connection is closed.
     *
     * @param userSession The user session.
     * @param reason      The reason for closure.
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
        this.isConnected = false;
        log.info("WebSocket connection closed: {}", reason.getReasonPhrase());
    }

    /**
     * Subscribes to transactions WebSocket events for the specified list of account or account with the specified instructions.
     *
     * @param accounts A list of (account / account;operations) addresses to subscribe to.
     * @param handler  A handler function to process TransactionEventData.
     * @param args     Additional arguments to pass to the handler.
     * @throws TONAPIError if the connection or subscription fails.
     * @implNote "accounts":[
     * "-1:5555555555555555555555555555555555555555555555555555555555555555",
     * "-1:3333333333333333333333333333333333333333333333333333333333333333;operations=JettonTransfer,0x0524c7ae"
     * ]
     */
    public void subscribeToTransactions(
            List<String> accounts,
            BiConsumer<TransactionEventData, Object[]> handler,
            Object... args) throws TONAPIError {
        String method = "subscribe_account";
        subscribeWebSocket(method, accounts, (params, handlerArgs) -> {
            try {
                TransactionEventData event = objectMapper.convertValue(params, TransactionEventData.class);
                handler.accept(event, handlerArgs);
            } catch (IllegalArgumentException e) {
                log.error("Failed to parse TransactionEventData: {}", e.getMessage());
            }
        }, args);
    }

    /**
     * Subscribes to traces WebSocket events for the specified accounts.
     *
     * @param accounts A list of account addresses to subscribe to.
     * @param handler  A handler function to process TraceEventData.
     * @param args     Additional arguments to pass to the handler.
     * @throws TONAPIError if the connection or subscription fails.
     */
    public void subscribeToTraces(
            List<String> accounts,
            BiConsumer<TraceEventData, Object[]> handler,
            Object... args) throws TONAPIError {
        String method = "subscribe_trace";
        subscribeWebSocket(method, accounts, (params, handlerArgs) -> {
            try {
                TraceEventData event = objectMapper.convertValue(params, TraceEventData.class);
                handler.accept(event, handlerArgs);
            } catch (IllegalArgumentException e) {
                log.error("Failed to parse TraceEventData: {}", e.getMessage());
            }
        }, args);
    }

    /**
     * Subscribes to mempool WebSocket events for the specified accounts.
     *
     * @param accounts A list of account addresses to subscribe to.
     * @param handler  A handler function to process MempoolEventData.
     * @param args     Additional arguments to pass to the handler.
     * @throws TONAPIError if the connection or subscription fails.
     */
    public void subscribeToMempool(
            List<String> accounts,
            BiConsumer<MempoolEventData, Object[]> handler,
            Object... args) throws TONAPIError {
        String method = "subscribe_mempool";
        if (accounts != null && !accounts.isEmpty()) {
            String accountsParam = String.join(",", accounts);
            String formattedParam = "accounts=" + accountsParam;
            subscribeWebSocket(method, Collections.singletonList(formattedParam), (params, handlerArgs) -> {
                try {
                    MempoolEventData event = objectMapper.convertValue(params, MempoolEventData.class);
                    handler.accept(event, handlerArgs);
                } catch (IllegalArgumentException e) {
                    log.error("Failed to parse MempoolEventData: {}", e.getMessage());
                }
            }, args);
        } else {
            subscribeWebSocket(method, Collections.emptyList(), (params, handlerArgs) -> {
                try {
                    MempoolEventData event = objectMapper.convertValue(params, MempoolEventData.class);
                    handler.accept(event, handlerArgs);
                } catch (IllegalArgumentException e) {
                    log.error("Failed to parse MempoolEventData: {}", e.getMessage());
                }
            }, args);
        }
    }

    /**
     * Closes the WebSocket connection gracefully.
     */
    public synchronized void close() {
        if (userSession != null && userSession.isOpen()) {
            try {
                userSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Test completed"));
                log.info("WebSocket connection closed gracefully");
            } catch (IOException e) {
                log.error("Failed to close WebSocket connection: {}", e.getMessage());
            }
        }
    }
}
