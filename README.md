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

Naulis, GitHub Packages üzerinden dağıtılmaktadır. Projenize eklemek için aşağıdaki adımları izleyin.

### Adım 1: `pom.xml` Dosyanıza Bağımlılığı Ekleyin

```xml
<dependency>
  <groupId>com.magnesify</groupId>
  <artifactId>naulis</artifactId>
  <version>1.0.1-alpha</version> <!-- En güncel sürümü kontrol edin -->
</dependency>
```

### Adım 2: `pom.xml` Dosyanıza GitHub Packages Deposunu Ekleyin

Naulis, Maven Central yerine GitHub Packages üzerinde barındırıldığı için, Maven'a bu depoyu nerede bulacağını söylemeniz gerekir. `pom.xml` dosyanızın içine aşağıdaki `<repositories>` bloğunu ekleyin.

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/hacimertgokhan/naulis</url>
    </repository>
</repositories>
```

### Adım 3: GitHub Packages için Kimlik Doğrulama

GitHub Packages, özel ve genel paketlere erişim için kimlik doğrulaması gerektirir. Bunun için bir **Personal Access Token (PAT)** oluşturmanız ve Maven'ı bu token'ı kullanacak şekilde yapılandırmanız gerekir.

1.  **Personal Access Token Oluşturun:**
    *   [GitHub'da PAT oluşturma sayfasına](https://github.com/settings/tokens/new) gidin.
    *   Token'a bir isim verin (örn: `MAVEN_GITHUB_PACKAGES`).
    *   Token'a **`read:packages`** yetkisini verin.
    *   Token'ı oluşturun ve **güvenli bir yere kopyalayın**. Bu token'ı tekrar göremeyeceksiniz.

2.  **Maven `settings.xml` Dosyasını Yapılandırın:**
    *   `~/.m2/settings.xml` dosyanızı (eğer yoksa oluşturun) açın ve aşağıdaki `<server>` bloğunu ekleyin.
    *   `YOUR_GITHUB_USERNAME` kısmını kendi GitHub kullanıcı adınızla, `YOUR_PERSONAL_ACCESS_TOKEN` kısmını ise az önce oluşturduğunuz token ile değiştirin.

    ```xml
    <settings>
      <servers>
        <server>
          <id>github</id>
          <username>YOUR_GITHUB_USERNAME</username>
          <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
        </server>
      </servers>
    </settings>
    ```

Artık projeniz `mvn install` veya `mvn package` komutları ile çalıştırıldığında Naulis kütüphanesini GitHub'dan sorunsuzca indirecektir.

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
