package org.example.proje;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SatisEkleController {

    @FXML private ComboBox<String> cmbMusteri;
    @FXML private ComboBox<String> cmbUrun;
    @FXML private TextField txtIslemId, txtAdet, txtBirimFiyat, txtToplamTutar;
    private DashboardController dashboardController;

    public void setDashboardController(DashboardController controller) { this.dashboardController = controller; }

    @FXML
    public void initialize() {
        txtIslemId.setText("SAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        txtAdet.textProperty().addListener((obs, old, val) -> hesaplaTutar());
        txtBirimFiyat.textProperty().addListener((obs, old, val) -> hesaplaTutar());
        verileriYukle();
    }

    private void verileriYukle() {
        new Thread(() -> {
            try {
                MongoDatabase db = MongoDBConnection.connect();
                for (Document doc : db.getCollection("Musteriler").find()) {
                    String ad = doc.getString("adSoyad");
                    Platform.runLater(() -> cmbMusteri.getItems().add(ad));
                }
                for (Document doc : db.getCollection("Urunler").find()) {
                    String urun = doc.getString("urunAdi");
                    Platform.runLater(() -> cmbUrun.getItems().add(urun));
                }
            } catch (Exception e) {}
        }).start();
    }

    private void hesaplaTutar() {
        try {
            int adet = txtAdet.getText().isEmpty() ? 0 : Integer.parseInt(txtAdet.getText());
            double birim = txtBirimFiyat.getText().isEmpty() ? 0.0 : Double.parseDouble(txtBirimFiyat.getText());
            txtToplamTutar.setText(String.format("%.2f", adet * birim).replace(",", "."));
        } catch (Exception e) { txtToplamTutar.setText("0.00"); }
    }

    @FXML
    void handleSatisKaydet(ActionEvent event) {
        String m = cmbMusteri.getValue();
        String u = cmbUrun.getValue();
        if (m == null || u == null || txtAdet.getText().isEmpty()) return;

        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        new Thread(() -> {
            try {
                MongoDatabase db = MongoDBConnection.connect();
                Document doc = new Document().append("islemId", txtIslemId.getText())
                        .append("musteri", m).append("urun", u)
                        .append("adet", Integer.parseInt(txtAdet.getText()))
                        .append("toplamTutar", Double.parseDouble(txtToplamTutar.getText()))
                        .append("tarih", tarih);
                db.getCollection("Satislar").insertOne(doc);
                Platform.runLater(() -> {
                    if (dashboardController != null) dashboardController.menuSatisTikla();
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                });
            } catch (Exception e) {}
        }).start();
    }

    @FXML void handleIptal(ActionEvent event) { ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); }
}