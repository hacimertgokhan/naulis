package com.magnesify.senders;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.magnesify.Magnesify.url_get;

public class Licences2 {


    public static void main(String[] args) {
        try {
            // URL ve bağlantı ayarları
            URL url = new URL(url_get);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("mail", "hacimertgokhan@gmail.com");
            jsonInput.put("key", "test2");

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Yanıtın okunması
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);
            System.out.println("POST Response Message :: " + connection.getResponseMessage());

            // Yanıt başarılı ise yanıt içeriğini oku
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
