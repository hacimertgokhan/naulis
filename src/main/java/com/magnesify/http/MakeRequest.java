// Dosya: com/magnesify/http/MakeRequest.java
package com.magnesify.http;

import com.google.gson.Gson;
import com.magnesify.enums.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MakeRequest {

    private final String url;
    private final HttpMethod method;
    private final String jsonBody;
    private final Map<String, String> headers;
    private final HttpClient client;
    private static final Gson gson = new Gson();

    private MakeRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.jsonBody = builder.jsonBody;
        this.headers = builder.headers;
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public CustomHttpResponse execute() {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(this.url));

            this.headers.forEach(requestBuilder::header);

            if (this.jsonBody != null && !this.jsonBody.isEmpty()) {
                requestBuilder.header("Content-Type", "application/json; charset=UTF-8");
            }

            HttpRequest.BodyPublisher bodyPublisher = (this.jsonBody != null && !this.jsonBody.isEmpty())
                    ? HttpRequest.BodyPublishers.ofString(this.jsonBody)
                    : HttpRequest.BodyPublishers.noBody();

            switch (this.method) {
                case POST:
                    requestBuilder.POST(bodyPublisher);
                    break;
                case PUT:
                    requestBuilder.PUT(bodyPublisher);
                    break;
                case DELETE:
                    requestBuilder.DELETE();
                    break;
                case PATCH:
                    requestBuilder.method("PATCH", bodyPublisher);
                    break;
                case GET:
                default:
                    requestBuilder.GET();
                    break;
            }

            HttpRequest request = requestBuilder.build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return CustomHttpResponse.from(response);

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("HTTP isteği sırasında bir hata oluştu: " + e.getMessage(), e);
        }
    }

    public static class Builder {
        private String url;
        private HttpMethod method = HttpMethod.GET; // Varsayılan metot GET
        private String jsonBody;
        private final Map<String, String> headers = new HashMap<>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder body(Object bodyObject) {
            this.jsonBody = gson.toJson(bodyObject);
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public MakeRequest build() {
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException("URL belirtilmelidir.");
            }
            return new MakeRequest(this);
        }
    }
}