package org.example.proje;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class OnayController {

    @FXML private TextField txtOnayEmail;
    @FXML private PasswordField txtOnaySifre;

    private DashboardController dashboardController;

    public void setDashboard(DashboardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    void handleOnaylama(ActionEvent event) {
        String email = txtOnayEmail.getText();
        String sifre = txtOnaySifre.getText();

        if (email.isEmpty() || sifre.isEmpty()) {
            mesajGoster("Hata", "Lütfen tüm alanları doldurun.");
            return;
        }

        	 	
        new Thread(() -> {
            try {
                MongoDatabase db = MongoDBConnection.connect();
                MongoCollection<Document> koleksiyon = db.getCollection("Kullanicilar");

                Document admin = koleksiyon.find(Filters.and(
                        Filters.eq("eposta", email),
                        Filters.eq("sifre", sifre)
                )).first();

               
                Platform.runLater(() -> {
                    if (admin != null) {
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close(); 
                        
                        if (dashboardController != null) {
                            dashboardController.yetkiOnaylandi(); 
                        }
                    } else {
                        mesajGoster("Yetki Hatası", "Yönetici e-postası veya şifresi hatalı.");
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() -> mesajGoster("Bağlantı Hatası", "Veritabanı doğrulaması sırasında bir hata oluştu."));
                e.printStackTrace();
            }
        }).start();
    }

    private void mesajGoster(String baslik, String icerik) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}