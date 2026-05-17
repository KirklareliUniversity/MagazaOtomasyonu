# Mağaza Otomasyon Sistemi

## Proje Konusu 
Modern yazılım mühendisliği prensipleri temel alınarak, perakende mağazalarının operasyonel süreçlerini dijital ortamda entegre ve otomatikleştirmeyi hedefleyen "Mağaza Otomasyon Sistemi"nin tasarımını ve geliştirilmesini içermektedir. Sistem, ürün yönetimi, müşteri ilişkileri, satış ve finansal raporlamayı kapsayan modüler yapısı ile, mağaza yönetim süreçlerindeki verimliliği artırmayı amaçlamaktadır. Aynı zamanda, yüksek veri tutarlılığı ve platform bağımsızlığı ilkeleri gözetilerek tasarlanmış, esnek ve ölçeklenebilir teknolojik altyapıya sahiptir.

## Kurulum Kılavuzu

### Gereksinimler
-Intellij IDEA
-Scene Builder
-MongoDB Atlas
-Docker

### IntelliJ IDEA Proje Kurulumu
1. IntelliJ IDEA'yı açın.
2. "File > New > Project from Existing Sources" seçeneğini seçin.
3. Proje dizinini seçin ve "Open" butonuna tıklayın.
4. Projede Gradle veya Maven kullanılıyorsa otomatik olarak bağımlılıklar yüklenecektir.

### Scene Builder ve Intellij IDEA Bağlantısı
1. Scene Builder' ı kurun.
2. IntelliJ IDEA'yı açın.
3. Üst menüden Settings penceresini açın .
4. Sol taraftaki menüden Languages & Frameworks > JavaFX sekmesine gidin.
5. Path to Scene Builder: kısmındaki klasör ikonuna tıklayarak bilgisayarınıza kurduğunuz SceneBuilder.exe dosyasını seçin ve Apply / OK butonuna basarak kaydedin.

**-Arayüz Tasarımını Düzenleme:**
    
1. Proje klasöründeki herhangi bir .fxml dosyasına sağ tıklayın.
2. En altta bulunan "Open in Scene Builder" seçeneğine tıklayın. Tasarım ekranı harici bir pencerede otomatik olarak açılacaktır.

**-IntelliJ IDEA Swing/FXML Eklentisi:**
    
1. Eğer FXML dosyalarını doğrudan IDE içinde kod tamamlama desteğiyle düzenlemek istiyorsanız: Settings -> Plugins sekmesine gidin.
2. Marketplace alanında JavaFX eklentisinin kurulu ve aktif olduğundan emin olun.

### Docker Kurulumu
**-WSL 2 (Windows Subsystem for Linux) Kurulumu:**
1. Docker'ın Windows üzerinde yüksek performanslı çalışabilmesi için WSL 2 gereklidir.
2. PowerShell'i yönetici olarak açın ve şu komutu yazıp çalıştırın:
    ``` 
    wsl --install
3. Komut tamamlandıktan sonra bilgisayarınızı yeniden başlatın.

**-Docker Desktop İndirme ve Kurulum:**
1. Docker resmi web sitesinden Docker Desktop for Windows yükleyicisini indirin.
2. İndirilen .exe dosyasını çalıştırın. Kurulum ekranında "Use WSL 2 instead of Hyper-V" seçeneğinin işaretli olduğundan emin olun.
3. Kurulum tamamlandıktan sonra bilgisayarı yeniden başlatın veya oturumu kapatıp açın.

**-Docker'ı Başlatma ve Kontrol:**
1. Masaüstündeki Docker Desktop uygulamasını çalıştırın ve servislerin arka planda hazır hale gelmesini (sol alttaki simgenin yeşil olmasını) bekleyin.
2. Kurulumun başarılı olduğunu doğrulamak için komut satırını (CMD veya PowerShell) açıp şu komutu yazın:
    ``` 
    docker --version

