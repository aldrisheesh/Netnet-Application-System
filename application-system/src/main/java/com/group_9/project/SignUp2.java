package com.group_9.project;

import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class SignUp2 extends JFrame {
    private final List<JTextComponent> trackedFields = new ArrayList<>();
    private final List<RoundedComponents.RoundedComboBox<String>> comboBoxes = new ArrayList<>();
    private RoundedComponents.RoundedTextField residencyField, contactField, ownerField;

    public SignUp2() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);
        container.add(innerContent);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color subColor = Color.decode("#302E2E");

        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        innerContent.add(title);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(1));
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(826, 60));

        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("RESIDENTIAL INFORMATION", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Provide your current place of residence details");
        subNote.setFont(FontUtil.getOutfitFont(12f));
        subNote.setForeground(subColor);

        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);

        infoPanel.add(leftLabels, BorderLayout.WEST);
        innerContent.add(infoPanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(826, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        innerContent.add(separator);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.gridy++;
        var homeOwnershipCombo = createStyledComboBox("Home Ownership", new String[]{"Owned", "Living with Relatives", "Mortgaged", "Rented"});
        formPanel.add(homeOwnershipCombo, gbc);
        comboBoxes.add(homeOwnershipCombo);

        gbc.gridx = 1;
        var companyPaidCombo = createStyledComboBox("Company Paid?", new String[]{"Yes", "No"});
        comboBoxes.add(companyPaidCombo);

        residencyField = createTrackedField("Years of Residency");
        addValidationListener(residencyField, s -> s.matches("^\\d+$") && Integer.parseInt(s) >= 0);
        formPanel.add(createPairPanel(companyPaidCombo, residencyField), gbc);
        ToolTipUtil.attachCustomTooltip(residencyField, "Years of Residency");

        // Owner and Contact
        gbc.gridx = 0;
        gbc.gridy++;
        ownerField = createTrackedField("Name of Owner");
        formPanel.add(ownerField, gbc);
        ToolTipUtil.attachCustomTooltip(ownerField, "Name of Owner");

        gbc.gridx = 1;
        contactField = createTrackedField("Contact No.");
        addValidationListener(contactField, s -> s.matches("^\\d{10,15}$"));
        formPanel.add(contactField, gbc);
        ToolTipUtil.attachCustomTooltip(contactField, "Contact No.");

        // Address fields
        gbc.gridx = 0;
        gbc.gridy++;
        var houseField = createTrackedField("House/Room No./Floor");
        formPanel.add(houseField, gbc);
        ToolTipUtil.attachCustomTooltip(houseField, "House/Room No./Floor");

        gbc.gridx = 1;
        var buildingField = createTrackedField("Apartment/Compound/Building");
        formPanel.add(buildingField, gbc);
        ToolTipUtil.attachCustomTooltip(buildingField, "Apartment/Compound/Building");

        gbc.gridx = 0;
        gbc.gridy++;
        var subdivisionField = createTrackedField("Subdivision");
        formPanel.add(subdivisionField, gbc);
        ToolTipUtil.attachCustomTooltip(subdivisionField, "Subdivision");

        gbc.gridx = 1;
        var barangayField = createTrackedField("Barangay");
        formPanel.add(barangayField, gbc);
        ToolTipUtil.attachCustomTooltip(barangayField, "Barangay");

        gbc.gridx = 0;
        gbc.gridy++;
        var streetField = createTrackedField("Street");
        var cityField = createTrackedField("Municipality/City");
        formPanel.add(createPairPanel(streetField, cityField), gbc);
        ToolTipUtil.attachCustomTooltip(streetField, "Street");
        ToolTipUtil.attachCustomTooltip(cityField, "Municipality/City");

        gbc.gridx = 1;
        var provinceField = createTrackedField("Province");
        var zipField = createTrackedField("Zip Code");
        formPanel.add(createPairPanel(provinceField, zipField), gbc);
        ToolTipUtil.attachCustomTooltip(provinceField, "Province");
        ToolTipUtil.attachCustomTooltip(zipField, "Zip Code");

        innerContent.add(formPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        var nextButton = new RoundedComponents.RoundedButton("NEXT", 25);
        nextButton.setPreferredSize(new Dimension(148, 41));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));

        var backButton = new RoundedComponents.RoundedButton("BACK", 25);
        backButton.setPreferredSize(new Dimension(148, 41));
        backButton.setBackground(Color.decode("#FFF1FF"));
        backButton.setForeground(Color.decode("#2B0243"));
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setBorderColor(Color.decode("#2B0243"));

        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        innerContent.add(buttonPanel);

        // Button Actions
        nextButton.addActionListener((ActionEvent e) -> {
            boolean allValid = true;

            for (JTextComponent field : trackedFields) {
                boolean valid = !field.getText().trim().isEmpty();
                if (field instanceof RoundedComponents.RoundedTextField textField) {
                    textField.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                }
                if (!valid) allValid = false;
            }

            for (RoundedComponents.RoundedComboBox<String> combo : comboBoxes) {
                boolean valid = combo.getSelectedIndex() != -1;
                combo.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                if (!valid) allValid = false;
            }

            if (!residencyField.getText().matches("^\\d+$") || Integer.parseInt(residencyField.getText()) < 0) {
                residencyField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Years of residency must be non-negative.");
                return;
            }

            if (!contactField.getText().matches("^\\d{10,15}$")) {
                contactField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Contact number must be 10–15 digits.");
                return;
            }

            if (allValid) {
                new SignUp3().setVisible(true);
                dispose();
            } else {
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Incomplete", "Please complete all required fields.");
            }
        });

        backButton.addActionListener(e -> {
            new SignUp1().setVisible(true);
            dispose();
        });

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    private RoundedComponents.RoundedTextField createTrackedField(String label) {
        var field = new RoundedComponents.RoundedTextField(label, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        trackedFields.add(field);
        addValidationListener(field, s -> !s.trim().isEmpty());
        return field;
    }

    private void addValidationListener(JTextComponent field, java.util.function.Predicate<String> validator) {
        if (field instanceof RoundedComponents.RoundedTextField textField) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { validate(); }
                public void removeUpdate(DocumentEvent e) { validate(); }
                public void changedUpdate(DocumentEvent e) { validate(); }

                private void validate() {
                    boolean isValid = validator.test(field.getText().trim());
                    textField.setValidationBorderColor(isValid ? Color.GRAY : Color.RED);
                }
            });
        }
    }

    private RoundedComponents.RoundedComboBox<String> createStyledComboBox(String placeholder, String[] options) {
        var combo = (RoundedComponents.RoundedComboBox<String>) FormComponent.createStyledComboBox(placeholder, options);
        combo.setPreferredSize(new Dimension(375, 50));
        return combo;
    }

    private JPanel createPairPanel(JComponent left, JComponent right) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);

        if (left instanceof RoundedComponents.RoundedTextField lText) {
            trackedFields.add(lText);
            addValidationListener(lText, s -> !s.trim().isEmpty());
        }

        if (right instanceof RoundedComponents.RoundedTextField rText) {
            trackedFields.add(rText);
            addValidationListener(rText, s -> !s.trim().isEmpty());
        }

        left.setPreferredSize(new Dimension(180, 50));
        right.setPreferredSize(new Dimension(180, 50));

        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.setOpaque(false);
        leftWrapper.add(left, BorderLayout.CENTER);

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.add(right, BorderLayout.CENTER);

        panel.add(leftWrapper);
        panel.add(rightWrapper);
        panel.setPreferredSize(new Dimension(375, 50));
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);

                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - shadowOffset - 1, getHeight() - shadowOffset - 1, 25, 25);
                g2.dispose();
            }
        };
        content.setBounds(235, 165, 970, 695);
        content.setOpaque(false);
        return content;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp2::new);
    }
}
