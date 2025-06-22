package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class SignUp1 extends JFrame {

    public SignUp1() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);
        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        background.add(pnlContainer);

        JPanel pnlInnerContent = new JPanel();
        pnlInnerContent.setLayout(new BoxLayout(pnlInnerContent, BoxLayout.Y_AXIS));
        pnlInnerContent.setOpaque(false);
        pnlInnerContent.setBounds(40, 40, 890, 615);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color subColor = Color.decode("#302E2E");

        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(titleColor);
        pnlInnerContent.add(lblTitle);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlStepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlStepWrapper.setOpaque(false);
        pnlStepWrapper.add(CreateStepTracker.createStepTracker(0));
        pnlInnerContent.add(pnlStepWrapper);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlInformation = new JPanel(new BorderLayout());
        pnlInformation.setOpaque(false);
        pnlInformation.setMaximumSize(new Dimension(826, 60));

        JPanel pnlLeftLabels = new JPanel();
        pnlLeftLabels.setLayout(new BoxLayout(pnlLeftLabels, BoxLayout.Y_AXIS));
        pnlLeftLabels.setOpaque(false);

        JLabel lblSubtitle = new JLabel("PERSONAL INFORMATION", SwingConstants.LEFT);
        lblSubtitle.setFont(FontUtil.getOutfitFont(16f));
        lblSubtitle.setForeground(subColor);

        JLabel lblSubnote = new JLabel("Provide the necessary details to register your information with us");
        lblSubnote.setFont(FontUtil.getInterFont(14f));
        lblSubnote.setForeground(subColor);

        pnlLeftLabels.add(lblSubtitle);
        pnlLeftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlLeftLabels.add(lblSubnote);

        pnlInformation.add(pnlLeftLabels, BorderLayout.WEST);
        pnlInnerContent.add(pnlInformation);

        JSeparator sepSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        sepSeparator.setMaximumSize(new Dimension(826, 2));
        sepSeparator.setForeground(Color.decode("#B2B2B2"));
        sepSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlInnerContent.add(sepSeparator);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        gbc.gridx = 0;
        gbc.gridy = 0;
        RoundedComponents.RoundedTextField txtUsername = createRoundedTextField("Username");
        txtUsername.setName("Username");
        ValidationUtil.addTextValidation(txtUsername, s -> !s.trim().isEmpty());
        pnlForm.add(txtUsername, gbc);
        ToolTipUtil.attachCustomTooltip(txtUsername, "Create a username");

        gbc.gridx = 1;
        RoundedComponents.RoundedPasswordField txtPassword = createRoundedPasswordField("Password");
        txtPassword.setName("Password");
        ValidationUtil.addTextValidation(txtPassword, s -> s.length() >= 8);
        pnlForm.add(txtPassword, gbc);
        ToolTipUtil.attachCustomTooltip(txtPassword, "Create a password");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedTextField txtCustomerName = createRoundedTextField("Customer Name");
        txtCustomerName.setName("CustomerName");
        ValidationUtil.addTextValidation(txtCustomerName, s -> !s.trim().isEmpty());
        pnlForm.add(txtCustomerName, gbc);
        ToolTipUtil.attachCustomTooltip(txtCustomerName, "Enter your full name");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField txtBirthday = createRoundedTextField("Birthday (MM/dd/yyyy)");
        txtBirthday.setName("Birthday");
        ValidationUtil.addTextValidation(txtBirthday, s -> {
            if (!s.matches("^\\d{2}/\\d{2}/\\d{4}$")) return false;
        
            try {
                int intMonth = Integer.parseInt(s.substring(0, 2));
                int intDay = Integer.parseInt(s.substring(3, 5));
                if (intMonth < 1 || intMonth > 12) return false;
                if (intDay < 1 || intDay > 31) return false;
        
                // Age check
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);
                Date datDob = sdf.parse(s);
                Calendar calMinAdult = Calendar.getInstance();
                calMinAdult.add(Calendar.YEAR, -18);
                return !datDob.after(calMinAdult.getTime());
        
            } catch (NumberFormatException | ParseException e) {
                return false;
            }
        });                       
        SmartFieldFormatter.attachDateFormatter(txtBirthday);

        RoundedComponents.RoundedComboBox<String> cboGender = (RoundedComponents.RoundedComboBox<String>)
                FormComponent.createStyledComboBox("Select Gender", new String[]{"Male", "Female"});
        ValidationUtil.addComboBoxValidation(cboGender);

        pnlForm.add(createPairPanel(txtBirthday, cboGender), gbc);

        ToolTipUtil.attachCustomTooltip(cboGender, "Select your gender");
        ToolTipUtil.attachCustomTooltip(txtBirthday, "Enter your birthday (MM/dd/yyyy)");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedComboBox<String> cboCivilStatus = (RoundedComponents.RoundedComboBox<String>)
                FormComponent.createStyledComboBox("Select Civil Status", new String[]{"Single", "Married", "Divorced", "Widowed"});
        ValidationUtil.addComboBoxValidation(cboCivilStatus);

        RoundedComponents.RoundedTextField txtNationality = createRoundedTextField("Nationality");
        txtNationality.setName("Nationality");
        ValidationUtil.addTextValidation(txtNationality, s -> !s.trim().isEmpty());
        pnlForm.add(createPairPanel(cboCivilStatus, txtNationality), gbc);

        ToolTipUtil.attachCustomTooltip(cboCivilStatus, "Select your civil status");
        ToolTipUtil.attachCustomTooltip(txtNationality, "Enter your nationality");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField txtMobileNo = createRoundedTextField("Mobile No.");
        txtMobileNo.setName("Mobile");
        ValidationUtil.addTextValidation(txtMobileNo, s -> s.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$"));
        ((AbstractDocument) txtMobileNo.getDocument()).setDocumentFilter(new LengthLimitFilter(17));
        SmartFieldFormatter.attachMobileFormatter(txtMobileNo);

        RoundedComponents.RoundedTextField txtEmail = createRoundedTextField("Email");
        txtEmail.setName("Email");
        ValidationUtil.addTextValidation(txtEmail, s -> s.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$"));

        pnlForm.add(createPairPanel(txtMobileNo, txtEmail), gbc);
        ToolTipUtil.attachCustomTooltip(txtMobileNo, "Enter your mobile No. (9XX-XXX-XXXX)");
        ToolTipUtil.attachCustomTooltip(txtEmail, "Enter a valid email address");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedTextField txtMaidenName = createRoundedTextField("Full Mother's Maiden Name");
        txtMaidenName.setName("MaidenName");
        ValidationUtil.addTextValidation(txtMaidenName, s -> !s.trim().isEmpty());
        pnlForm.add(txtMaidenName, gbc);
        ToolTipUtil.attachCustomTooltip(txtMaidenName, "Enter your mother's maiden name");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField txtSpouseName = createRoundedTextField("Spouse Name (if married)");
        txtSpouseName.setName("Spouse");
        pnlForm.add(txtSpouseName, gbc);
        ToolTipUtil.attachCustomTooltip(txtSpouseName, "Enter your spouse's name (if married)");

        pnlInnerContent.add(pnlForm);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setOpaque(false);
        pnlButtons.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton cmdNext = new RoundedComponents.RoundedButton("NEXT", 25);
        cmdNext.setPreferredSize(new Dimension(148, 41));
        cmdNext.setBackground(Color.decode("#2A0243"));
        cmdNext.setForeground(Color.WHITE);
        cmdNext.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdNext.setBorderColor(Color.decode("#2A0243"));

        pnlButtons.add(cmdNext);
        pnlInnerContent.add(pnlButtons);
        pnlContainer.add(pnlInnerContent);

        cmdNext.addActionListener((ActionEvent e) -> {
            List<JTextComponent> inputFields = new ArrayList<>();
            String strPassword = "", strEmail = "", strMobile = "", strBirthdate = "";
        
            for (Component comp : pnlForm.getComponents()) {
                if (comp instanceof JTextComponent) {
                    inputFields.add((JTextComponent) comp);
                } else if (comp instanceof JPanel) {
                    for (Component inner : ((JPanel) comp).getComponents()) {
                        if (inner instanceof JTextComponent) {
                            inputFields.add((JTextComponent) inner);
                        } else if (inner instanceof JPanel) {
                            for (Component sub : ((JPanel) inner).getComponents()) {
                                if (sub instanceof JTextComponent) {
                                    inputFields.add((JTextComponent) sub);
                                }
                            }
                        }
                    }
                }
            }
        
            boolean boolAllValid = true;
        
            for (JTextComponent field : inputFields) {
                String strName = field.getName();
                boolean boolOptional = strName != null && strName.equalsIgnoreCase("Spouse");
                boolean boolValid = boolOptional || !field.getText().trim().isEmpty();
        
                if (field instanceof RoundedComponents.RoundedTextField textField) {
                    textField.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
                } else if (field instanceof RoundedComponents.RoundedPasswordField pwdField) {
                    pwdField.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
                }
        
                if (!boolValid) boolAllValid = false;
            }
        
            List<RoundedComponents.RoundedComboBox<String>> comboBoxes = List.of(cboGender, cboCivilStatus);
            boolean boolComboValid = true;
        
            for (var combo : comboBoxes) {
                boolean boolValid = combo.getSelectedIndex() != -1;
                combo.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
                if (!boolValid) boolComboValid = false;
            }
        
            if (!boolAllValid || !boolComboValid) {
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Missing Information", "Please complete all required fields and selections.");
                return;
            }
        
            for (JTextComponent field : inputFields) {
                if (field.getName() == null) continue;
                switch (field.getName()) {
                    case "Password": strPassword = field.getText(); break;
                    case "Mobile": strMobile = field.getText().trim(); break;
                    case "Email": strEmail = field.getText().trim(); break;
                    case "Birthday": strBirthdate = field.getText().trim(); break;
                }
            }
            
            // recolor border if invalid before dialog
            if (strPassword.length() < 8) {
                txtPassword.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Weak Password", "Password must be at least 8 characters long.");
                return;
            }
            
            try {
                if (!strBirthdate.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    txtBirthday.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Format", "Please use the format MM/dd/yyyy.");
                    return;
                }
            
                int intMonth = Integer.parseInt(strBirthdate.substring(0, 2));
                int intDay = Integer.parseInt(strBirthdate.substring(3, 5));
                if (intMonth < 1 || intMonth > 12) {
                    txtBirthday.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "Month must be between 01 and 12.");
                    return;
                }
                if (intDay < 1 || intDay > 31) {
                    txtBirthday.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "Day must be between 01 and 31.");
                    return;
                }
            
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false); // Enforce exact match
                Date datDob = sdf.parse(strBirthdate);
            
                Calendar calParsed = Calendar.getInstance();
                calParsed.setTime(datDob);
                if ((calParsed.get(Calendar.MONTH) + 1) != intMonth || calParsed.get(Calendar.DAY_OF_MONTH) != intDay) {
                    txtBirthday.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "The date you entered does not exist.");
                    return;
                }
            
                Calendar calMinAdult = Calendar.getInstance();
                calMinAdult.add(Calendar.YEAR, -18);
                if (datDob.after(calMinAdult.getTime())) {
                    txtBirthday.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Underage", "You must be at least 18 years old to register.");
                    return;
                }
            
            } catch (ParseException ex) {
                txtBirthday.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Birthdate", "Please enter a valid date in MM/dd/yyyy format.");
                return;
            }
            

            if (!strMobile.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$")) {
                txtMobileNo.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Mobile Number", "Mobile number must be in the format +63 9XX-XXX-XXXX.");
                return;
            }
        
            if (!strEmail.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$")) {
                txtEmail.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Email", "Please enter a valid email address.");
                return;
            }
        
            // everything passed, proceed and store data
            UserApplicationData.set("Username", txtUsername.getText().trim());
            UserApplicationData.set("Password", txtPassword.getText());
            UserApplicationData.set("CustomerName", txtCustomerName.getText().trim());
            UserApplicationData.set("Birthday", txtBirthday.getText().trim());
            UserApplicationData.set("Gender", (String) cboGender.getSelectedItem());
            UserApplicationData.set("CivilStatus", (String) cboCivilStatus.getSelectedItem());
            UserApplicationData.set("Nationality", txtNationality.getText().trim());
            UserApplicationData.set("Mobile", txtMobileNo.getText().trim());
            UserApplicationData.set("Email", txtEmail.getText().trim());
            UserApplicationData.set("MaidenName", txtMaidenName.getText().trim());
            UserApplicationData.set("Spouse", txtSpouseName.getText().trim());
        
            new SignUp2();
            dispose();
        });
        

        // Restore Data
        txtUsername.setText(UserApplicationData.get("Username"));
        txtPassword.setText(UserApplicationData.get("Password"));
        txtCustomerName.setText(UserApplicationData.get("CustomerName"));
        txtBirthday.setText(UserApplicationData.get("Birthday"));
        cboGender.setSelectedItem(UserApplicationData.get("Gender"));
        cboCivilStatus.setSelectedItem(UserApplicationData.get("CivilStatus"));
        txtNationality.setText(UserApplicationData.get("Nationality"));
        txtMobileNo.setText(UserApplicationData.get("Mobile"));
        txtEmail.setText(UserApplicationData.get("Email"));
        txtMaidenName.setText(UserApplicationData.get("MaidenName"));
        txtSpouseName.setText(UserApplicationData.get("Spouse"));

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }


    private RoundedComponents.RoundedTextField createRoundedTextField(String strPlaceholder) {
        RoundedComponents.RoundedTextField field = new RoundedComponents.RoundedTextField(strPlaceholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        return field;
    }

    private RoundedComponents.RoundedPasswordField createRoundedPasswordField(String strPlaceholder) {
        RoundedComponents.RoundedPasswordField field = new RoundedComponents.RoundedPasswordField(strPlaceholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        return field;
    }

    private JPanel createPairPanel(JComponent cmpLeft, JComponent cmpRight) {
        JPanel pnlPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlPanel.setOpaque(false);
        cmpLeft.setPreferredSize(new Dimension(180, 50));
        cmpRight.setPreferredSize(new Dimension(180, 50));
        JPanel pnlLeftWrapper = new JPanel(new BorderLayout());
        pnlLeftWrapper.setOpaque(false);
        pnlLeftWrapper.add(cmpLeft, BorderLayout.CENTER);
        JPanel pnlRightWrapper = new JPanel(new BorderLayout());
        pnlRightWrapper.setOpaque(false);
        pnlRightWrapper.add(cmpRight, BorderLayout.CENTER);
        pnlPanel.add(pnlLeftWrapper);
        pnlPanel.add(pnlRightWrapper);
        pnlPanel.setPreferredSize(new Dimension(375, 50));
        return pnlPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp1::new);
    }
}
