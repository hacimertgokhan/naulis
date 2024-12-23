package com.magnesify.senders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static com.magnesify.Main.url_get;

public class Request {
    private String data;

    public Request(String email, String key) {
        request(email, key);
    }

    private void request(String email, String key) {
        Map<String, String> data = new HashMap<>();
        data.put("email", (email));
        data.put("licenseKey", (key));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_get))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapToJson(data)))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        setData(response.body());
    }

    private String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        json.deleteCharAt(json.length() - 1); // Son virgülü sil
        json.append("}");
        return json.toString();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
