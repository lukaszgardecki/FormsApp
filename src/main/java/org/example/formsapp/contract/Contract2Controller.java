package org.example.formsapp.contract;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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

public class Contract2Controller {
    private static final String TEMPLATE = "/org/example/formsapp/templates/contract2.docx";
    private static final String RESULT_FILE_PREFIX_NAME = "UmowaKS-";

    @FXML private TextField city, sellerName, sellerId, sellerPesel, sellerAddress, sellerPostalCode;
    @FXML private TextField buyerName, buyerId, buyerPesel, buyerAddress, buyerPostalCode;
    @FXML private TextField car, carProdDate, carEngineVol, carVin, carRegNum, carMileage;
    @FXML private TextField extras, itemsToRepair, price;
    @FXML private DatePicker date;
    @FXML private CheckBox noRepairsCheckBox;

    @FXML private ButtonBarController buttonBarController;
    private FormValidator validator = new FormValidator();

    @FXML
    public void initialize() {
        setupValidators();
        setButtonBarActions();
        setDateFieldsConverters();
        setCheckBoxes();
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
        // Miejsce i data
        formData.put("city", city.getText());
        formData.put("date", date.getEditor().getText());

        // Sprzedający
        formData.put("seller_name", sellerName.getText());
        formData.put("seller_id", sellerId.getText());
        formData.put("seller_pesel", sellerPesel.getText());
        formData.put("seller_address", sellerAddress.getText());
        formData.put("seller_postal_code", sellerPostalCode.getText());

        // Kupujący
        formData.put("buyer_name", buyerName.getText());
        formData.put("buyer_id", buyerId.getText());
        formData.put("buyer_pesel", buyerPesel.getText());
        formData.put("buyer_address", buyerAddress.getText());
        formData.put("buyer_postal_code", buyerPostalCode.getText());

        // Pojazd
        formData.put("car", car.getText());
        formData.put("car_prod_date", carProdDate.getText());
        formData.put("car_engine_vol", carEngineVol.getText());
        formData.put("car_vin", carVin.getText());
        formData.put("car_reg_num", carRegNum.getText());
        formData.put("car_mileage", carMileage.getText());
        formData.put("extras", extras.getText());
        formData.put("items_to_repair", itemsToRepair.getText());

        // Cena
        String priceText = price.getText();
        String formatted = priceText.replace(",", ".");
        formData.put("price", priceText);
        formData.put(
                "price_in_words",
                MoneyConverters.POLISH_BANKING_MONEY_VALUE.asWords(new BigDecimal(formatted))
        );
        return formData;
    }

    private void setupValidators() {
        // Miejsce i data
        validator.addTextValueValidation(city, "Miasto");
        validator.addDateValidation(date, "Data zawarcia");

        // Sprzedający
        validator.addTextValueValidation(sellerName, "Imię i nazwisko sprzedającego");
        validator.addTextValueValidation(sellerId, "Numer dowodu sprzedającego");
        validator.addTextValueValidation(sellerPesel, "PESEL sprzedającego");
        validator.addTextValueValidation(sellerAddress, "Adres sprzedającego");
        validator.addTextValueValidation(sellerPostalCode, "Kod pocztowy sprzedającego");

        // Kupujący
        validator.addTextValueValidation(buyerName, "Imię i nazwisko kupującego");
        validator.addTextValueValidation(buyerId, "Numer dowodu kupującego");
        validator.addTextValueValidation(buyerPesel, "PESEL kupującego");
        validator.addTextValueValidation(buyerAddress, "Adres kupującego");
        validator.addTextValueValidation(buyerPostalCode, "Kod pocztowy kupującego");

        // Pojazd
        validator.addTextValueValidation(car, "Marka pojazdu");
        validator.addTextValueValidation(carProdDate, "Rok produkcji");
        validator.addTextValueValidation(carEngineVol, "Pojemność silnika");
        validator.addTextValueValidation(carVin, "Numer VIN");
        validator.addTextValueValidation(carRegNum, "Numer rejestracyjny");
        validator.addTextValueValidation(carMileage, "Przebieg");
        validator.addTextValueValidation(extras, "Wyposażenie dodatkowe");
        validator.addTextValueValidation(itemsToRepair, "Elementy do naprawy");

        // Cena
        validator.addTextValueValidation(price, "Cena");

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

    private void setCheckBoxes() {
        noRepairsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                itemsToRepair.setText(
                        "żadne elementy eksploatacyjne na dzień zawarcia umowy nie wymagają wymiany lub naprawy"
                );
                itemsToRepair.setDisable(true);
            } else {
                itemsToRepair.clear();
                itemsToRepair.setDisable(false);
            }
        });
    }
}
