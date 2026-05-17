package org.example.proje;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import java.util.UUID;

public class MusteriEkleController {

    @FXML private TextField txtMusteriID;
    @FXML private TextField txtAdSoyad;
    @FXML private TextField txtTelefon;
    @FXML private TextField txtEposta;
    @FXML private TextArea txtAdres;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    public void initialize() {
        if (txtMusteriID != null) {
            txtMusteriID.setText("M-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        }
    }

    @FXML
    void handleKaydet(ActionEvent event) {
        String ad = txtAdSoyad != null ? txtAdSoyad.getText() : "";
        String tel = txtTelefon != null ? txtTelefon.getText() : "";

        if (ad.isEmpty() || tel.isEmpty()) {
            mesajGoster("Hata", "Ad Soyad ve Telefon alanları zorunludur.");
            return;
        }

        new Thread(() -> {
            try {
                MongoDatabase db = MongoDBConnection.connect();
                MongoCollection<Document> collection = db.getCollection("Musteriler");

                Document yeniMusteri = new Document()
                        .append("musteriID", txtMusteriID != null ? txtMusteriID.getText() : "")
                        .append("adSoyad", ad)
                        .append("telefon", tel)
                        .append("eposta", txtEposta != null ? txtEposta.getText() : "")
                        .append("adres", txtAdres != null ? txtAdres.getText() : "");

                collection.insertOne(yeniMusteri);

                Platform.runLater(() -> {
                    if (dashboardController != null) {
                        dashboardController.musterileriYukle();
                        dashboardController.menuMusteriTikla();
                    }
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> mesajGoster("Hata", "Müşteri kaydedilemedi!"));
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