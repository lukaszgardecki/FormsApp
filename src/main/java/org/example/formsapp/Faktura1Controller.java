package org.example.formsapp;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.apache.poi.xwpf.usermodel.*;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.*;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Faktura1Controller {

    // Mapowanie pól FXML
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
    public void onGenerateClicked() {
        Map<String, String> formData = getFormData();
        XWPFDocument document = generateDocument(formData);
        printXWPFDocument(document);
    }

    @FXML
    public void onSaveDocsOnDesktop() {
        Map<String, String> formData = getFormData();
        XWPFDocument document = generateDocument(formData);
        saveDocumentOnDesktop(document, "faktura.docx");
    }

    private XWPFDocument generateDocument(Map<String, String> data) {
        String szablon = "/org/example/formsapp/templates/szablon_1_faktura.docx";
        XWPFDocument doc = null;

        try (InputStream inputStream = getClass().getResourceAsStream(szablon)) {
            if (inputStream == null) throw new FileNotFoundException("Nie znaleziono szablonu: " + szablon);

            doc = new XWPFDocument(inputStream);

            for (XWPFParagraph p : doc.getParagraphs()) {
                replacePlaceholders(p, data);
            }

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            replacePlaceholders(p, data);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public void printXWPFDocument(XWPFDocument document) {
        try {
            File docxFile = File.createTempFile("faktura", ".docx");
            try (FileOutputStream fos = new FileOutputStream(docxFile)) {
                document.write(fos);
            }

            PrinterJob job = PrinterJob.getPrinterJob();
            HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();

            if (job.printDialog(attributes)) {
                Desktop.getDesktop().print(docxFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDocumentOnDesktop(XWPFDocument doc, String destFileName) {
        String katalogUzytkownika = System.getProperty("user.home");
        String destFilePath = katalogUzytkownika + File.separator + "Desktop" + File.separator + destFileName;
        File destFile = new File(destFilePath);
        saveDocument(doc, destFile);
    }

    private void saveDocument(XWPFDocument doc, File destFile) {
        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            doc.write(fos);
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania pliku.");
        }
    }

    private void replacePlaceholders(XWPFParagraph p, Map<String, String> data) {
        String text = p.getText();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            if (text.contains(placeholder)) {
                text = text.replace(placeholder, entry.getValue());
            }
        }

        // Aktualizacja tekstu w akapicie (zachowując formatowanie)
        if (!text.equals(p.getText())) {
            XWPFRun run = p.getRuns().getFirst();
            run.setText(text, 0);
        }
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