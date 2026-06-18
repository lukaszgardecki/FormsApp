package org.example.formsapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.synedra.validatorfx.Validator;

public class ButtonBarController {

    @FXML private Button printButton, saveDocxButton, savePDFButton;
    private Runnable savePdfAction;
    private Runnable saveDocxAction;
    private Runnable printAction;

    @FXML private void onSavePdfOnDesktop() { savePdfAction.run(); }
    @FXML private void onSaveDocxOnDesktop() { saveDocxAction.run(); }
    @FXML private void onPrintDocument() { printAction.run(); }

    public void setActions(Runnable pdf, Runnable docx, Runnable print) {
        this.savePdfAction = pdf;
        this.saveDocxAction = docx;
        this.printAction = print;
    }

    public void setupValidator(Validator validator) {
        savePDFButton.disableProperty().bind(validator.containsErrorsProperty());
        saveDocxButton.disableProperty().bind(validator.containsErrorsProperty());
        printButton.disableProperty().bind(validator.containsErrorsProperty());
    }

}
