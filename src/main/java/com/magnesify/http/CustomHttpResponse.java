package com.magnesify.http;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class CustomHttpResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, List<String>> headers;

    public CustomHttpResponse(int statusCode, String body, Map<String, List<String>> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static CustomHttpResponse from(HttpResponse<String> response) {
        return new CustomHttpResponse(response.statusCode(), response.body(), response.headers().map());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "CustomHttpResponse{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                '}';
    }
}