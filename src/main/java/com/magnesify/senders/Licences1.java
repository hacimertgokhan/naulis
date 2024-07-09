package com.magnesify.senders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static com.magnesify.Magnesify.url_get;


public class Licences1 {

    public static String getResponse() {
        Map<String, String> data = new HashMap<>();
        data.put("mail", ("hacimertgokhan@gmail.com"));
        data.put("key", ("test2"));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_get))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapToJson(data)))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
    private static String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        json.deleteCharAt(json.length() - 1); // Son virgülü sil
        json.append("}");
        return json.toString();
    }

}
