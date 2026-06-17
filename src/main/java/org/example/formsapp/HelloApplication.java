package org.example.formsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forms-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        stage.setTitle("FormsApp 1.0");
//        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
