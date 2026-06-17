package org.example.formsapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    protected void showInvoice1() {
        changeForm("invoices/faktura1.fxml");
    }

    private void changeForm(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newView = loader.load();
            mainBorderPane.setCenter(newView);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error opening view: " + fxmlFile);
        }
    }
}
