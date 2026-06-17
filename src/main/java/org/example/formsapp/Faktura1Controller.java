package org.example.formsapp;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import net.synedra.validatorfx.Validator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Faktura1Controller {
    private static final String TEMPLATE = "/org/example/formsapp/templates/szablon_1_faktura.docx";

    @FXML private Button printButton;
    @FXML private Button saveDocxButton;
    @FXML private Button savePDFButton;

    @FXML private TextField nazwaFirmyField;
    @FXML private TextField nrFakturyField;
    @FXML private DatePicker datePicker;
    @FXML private TextField wystawcaUlicaField;
    @FXML private TextField wystawcaKodField;
    @FXML private TextField wystawcaMiastoField;
    @FXML private TextField wystawcaTelefonField;

    @FXML private TextField nabywcaImieNazwiskoField;
    @FXML private TextField nabywcaNazwaFirmyField;
    @FXML private TextField nabywcaUlicaField;
    @FXML private TextField nabywcaKodField;
    @FXML private TextField nabywcaMiastoField;
    @FXML private TextField nabywcaTelefonField;

    @FXML private TextField adresatImieNazwiskoField;
    @FXML private TextField adresatNazwaFirmyField;
    @FXML private TextField adresatUlicaField;
    @FXML private TextField adresatKodField;
    @FXML private TextField adresatMiastoField;
    @FXML private TextField adresatTelefonField;

    @FXML private TextField kontaktImieNazwiskoField;
    @FXML private TextField kontaktEmailField;
    @FXML private TextField kontaktTelefonField;

    private Validator validator = new Validator();

    @FXML
    public void initialize() {
        setupValidators();

        StringConverter<LocalDate> converter = new StringConverter<>() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            @Override public String toString(LocalDate date) { return date == null ? "" : dtf.format(date); }
            @Override public LocalDate fromString(String string) {
                try { return LocalDate.parse(string, dtf); }
                catch (Exception e) { return null; }
            }
        };
        datePicker.setConverter(converter);
    }

    @FXML
    public void onPrintDocument() {
        if (validator.containsErrors()) return;
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.printXWPFDocument(document, "faktura");
    }

    @FXML
    public void onSaveDocxOnDesktop() {
        if (validator.containsErrors()) return;
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.saveDocxOnDesktop(document, "faktura");
    }

    @FXML
    public void onSavePdfOnDesktop() {
        if (validator.containsErrors()) return;
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.saveAsPdfOnDesktop(document, "faktura");
    }

    private Map<String, String> getFormData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("nazwa_firmy", nazwaFirmyField.getText());
        formData.put("nr_faktury", nrFakturyField.getText());
        if (datePicker.getValue() != null) {
            formData.put("data", datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            formData.put("data", "");
        }
        formData.put("wystawca_ulica", wystawcaUlicaField.getText());
        formData.put("wystawca_kod", wystawcaKodField.getText());
        formData.put("wystawca_miasto", wystawcaMiastoField.getText());
        formData.put("wystawca_telefon", wystawcaTelefonField.getText());
        formData.put("nabywca_imie_nazwisko", nabywcaImieNazwiskoField.getText());
        formData.put("nabywca_nazwa_firmy", nabywcaNazwaFirmyField.getText());
        formData.put("nabywca_ulica", nabywcaUlicaField.getText());
        formData.put("nabywca_kod", nabywcaKodField.getText());
        formData.put("nabywca_miasto", nabywcaMiastoField.getText());
        formData.put("nabywca_telefon", nabywcaTelefonField.getText());
        formData.put("adresat_imie_nazwisko", adresatImieNazwiskoField.getText());
        formData.put("adresat_nazwa_firmy", adresatNazwaFirmyField.getText());
        formData.put("adresat_ulica", adresatUlicaField.getText());
        formData.put("adresat_kod", adresatKodField.getText());
        formData.put("adresat_miasto", adresatMiastoField.getText());
        formData.put("adresat_telefon", adresatTelefonField.getText());
        formData.put("kontakt_imie_nazwisko", kontaktImieNazwiskoField.getText());
        formData.put("kontakt_email", kontaktEmailField.getText());
        formData.put("kontakt_telefon", kontaktTelefonField.getText());
        return formData;
    }

    private void setupValidators() {
        addTextValueValidation(nazwaFirmyField, "Nazwa firmy");
        addTextValueValidation(nrFakturyField, "Nr faktury");
        addTextValueValidation(wystawcaUlicaField, "Ulica wystawcy");
        addPostalCodeValidation(wystawcaKodField, "Kod pocztowy wystawcy");
        addTextValueValidation(wystawcaMiastoField, "Miasto wystawcy");
        addTextValueValidation(wystawcaTelefonField, "Telefon wystawcy");

        addTextValueValidation(nabywcaImieNazwiskoField, "Imię i nazwisko nabywcy");
        addTextValueValidation(nabywcaNazwaFirmyField, "Nazwa firmy nabywcy");
        addTextValueValidation(nabywcaUlicaField, "Ulica nabywcy");
        addPostalCodeValidation(nabywcaKodField, "Kod pocztowy nabywcy");
        addTextValueValidation(nabywcaMiastoField, "Miasto nabywcy");
        addTextValueValidation(nabywcaTelefonField, "Telefon nabywcy");

        addTextValueValidation(adresatImieNazwiskoField, "Imię i nazwisko nabywcy");
        addTextValueValidation(adresatNazwaFirmyField, "Nazwa firmy nabywcy");
        addTextValueValidation(adresatUlicaField, "Ulica nabywcy");
        addPostalCodeValidation(adresatKodField, "Kod pocztowy nabywcy");
        addTextValueValidation(adresatMiastoField, "Miasto nabywcy");
        addTextValueValidation(adresatTelefonField, "Telefon nabywcy");

        addTextValueValidation(kontaktImieNazwiskoField, "Osoba do kontaktu");
        addTextValueValidation(kontaktEmailField, "Email kontaktowy");
        addTextValueValidation(kontaktTelefonField, "Telefon kontaktowy");

        addDateValidation(datePicker, "Data wystawienia");

        printButton.disableProperty().bind(validator.containsErrorsProperty());
        saveDocxButton.disableProperty().bind(validator.containsErrorsProperty());
        savePDFButton.disableProperty().bind(validator.containsErrorsProperty());
    }

    private void addTextValueValidation(TextField field, String name) {
        addValidation(field, field.textProperty(), name, s -> !s.trim().isEmpty(), " nie może być puste!");
    }

    private void addPostalCodeValidation(TextField field, String name) {
        addValidation(field, field.textProperty(), name, s -> s.matches("\\d{2}-\\d{3}"), " musi mieć format XX-XXX");
    }

    private void addDateValidation(DatePicker dp, String name) {
        addValidation(dp, dp.valueProperty(), name, s -> s.matches("\\d{2}\\.\\d{2}\\.\\d{4}"), " musi mieć format DD.MM.YYYY");
    }

    private void addValidation(
            Node node, ObservableValue<?> observable, String fieldName,
            Predicate<String> validatorPredicate, String errorMsg
    ) {
        validator.createCheck()
                .dependsOn(fieldName, observable)
                .withMethod(c -> {
                    Object val = c.get(fieldName);
                    String valStr = (val == null) ? "" :
                            (val instanceof LocalDate) ? ((LocalDate) val).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : val.toString();

                    if (valStr.trim().isEmpty()) {
                        c.error(fieldName + " nie może być puste!");
                    } else if (!validatorPredicate.test(valStr)) {
                        c.error(fieldName + errorMsg);
                    }
                })
                .immediate();

        TextField target = (node instanceof DatePicker) ? ((DatePicker) node).getEditor() : (TextField) node;
        target.textProperty().addListener((obs, old, val) -> {
            node.getStyleClass().removeAll("error", "success");
            if (val == null || val.isEmpty()) return;

            node.getStyleClass().add(validatorPredicate.test(val) ? "success" : "error");
        });
    }
}