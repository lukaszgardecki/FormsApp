package org.example.formsapp.contract;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.example.formsapp.ButtonBarController;
import org.example.formsapp.FileGenerator;
import org.example.formsapp.FormValidator;
import pl.allegro.finance.tradukisto.MoneyConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Contract1Controller {
    private static final String TEMPLATE = "/org/example/formsapp/templates/contract1.docx";
    private static final String RESULT_FILE_PREFIX_NAME = "UmowaZ-";

    @FXML private TextField nrUmowy, miejsceZawarcia, opisCzynnosci;
    @FXML private TextField imieNazwiskoZd, nazwaZd, adresZd;
    @FXML private TextField imieNazwiskoZb, nazwaZb, adresZb;
    @FXML private TextField stawkaH, wyplataDataMax, okresWypow;
    @FXML private DatePicker dataZawarcia, okresOd, okresDo;

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
        formData.put("nr_umowy", nrUmowy.getText());
        formData.put("miejsce_zawarcia", miejsceZawarcia.getText());
        formData.put("opis_czynnosci", opisCzynnosci.getText());

        formData.put("imie_nazwisko_zd", imieNazwiskoZd.getText());
        formData.put("nazwa_zd", nazwaZd.getText());
        formData.put("adres_zd", adresZd.getText());

        formData.put("imie_nazwisko_zb", imieNazwiskoZb.getText());
        formData.put("nazwa_zb", nazwaZb.getText());
        formData.put("adres_zb", adresZb.getText());

        String stawkaText = stawkaH.getText();
        String formatted = stawkaText.replace(",", ".");
        formData.put("stawka_h", stawkaText);
        formData.put(
                "stawka_slownie",
                MoneyConverters.POLISH_BANKING_MONEY_VALUE.asWords(new BigDecimal(formatted))
        );
        formData.put("wyplata_data_max", wyplataDataMax.getText());
        formData.put("okres_wypow", okresWypow.getText());

        formData.put("data_zawarcia", dataZawarcia.getEditor().getText());
        formData.put("okres_trwania_od", okresOd.getEditor().getText());
        formData.put("okres_trwania_do", okresDo.getEditor().getText());
        return formData;
    }

    private void setupValidators() {
        validator.addTextValueValidation(nrUmowy, "Numer umowy");
        validator.addTextValueValidation(miejsceZawarcia, "Miejsce zawarcia umowy");
        validator.addTextValueValidation(opisCzynnosci, "Opis czynności");

        validator.addTextValueValidation(imieNazwiskoZd, "Imię i naziwsko zleceniodawcy");
        validator.addTextValueValidation(nazwaZd, "Nazwa zleceniodawcy");
        validator.addTextValueValidation(adresZd, "Adres zleceniodawcy");

        validator.addTextValueValidation(imieNazwiskoZb, "Imię i naziwsko zleceniobiorcy");
        validator.addTextValueValidation(nazwaZb, "Nazwa zleceniobiorcy");
        validator.addTextValueValidation(adresZb, "Adres zleceniobiorcy");

        validator.addTextValueValidation(stawkaH, "Stawka godzinowa");
        validator.addTextValueValidation(wyplataDataMax, "Dzień wyplaty");
        validator.addTextValueValidation(okresWypow, "Okres wypowiedzenia");

        validator.addDateValidation(dataZawarcia, "Data zawarcia umowy");
        validator.addDateValidation(okresOd, "Okres trwania umowy (od)");
        validator.addDateValidation(okresDo, "Okres trwania umowy (do)");

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
        dataZawarcia.setConverter(converter);
        okresOd.setConverter(converter);
        okresDo.setConverter(converter);
    }
}
