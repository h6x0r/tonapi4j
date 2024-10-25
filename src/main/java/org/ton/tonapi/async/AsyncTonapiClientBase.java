package org.ton.tonapi.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSource;
import org.ton.exception.TONAPIError;
import org.ton.exception.TONAPISSEError;
import org.ton.exception.TONAPITooManyRequestsError;
import org.ton.util.ObjectMapperProvider;
import org.ton.util.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class AsyncTonapiClientBase {
    protected final ObjectMapper objectMapper = ObjectMapperProvider.getMapper();
    protected String apiKey;
    protected boolean isTestnet;
    protected Float timeout;
    protected int maxRetries;
    protected String baseUrl;
    protected String websocketUrl;
    protected Map<String, String> headers;
    protected OkHttpClient httpClient;

    public AsyncTonapiClientBase(AsyncTonapiClientBase clientBase) {
        this.apiKey = clientBase.apiKey;
        this.isTestnet = clientBase.isTestnet;
        this.maxRetries = clientBase.maxRetries;
        this.timeout = clientBase.timeout;
        this.baseUrl = clientBase.baseUrl;
        this.websocketUrl = clientBase.websocketUrl;
        this.headers = clientBase.headers;
        this.httpClient = clientBase.httpClient;
    }

    public AsyncTonapiClientBase(String apiKey,
                                 Boolean isTestnet,
                                 Integer maxRetries,
                                 String baseUrl,
                                 String websocketUrl,
                                 Map<String, String> headers,
                                 Float timeout) {
        this.apiKey = apiKey;
        this.isTestnet = Optional.ofNullable(isTestnet).orElse(false);
        this.maxRetries = Optional.ofNullable(maxRetries).orElse(0);
        this.timeout = Optional.ofNullable(timeout).orElse(10.0f);

        this.baseUrl = Optional.ofNullable(baseUrl)
                .orElse(this.isTestnet ? "https://testnet.tonapi.io/" : "https://tonapi.io/");
        this.websocketUrl = Optional.ofNullable(websocketUrl)
                .orElse(this.isTestnet ? "wss://testnet.tonapi.io/v2/websocket" : "wss://tonapi.io/v2/websocket");
        this.headers = new HashMap<>();
        this.headers.put("Authorization", "Bearer " + apiKey);
        if (headers != null) {
            this.headers.putAll(headers);
        }

        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(this.timeout.longValue(), TimeUnit.SECONDS)
                .readTimeout(this.timeout.longValue(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * Processes the HTTP response and deserializes it into the specified type.
     *
     * @param statusCode   The HTTP status code.
     * @param responseBody The response body as a string.
     * @param responseType The class type to deserialize the response into.
     * @param <T>          The type of the response object.
     * @return The deserialized response object.
     * @throws TONAPIError If the status code is not 200, deserialization fails or received response is not valid.
     */
    private <T> T processResponse(int statusCode, String responseBody, TypeReference<T> responseType) throws TONAPIError {
        if (statusCode >= 200 && statusCode < 300) {
            if (responseBody == null || responseBody.isEmpty()) {
                Type responseJavaType = responseType.getType();
                if (responseJavaType == Void.class || responseJavaType == Void.TYPE) {
                    return null;
                }
                throw new TONAPIError("Expected response body but received empty response");
            }
            try {
                return objectMapper.readValue(responseBody, responseType);
            } catch (IOException e) {
                throw new TONAPIError("Failed to parse response: " + e.getMessage(), e);
            }
        } else {
            throw Utils.mapStatusCodeToException(statusCode, responseBody);
        }
    }

    /**
     * Makes an asynchronous HTTP request.
     *
     * @param method       The HTTP method ("GET" or "POST").
     * @param path         The API path.
     * @param headers      Optional headers to include in the request.
     * @param params       Optional query parameters.
     * @param body         Optional request body data.
     * @param responseType The class type to deserialize the response into.
     * @param <T>          The type of the response object.
     * @return A CompletableFuture of the deserialized response object.
     * @throws TONAPIError If an error occurs during the request.
     */
    private <T> CompletableFuture<T> requestAsync(String method,
                                                  String path,
                                                  Map<String, String> headers,
                                                  Map<String, Object> params,
                                                  Object body,
                                                  TypeReference<T> responseType) throws TONAPIError {
        String url = baseUrl + path;

        if (params != null && !params.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                joiner.add(entry.getKey() + "=" + entry.getValue().toString());
            }
            url += joiner.toString();
        }

        Request.Builder requestBuilder = new Request.Builder().url(url);

        Map<String, String> allHeaders = new HashMap<>(this.headers);
        if (headers != null) {
            allHeaders.putAll(headers);
        }
        allHeaders.put("Content-Type", "application/json");

        for (Map.Entry<String, String> header : allHeaders.entrySet()) {
            requestBuilder.addHeader(header.getKey(), header.getValue());
        }

        if ("POST".equalsIgnoreCase(method)) {
            String requestBody = "";
            if (body != null) {
                try {
                    requestBody = objectMapper.writeValueAsString(body);
                } catch (JsonProcessingException e) {
                    throw new TONAPIError("Failed to serialize request body: " + e.getMessage(), e);
                }
            }
            RequestBody requestBodyObj = RequestBody.create(MediaType.parse("application/json"), requestBody);
            requestBuilder.post(requestBodyObj);
        } else {
            requestBuilder.get();
        }

        Request request = requestBuilder.build();

        log.info("Request {}: {}", method, url);
        log.info("Request headers: {}", allHeaders);
        if (params != null) {
            log.info("Request params: {}", params);
        }
        if (body != null) {
            log.info("Request body: {}", body);
        }

        CompletableFuture<T> future = new CompletableFuture<>();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("Error during request: {}", e.getMessage());
                future.completeExceptionally(new TONAPIError(e.getMessage(), e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    int statusCode = response.code();
                    String responseBody = response.body() != null ? response.body().string() : null;

                    log.info("Response received - Status code: {}", statusCode);
                    log.info("Response headers: {}", response.headers());
                    log.info("Response body: {}", responseBody);

                    T result = processResponse(statusCode, responseBody, responseType);
                    future.complete(result);
                } catch (Exception e) {
                    log.error("Error processing response: {}", e.getMessage());
                    future.completeExceptionally(new TONAPIError(e.getMessage(), e));
                }
            }
        });

        return future;
    }

    /**
     * Makes an asynchronous HTTP request with retries if rate limit is reached.
     *
     * @param method       The HTTP method ("GET" or "POST").
     * @param path         The API path.
     * @param headers      Optional headers to include in the request.
     * @param params       Optional query parameters.
     * @param body         Optional request body data.
     * @param responseType The class type to deserialize the response into.
     * @param <T>          The type of the response object.
     * @return A CompletableFuture of the deserialized response object.
     * @throws TONAPIError If the maximum number of retries is exceeded.
     */
    private <T> CompletableFuture<T> requestWithRetriesAsync(String method,
                                                             String path,
                                                             Map<String, String> headers,
                                                             Map<String, Object> params,
                                                             Object body,
                                                             TypeReference<T> responseType) throws TONAPIError {
        CompletableFuture<T> future = new CompletableFuture<>();
        AtomicInteger attempt = new AtomicInteger(0);

        Runnable retryTask = new Runnable() {
            @Override
            public void run() {
                try {
                    requestAsync(method, path, headers, params, body, responseType)
                            .thenAccept(future::complete)
                            .exceptionally(e -> {
                                Throwable cause = e.getCause() != null ? e.getCause() : e;
                                if (cause instanceof TONAPITooManyRequestsError && attempt.incrementAndGet() <= maxRetries) {
                                    log.warn("Rate limit exceeded. Retrying {}/{} in 1 second.", attempt.get(), maxRetries);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException interruptedException) {
                                        Thread.currentThread().interrupt();
                                        future.completeExceptionally(new TONAPIError("Thread interrupted during retry sleep.", interruptedException));
                                        return null;
                                    }
                                    this.run();
                                } else {
                                    log.error("Max retries exceeded or non-rate limit error: {}", e.getMessage());
                                    future.completeExceptionally(cause);
                                }
                                return null;
                            });
                } catch (TONAPIError ex) {
                    future.completeExceptionally(ex);
                }
            }
        };

        retryTask.run();
        return future;
    }

    /**
     * Makes an asynchronous GET request.
     *
     * @param method       The API method path.
     * @param params       Optional query parameters.
     * @param headers      Optional headers to include in the request.
     * @param responseType The class type to deserialize the response into.
     * @param <T>          The type of the response object.
     * @return A CompletableFuture of the deserialized response object.
     * @throws TONAPIError If an error occurs during the request.
     */
    protected <T> CompletableFuture<T> get(String method,
                                           Map<String, Object> params,
                                           Map<String, String> headers,
                                           TypeReference<T> responseType) throws TONAPIError {
        if (maxRetries > 0) {
            return requestWithRetriesAsync("GET", method, headers, params, null, responseType);
        } else {
            return requestAsync("GET", method, headers, params, null, responseType);
        }
    }

    /**
     * Makes an asynchronous POST request.
     *
     * @param method       The API method path.
     * @param params       Optional query parameters.
     * @param body         The request body data.
     * @param headers      Optional headers to include in the request.
     * @param responseType The class type to deserialize the response into.
     * @param <T>          The type of the response object.
     * @return A CompletableFuture of the deserialized response object.
     * @throws TONAPIError If an error occurs during the request.
     */
    protected <T> CompletableFuture<T> post(String method,
                                            Map<String, Object> params,
                                            Object body,
                                            Map<String, String> headers,
                                            TypeReference<T> responseType) throws TONAPIError {
        if (maxRetries > 0) {
            return requestWithRetriesAsync("POST", method, headers, params, body, responseType);
        } else {
            return requestAsync("POST", method, headers, params, body, responseType);
        }
    }

    /**
     * Subscribes to an SSE event stream asynchronously.
     *
     * @param method    The API method to subscribe to.
     * @param params    Optional parameters for the API method.
     * @param eventType The class type of the event data.
     * @param handler   A consumer function to handle the event data.
     * @param <T>       The type of the event data.
     * @throws TONAPIError If there is an error during subscription.
     */
    protected <T> void subscribe(String method,
                                 Map<String, Object> params,
                                 TypeReference<T> eventType,
                                 Consumer<T> handler) throws TONAPIError {
        String url = baseUrl + method;

        if (params != null && !params.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                joiner.add(entry.getKey() + "=" + entry.getValue().toString());
            }
            url += joiner.toString();
        }

        Request.Builder requestBuilder = new Request.Builder().url(url);

        Map<String, String> allHeaders = new HashMap<>(this.headers);
        allHeaders.put("Accept", "text/event-stream");

        for (Map.Entry<String, String> header : allHeaders.entrySet()) {
            requestBuilder.addHeader(header.getKey(), header.getValue());
        }

        Request request = requestBuilder.get().build();

        log.info("Subscribing to SSE with URL: {} and params: {}", url, params);

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("Error during SSE subscription: {}", e.getMessage());
                throw new TONAPISSEError("IOException during SSE subscription " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : null;
                    processResponse(response.code(), errorBody, new TypeReference<Map<String, String>>() {
                    });
                    return;
                }

                try (BufferedSource source = response.body().source()) {
                    while (!source.exhausted()) {
                        String line = source.readUtf8LineStrict();
                        try {
                            if (line.isEmpty()) {
                                continue;
                            }
                            String[] parts = line.split(": ", 2);
                            if (parts.length != 2) {
                                log.info("Skipped line due to parsing error: {}", line);
                                continue;
                            }
                            String key = parts[0];
                            String value = parts[1];

                            if ("data".equals(key)) {
                                if ("heartbeat".equals(value)) {
                                    log.info("Received heartbeat");
                                    continue;
                                }
                                log.info("Received SSE data: {}", value);

                                T event = objectMapper.readValue(value, eventType);

                                handler.accept(event);
                            }
                        } catch (Exception e) {
                            log.error("Error processing SSE line: {}", e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    log.error("IOException during SSE subscription: {}", e.getMessage());
                    throw new TONAPISSEError("IOException during SSE subscription " + e.getMessage());
                }
            }
        });
    }
}
