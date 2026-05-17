package org.example.proje;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class UrunEkleController {

    @FXML private TextField txtUrunAdi;
    @FXML private TextField txtKategori;
    @FXML private TextField txtStok;
    @FXML private TextField txtFiyat;
    @FXML private TextField txtUrunSahibi;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    void handleKaydet(ActionEvent event) {
        String ad = txtUrunAdi != null ? txtUrunAdi.getText() : "";
        String kat = txtKategori != null ? txtKategori.getText() : "";
        String stok = txtStok != null ? txtStok.getText() : "0";
        String fiyat = txtFiyat != null ? txtFiyat.getText() : "0";
        String sahip = txtUrunSahibi != null ? txtUrunSahibi.getText() : "";

        if (ad.isEmpty() || kat.isEmpty()) {
            mesajGoster("Hata", "Lütfen gerekli alanları doldurun.");
            return;
        }

        new Thread(() -> {
            try {
                MongoDatabase db = MongoDBConnection.connect();
                MongoCollection<Document> collection = db.getCollection("Urunler");

                Document yeniUrun = new Document()
                        .append("urunAdi", ad)
                        .append("kategori", kat)
                        .append("stokMiktari", Integer.parseInt(stok))
                        .append("birimFiyati", Double.parseDouble(fiyat))
                        .append("urunSahibi", sahip);

                collection.insertOne(yeniUrun);

                Platform.runLater(() -> {
                    if (dashboardController != null) {
                        dashboardController.urunleriYukle();
                        dashboardController.menuUrunTikla();
                    }
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> mesajGoster("Hata", "Ürün kaydedilemedi!"));
            }
        }).start();
    }

    @FXML
    void handleIptal(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void mesajGoster(String baslik, String icerik) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}