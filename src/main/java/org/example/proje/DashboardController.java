package org.example.proje;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;
import java.io.IOException;

public class DashboardController {

    @FXML private TableView<Object> anaTablo;
    @FXML private Label lblToplamMusteri, lblToplamUrun, lblToplamKazanc;
    @FXML private TextField txtArama;
    @FXML private Button btnUrunler, btnMusteriler, btnSatis;

    private static ObservableList<Object> urunListesi = FXCollections.observableArrayList();
    private static ObservableList<Object> musteriListesi = FXCollections.observableArrayList();
    private static ObservableList<Object> satisListesi = FXCollections.observableArrayList();
    private String acilacakForm = "";

    public static ObservableList<Object> getUrunListesi() { return urunListesi; }
    public static ObservableList<Object> getMusteriListesi() { return musteriListesi; }
    public static ObservableList<Object> getSatisListesi() { return satisListesi; }

    @FXML
    void initialize() {
        menuUrunTikla();
        aramaYap();
    }

    public void istatistikleriGuncelle() {
        lblToplamMusteri.setText(String.valueOf(musteriListesi.size()));
        lblToplamUrun.setText(String.valueOf(urunListesi.size()));
        double toplam = 0;
        try {
            MongoDatabase db = MongoDBConnection.connect();
            for (Document doc : db.getCollection("Satislar").find()) {
                Object t = doc.get("toplamTutar");
                if (t instanceof Number) toplam += ((Number) t).doubleValue();
            }
        } catch (Exception e) {}
        lblToplamKazanc.setText(String.format("%.2f TL", toplam));
    }

