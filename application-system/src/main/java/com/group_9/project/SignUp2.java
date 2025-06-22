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
    private final List<JTextComponent> lstTrackedFields = new ArrayList<>();
    private final List<RoundedComponents.RoundedComboBox<String>> lstComboBoxes = new ArrayList<>();

    private RoundedComponents.RoundedTextField txtResidency, txtContact, txtOwner;
    private RoundedComponents.RoundedTextField txtHouse, txtBuilding, txtSubdivision, txtBarangay;
    private RoundedComponents.RoundedTextField txtStreet, txtCity, txtProvince, txtZip;
    private RoundedComponents.RoundedComboBox<String> cboHomeOwnership, cboCompanyPaid;

    public SignUp2() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);
        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        pnlBackground.add(pnlContainer);

        JPanel pnlInnerContent = new JPanel();
        pnlInnerContent.setLayout(new BoxLayout(pnlInnerContent, BoxLayout.Y_AXIS));
        pnlInnerContent.setOpaque(false);
        pnlInnerContent.setBounds(40, 40, 890, 615);
        pnlContainer.add(pnlInnerContent);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color clrTitle = Color.decode("#2B0243");
        Color clrSub = Color.decode("#302E2E");

        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(clrTitle);
        pnlInnerContent.add(lblTitle);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlStepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlStepWrapper.setOpaque(false);
        pnlStepWrapper.add(CreateStepTracker.createStepTracker(1));
        pnlInnerContent.add(pnlStepWrapper);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.setOpaque(false);
        pnlInfo.setMaximumSize(new Dimension(826, 60));

        JPanel pnlLeftLabels = new JPanel();
        pnlLeftLabels.setLayout(new BoxLayout(pnlLeftLabels, BoxLayout.Y_AXIS));
        pnlLeftLabels.setOpaque(false);

        JLabel lblSubtitle = new JLabel("RESIDENTIAL INFORMATION", SwingConstants.LEFT);
        lblSubtitle.setFont(FontUtil.getOutfitFont(16f));
        lblSubtitle.setForeground(clrSub);

        JLabel lblSubNote = new JLabel("Provide your current place of residence details");
        lblSubNote.setFont(FontUtil.getInterFont(14f));
        lblSubNote.setForeground(clrSub);

        pnlLeftLabels.add(lblSubtitle);
        pnlLeftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlLeftLabels.add(lblSubNote);

        pnlInfo.add(pnlLeftLabels, BorderLayout.WEST);
        pnlInnerContent.add(pnlInfo);

        JSeparator sepMain = new JSeparator(SwingConstants.HORIZONTAL);
        sepMain.setMaximumSize(new Dimension(826, 2));
        sepMain.setForeground(Color.decode("#B2B2B2"));
        sepMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlInnerContent.add(sepMain);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.gridy++;
        cboHomeOwnership = createStyledComboBox("Home Ownership", new String[]{"Owned", "Living with Relatives", "Mortgaged", "Rented"});
        pnlForm.add(cboHomeOwnership, gbc);
        lstComboBoxes.add(cboHomeOwnership);
        ValidationUtil.addComboBoxValidation(cboHomeOwnership);
        ToolTipUtil.attachCustomTooltip(cboHomeOwnership, "Select home ownership status");

        gbc.gridx = 1;
        cboCompanyPaid = createStyledComboBox("Company Paid?", new String[]{"Yes", "No"});
        lstComboBoxes.add(cboCompanyPaid);
        ValidationUtil.addComboBoxValidation(cboCompanyPaid);
        ToolTipUtil.attachCustomTooltip(cboCompanyPaid, "Select if your residence is company-paid");

        txtResidency = createTrackedField("Years of Residency", s -> s.matches("^\\d{1,3}$"));
        ((AbstractDocument) txtResidency.getDocument()).setDocumentFilter(new LengthLimitFilter(3));
        pnlForm.add(createPairPanel(cboCompanyPaid, txtResidency), gbc);
        ToolTipUtil.attachCustomTooltip(txtResidency, "Enter years of stay at current address");

        gbc.gridx = 0;
        gbc.gridy++;
        txtOwner = createTrackedField("Name of Owner", s -> !s.trim().isEmpty());
        pnlForm.add(txtOwner, gbc);
        ToolTipUtil.attachCustomTooltip(txtOwner, "Enter the property owner's full name");

        gbc.gridx = 1;
        txtContact = createTrackedField("Contact No.", s -> s.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$"));
        ((AbstractDocument) txtContact.getDocument()).setDocumentFilter(new LengthLimitFilter(17));
        SmartFieldFormatter.attachMobileFormatter(txtContact);
        pnlForm.add(txtContact, gbc);
        ToolTipUtil.attachCustomTooltip(txtContact, "Enter owner's contact number (+63 9XX-XXX-XXXX)");

        gbc.gridx = 0;
        gbc.gridy++;
        txtHouse = createTrackedField("House/Room No./Floor", s -> !s.trim().isEmpty());
        pnlForm.add(txtHouse, gbc);
        ToolTipUtil.attachCustomTooltip(txtHouse, "Enter house/room/floor number");

        gbc.gridx = 1;
        txtBuilding = createTrackedField("Apartment/Compound/Building", s -> !s.trim().isEmpty());
        pnlForm.add(txtBuilding, gbc);
        ToolTipUtil.attachCustomTooltip(txtBuilding, "Enter apartment/compound/building name");

        gbc.gridx = 0;
        gbc.gridy++;
        txtSubdivision = createTrackedField("Subdivision", s -> !s.trim().isEmpty());
        pnlForm.add(txtSubdivision, gbc);
        ToolTipUtil.attachCustomTooltip(txtSubdivision, "Enter subdivision name");

        gbc.gridx = 1;
        txtBarangay = createTrackedField("Barangay", s -> !s.trim().isEmpty());
        pnlForm.add(txtBarangay, gbc);
        ToolTipUtil.attachCustomTooltip(txtBarangay, "Enter barangay");

        gbc.gridx = 0;
        gbc.gridy++;
        txtStreet = createTrackedField("Street", s -> !s.trim().isEmpty());
        txtCity = createTrackedField("Municipality/City", s -> !s.trim().isEmpty());
        pnlForm.add(createPairPanel(txtStreet, txtCity), gbc);
        ToolTipUtil.attachCustomTooltip(txtStreet, "Enter street name");
        ToolTipUtil.attachCustomTooltip(txtCity, "Enter municipality or city");

        gbc.gridx = 1;
        txtProvince = createTrackedField("Province", s -> !s.trim().isEmpty());
        txtZip = createTrackedField("Zip Code", s -> s.matches("^\\d{4}$"));
        ((AbstractDocument) txtZip.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        pnlForm.add(createPairPanel(txtProvince, txtZip), gbc);
        ToolTipUtil.attachCustomTooltip(txtProvince, "Enter province");
        ToolTipUtil.attachCustomTooltip(txtZip, "Enter 4-digit ZIP code (e.g., 1012)");

        pnlInnerContent.add(pnlForm);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlButton = new JPanel(new BorderLayout());
        pnlButton.setOpaque(false);
        pnlButton.setMaximumSize(new Dimension(826, 50));

        var cmdNext = new RoundedComponents.RoundedButton("NEXT", 25);
        cmdNext.setPreferredSize(new Dimension(148, 41));
        cmdNext.setBackground(Color.decode("#2A0243"));
        cmdNext.setForeground(Color.WHITE);
        cmdNext.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdNext.setBorderColor(Color.decode("#2A0243"));

        var cmdBack = new RoundedComponents.RoundedButton("BACK", 25);
        cmdBack.setPreferredSize(new Dimension(148, 41));
        cmdBack.setBackground(Color.decode("#FFF1FF"));
        cmdBack.setForeground(Color.decode("#2B0243"));
        cmdBack.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdBack.setBorderColor(Color.decode("#2B0243"));

        pnlButton.add(cmdBack, BorderLayout.WEST);
        pnlButton.add(cmdNext, BorderLayout.EAST);
        getRootPane().setDefaultButton(cmdNext);
        pnlInnerContent.add(pnlButton);

        cmdNext.addActionListener((ActionEvent e) -> {
            boolean boolAllFilled = true;

            for (JTextComponent txtField : lstTrackedFields) {
                boolean boolIsFilled = !txtField.getText().trim().isEmpty();
                if (txtField instanceof RoundedComponents.RoundedTextField txtTextField) {
                    txtTextField.setValidationBorderColor(boolIsFilled ? Color.GRAY : Color.RED);
                }
                if (!boolIsFilled) boolAllFilled = false;
            }

            for (RoundedComponents.RoundedComboBox<String> cboCombo : lstComboBoxes) {
                boolean boolValid = cboCombo.getSelectedIndex() != -1;
                cboCombo.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
                if (!boolValid) boolAllFilled = false;
            }

            if (!boolAllFilled) {
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Missing Information", "Please fill in all required fields before proceeding.");
                return;
            }

            if (!txtResidency.getText().matches("^\\d{1,3}$")) {
                txtResidency.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Years of residency must be 1 to 3 digits.");
                return;
            }

            if (!txtContact.getText().matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$")) {
                txtContact.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this,
                    "Invalid", "Contact number must be in the format +63 9XX-XXX-XXXX.");
                return;
            }            

            if (!txtZip.getText().matches("^\\d{4}$")) {
                txtZip.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp2.this, "Invalid", "Zip Code must be exactly 4 digits.");
                return;
            }

            UserApplicationData.set("strHomeOwnership", (String) cboHomeOwnership.getSelectedItem());
            UserApplicationData.set("strCompanyPaid", (String) cboCompanyPaid.getSelectedItem());
            UserApplicationData.set("intYearsOfResidency", txtResidency.getText());
            UserApplicationData.set("strNameOfOwner", txtOwner.getText());
            UserApplicationData.set("strContactNumber", txtContact.getText());
            String strResidenceAddress = txtHouse.getText() + ", " +
                          txtBuilding.getText() + ", " +
                          txtSubdivision.getText() + ", " +
                          txtBarangay.getText() + ", " +
                          txtStreet.getText() + ", " +
                          txtCity.getText() + ", " +
                          txtProvince.getText() + ", " +
                          txtZip.getText();

            UserApplicationData.set("strResidenceAddress", strResidenceAddress);
            new SignUp3();
            dispose();
        });

        cmdBack.addActionListener(e -> {
            UserApplicationData.set("strHomeOwnership", (String) cboHomeOwnership.getSelectedItem());
            UserApplicationData.set("strCompanyPaid", (String) cboCompanyPaid.getSelectedItem());
            UserApplicationData.set("intYearsOfResidency", txtResidency.getText());
            UserApplicationData.set("strNameOfOwner", txtOwner.getText());
            UserApplicationData.set("strContactNumber", txtContact.getText());
            String strResidenceAddress = txtHouse.getText() + ", " +
                          txtBuilding.getText() + ", " +
                          txtSubdivision.getText() + ", " +
                          txtBarangay.getText() + ", " +
                          txtStreet.getText() + ", " +
                          txtCity.getText() + ", " +
                          txtProvince.getText() + ", " +
                          txtZip.getText();

            UserApplicationData.set("strResidenceAddress", strResidenceAddress);

            new SignUp1();
            dispose();
        });

        // Restore
        cboHomeOwnership.setSelectedItem(UserApplicationData.get("strHomeOwnership"));
        cboCompanyPaid.setSelectedItem(UserApplicationData.get("strCompanyPaid"));
        txtResidency.setText(UserApplicationData.get("intYearsOfResidency"));
        txtOwner.setText(UserApplicationData.get("strNameOfOwner"));
        txtContact.setText(UserApplicationData.get("strContactNumber"));
        String strResidenceAddress = UserApplicationData.get("strResidenceAddress");
        if (strResidenceAddress != null) {
            String[] arrParts = strResidenceAddress.split(",\\s*");
            if (arrParts.length >= 8) {
                txtHouse.setText(arrParts[0]);
                txtBuilding.setText(arrParts[1]);
                txtSubdivision.setText(arrParts[2]);
                txtBarangay.setText(arrParts[3]);
                txtStreet.setText(arrParts[4]);
                txtCity.setText(arrParts[5]);
                txtProvince.setText(arrParts[6]);
                txtZip.setText(arrParts[7]);
            }
        }

        setVisible(true);
        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());
    }

    private RoundedComponents.RoundedTextField createTrackedField(String strLabel, Predicate<String> prdValidator) {
        var txtField = new RoundedComponents.RoundedTextField(strLabel, 15);
        txtField.setFont(FontUtil.getOutfitFont(15f));
        txtField.setPreferredSize(new Dimension(375, 50));
        lstTrackedFields.add(txtField);
        ValidationUtil.addTextValidation(txtField, prdValidator);
        return txtField;
    }

    private RoundedComponents.RoundedComboBox<String> createStyledComboBox(String strPlaceholder, String[] arrOptions) {
        var cboCombo = (RoundedComponents.RoundedComboBox<String>) FormComponent.createStyledComboBox(strPlaceholder, arrOptions);
        cboCombo.setPreferredSize(new Dimension(375, 50));
        return cboCombo;
    }

    private JPanel createPairPanel(JComponent cmpLeft, JComponent cmpRight) {
        JPanel pnlPair = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlPair.setOpaque(false);

        if (cmpLeft instanceof RoundedComponents.RoundedTextField txtLeft) {
            lstTrackedFields.add(txtLeft);
            ValidationUtil.addTextValidation(txtLeft, s -> !s.trim().isEmpty());
        }

        if (cmpRight instanceof RoundedComponents.RoundedTextField txtRight) {
            lstTrackedFields.add(txtRight);
            ValidationUtil.addTextValidation(txtRight, s -> !s.trim().isEmpty());
        }

        cmpLeft.setPreferredSize(new Dimension(180, 50));
        cmpRight.setPreferredSize(new Dimension(180, 50));

        JPanel pnlLeftWrapper = new JPanel(new BorderLayout());
        pnlLeftWrapper.setOpaque(false);
        pnlLeftWrapper.add(cmpLeft, BorderLayout.CENTER);

        JPanel pnlRightWrapper = new JPanel(new BorderLayout());
        pnlRightWrapper.setOpaque(false);
        pnlRightWrapper.add(cmpRight, BorderLayout.CENTER);

        pnlPair.add(pnlLeftWrapper);
        pnlPair.add(pnlRightWrapper);
        pnlPair.setPreferredSize(new Dimension(375, 50));
        return pnlPair;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp2::new);
    }
}