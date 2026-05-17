package org.example.proje;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSifre;

    @FXML
    void handleGiris(ActionEvent event) {
        String email = txtEmail.getText();
        String sifre = txtSifre.getText();

        if (email.isEmpty() || sifre.isEmpty()) {
            mesajGoster("Hata", "Lütfen alanları doldurun.");
            return;
        }

        try {
           
            MongoDatabase db = MongoDBConnection.connect();
            MongoCollection<Document> kullanicilar = db.getCollection("Kullanicilar");

            Document kullanici = kullanicilar.find(Filters.and(
                    Filters.eq("eposta", email),
                    Filters.eq("sifre", sifre)
            )).first();

            if (kullanici != null) {
                anaSayfayaGit(event);
            } else {
                mesajGoster("Hata", "Giriş bilgileri yanlış.");
            }
        } catch (Exception e) {
            mesajGoster("Hata", "Bir veritabanı hatası oluştu.");
            e.printStackTrace();
        }
    }

    private void anaSayfayaGit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/proje/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mağaza Otomasyonu - Yönetim Paneli");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.err.println("Dashboard yüklenirken hata: " + e.getMessage());
        }
    }

    private void mesajGoster(String baslik, String icerik) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}