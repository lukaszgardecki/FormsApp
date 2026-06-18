package org.example.formsapp.invoice;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.example.formsapp.ButtonBarController;
import org.example.formsapp.FileGenerator;
import org.example.formsapp.FormValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Invoice1Controller {
    private static final String TEMPLATE = "/org/example/formsapp/templates/invoice1.docx";
    private static final String RESULT_FILE_PREFIX_NAME = "Faktura-";

    @FXML private TextField nazwaFirmy, nrFaktury;
    @FXML private DatePicker date;
    @FXML private TextField wystawcaUlica, wystawcaKod, wystawcaMiasto, wystawcaTelefon;
    @FXML private TextField nabywcaImieNazwisko, nabywcaNazwaFirmy, nabywcaUlica, nabywcaKodField, nabywcaMiasto, nabywcaTelefon;
    @FXML private TextField adresatImieNazwisko, adresatNazwaFirmy, adresatUlica, adresatKod, adresatMiasto, adresatTelefon;
    @FXML private TextField kontaktImieNazwisko, kontaktEmail, kontaktTelefon;

    @FXML private ButtonBarController buttonBarController;
    private FormValidator validator = new FormValidator();

    @FXML
    public void initialize() {
        setupValidators();
        setButtonBarActions();
        setDateFieldsConverters();
    }

    private void onPrintDocument() {
        if (validator.containsErrors()) return;
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(getFormData(), TEMPLATE);
        FileGenerator.printXWPFDocument(document, RESULT_FILE_PREFIX_NAME+System.currentTimeMillis());
    }

    private void onSaveDocxOnDesktop() {
        if (validator.containsErrors()) return;
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(getFormData(), TEMPLATE);
        FileGenerator.saveDocxOnDesktop(document, RESULT_FILE_PREFIX_NAME+System.currentTimeMillis());
    }

    private void onSavePdfOnDesktop() {
        if (validator.containsErrors()) return;
        XWPFDocument document = FileGenerator.generateDocumentFromTemplate(getFormData(), TEMPLATE);
        FileGenerator.saveAsPdfOnDesktop(document, RESULT_FILE_PREFIX_NAME+System.currentTimeMillis());
    }

    private Map<String, String> getFormData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("nazwa_firmy", nazwaFirmy.getText());
        formData.put("nr_faktury", nrFaktury.getText());
        if (date.getValue() != null) {
            formData.put("data", date.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            formData.put("data", "");
        }
        formData.put("wystawca_ulica", wystawcaUlica.getText());
        formData.put("wystawca_kod", wystawcaKod.getText());
        formData.put("wystawca_miasto", wystawcaMiasto.getText());
        formData.put("wystawca_telefon", wystawcaTelefon.getText());
        formData.put("nabywca_imie_nazwisko", nabywcaImieNazwisko.getText());
        formData.put("nabywca_nazwa_firmy", nabywcaNazwaFirmy.getText());
        formData.put("nabywca_ulica", nabywcaUlica.getText());
        formData.put("nabywca_kod", nabywcaKodField.getText());
        formData.put("nabywca_miasto", nabywcaMiasto.getText());
        formData.put("nabywca_telefon", nabywcaTelefon.getText());
        formData.put("adresat_imie_nazwisko", adresatImieNazwisko.getText());
        formData.put("adresat_nazwa_firmy", adresatNazwaFirmy.getText());
        formData.put("adresat_ulica", adresatUlica.getText());
        formData.put("adresat_kod", adresatKod.getText());
        formData.put("adresat_miasto", adresatMiasto.getText());
        formData.put("adresat_telefon", adresatTelefon.getText());
        formData.put("kontakt_imie_nazwisko", kontaktImieNazwisko.getText());
        formData.put("kontakt_email", kontaktEmail.getText());
        formData.put("kontakt_telefon", kontaktTelefon.getText());
        return formData;
    }

    private void setupValidators() {
        validator.addTextValueValidation(nazwaFirmy, "Nazwa firmy");
        validator.addTextValueValidation(nrFaktury, "Nr faktury");
        validator.addTextValueValidation(wystawcaUlica, "Ulica wystawcy");
        validator.addPostalCodeValidation(wystawcaKod, "Kod pocztowy wystawcy");
        validator.addTextValueValidation(wystawcaMiasto, "Miasto wystawcy");
        validator.addTextValueValidation(wystawcaTelefon, "Telefon wystawcy");

        validator.addTextValueValidation(nabywcaImieNazwisko, "Imię i nazwisko nabywcy");
        validator.addTextValueValidation(nabywcaNazwaFirmy, "Nazwa firmy nabywcy");
        validator.addTextValueValidation(nabywcaUlica, "Ulica nabywcy");
        validator.addPostalCodeValidation(nabywcaKodField, "Kod pocztowy nabywcy");
        validator.addTextValueValidation(nabywcaMiasto, "Miasto nabywcy");
        validator.addTextValueValidation(nabywcaTelefon, "Telefon nabywcy");

        validator.addTextValueValidation(adresatImieNazwisko, "Imię i nazwisko nabywcy");
        validator.addTextValueValidation(adresatNazwaFirmy, "Nazwa firmy nabywcy");
        validator.addTextValueValidation(adresatUlica, "Ulica nabywcy");
        validator.addPostalCodeValidation(adresatKod, "Kod pocztowy nabywcy");
        validator.addTextValueValidation(adresatMiasto, "Miasto nabywcy");
        validator.addTextValueValidation(adresatTelefon, "Telefon nabywcy");

        validator.addTextValueValidation(kontaktImieNazwisko, "Osoba do kontaktu");
        validator.addTextValueValidation(kontaktEmail, "Email kontaktowy");
        validator.addTextValueValidation(kontaktTelefon, "Telefon kontaktowy");

        validator.addDateValidation(date, "Data wystawienia");

        if (buttonBarController != null) buttonBarController.setupValidator(validator);
    }

    private void setButtonBarActions() {
        buttonBarController.setActions(
                this::onSavePdfOnDesktop,
                this::onSaveDocxOnDesktop,
                this::onPrintDocument
        );
    }

    private void setDateFieldsConverters() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            @Override public String toString(LocalDate date) { return date == null ? "" : dtf.format(date); }
            @Override public LocalDate fromString(String string) {
                try { return LocalDate.parse(string, dtf); }
                catch (Exception e) { return null; }
            }
        };
        date.setConverter(converter);
    }
}