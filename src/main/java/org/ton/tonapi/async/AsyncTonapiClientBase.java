package org.ton.tonapi.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ton.exception.TONAPIError;
import org.ton.exception.TONAPISSEError;
import org.ton.exception.TONAPITooManyRequestsError;
import org.ton.util.ObjectMapperProvider;
import org.ton.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;


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
    protected HttpClient httpClient;

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
        this.timeout = Optional.ofNullable(timeout).orElse((float) 10);

        this.baseUrl = Optional.ofNullable(baseUrl)
                .orElse(this.isTestnet ? "https://testnet.tonapi.io/" : "https://tonapi.io/");
        this.websocketUrl = Optional.ofNullable(websocketUrl)
                .orElse(this.isTestnet ? "wss://testnet.tonapi.io/v2/websocket" : "wss://tonapi.io/v2/websocket");
        this.headers = new HashMap<>();
        this.headers.put("Authorization", "Bearer " + apiKey);
        if (headers != null) {
            this.headers.putAll(headers);
        }

        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(timeout != null ? timeout.longValue() : 30))
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
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(timeout != null ? timeout.longValue() : 30));

        Map<String, String> allHeaders = new HashMap<>(this.headers);
        if (headers != null) {
            allHeaders.putAll(headers);
        }
        allHeaders.put("Content-Type", "application/json");
        allHeaders.forEach(requestBuilder::header);

        if (params != null && !params.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            params.forEach((k, v) -> joiner.add(k + "=" + v.toString()));
            url += joiner.toString();
        }

        requestBuilder.uri(URI.create(url));

        if ("POST".equalsIgnoreCase(method)) {
            String requestBody = "";
            if (body != null) {
                try {
                    requestBody = objectMapper.writeValueAsString(body);
                } catch (JsonProcessingException e) {
                    throw new TONAPIError("Failed to serialize request body: " + e.getMessage(), e);
                }
            }
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        } else {
            requestBuilder.GET();
        }

        HttpRequest request = requestBuilder.build();

        log.info("Request {}: {}", method, url);
        log.info("Request headers: {}", allHeaders);
        if (params != null) {
            log.info("Request params: {}", params);
        }
        if (body != null) {
            log.info("Request body: {}", body);
        }

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    log.info("Response received - Status code: {}", response.statusCode());
                    log.info("Response headers: {}", response.headers());
                    log.info("Response body: {}", response.body());
                    return processResponse(response.statusCode(), response.body(), responseType);
                })
                .exceptionally(e -> {
                    log.error("Error during request: {}", e.getMessage());
                    throw new TONAPIError(e.getMessage(), e);
                });
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
                                if (e.getCause() instanceof TONAPITooManyRequestsError && attempt.getAndIncrement() < maxRetries) {
                                    log.warn("Rate limit exceeded. Retrying {}/{} in 1 second.", attempt.get(), maxRetries);
                                    CompletableFuture.delayedExecutor(1, java.util.concurrent.TimeUnit.SECONDS).execute(this);
                                } else {
                                    log.error("Max retries exceeded or non-rate limit error: {}", e.getMessage());
                                    future.completeExceptionally(e.getCause());
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
        Map<String, String> allHeaders = new HashMap<>(this.headers);

        if (params != null && !params.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            params.forEach((k, v) -> joiner.add(k + "=" + v.toString()));
            url += joiner.toString();
        }

        log.info("Subscribing to SSE with URL: {} and params: {}", url, params);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeout != null ? timeout.longValue() : 30))
                .headers(allHeaders.entrySet().stream()
                        .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
                        .toArray(String[]::new))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenCompose(response -> {
                    if (response.statusCode() != 200) {
                        return CompletableFuture.supplyAsync(() -> {
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                                String errorBody = sb.toString();
                                processResponse(response.statusCode(), errorBody, new TypeReference<Map<String, String>>() {
                                });
                                return null;
                            } catch (IOException e) {
                                log.error("Error reading error response body: {}", e.getMessage());
                                throw new TONAPISSEError("Error reading error response body " + e.getMessage());
                            }
                        });
                    }
                    return CompletableFuture.completedFuture(response.body());
                })
                .thenAcceptAsync(inputStream -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
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
                })
                .exceptionally(e -> {
                    log.error("Exception during SSE subscription: {}", e.getMessage());
                    throw new TONAPIError(e.getMessage(), e);
                });
    }
}
