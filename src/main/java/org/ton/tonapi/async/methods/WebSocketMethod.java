package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.TraceEventData;
import org.ton.schema.events.TransactionEventData;
import org.ton.schema.events.block.BlockEventData;
import org.ton.schema.events.mempool.MempoolEventData;
import org.ton.tonapi.async.AsyncTonapiClientBase;

@Slf4j
@ClientEndpoint
public class WebSocketMethod extends AsyncTonapiClientBase {

  // Map to store handlers keyed by notification method name
  private final Map<String, BiConsumer<Map<String, Object>, Object[]>> handlers = new ConcurrentHashMap<>();
  private final Map<String, SubscriptionInfo> subscriptions = new ConcurrentHashMap<>();

  private final AtomicInteger requestIdCounter = new AtomicInteger(1);
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private final int maxReconnectAttempts = 5;
  private Session userSession = null;
  private boolean isConnected = false;
  private int reconnectAttempts = 0;

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
   * @param method          The subscription method.
   * @param paramsList      The parameters for the subscription.
   * @param handler         The handler function to process incoming messages.
   * @param subscriptionKey A unique key to identify the subscription.
   * @param args            Additional arguments to pass to the handler.
   * @throws TONAPIError if the connection or subscription fails.
   */
  private synchronized void subscribeWebSocket(
      String method,
      List<String> paramsList,
      BiConsumer<Map<String, Object>, Object[]> handler,
      String subscriptionKey,
      Object... args) throws TONAPIError {

    if (userSession == null || !userSession.isOpen()) {
      initializeWebSocket();
    }

    int requestId = requestIdCounter.getAndIncrement();

    Map<String, Object> subscriptionMessage = new HashMap<>();
    subscriptionMessage.put("id", requestId);
    subscriptionMessage.put("jsonrpc", "2.0");
    subscriptionMessage.put("method", method);
    subscriptionMessage.put("params", paramsList);

    // Store subscription info for resubscription if needed
    subscriptions.put(subscriptionKey, new SubscriptionInfo(method, paramsList, handler, args));

    // Store the handler with the expected notification method name
    String notificationMethodName = getNotificationMethodName(method);
    handlers.put(notificationMethodName, handler);

    try {
      String message = objectMapper.writeValueAsString(subscriptionMessage);
      log.info("Sending subscription message: {}", message);
      userSession.getAsyncRemote().sendText(message);
      log.info("Subscribed to method: {} with id: {}", method, requestId);
    } catch (IOException e) {
      throw new TONAPIError("Failed to send subscription message: " + e.getMessage(), e);
    }
  }