### MongoDB Atlas Kurulumu
1. (https://www.mongodb.com/cloud/atlas/register) üzerinde ücretsiz bir hesap açın ve **(Free)** planıyla bir Cluster oluşturun.
2. **Database Access** menüsünden sadece harf ve rakam içeren bir kullanıcı adı ve şifre oluşturun.
3. **Network Access** menüsünden "Allow Access From Anywhere" (`0.0.0.0/0`) iznini ekleyin.
4. **Database > Connect > Drivers > Java** adımlarını izleyerek size verilen bağlantı linkini (URI) kopyalayın.

## Olası Sorunlar ve Çözümleri
-**NullPointerException hatası**:Scene Builder'da tasarladığınız butonun fx:id alanının veya On Action kısmına yazdığınız metod adının (Örn: #handleLogin), Java Controller sınıfınızdaki değişken ve metod isimleriyle küçük-büyük harf dahil birebir eşleştiğinden emin olun.
-**FXML yükleme hatası**: FXML dosyasının en üst katmanında (Root Pane) tanımlı olan fx:controller yolunun (controller.LoginController gibi) projedeki gerçek paket ve sınıf yapısıyla uyuşup uyuşmadığını kontrol edin.

-**Docker Ports are already allocated hatası**:Bilgisayarınızda veya sunucunuzda aynı portu (Örn: 8080) kullanan başka bir yerel servis (XAMPP, Tomcat, IIS veya yerel bir veritabanı) açık kalmış olabilir. Yerel servisi kapatın ya da Docker üzerinde uygulamayı çalıştırırken dış portu değiştirin:
    ```
    docker run -d -p 9090:8080 --name magaza-app magaza-otomasyonu

-**Docker Desktop açılırken WSL 2 veya sanallaştırma hatası**:Bilgisayarınızın BIOS ayarlarından "Virtualization Technology" (VT-x / AMD-V) özelliğinin etkin (Enabled) olduğunu kontrol edin. Ardından Windows terminalinde wsl --update komutunu çalıştırarak WSL çekirdeğini güncelleyin.

-**MongoDB Atlas Timeout / No server available matches TargetTopologySelector hatası**:MongoDB Atlas panelindeki Network Access ayarlarına gidin. IP beyaz listenizde (Whitelist) 0.0.0.0/0 (herkese açık) izninin ekli ve durumunun Active olduğunu doğrulayın. Docker izole bir ağ maskesi kullandığı için bu izin verilmezse buluta erişemez.

-**MongoDB AuthenticationFailed hatası**:Java koduna (Database.java) yapıştırdığınız bağlantı linkindeki <username> ve <password> alanlarının başındaki ve sonundaki < > işaretlerini sildiğinizden emin olun. Ayrıca şifrenizde özel karakterler (@, $, /, :) varsa, bağlantı adresinin bozulmaması için şifrenizi MongoDB Atlas üzerinden sadece harf ve rakamlardan oluşacak şekilde güncelleyin. 

## Teknoloji Seçimleri ve Mimarisi
- **Dil ve Paradigma:** Sistem, Java programlama dili kullanılarak geliştirilmiş ve nesne yönelimli programlama (OOP) ilkeleri esas alınmıştır. Bu sayede, kodun modülerliği, yeniden kullanılabilirliği ve bakım kolaylığı sağlanmış, yüksek seviyede kapsülleme ve soyutlama gerçekleştirilmiştir.
- **Kapsülleme ve Güvenlik:** Kritik veri alanları, "private" erişim belirleyicisi ile koruma altına alınmış ve bu veriler, kontrollü şekilde erişim sağlayan "getter" ve "setter" metodları kullanılarak işlenmiştir. Bu sayede, dışarıdan gelen müdahaleler en aza indirilmiş ve veri bütünlüğü korunmuştur.
- **Kalıtım ve Arayüzler:** Ortak özelliklere sahip nesneler, kalıtım yapısı ve arayüzler aracılığıyla modellenmiş; bu sayede, kod tekrarını azaltmış ve yeni nesne türlerinin entegrasyonu kolaylaştırılmıştır.

## Teknik Detaylar
- MongoDB Atlas kullanıldı.
- NoSQL veritabanı kullanıldı

## Kullanılan Geliştirme Ortamı
- IntelliJ IDEA
- Maven proje yapısı.
- MongoDB Atlas, Docker 

**NoSQL Avantajları**
- Esnek ve şemasız mimari.
- Yüksek performans ve hızlı erişim
- Yatay ölçeklenebilirlik
- Hızlı geliştirme süreci

**NoSQL Dezavantajları**
- ACID uyumluluğu ve ilişki eksikliği
- Tekrarlanan veri ve fazla depolama
- Standardizasyon ve sorgu karmaşıklığı

## Projenin Özellikleri##
- Kullanıcı kaydı ve kimlik doğrulama
- Müşteri ekleme
- Ürün ekleme
- Satış ekleme
- Güvenli giriş

## Geliştirmek İçin Neler Yapılabilir?
1. Web ve Mobil Arayüz Dönüşümü (Bulut Vizyonu)
    - **Web Arayüze Geçiş:** Gelecekteki bulut vizyonuna hazırlık olarak, masaüstü odaklı JavaFX mimarisi yerine backend katmanı bir REST API'ye dönüştürülebilir. Ön yüz (Frontend) ise React, Vue.js veya Angular gibi modern web teknolojileriyle responsive (mobil uyumlu) hale getirilebilir.
    - **Mobil Entegrasyon:** Mağaza içi personelin reyonlar arasında el terminalleriyle veya telefonlarıyla anlık stok sayımı yapabilmesi için Flutter veya React Native ile entegre bir mobil uygulama geliştirilebilir.
2. Akıllı Envanter ve Tahminleme Modülleri (Yapay Zeka)
    - **Kritik Stok Algoritması:** Envanter modülündeki mevcut kritik stok uyarı sistemi, geçmiş satış verilerini inceleyen basit bir makine öğrenmesi modeliyle entegre edilebilir. Böylece sistem, "Hangi üründen ne zaman sipariş geçilmesi gerektiğini" otomatik tahmin edebilir.
    - **Müşteri Satın Alma Analitiği:** Müşteri sadakat puanları (mpuan) ve geçmiş satın alma alışkanlıkları  analiz edilerek kişiye özel dinamik indirimler veya ürün öneri mekanizmaları kurgulanabilir.
3. Gelişmiş Güvenlik, Oturum Yönetimi ve Hashleme
    - **Şifre Güvenliği:** Kullanıcı şifreleri veritabanında doğrudan metin (plain text) olarak tutuluyorsa, endüstri standardı olan BCrypt veya SHA-256 gibi kriptografik algoritmalarla hashlenerek saklanabilir.
    - **JWT (JSON Web Token):** Sisteme giriş yapan personelin oturum güvenliğini sağlamak ve yetkisiz modüllere erişimini engellemek için JWT tabanlı bir yetkilendirme katmanı entegre edilebilir.

## Olumlu Yönler
- Projede MongoDB ve Docker gibi sektör standartlarında modern teknolojiler bir araya getirilerek kurumsal düzeyde bir full-stack altyapısı kurulmuştur.
- NoSQL (MongoDB) kullanımı sayesinde tekstil, elektronik ve gıda gibi farklı ürün grupları mevcut veritabanı şeması bozulmadan esnekçe depolanabilmektedir.
- Nesne yönelimli programlama ilkelerine sadık kalınarak, kritik veri alanları private erişim belirleyicileri ve kontrollü getter/setter metodları ile dış müdahalelerden korunmuştur.
- Ürün envanter modülünde, stok seviyeleri kritik sınırın altına düştüğünde devreye giren otomatik bir uyarı ve alarm mekanizması kodlanmıştır.
- Hazırlanan Dockerfile ve konteyner yapısı sayesinde, uygulamanın farklı bilgisayarlarda bağımlılık veya sürüm çakışması yaşamadan tutarlı çalışması garanti altına alınmıştır.  

## Olumsuz Yönler
- Arka plandaki bulut ve modern konteyner vizyonuna rağmen, kullanıcı arayüzünde hala masaüstü odaklı geleneksel JavaFX teknolojisinin tercih edilmiş olması mimari bir tezatlık oluşturmaktadır.
- JavaFX geliştirme sürecinde, Scene Builder ile Java kodları arasındaki ID farklılıkları ve Controller bağlantı hataları yüzünden butonların çalışmaması ve arayüzün yüklenememesi gibi teknik sorunlar yaşanmıştır
- NoSQL yapısı gereği katı tablo ilişkileri bulunmadığından, mağaza içi stok ve finansal süreçlerdeki veri tutarlılığını koruma yükü tamamen yazılımcının kod katmanına binmektedir.

## Sonuç ve Değerlendirme
Bu sistem, sadece akademik standartların ötesinde, gerçek dünya uygulamalarına uygun, modern ve ölçeklenebilir bir yapıya sahiptir.
- **Gelecek Potansiyeli:** Teknolojinin seçimi (Java, MongoDB, Docker), sistemin mikroservis mimarisine veya bulut ortamlarına geçişini destekleyecek ve esnekliğini artıracaktır.
- **Geliştirilebilirlik:** Esneklik ve modülerlik, yeni özelliklerin entegrasyonu ve sistemin zamanla büyütülmesine imkan tanır.
- **Veri Güvenliği ve Uyumluluk:** Kullanılan şifreleme ve erişim kontrol mekanizmaları, endüstri standartlarına uygun güvenlik seviyeleri sağlar.


## Projeyi Hazırlayanlar
- Kerem Zorlu
- Baran Sarıbulak
- Egehan Vuran
