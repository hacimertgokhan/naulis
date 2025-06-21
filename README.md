# Naulis
**Naulis**, Java ile modern ve akıcı bir şekilde HTTP istekleri yapmak için tasarlanmış hafif bir istemci kütüphanesidir. Özellikle **lisans doğrulama** gibi mikroservis etkileşimleri için optimize edilmiştir. Java 11'in yerleşik `HttpClient`'ını temel alır ve **Builder deseni** sayesinde okunabilir ve zincirleme kod yazmanızı sağlar.

Karmaşık konfigürasyonlarla uğraşmadan, REST API'ler ile hızlı ve güvenilir bir şekilde iletişim kurun.

## ✨ Temel Özellikler

*   **Akıcı Arayüz (Fluent API):** `.url(...).method(...).body(...)` gibi zincirleme metotlarla isteklerinizi kolayca oluşturun.
*   **Otomatik JSON Dönüşümü:** Arka planda **Jackson** kütüphanesini kullanarak Java nesnelerinizi (POJO, Map vb.) otomatik olarak JSON formatına çevirir.
*   **Modern Java Desteği:** Arka planda Java 11+ ile gelen performanslı ve asenkron `java.net.http.HttpClient` kullanır.
*   **Yapılandırılmış Cevaplar:** API'den dönen cevabı (status code, body, headers) tek bir nesne üzerinden kolayca yönetin.
*   **Kolay Header Yönetimi:** İsteklerinize `Authorization`, `Content-Type` gibi başlıkları basitçe ekleyin.

## 🚀 Kurulum (Maven)

Naulis, GitHub Packages üzerinden dağıtılmaktadır.

```xml
        <dependency>
            <groupId>com.magnesify</groupId>
            <artifactId>naulis</artifactId>
            <version>v2.1.0-rc</version>
        </dependency>
```


## 💻 Kullanım

Naulis'i kullanmak oldukça basittir. `MakeRequest.Builder` sınıfı ile isteğinizi yapılandırın, `build()` ile oluşturun ve `execute()` ile çalıştırın.

### Örnek: Lisans Token'ı ile POST İsteği

```java
import com.magnesify.http.MakeRequest;
import com.magnesify.http.CustomHttpResponse;
import com.magnesify.enums.HttpMethod;
import java.util.Map;

public class LicenseValidator {
    public static void main(String[] args) {
        String apiUrl = "https://microservice.magnesify.com/api/licenses/validate";
        String myToken = "YOUR_LICENSE_TOKEN_HERE";

        // Gönderilecek JSON body'yi bir Map ile kolayca oluştur
        Map<String, String> requestBody = Map.of("token", myToken);
        
        try {
            // İstek oluştur ve çalıştır
            CustomHttpResponse response = new MakeRequest.Builder()
                    .url(apiUrl)
                    .method(HttpMethod.POST)
                    .body(requestBody) // Map, otomatik olarak {"token":"..."} JSON'una dönüşür
                    .build()
                    .execute();

            // Cevabı işle
            System.out.println("Durum Kodu: " + response.getStatusCode());
            System.out.println("Doğrulama Cevabı: " + response.getBody());
            
            if (response.getStatusCode() == 200) {
                System.out.println("Lisans geçerli!");
            } else {
                System.out.println("Lisans geçersiz veya bir hata oluştu.");
            }

        } catch (Exception e) {
            System.err.println("İstek gönderilirken bir hata oluştu: " + e.getMessage());
        }
    }
}
```
