package org.example.formsapp;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class FormValidator extends Validator {

    public void addTextValueValidation(TextField field, String name) {
        addValidation(field, field.textProperty(), name, s -> !s.trim().isEmpty(), " nie może być puste!");
    }

    public void addPostalCodeValidation(TextField field, String name) {
        addValidation(field, field.textProperty(), name, s -> s.matches("\\d{2}-\\d{3}"), " musi mieć format XX-XXX");
    }

    public void addDateValidation(DatePicker dp, String name) {
        addValidation(dp, dp.valueProperty(), name, s -> s.matches("\\d{2}\\.\\d{2}\\.\\d{4}"), " musi mieć format DD.MM.YYYY");
    }

    private void addValidation(
            Node node, ObservableValue<?> observable, String fieldName,
            Predicate<String> validatorPredicate, String errorMsg
    ) {
        createCheck()
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
