package org.example.proje;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proje/dashboard.fxml"));
        Parent root = fxmlLoader.load();
        
        stage.setTitle("Mağaza Otomasyonu - Giriş");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        MongoDBConnection.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}