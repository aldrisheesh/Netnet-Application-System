package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SmartFieldFormatter {

    // 📆 DATE FORMATTER (MM/dd/yyyy)
    public static void attachDateFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean bolUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { format(field); }
            @Override public void removeUpdate(DocumentEvent e) { format(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void format(JTextField field) {
                if (bolUpdating) return;
                bolUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        String strRaw = field.getText();
                        int intCaretPos = field.getCaretPosition();

                        // Get only digits
                        String strDigits = strRaw.replaceAll("\\D", "");
                        if (strDigits.length() > 8) strDigits = strDigits.substring(0, 8);

                        // Format: MM/dd/yyyy
                        StringBuilder sbFormatted = new StringBuilder();
                        int intNewCaret = intCaretPos;

                        for (int i = 0; i < strDigits.length(); i++) {
                            if (i == 2 || i == 4) {
                                sbFormatted.append('/');
                                if (i < intCaretPos) intNewCaret++;
                            }
                            sbFormatted.append(strDigits.charAt(i));
                            if (i < intCaretPos) intNewCaret++;
                        }

                        field.setText(sbFormatted.toString());
                        field.setCaretPosition(Math.min(intNewCaret, sbFormatted.length()));

                    } finally {
                        bolUpdating = false;
                    }
                });
            }
        });
    }

    // 📱 MOBILE FORMATTER (+63 9XX-XXX-XXXX)
    public static void attachMobileFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean bolUpdating = false;
    
            @Override public void insertUpdate(DocumentEvent e) { formatMobile(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatMobile(field); }
            @Override public void changedUpdate(DocumentEvent e) {}
    
            private void formatMobile(JTextField field) {
                if (bolUpdating) return;
                bolUpdating = true;
    
                SwingUtilities.invokeLater(() -> {
                    try {
                        int intCaret = field.getCaretPosition();
                        String strRaw = field.getText();
    
                        // Strip all non-digits
                        String strDigits = strRaw.replaceAll("\\D", "");
    
                        // Remove Philippine prefix if present
                        if (strDigits.startsWith("63")) {
                            strDigits = strDigits.substring(2);
                        }
    
                        // 🚫 Remove leading 0 if present
                        if (strDigits.startsWith("0")) {
                            strDigits = strDigits.substring(1);
                            if (intCaret > 0) intCaret--;
                        }
    
                        // Trim to 10 digits
                        if (strDigits.length() > 10) {
                            strDigits = strDigits.substring(0, 10);
                        }
    
                        // Format as +63 9XX-XXX-XXXX
                        StringBuilder sbFormatted = new StringBuilder("+63 ");
                        int intNewCaret = 4;
    
                        for (int i = 0; i < strDigits.length(); i++) {
                            if (i == 3 || i == 6) {
                                sbFormatted.append('-');
                                if (i < intCaret) intNewCaret++;
                            }
                            sbFormatted.append(strDigits.charAt(i));
                            if (i < intCaret) intNewCaret++;
                        }

                        field.setText(sbFormatted.toString());
                        field.setCaretPosition(Math.min(intNewCaret, sbFormatted.length()));
    
                    } finally {
                        bolUpdating = false;
                    }
                });
            }
        });
    }
    
    // 💳 CARD NUMBER FORMATTER (XXXX XXXX XXXX XXXX)
    public static void attachCardNumberFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean bolUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { formatCard(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatCard(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void formatCard(JTextField field) {
                if (bolUpdating) return;
                bolUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        int intCaret = field.getCaretPosition();
                        String strRaw = field.getText();

                        // Strip all non-digits
                        String strDigits = strRaw.replaceAll("\\D", "");

                        // Trim to max 19 digits (standard max for card numbers)
                        if (strDigits.length() > 19) {
                            strDigits = strDigits.substring(0, 19);
                        }

                        StringBuilder sbFormatted = new StringBuilder();
                        int intNewCaret = intCaret;
                        int intDigitCount = 0;

                        for (int i = 0; i < strDigits.length(); i++) {
                            if (i > 0 && i % 4 == 0) {
                                sbFormatted.append(" ");
                                if (i < intCaret) intNewCaret++;
                            }
                            sbFormatted.append(strDigits.charAt(i));
                            if (i < intCaret) intNewCaret++;
                            intDigitCount++;
                        }

                        field.setText(sbFormatted.toString());
                        field.setCaretPosition(Math.min(intNewCaret, sbFormatted.length()));

                    } finally {
                        bolUpdating = false;
                    }
                });
            }
        });
    }

    // 🗓️ EXPIRY DATE FORMATTER (MM/YY)
    public static void attachExpiryDateFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean bolUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { formatExpiry(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatExpiry(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void formatExpiry(JTextField field) {
                if (bolUpdating) return;
                bolUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        String strRaw = field.getText();
                        int intCaret = field.getCaretPosition();

                        // Strip all non-digit
                        String strDigits = strRaw.replaceAll("\\D", "");

                        if (strDigits.length() > 4) {
                            strDigits = strDigits.substring(0, 4);
                        }

                        StringBuilder sbFormatted = new StringBuilder();
                        int intNewCaret = intCaret;

                        for (int i = 0; i < strDigits.length(); i++) {
                            if (i == 2) {
                                sbFormatted.append('/');
                                if (i < intCaret) intNewCaret++;
                            }
                            sbFormatted.append(strDigits.charAt(i));
                            if (i < intCaret) intNewCaret++;
                        }

                        field.setText(sbFormatted.toString());
                        field.setCaretPosition(Math.min(intNewCaret, sbFormatted.length()));

                    } finally {
                        bolUpdating = false;
                    }
                });
            }
        });
    }

}
