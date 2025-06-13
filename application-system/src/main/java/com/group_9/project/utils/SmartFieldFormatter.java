package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SmartFieldFormatter {

    // 📆 DATE FORMATTER (MM/dd/yyyy)
    public static void attachDateFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean isUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { format(field); }
            @Override public void removeUpdate(DocumentEvent e) { format(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void format(JTextField field) {
                if (isUpdating) return;
                isUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        String raw = field.getText();
                        int caretPos = field.getCaretPosition();

                        // Get only digits
                        String digits = raw.replaceAll("\\D", "");
                        if (digits.length() > 8) digits = digits.substring(0, 8);

                        // Format: MM/dd/yyyy
                        StringBuilder formatted = new StringBuilder();
                        int newCaret = caretPos;

                        for (int i = 0; i < digits.length(); i++) {
                            if (i == 2 || i == 4) {
                                formatted.append('/');
                                if (i < caretPos) newCaret++;
                            }
                            formatted.append(digits.charAt(i));
                            if (i < caretPos) newCaret++;
                        }

                        field.setText(formatted.toString());
                        field.setCaretPosition(Math.min(newCaret, formatted.length()));

                    } finally {
                        isUpdating = false;
                    }
                });
            }
        });
    }

    // 📱 MOBILE FORMATTER (+63 9XX-XXX-XXXX)
    public static void attachMobileFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean isUpdating = false;
    
            @Override public void insertUpdate(DocumentEvent e) { formatMobile(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatMobile(field); }
            @Override public void changedUpdate(DocumentEvent e) {}
    
            private void formatMobile(JTextField field) {
                if (isUpdating) return;
                isUpdating = true;
    
                SwingUtilities.invokeLater(() -> {
                    try {
                        int caret = field.getCaretPosition();
                        String raw = field.getText();
    
                        // Strip all non-digits
                        String digits = raw.replaceAll("\\D", "");
    
                        // Remove Philippine prefix if present
                        if (digits.startsWith("63")) {
                            digits = digits.substring(2);
                        }
    
                        // 🚫 Remove leading 0 if present
                        if (digits.startsWith("0")) {
                            digits = digits.substring(1);
                            if (caret > 0) caret--;
                        }
    
                        // Trim to 10 digits
                        if (digits.length() > 10) {
                            digits = digits.substring(0, 10);
                        }
    
                        // Format as +63 9XX-XXX-XXXX
                        StringBuilder formatted = new StringBuilder("+63 ");
                        int newCaret = 4;
    
                        for (int i = 0; i < digits.length(); i++) {
                            if (i == 3 || i == 6) {
                                formatted.append('-');
                                if (i < caret) newCaret++;
                            }
                            formatted.append(digits.charAt(i));
                            if (i < caret) newCaret++;
                        }
    
                        field.setText(formatted.toString());
                        field.setCaretPosition(Math.min(newCaret, formatted.length()));
    
                    } finally {
                        isUpdating = false;
                    }
                });
            }
        });
    }
    
    // 💳 CARD NUMBER FORMATTER (XXXX XXXX XXXX XXXX)
    public static void attachCardNumberFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean isUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { formatCard(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatCard(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void formatCard(JTextField field) {
                if (isUpdating) return;
                isUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        int caret = field.getCaretPosition();
                        String raw = field.getText();

                        // Strip all non-digits
                        String digits = raw.replaceAll("\\D", "");

                        // Trim to max 19 digits (standard max for card numbers)
                        if (digits.length() > 19) {
                            digits = digits.substring(0, 19);
                        }

                        StringBuilder formatted = new StringBuilder();
                        int newCaret = caret;
                        int digitCount = 0;

                        for (int i = 0; i < digits.length(); i++) {
                            if (i > 0 && i % 4 == 0) {
                                formatted.append(" ");
                                if (i < caret) newCaret++;
                            }
                            formatted.append(digits.charAt(i));
                            if (i < caret) newCaret++;
                            digitCount++;
                        }

                        field.setText(formatted.toString());
                        field.setCaretPosition(Math.min(newCaret, formatted.length()));

                    } finally {
                        isUpdating = false;
                    }
                });
            }
        });
    }

    // 🗓️ EXPIRY DATE FORMATTER (MM/YY)
    public static void attachExpiryDateFormatter(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            boolean isUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) { formatExpiry(field); }
            @Override public void removeUpdate(DocumentEvent e) { formatExpiry(field); }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void formatExpiry(JTextField field) {
                if (isUpdating) return;
                isUpdating = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        String raw = field.getText();
                        int caret = field.getCaretPosition();

                        // Strip all non-digit
                        String digits = raw.replaceAll("\\D", "");

                        if (digits.length() > 4) {
                            digits = digits.substring(0, 4);
                        }

                        StringBuilder formatted = new StringBuilder();
                        int newCaret = caret;

                        for (int i = 0; i < digits.length(); i++) {
                            if (i == 2) {
                                formatted.append('/');
                                if (i < caret) newCaret++;
                            }
                            formatted.append(digits.charAt(i));
                            if (i < caret) newCaret++;
                        }

                        field.setText(formatted.toString());
                        field.setCaretPosition(Math.min(newCaret, formatted.length()));

                    } finally {
                        isUpdating = false;
                    }
                });
            }
        });
    }

}
