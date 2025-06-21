package com.magnesify;

import com.magnesify.enums.HttpMethod;
import com.magnesify.http.CustomHttpResponse;
import com.magnesify.http.MakeRequest;

import java.util.HashMap;
import java.util.Map;

public class Naulis {

    public static void main(String[] args) {
        String apiUrl = "https://microservice.magnesify.com/api/licenses/validate";
        String licenseToken = "BURAYA_KENDI_LISANS_TOKENINIZI_YAZIN";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", licenseToken);
        System.out.println("Lisans doğrulama isteği gönderiliyor...");
        System.out.println("URL: " + apiUrl);
        System.out.println("Gönderilecek Veri (JSON olarak): {\"token\": \"" + licenseToken + "\"}");
        try {
            CustomHttpResponse response = new MakeRequest.Builder()
                    .url(apiUrl)
                    .method(HttpMethod.POST)
                    .body(requestBody)
                    .build()
                    .execute();

            System.out.println("\n--- Cevap Alındı ---");
            System.out.println("Durum Kodu (Status Code): " + response.getStatusCode());
            System.out.println("Cevap Gövdesi (Response Body): " + response.getBody());
            if (response.getStatusCode() == 200) {
                System.out.println("\nSonuç: Lisans doğrulama başarılı!");
            } else {
                System.out.println("\nSonuç: Lisans doğrulama başarısız veya bir hata oluştu.");
            }

        } catch (Exception e) {
            System.err.println("\nİstek sırasında kritik bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}