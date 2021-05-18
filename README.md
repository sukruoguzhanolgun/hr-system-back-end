# İnsan Kaynakları Sistemi

## Giriş

Bu sistem iş başvurusu yapmak ve bu başvuruları görüntülemek için geliştirilmiştir.

Sisteme giriş yapabilmek için üyelik oluşturulması gerekmektedir. 2 Farklı üyelik türü vardır;
```json
{
  "Role": "Admin",
  "Role": "User"
}
```
Admin rolü ile iş başvurularını listeleyebilir, silebilir ve admin rolünde kullanıcı oluşturabilirsiniz.
User rolü ile yeni başvuru yapabilirsiniz.

## Uygulama Ayrıntıları

Uygulama 2 kısımdan oluşmaktadır. Front-end için JavaScript, HTML5, CSS kullanışmıştır. Kodlara [buradan](https://github.com/sukruoguzhanolgun/hr-system-front-end.git)
ulaşabilirsiniz.

Back-end **SpringBoot** projesi olarak **Java 8** ile  çalışmaktadır. Aynı zamanda proje yönetimi için Maven kullanıldığı için
uygulama için gerekili bağımlılıklar ```pom.xml``` dosyasına eklenmiştir.

Veritabanı olarak **H2 Database** kullanılmıştır. Bu veritabanını 
kullanmak için bilgisayarınıza kurulum yapmanız gerekmemektedir.
Veritabanının arayüzüne ulaşmak için http://localhost:8080/h2-console linkini kullanabilirsiniz.
sisteme giriş yapmak için aşağıdaki kullanıcıadı ve şifre kullanılır.
```json
{
  "username": "SA",
  "password": "password"
}
```
Kullanıcıadı, şifre ve veritabanı ile ilgili konfigürasyonlar 
```application.properties``` dosyasından yönetilebilir.

H2 Database sistemde gömülü olarak kullanıldığı için uygulama kapatıldığında içindeki veriler silinmektedir.
Bu sebeple uygulama başladığında sisteme 2 adet kullanıcı ve 1 adet başvuru eklenmektedir.

Bu kullanıcılar:
```json
{
  "Username": "admin",
  "Password": "1234",
  "Role": "ROLE_ADMIN"
}
```
```json
{
  "Username": "oguzhan",
  "Password": "1234",
  "Role": "ROLE_USER"
}
```
Uygulamaya giriş yapmak için **JSON Web Token** kullanılmıştır.  JWT, kullanıcının doğrulanması,
web servis güvenliği, bilgi güvenliği gibi birçok konuda kullanılabilir.
JWT hakkında detaylı bilgiye [buradan](https://jwt.io) ulaşabilirsiniz.

Kullanıcı sisteme başarılı giriş yaptığında onun için bir token oluşturulur
ve kullanıcının sistemdeki rolüne göre servislere erişim izni verilir.

Controller paketi altındaki web hizmetlerini kontrol etmek için uygulamaya
**Swagger** eklenmiştir. Swagger, JSON kullanılarak ifade edilen RESTful API'leri 
açıklamak için bir Arayüz Tanımlama Dilidir. Swagger, RESTful web 
hizmetlerini tasarlamak, oluşturmak, belgelemek ve kullanmak 
için bir dizi açık kaynaklı yazılım aracıyla birlikte kullanılır.
Swagger ile ilgili detaylı bilgiye [buradan](https://swagger.io) ulaşabilirsiniz.
Swagger arayüzüne http://localhost:8080/swagger-ui.html linki ile ulaşabilirsiniz.
Swagger ile ilgili konfigürasyon dosyaları ```SpringFoxConfig``` sınıfından yönetilmektedir.

Uygulama Front-end ve Back-end olarak 2 ayrı sunucu üzerinde çalışmaktadır. Tarayıcılar kullanıcıyı korumak üzere
bir sitede gezinti yaparken ilgili sitenin başka bir siteye web isteği yapmasına sınırlama getirir. Bu sınırlama
**Same Origin Policy - SOP** olarak adlandırılır. Bu sebeple JavaScript ile ilgili servislere istek gönderebilmek için
**Cross-Origin Resource Sharing - CORS** filtresi uygulamak gerekir. İlgili filtre ```RequestFilter``` sınıfından yönetilmektedir.
CORS ile ilgili detaylı bilgiye [buradan](https://tr.wikipedia.org/wiki/Kökler_Arası_Kaynak_Paylaşımı) ulaşabilirsiniz.

Uygulamada 2 adet controller sınıfı kullanılmıştır. Bunlar:

```LoginController```

```JobApplicationController```

```LoginController``` Sınıfında 5 adet metod bulunmaktadır. Bu metodlar ile User ve Admin
rolünde kullanıcı kaydı ve girişi yapılabilir. Aktif kullanıcılar listelenip silinebilir. 
Kullanıcı girişi ve User rolünde kullanıcı oluşturmak için her hangi bir yetkiye sahip olmak 
gerekmemektedir. Diğer metodlar için Admin rolünde kullanıcı girişi yapmak gerekmektedir.

```JobApplicationController``` Sınıfında 3 adet metod bulunmaktadır. Bu metodlar ile iş başvurusunda bulunulabilir,
iş başvuruları listelenebilir ve silinebilir. İş başvurusu yapmak için User rolü ile sisteme giriş yapmak gerekmektedir.
Diğer metodlar için Admin rolü ile sisteme giriş yapmak gerekmektedir.

# Projeyi çalıştırabilmek için:

1. Bilgisayarınızda [Java 8](https://www.java.com/download/) ve [Maven](https://maven.apache.org) kurulu olduğundan emin olun.


2. GitHub üzerinden projeyi bilgisayarınıza indirin.

```
$ git clone https://github.com/sukruoguzhanolgun/hr-system-back-end.git
```

3. Uygulamanın indirileceği klasörü seçin.

```
$ cd //sizin dosya adınız
```

4. Bağımlılıkları yükleyin.

```
$ mvn install
```

5. Projeyi çalıştırın

```
$ mvn spring-boot:run
```

6. Servislerin çalışılabilirliğini kontrol etmek için http://localhost:8080/swagger-ui.html Swagger uygulaması kullanılabilir.



7. Uygulamaya giriş yapmak ve Token oluşturmak için `/api/user/signin metoduna` POST isteği yapılmalıdır.

```
$ curl -X GET http://localhost:8080/api/user/signin
```

8. Make a POST request to `/users/signin` with the default admin user we programatically created to get a valid JWT token


9. Metod ile oluşan Token ile Swagger UI üzerinden yetki alınmalıdır ve diğer metodlara o şekilde ulaşılabilir.




