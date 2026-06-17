package org.example.formsapp;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.HashMap;
import java.util.Map;

public class Faktura1Controller {
    private static final String TEMPLATE = "/org/example/formsapp/templates/szablon_1_faktura.docx";

    @FXML private TextField nazwaFirmyField;
    @FXML private TextField nrFakturyField;
    @FXML private DatePicker dataPicker;
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

    @FXML
    public void onPrintDocument() {
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.printXWPFDocument(document, "faktura");
    }

    @FXML
    public void onSaveDocxOnDesktop() {
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.saveDocxOnDesktop(document, "faktura");
    }

    @FXML
    public void onPrintPdfOnDesktop() {
        Map<String, String> formData = getFormData();
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(formData, TEMPLATE);
        FileGenerator.saveAsPdfOnDesktop(document, "faktura");
    }

    private Map<String, String> getFormData() {
        Map<String, String> formData = new HashMap<>();
//        formData.put("nazwa_firmy", nazwaFirmyField.getText());
//        formData.put("nr_faktury", nrFakturyField.getText());
//        if (dataPicker.getValue() != null) {
//            formData.put("data", dataPicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//        } else {
//            formData.put("data", "");
//        }
//        formData.put("wystawca_ulica", wystawcaUlicaField.getText());
//        formData.put("wystawca_kod", wystawcaKodField.getText());
//        formData.put("wystawca_miasto", wystawcaMiastoField.getText());
//        formData.put("wystawca_telefon", wystawcaTelefonField.getText());
//        formData.put("nabywca_imie_nazwisko", nabywcaImieNazwiskoField.getText());
//        formData.put("nabywca_nazwa_firmy", nabywcaNazwaFirmyField.getText());
//        formData.put("nabywca_ulica", nabywcaUlicaField.getText());
//        formData.put("nabywca_kod", nabywcaKodField.getText());
//        formData.put("nabywca_miasto", nabywcaMiastoField.getText());
//        formData.put("nabywca_telefon", nabywcaTelefonField.getText());
//        formData.put("adresat_imie_nazwisko", adresatImieNazwiskoField.getText());
//        formData.put("adresat_nazwa_firmy", adresatNazwaFirmyField.getText());
//        formData.put("adresat_ulica", adresatUlicaField.getText());
//        formData.put("adresat_kod", adresatKodField.getText());
//        formData.put("adresat_miasto", adresatMiastoField.getText());
//        formData.put("adresat_telefon", adresatTelefonField.getText());
//        formData.put("kontakt_imie_nazwisko", kontaktImieNazwiskoField.getText());
//        formData.put("kontakt_email", kontaktEmailField.getText());
//        formData.put("kontakt_telefon", kontaktTelefonField.getText());


        formData.put("nazwa_firmy", "TechSolutions Sp. z o.o.");
        formData.put("nr_faktury", "FV/2026/06/001");
        formData.put("data", "17.06.2026");
        formData.put("wystawca_ulica", "ul. Programistów 12/4");
        formData.put("wystawca_kod", "00-001");
        formData.put("wystawca_miasto", "Warszawa");
        formData.put("wystawca_telefon", "+48 123 456 789");
        formData.put("nabywca_imie_nazwisko", "Jan Kowalski");
        formData.put("nabywca_nazwa_firmy", "Firma Budowlana Kowalski");
        formData.put("nabywca_ulica", "ul. Główna 5");
        formData.put("nabywca_kod", "80-123");
        formData.put("nabywca_miasto", "Gdańsk");
        formData.put("nabywca_telefon", "+48 987 654 321");
        formData.put("adresat_imie_nazwisko", "Anna Nowak");
        formData.put("adresat_nazwa_firmy", "Biuro Rachunkowe Nowak");
        formData.put("adresat_ulica", "ul. Szkolna 10");
        formData.put("adresat_kod", "30-001");
        formData.put("adresat_miasto", "Kraków");
        formData.put("adresat_telefon", "+48 500 600 700");
        formData.put("kontakt_imie_nazwisko", "Marek Zieliński");
        formData.put("kontakt_email", "m.zielinski@example.com");
        formData.put("kontakt_telefon", "+48 777 888 999");
        return formData;
    }
}