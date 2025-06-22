package com.group_9.project.utils;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Predicate;

public class ValidationUtil {

    // Validation for RoundedTextField
    public static void addTextValidation(RoundedComponents.RoundedTextField txtField, Predicate<String> validator) {
        txtField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }

            private void validate() {
                boolean boolValid = validator.test(txtField.getText().trim());
                txtField.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
            }
        });
    }

    // Validation for RoundedPasswordField
    public static void addTextValidation(RoundedComponents.RoundedPasswordField pwdField, Predicate<String> validator) {
        pwdField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }

            private void validate() {
                String strText = new String(pwdField.getPassword());
                boolean boolValid = validator.test(strText);
                pwdField.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
            }
        });
    }

    // Validation for ComboBox
    public static void addComboBoxValidation(RoundedComponents.RoundedComboBox<String> cboComboBox) {
        cboComboBox.addItemListener(e -> {
            boolean boolValid = cboComboBox.getSelectedIndex() != -1;
            cboComboBox.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
        });
    }

    public static void addTextValidation(JTextField txtField, JComponent cmpWrapper, Predicate<String> validator) {
        cmpWrapper.putClientProperty("validator", validator);
        txtField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validate(); }
            public void removeUpdate(DocumentEvent e) { validate(); }
            public void changedUpdate(DocumentEvent e) { validate(); }
    
            private void validate() {
                boolean boolValid = validator.test(txtField.getText().trim());
                cmpWrapper.putClientProperty("validationColor", boolValid ? Color.GRAY : Color.RED);
                cmpWrapper.repaint();
            }
        });
    }    
}
