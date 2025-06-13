package com.group_9.project.utils;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Predicate;

public class ValidationUtil {

    // Validation for RoundedTextField
    public static void addTextValidation(RoundedComponents.RoundedTextField field, Predicate<String> validator) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }

            private void validate() {
                boolean isValid = validator.test(field.getText().trim());
                field.setValidationBorderColor(isValid ? Color.GRAY : Color.RED);
            }
        });
    }

    // Validation for RoundedPasswordField
    public static void addTextValidation(RoundedComponents.RoundedPasswordField field, Predicate<String> validator) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }

            private void validate() {
                String text = new String(field.getPassword());
                boolean isValid = validator.test(text);
                field.setValidationBorderColor(isValid ? Color.GRAY : Color.RED);
            }
        });
    }

    // Validation for ComboBox
    public static void addComboBoxValidation(RoundedComponents.RoundedComboBox<String> comboBox) {
        comboBox.addItemListener(e -> {
            boolean isValid = comboBox.getSelectedIndex() != -1;
            comboBox.setValidationBorderColor(isValid ? Color.GRAY : Color.RED);
        });
    }

    public static void addTextValidation(JTextField field, JComponent wrapper, Predicate<String> validator) {
        wrapper.putClientProperty("validator", validator);
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }
    
            private void validate() {
                boolean isValid = validator.test(field.getText().trim());
                wrapper.putClientProperty("validationColor", isValid ? Color.GRAY : Color.RED);
                wrapper.repaint();
            }
        });
    }    
}