  /**
   * Maps the subscription method to the expected notification method name.
   *
   * @param method The subscription method name.
   * @return The corresponding notification method name.
   */
  private String getNotificationMethodName(String method) {
    switch (method) {
      case "subscribe_account":
        return "account_transaction";
      case "subscribe_trace":
        return "trace";
      case "subscribe_mempool":
        return "mempool_message";
      case "subscribe_block":
        return "block";
      default:
        return method;
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
    this.reconnectAttempts = 0;
    log.info("WebSocket connection established");
  }

  /**
   * Callback method for when a message is received from the server.
   *
   * @param message The incoming message.
   */
  @OnMessage
  public void onMessage(String message) {
    log.info("Received message: {}", message);
    try {
      Map<String, Object> data = objectMapper.readValue(message,
          new TypeReference<Map<String, Object>>() {
          });
      String jsonrpc = (String) data.get("jsonrpc");
      if (!"2.0".equals(jsonrpc)) {
        log.warn("Received message with unexpected jsonrpc version: {}", jsonrpc);
        return;
      }

      if (data.containsKey("id")) {
        // Handle subscription response
        Integer id = (Integer) data.get("id");

        if (data.containsKey("result")) {
          // Successful subscription
          String result = (String) data.get("result");
          log.info("Subscription result: {}", result);
          // Remove temporary handler if stored
          // handlers.remove(requestIdStr);
        } else if (data.containsKey("error")) {
          // Handle error response
          Map<String, Object> error = (Map<String, Object>) data.get("error");
          Integer errorCode = (Integer) error.get("code");
          String errorMessage = (String) error.get("message");
          log.error("Error in subscription response - Code: {}, Message: {}", errorCode,
              errorMessage);
        }
      } else if (data.containsKey("method")) {
        // Handle incoming notifications
        String method = (String) data.get("method");
        Map<String, Object> params = (Map<String, Object>) data.get("params");

        BiConsumer<Map<String, Object>, Object[]> handler = handlers.get(method);

        if (handler != null) {
          handler.accept(params, null);
        } else {
          log.warn("No handler found for method: {}", method);
        }
      } else if (data.containsKey("error")) {
        // Handle error notification
        Map<String, Object> error = (Map<String, Object>) data.get("error");
        Integer errorCode = (Integer) error.get("code");
        String errorMessage = (String) error.get("message");
        log.error("Received error from server - Code: {}, Message: {}", errorCode, errorMessage);
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
    attemptReconnection();
  }

  /**
   * Attempts to reconnect to the WebSocket server with exponential backoff.
   */
  private void attemptReconnection() {
    if (reconnectAttempts < maxReconnectAttempts) {
      reconnectAttempts++;
      int delay = reconnectAttempts * 2; // Exponential backoff
      log.info("Attempting to reconnect in {} seconds...", delay);
      scheduler.schedule(() -> {
        try {
          initializeWebSocket();
          // Resubscribe to existing subscriptions
          resubscribeAll();
        } catch (Exception e) {
          log.error("Reconnection attempt failed: {}", e.getMessage());
          attemptReconnection();
        }
      }, delay, TimeUnit.SECONDS);
    } else {
      log.error("Max reconnection attempts reached. Giving up.");
    }
  }

  /**
   * Resubscribes to all existing subscriptions after reconnection.
   */
  private void resubscribeAll() {
    for (SubscriptionInfo subscriptionInfo : subscriptions.values()) {
      try {
        subscribeWebSocket(
            subscriptionInfo.method,
            subscriptionInfo.paramsList,
            subscriptionInfo.handler,
            subscriptionInfo.subscriptionKey,
            subscriptionInfo.args
        );
      } catch (TONAPIError e) {
        log.error("Failed to resubscribe to method {}: {}", subscriptionInfo.method,
            e.getMessage());
      }
    }
  }

  /**
   * Subscribes to new blocks over WebSocket.
   *
   * @param workchain The workchain ID to subscribe to. Can be null to subscribe to all workchains.
   * @param handler   A handler function to process BlockEventData.
   * @param args      Additional arguments to pass to the handler.
   * @throws TONAPIError if the connection or subscription fails.
   */
  public void subscribeToBlocks(
      Long workchain,
      BiConsumer<BlockEventData, Object[]> handler,
      Object... args) throws TONAPIError {
    String method = "subscribe_block";
    List<String> paramsList = new ArrayList<>();
    if (workchain != null) {
      paramsList.add("workchain=" + workchain);
    }

    String subscriptionKey = method + (workchain != null ? "_" + workchain : "");

    subscribeWebSocket(method, paramsList, (params, handlerArgs) -> {
      try {
        BlockEventData event = objectMapper.convertValue(params, BlockEventData.class);
        handler.accept(event, handlerArgs);
      } catch (IllegalArgumentException e) {
        log.error("Failed to parse BlockEventData: {}", e.getMessage());
      }
    }, subscriptionKey, args);
  }

  /**
   * Subscribes to transactions over WebSocket.
   *
   * @param accounts List of accounts and optional operations.
   * @param handler  A handler function to process TransactionEventData.
   * @param args     Additional arguments to pass to the handler.
   * @throws TONAPIError if the connection or subscription fails.
   */
  public void subscribeToTransactions(
      List<String> accounts,
      BiConsumer<TransactionEventData, Object[]> handler,
      Object... args) throws TONAPIError {
    String method = "subscribe_account";

    String subscriptionKey = method + "_" + String.join(",", accounts);

    subscribeWebSocket(method, accounts, (params, handlerArgs) -> {
      try {
        TransactionEventData event = objectMapper.convertValue(params, TransactionEventData.class);
        handler.accept(event, handlerArgs);
      } catch (IllegalArgumentException e) {
        log.error("Failed to parse TransactionEventData: {}", e.getMessage());
      }
    }, subscriptionKey, args);
  }

  /**
   * Subscribes to traces over WebSocket.
   *
   * @param accounts List of account IDs to subscribe to.
   * @param handler  A handler function to process TraceEventData.
   * @param args     Additional arguments to pass to the handler.
   * @throws TONAPIError if the connection or subscription fails.
   */
  public void subscribeToTraces(
      List<String> accounts,
      BiConsumer<TraceEventData, Object[]> handler,
      Object... args) throws TONAPIError {
    String method = "subscribe_trace";

    String subscriptionKey = method + "_" + String.join(",", accounts);

    subscribeWebSocket(method, accounts, (params, handlerArgs) -> {
      try {
        TraceEventData event = objectMapper.convertValue(params, TraceEventData.class);
        handler.accept(event, handlerArgs);
      } catch (IllegalArgumentException e) {
        log.error("Failed to parse TraceEventData: {}", e.getMessage());
      }
    }, subscriptionKey, args);
  }

  /**
   * Subscribes to mempool messages over WebSocket.
   *
   * @param accounts List of accounts to filter messages. Can be empty.
   * @param handler  A handler function to process MempoolEventData.
   * @param args     Additional arguments to pass to the handler.
   * @throws TONAPIError if the connection or subscription fails.
   */
  public void subscribeToMempool(
      List<String> accounts,
      BiConsumer<MempoolEventData, Object[]> handler,
      Object... args) throws TONAPIError {
    String method = "subscribe_mempool";
    List<String> paramsList = new ArrayList<>();

    if (accounts != null && !accounts.isEmpty()) {
      paramsList.add("accounts=" + String.join(",", accounts));
    }

    String subscriptionKey = method + (accounts != null ? "_" + String.join(",", accounts) : "");

    subscribeWebSocket(method, paramsList, (params, handlerArgs) -> {
      try {
        MempoolEventData event = objectMapper.convertValue(params, MempoolEventData.class);
        handler.accept(event, handlerArgs);
      } catch (IllegalArgumentException e) {
        log.error("Failed to parse MempoolEventData: {}", e.getMessage());
      }
    }, subscriptionKey, args);
  }

  /**
   * Closes the WebSocket connection gracefully.
   */
  public synchronized void close() {
    if (userSession != null && userSession.isOpen()) {
      try {
        userSession.close(
            new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client initiated closure"));
        log.info("WebSocket connection closed gracefully");
      } catch (IOException e) {
        log.error("Failed to close WebSocket connection: {}", e.getMessage());
      }
    }
  }

  /**
   * Inner class to store subscription information for resubscription.
   */
  private static class SubscriptionInfo {

    String method;
    List<String> paramsList;
    BiConsumer<Map<String, Object>, Object[]> handler;
    Object[] args;
    String subscriptionKey;

    SubscriptionInfo(String method, List<String> paramsList,
        BiConsumer<Map<String, Object>, Object[]> handler,
        Object[] args) {
      this.method = method;
      this.paramsList = paramsList;
      this.handler = handler;
      this.args = args;
      this.subscriptionKey = method + "_" + paramsList.toString();
    }
  }
}
