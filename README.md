# Naulis Kütüphanesi
**Naulis**, lisans yönetim sistemiyle etkileşim için basit bir Java kütüphanesidir. HTTP POST istekleri gönderir, TOML formatından yapılandırma okur ve JSON yanıtlarını işler.

## Özellikler
- E-posta ve lisans anahtarıyla API'ye bağlanır.
- TOML yapılandırma dosyasından `url_get` bilgisi alır.
- JSON yanıtlarını düzenli olarak yazdırır.

### TOML Dosyası
`licenses.toml` oluşturun:
```toml
[url]
url_get = "https://your-api-endpoint.com"
```

### Örnek Kullanım
```java
Main.loadToml();
Request request = new Request("email@example.com", "your-license-key");
System.out.println(request.getData());
```