    private void aramaYap() {
        FilteredList<Object> filteredData = new FilteredList<>(urunListesi, p -> true);
        txtArama.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(obj -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String f = newVal.toLowerCase();
                if (obj instanceof Urun) return ((Urun) obj).getUrunAdi().toLowerCase().contains(f);
                if (obj instanceof Musteri) return ((Musteri) obj).getAdSoyad().toLowerCase().contains(f);
                if (obj instanceof Satis) return ((Satis) obj).getMusteri().toLowerCase().contains(f);
                return false;
            });
        });
        anaTablo.setItems(filteredData);
    }

    @FXML
    void menuUrunTikla() {
        anaTablo.getColumns().clear();
        TableColumn<Object, String> c1 = new TableColumn<>("Ürün İsmi");
        c1.setCellValueFactory(new PropertyValueFactory<>("urunAdi"));
        TableColumn<Object, String> c2 = new TableColumn<>("Kategori");
        c2.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        TableColumn<Object, Integer> c3 = new TableColumn<>("Stok");
        c3.setCellValueFactory(new PropertyValueFactory<>("stokMiktari"));
        TableColumn<Object, Double> c4 = new TableColumn<>("Fiyat");
        c4.setCellValueFactory(new PropertyValueFactory<>("birimFiyati"));
        anaTablo.getColumns().addAll(c1, c2, c3, c4);
        urunleriYukle();
        anaTablo.setItems(urunListesi);
        istatistikleriGuncelle();
    }

    @FXML
    void menuMusteriTikla() {
        anaTablo.getColumns().clear();
        TableColumn<Object, String> c1 = new TableColumn<>("Müşteri No");
        c1.setCellValueFactory(new PropertyValueFactory<>("musteriID"));
        TableColumn<Object, String> c2 = new TableColumn<>("Ad Soyad");
        c2.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        TableColumn<Object, String> c3 = new TableColumn<>("Telefon");
        c3.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        anaTablo.getColumns().addAll(c1, c2, c3);
        musterileriYukle();
        anaTablo.setItems(musteriListesi);
        istatistikleriGuncelle();
    }

    @FXML
    void menuSatisTikla() {
        anaTablo.getColumns().clear();
        TableColumn<Object, String> c1 = new TableColumn<>("İşlem ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("islemId"));
        TableColumn<Object, String> c2 = new TableColumn<>("Müşteri");
        c2.setCellValueFactory(new PropertyValueFactory<>("musteri"));
        TableColumn<Object, String> c3 = new TableColumn<>("Ürün");
        c3.setCellValueFactory(new PropertyValueFactory<>("urun"));
        TableColumn<Object, Integer> c4 = new TableColumn<>("Adet");
        c4.setCellValueFactory(new PropertyValueFactory<>("adet"));
        TableColumn<Object, Double> c5 = new TableColumn<>("Toplam");
        c5.setCellValueFactory(new PropertyValueFactory<>("toplamTutar"));
        TableColumn<Object, String> c6 = new TableColumn<>("Tarih");
        c6.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        anaTablo.getColumns().addAll(c1, c2, c3, c4, c5, c6);
        satislarıYukle();
        anaTablo.setItems(satisListesi);
        istatistikleriGuncelle();
    }

    public void urunleriYukle() {
        urunListesi.clear();
        try {
            MongoDatabase db = MongoDBConnection.connect();
            for (Document doc : db.getCollection("Urunler").find()) {
                urunListesi.add(new Urun(doc.getString("urunAdi"), doc.getString("kategori"), 
                        doc.getInteger("stokMiktari"), doc.getDouble("birimFiyati"), doc.getString("urunSahibi")));
            }
        } catch (Exception e) {}
    }

    public void musterileriYukle() {
        musteriListesi.clear();
        try {
            MongoDatabase db = MongoDBConnection.connect();
            for (Document doc : db.getCollection("Musteriler").find()) {
                musteriListesi.add(new Musteri(doc.getString("musteriID"), doc.getString("adSoyad"), 
                        doc.getString("telefon"), doc.getString("eposta"), doc.getString("adres")));
            }
        } catch (Exception e) {}
    }

    public void satislarıYukle() {
        satisListesi.clear();
        try {
            MongoDatabase db = MongoDBConnection.connect();
            for (Document doc : db.getCollection("Satislar").find()) {
                satisListesi.add(new Satis(doc.getString("islemId"), doc.getString("musteri"), 
                        doc.getString("urun"), doc.getInteger("adet"), doc.getDouble("toplamTutar"), doc.getString("tarih")));
            }
        } catch (Exception e) {}
    }

    @FXML void handleSil() {
        Object s = anaTablo.getSelectionModel().getSelectedItem();
        if (s == null) return;
        try {
            MongoDatabase db = MongoDBConnection.connect();
            if (s instanceof Urun) {
                db.getCollection("Urunler").deleteOne(Filters.eq("urunAdi", ((Urun) s).getUrunAdi()));
                urunListesi.remove(s);
            } else if (s instanceof Musteri) {
                db.getCollection("Musteriler").deleteOne(Filters.eq("musteriID", ((Musteri) s).getMusteriID()));
                musteriListesi.remove(s);
            } else if (s instanceof Satis) {
                db.getCollection("Satislar").deleteOne(Filters.eq("islemId", ((Satis) s).getIslemId()));
                satisListesi.remove(s);
            }
            anaTablo.refresh();
            istatistikleriGuncelle();
        } catch (Exception e) {}
    }

    @FXML void urunEklemePenceresiniAc() { this.acilacakForm = "urun"; yetkiliGirisiniAc(); }
    @FXML void musteriEklemePenceresiniAc() { this.acilacakForm = "musteri"; yetkiliGirisiniAc(); }
    @FXML void satisEklemePenceresiniAc() { this.acilacakForm = "satis"; yetkiliGirisiniAc(); }

    @FXML
    void yetkiliGirisiniAc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proje/onay_ekrani.fxml"));
            Parent root = loader.load();
            OnayController controller = loader.getController();
            controller.setDashboard(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {}
    }

    public void yetkiOnaylandi() {
        String fxmlPath = "";
        if ("urun".equals(acilacakForm)) fxmlPath = "/org/example/proje/urun_ekle.fxml";
        else if ("musteri".equals(acilacakForm)) fxmlPath = "/org/example/proje/musteri_ekle.fxml";
        else if ("satis".equals(acilacakForm)) fxmlPath = "/org/example/proje/satis_ekle.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object ctrl = loader.getController();
            if (ctrl instanceof UrunEkleController) ((UrunEkleController) ctrl).setDashboardController(this);
            else if (ctrl instanceof MusteriEkleController) ((MusteriEkleController) ctrl).setDashboardController(this);
            else if (ctrl instanceof SatisEkleController) ((SatisEkleController) ctrl).setDashboardController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {}
    }

    @FXML void handleCikisYap() { System.exit(0); }
}