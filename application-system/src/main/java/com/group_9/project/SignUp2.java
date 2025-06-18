package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SignUp2 extends JFrame {
    private final List<JTextComponent> trackedFields = new ArrayList<>();
    private final List<RoundedComponents.RoundedComboBox<String>> comboBoxes = new ArrayList<>();

    private RoundedComponents.RoundedTextField residencyField, contactField, ownerField;
    private RoundedComponents.RoundedTextField houseField, buildingField, subdivisionField, barangayField;
    private RoundedComponents.RoundedTextField streetField, cityField, provinceField, zipField;
    private RoundedComponents.RoundedComboBox<String> homeOwnershipCombo, companyPaidCombo;

    public SignUp2() {
        BaseFrameSetup.applyAppIcon(this);
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
        subNote.setFont(FontUtil.getInterFont(14f));
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
        homeOwnershipCombo = createStyledComboBox("Home Ownership", new String[]{"Owned", "Living with Relatives", "Mortgaged", "Rented"});
        formPanel.add(homeOwnershipCombo, gbc);
        comboBoxes.add(homeOwnershipCombo);
        ValidationUtil.addComboBoxValidation(homeOwnershipCombo);
        ToolTipUtil.attachCustomTooltip(homeOwnershipCombo, "Select home ownership status");

        gbc.gridx = 1;
        companyPaidCombo = createStyledComboBox("Company Paid?", new String[]{"Yes", "No"});
        comboBoxes.add(companyPaidCombo);
        ValidationUtil.addComboBoxValidation(companyPaidCombo);
        ToolTipUtil.attachCustomTooltip(companyPaidCombo, "Select if your residence is company-paid");

        residencyField = createTrackedField("Years of Residency", s -> s.matches("^\\d{1,3}$"));
        ((AbstractDocument) residencyField.getDocument()).setDocumentFilter(new LengthLimitFilter(3));
        formPanel.add(createPairPanel(companyPaidCombo, residencyField), gbc);
        ToolTipUtil.attachCustomTooltip(residencyField, "Enter years of stay at current address");

        gbc.gridx = 0;
        gbc.gridy++;
        ownerField = createTrackedField("Name of Owner", s -> !s.trim().isEmpty());
        formPanel.add(ownerField, gbc);
        ToolTipUtil.attachCustomTooltip(ownerField, "Enter the property owner's full name");

        gbc.gridx = 1;
        contactField = createTrackedField("Contact No.", s -> s.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$"));
        ((AbstractDocument) contactField.getDocument()).setDocumentFilter(new LengthLimitFilter(17));
        SmartFieldFormatter.attachMobileFormatter(contactField);
        formPanel.add(contactField, gbc);
        ToolTipUtil.attachCustomTooltip(contactField, "Enter owner's contact number (+63 9XX-XXX-XXXX)");

        gbc.gridx = 0;
        gbc.gridy++;
        houseField = createTrackedField("House/Room No./Floor", s -> !s.trim().isEmpty());
        formPanel.add(houseField, gbc);
        ToolTipUtil.attachCustomTooltip(houseField, "Enter house/room/floor number");

        gbc.gridx = 1;
        buildingField = createTrackedField("Apartment/Compound/Building", s -> !s.trim().isEmpty());
        formPanel.add(buildingField, gbc);
        ToolTipUtil.attachCustomTooltip(buildingField, "Enter apartment/compound/building name");

        gbc.gridx = 0;
        gbc.gridy++;
        subdivisionField = createTrackedField("Subdivision", s -> !s.trim().isEmpty());
        formPanel.add(subdivisionField, gbc);
        ToolTipUtil.attachCustomTooltip(subdivisionField, "Enter subdivision name");

        gbc.gridx = 1;
        barangayField = createTrackedField("Barangay", s -> !s.trim().isEmpty());
        formPanel.add(barangayField, gbc);
        ToolTipUtil.attachCustomTooltip(barangayField, "Enter barangay");

        gbc.gridx = 0;
        gbc.gridy++;
        streetField = createTrackedField("Street", s -> !s.trim().isEmpty());
        cityField = createTrackedField("Municipality/City", s -> !s.trim().isEmpty());
        formPanel.add(createPairPanel(streetField, cityField), gbc);
        ToolTipUtil.attachCustomTooltip(streetField, "Enter street name");
        ToolTipUtil.attachCustomTooltip(cityField, "Enter municipality or city");

        gbc.gridx = 1;
        provinceField = createTrackedField("Province", s -> !s.trim().isEmpty());
        zipField = createTrackedField("Zip Code", s -> s.matches("^\\d{4}$"));
        ((AbstractDocument) zipField.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        formPanel.add(createPairPanel(provinceField, zipField), gbc);
        ToolTipUtil.attachCustomTooltip(provinceField, "Enter province");
        ToolTipUtil.attachCustomTooltip(zipField, "Enter 4-digit ZIP code (e.g., 1012)");

        innerContent.add(formPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

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

        nextButton.addActionListener((ActionEvent e) -> {
            boolean allFilled = true;

            for (JTextComponent field : trackedFields) {
                boolean isFilled = !field.getText().trim().isEmpty();
                if (field instanceof RoundedComponents.RoundedTextField textField) {
                    textField.setValidationBorderColor(isFilled ? Color.GRAY : Color.RED);
                }
                if (!isFilled) allFilled = false;
            }

            for (RoundedComponents.RoundedComboBox<String> combo : comboBoxes) {
                boolean valid = combo.getSelectedIndex() != -1;
                combo.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                if (!valid) allFilled = false;
            }

            if (!allFilled) {
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Missing Information", "Please fill in all required fields before proceeding.");
                return;
            }

            if (!residencyField.getText().matches("^\\d{1,3}$")) {
                residencyField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Years of residency must be 1 to 3 digits.");
                return;
            }

            if (!contactField.getText().matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$")) {
                contactField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this,
                    "Invalid", "Contact number must be in the format +63 9XX-XXX-XXXX.");
                return;
            }            

            if (!zipField.getText().matches("^\\d{4}$")) {
                zipField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Zip Code must be exactly 4 digits.");
                return;
            }

            UserApplicationData.set("HomeOwnership", (String) homeOwnershipCombo.getSelectedItem());
            UserApplicationData.set("CompanyPaid", (String) companyPaidCombo.getSelectedItem());
            UserApplicationData.set("YearsOfResidency", residencyField.getText());
            UserApplicationData.set("NameOfOwner", ownerField.getText());
            UserApplicationData.set("ContactNumber", contactField.getText());
            String residenceAddress = houseField.getText() + ", " +
                          buildingField.getText() + ", " +
                          subdivisionField.getText() + ", " +
                          barangayField.getText() + ", " +
                          streetField.getText() + ", " +
                          cityField.getText() + ", " +
                          provinceField.getText() + ", " +
                          zipField.getText();

            UserApplicationData.set("ResidenceAddress", residenceAddress);
            new SignUp3();
            dispose();
        });

        backButton.addActionListener(e -> {
            UserApplicationData.set("HomeOwnership", (String) homeOwnershipCombo.getSelectedItem());
            UserApplicationData.set("CompanyPaid", (String) companyPaidCombo.getSelectedItem());
            UserApplicationData.set("YearsOfResidency", residencyField.getText());
            UserApplicationData.set("NameOfOwner", ownerField.getText());
            UserApplicationData.set("ContactNumber", contactField.getText());
            String residenceAddress = houseField.getText() + ", " +
                          buildingField.getText() + ", " +
                          subdivisionField.getText() + ", " +
                          barangayField.getText() + ", " +
                          streetField.getText() + ", " +
                          cityField.getText() + ", " +
                          provinceField.getText() + ", " +
                          zipField.getText();

            UserApplicationData.set("ResidenceAddress", residenceAddress);

            new SignUp1();
            dispose();
        });

        // Restore
        homeOwnershipCombo.setSelectedItem(UserApplicationData.get("HomeOwnership"));
        companyPaidCombo.setSelectedItem(UserApplicationData.get("CompanyPaid"));
        residencyField.setText(UserApplicationData.get("YearsOfResidency"));
        ownerField.setText(UserApplicationData.get("NameOfOwner"));
        contactField.setText(UserApplicationData.get("ContactNumber"));
        String residenceAddress = UserApplicationData.get("ResidenceAddress");
        if (residenceAddress != null) {
            String[] parts = residenceAddress.split(",\\s*");
            if (parts.length >= 8) {
                houseField.setText(parts[0]);
                buildingField.setText(parts[1]);
                subdivisionField.setText(parts[2]);
                barangayField.setText(parts[3]);
                streetField.setText(parts[4]);
                cityField.setText(parts[5]);
                provinceField.setText(parts[6]);
                zipField.setText(parts[7]);
            }
        }

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    private RoundedComponents.RoundedTextField createTrackedField(String label, Predicate<String> validator) {
        var field = new RoundedComponents.RoundedTextField(label, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        trackedFields.add(field);
        ValidationUtil.addTextValidation(field, validator);
        return field;
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
            ValidationUtil.addTextValidation(lText, s -> !s.trim().isEmpty());
        }

        if (right instanceof RoundedComponents.RoundedTextField rText) {
            trackedFields.add(rText);
            ValidationUtil.addTextValidation(rText, s -> !s.trim().isEmpty());
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
