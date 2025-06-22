package com.group_9.project;
import com.group_9.project.database.AccountService;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.*;

import com.group_9.project.utils.RoundedComponents.*;

public class AccountDetailsPage extends Template {
    
    private boolean boolEditMode = false;
    private JButton cmdAction;
    private List<JTextField> lstTextFields = new ArrayList<>();
    private List<JComboBox<String>> lstComboBoxes = new ArrayList<>();
    private JTextField txtPasswordField;

    public AccountDetailsPage() {
        BaseFrameSetup.applyAppIcon(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 3);

        JPanel pnlSidebar = AccountSidebarUtil.createSidebar(this, "My Details");
        pnlBackground.add(pnlSidebar);

        JPanel pnlContent = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContent.setBounds(290, 150, 1020, 720);
        pnlBackground.add(pnlContent);

        JPanel pnlDetailsContainer = createDetailsContainer();
        pnlContent.add(pnlDetailsContainer);

        populateFromSession();
        
        SwingUtilities.invokeLater(() -> {
            pnlBackground.requestFocusInWindow();
        });
    }

    private JPanel createDetailsContainer() {
        JPanel pnlDetailsContainer = new JPanel(null);
        pnlDetailsContainer.setBackground(new Color(0, 0, 0, 0));
        pnlDetailsContainer.setBounds(0, 0, 1250, 700);
        pnlDetailsContainer.setOpaque(false);
    
        JLabel lblTitle = new JLabel("MY DETAILS");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setForeground(new Color(42, 2, 67, 255));
        lblTitle.setBounds(70, 50, 300, 30);
        pnlDetailsContainer.add(lblTitle);
    
        JLabel lblSection = new JLabel("PERSONAL INFORMATION");
        lblSection.setFont(FontUtil.getOutfitFont(16f));
        lblSection.setBounds(70, 100, 300, 20);
        pnlDetailsContainer.add(lblSection);
    
        JSeparator sepSeparator = new JSeparator();
        sepSeparator.setBounds(70, 130, 880, 1);
        sepSeparator.setForeground(new Color(180, 180, 180));
        pnlDetailsContainer.add(sepSeparator);
    
        String[] arrLeftLabels = { "USERNAME", "PASSWORD", "EMAIL ADDRESS", "MOBILE NO. / TEL. NO." };
        String[] arrRightLabels = { "FULL NAME", "BIRTHDAY", "GENDER", "CIVIL STATUS", "NATIONALITY", "NAME OF SPOUSE (IF MARRIED)", "FULL MOTHER'S MAIDEN NAME" };
    
        int intYLeft = 150;
        for (int i = 0; i < arrLeftLabels.length; i++) {
            JLabel lbl = new JLabel(arrLeftLabels[i]);
            lbl.setFont(FontUtil.getOutfitBoldFont(13f));
            lbl.setForeground(new Color(42, 2, 67));
            lbl.setBounds(95, intYLeft, 200, 40);
            pnlDetailsContainer.add(lbl);
    
            JTextField txtField;
            if (arrLeftLabels[i].equals("PASSWORD")) {
                txtField = new RoundedPasswordField("Enter your password", 20);
                txtPasswordField = txtField;
            } else {
                txtField = new RoundedTextField("Enter your " + arrLeftLabels[i].toLowerCase(), 20);
            }
    
            txtField.setFont(FontUtil.getOutfitFont(15f));
            txtField.setBackground(Color.WHITE);
            txtField.setForeground(Color.BLACK);
            txtField.setCaretColor(Color.BLACK);
            txtField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            txtField.setEditable(false);
            txtField.setFocusable(false);
            lstTextFields.add(txtField);
    
            JPanel pnlWrapper = createTextFieldWrapper(txtField, 20);
            pnlWrapper.setBounds(95, intYLeft + 33, 330, 47);
            pnlDetailsContainer.add(pnlWrapper);
    
            // 🔍 Add real-time validation
            if (arrLeftLabels[i].equals("PASSWORD")) {
                ValidationUtil.addTextValidation(txtField, pnlWrapper, s -> s.length() >= 8);
            } else if (arrLeftLabels[i].equals("EMAIL ADDRESS")) {
                ValidationUtil.addTextValidation(txtField, pnlWrapper, s -> s.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$"));
            } else if (arrLeftLabels[i].equals("MOBILE NO. / TEL. NO.")) {
                ValidationUtil.addTextValidation(txtField, pnlWrapper, s -> s.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$"));
                SmartFieldFormatter.attachMobileFormatter(txtField);
            }
    
            intYLeft += 74;
            if (i == 1) intYLeft += 130;
        }
    
        JLabel contactLabel = new JLabel("Contact Information");
        contactLabel.setFont(FontUtil.getOutfitBoldFont(17f));
        contactLabel.setBounds(95, 350, 300, 20);
        pnlDetailsContainer.add(contactLabel);
    
        JLabel contactInfo = new JLabel("<html>Keep your contact info up to date so we can reach you with important updates.</html>");
        contactInfo.setFont(FontUtil.getInterFont(14f));
        contactInfo.setBounds(95, 375, 350, 40);
        pnlDetailsContainer.add(contactInfo);
    
        int intYRight = 150;
        for (int i = 0; i < arrRightLabels.length; i++) {
            String labelText = arrRightLabels[i];
            JLabel lblLabel = new JLabel(labelText);
            lblLabel.setFont(FontUtil.getOutfitBoldFont(13f));
            lblLabel.setForeground(new Color(42, 2, 67));
    
            if (labelText.equals("BIRTHDAY")) {
                lblLabel.setBounds(490, intYRight + 5, 150, 28);
                pnlDetailsContainer.add(lblLabel);
    
                JTextField txtBdayField = new RoundedTextField("MM/DD/YYYY", 15);
                txtBdayField.setFont(FontUtil.getOutfitFont(15f));
                txtBdayField.setBackground(Color.WHITE);
                txtBdayField.setForeground(Color.BLACK);
                txtBdayField.setCaretColor(Color.BLACK);
                txtBdayField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                txtBdayField.setEditable(false);
                txtBdayField.setFocusable(false);
                lstTextFields.add(txtBdayField);
    
                JPanel pnlBdayWrapper = createTextFieldWrapper(txtBdayField, 15);
                pnlBdayWrapper.setBounds(490, intYRight + 31, 210, 47);
                pnlDetailsContainer.add(pnlBdayWrapper);
    
                SmartFieldFormatter.attachDateFormatter(txtBdayField);
                ValidationUtil.addTextValidation(txtBdayField, pnlBdayWrapper, s -> {
                    if (!s.matches("^\\d{2}/\\d{2}/\\d{4}$")) return false;
                    try {
                        Date dob = new SimpleDateFormat("MM/dd/yy").parse(s);
                        Calendar minAdult = Calendar.getInstance();
                        minAdult.add(Calendar.YEAR, -18);
                        return !dob.after(minAdult.getTime());
                    } catch (Exception e) {
                        return false;
                    }
                });
    
                JLabel lblGenderLabel = new JLabel("GENDER");
                lblGenderLabel.setFont(FontUtil.getOutfitBoldFont(13f));
                lblGenderLabel.setForeground(new Color(42, 2, 67));
                lblGenderLabel.setBounds(730, intYRight + 5, 150, 28);
                pnlDetailsContainer.add(lblGenderLabel);
    
                JComboBox<String> cboGenderCombo = FormComponent.createStyledComboBox("Choose Gender", new String[]{"Male", "Female"});
                cboGenderCombo.setEnabled(false);
                lstComboBoxes.add(cboGenderCombo);
                cboGenderCombo.setBounds(730, intYRight + 30, 190, 45);
                pnlDetailsContainer.add(cboGenderCombo);
    
                intYRight += 77;
                i++;
            } else if (labelText.equals("CIVIL STATUS")) {
                lblLabel.setBounds(490, intYRight + 5, 150, 20);
                pnlDetailsContainer.add(lblLabel);
    
                JComboBox<String> cboCivilCombo = FormComponent.createStyledComboBox("Choose Civil Status", new String[]{"Single", "Married", "Separated", "Widow"});
                cboCivilCombo.setEnabled(false);
                lstComboBoxes.add(cboCivilCombo);
                cboCivilCombo.setBounds(490, intYRight + 25, 210, 45);
                pnlDetailsContainer.add(cboCivilCombo);
    
                JLabel lblNatLabel = new JLabel("NATIONALITY");
                lblNatLabel.setFont(FontUtil.getOutfitBoldFont(13f));
                lblNatLabel.setForeground(new Color(42, 2, 67));
                lblNatLabel.setBounds(730, intYRight + 5, 150, 20);
                pnlDetailsContainer.add(lblNatLabel);
    
                JTextField txtNatField = new RoundedTextField("e.g., Filipino", 25);
                txtNatField.setFont(FontUtil.getOutfitFont(15f));
                txtNatField.setBackground(Color.WHITE);
                txtNatField.setForeground(Color.BLACK);
                txtNatField.setCaretColor(Color.BLACK);
                txtNatField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                txtNatField.setEditable(false);
                txtNatField.setFocusable(false);
                lstTextFields.add(txtNatField);
    
                JPanel pnlNatWrapper = createTextFieldWrapper(txtNatField, 15);
                pnlNatWrapper.setBounds(730, intYRight + 25, 190, 47);
                pnlDetailsContainer.add(pnlNatWrapper);
    
                // Optional validation
                ValidationUtil.addTextValidation(txtNatField, pnlNatWrapper, s -> !s.trim().isEmpty());
    
                intYRight += 74;
                i++;
            } else {
                lblLabel.setBounds(490, intYRight + 10, 270, 20);
                pnlDetailsContainer.add(lblLabel);
    
                JTextField txtField = new RoundedTextField("Enter " + labelText.toLowerCase(), 20);
                txtField.setFont(FontUtil.getOutfitFont(15f));
                txtField.setBackground(Color.WHITE);
                txtField.setForeground(Color.BLACK);
                txtField.setCaretColor(Color.BLACK);
                txtField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                txtField.setEditable(false);
                txtField.setFocusable(false);
                lstTextFields.add(txtField);

                JPanel pnlWrapper = createTextFieldWrapper(txtField, 15);
                pnlWrapper.setBounds(490, intYRight + 33, 430, 47);
                pnlDetailsContainer.add(pnlWrapper);

                // Optional basic validation
                ValidationUtil.addTextValidation(txtField, pnlWrapper, s -> !s.trim().isEmpty());
    
                intYRight += 75;
            }
        }
    
        cmdAction = new RoundedComponents.RoundedButton("UPDATE", 20);
        cmdAction.setFont(FontUtil.getOutfitBoldFont(18f));
        cmdAction.setBounds(740, 590, 150, 45);
        styleButton(cmdAction);
    
        cmdAction.addActionListener(e -> {
            if (!boolEditMode) {
                boolEditMode = true;
                cmdAction.setText("SAVE CHANGES");
                enableEditing(true);
            } else {
                if (!validateFields()) {
                    CustomDialogUtil.showStyledErrorDialog(this, "Validation Error", "Please correct the highlighted fields before saving.");
                    return;
                }
                
                saveChanges();

                boolEditMode = false;
                cmdAction.setText("UPDATE");
                enableEditing(false);
            }
        });

        pnlDetailsContainer.add(cmdAction);
        return pnlDetailsContainer;

    }

    private void enableEditing(boolean enabled) {
        for (JTextField field : lstTextFields) {
            field.setEditable(enabled);
            field.setFocusable(enabled); 
            field.setBackground(Color.WHITE);
        }
        for (JComboBox<String> combo : lstComboBoxes) {
            combo.setEnabled(enabled);
        }
    }

    private boolean validateFields() {
        boolean allValid = true;
    
        for (JTextField txtField : lstTextFields) {
            if (!txtField.isEditable()) continue;
    
            String strNewValue = txtField.getText().trim();
            JComponent cmpWrapper = (JComponent) txtField.getParent();
    
            Predicate<String> validator = (Predicate<String>) cmpWrapper.getClientProperty("validator");
            if (validator != null) {
                boolean boolValid = validator.test(strNewValue);
                cmpWrapper.putClientProperty("validationColor", boolValid ? Color.GRAY : Color.RED);
                cmpWrapper.repaint();
                if (!boolValid) allValid = false;
            }
        }
    
        for (JComboBox<String> cboCombo : lstComboBoxes) {
            if (!cboCombo.isEnabled()) continue; 
    
            if (cboCombo instanceof RoundedComponents.RoundedComboBox<String> styledCombo) {
                boolean boolValid = styledCombo.getSelectedIndex() != -1;
                styledCombo.setValidationBorderColor(boolValid ? Color.GRAY : Color.RED);
                if (!boolValid) allValid = false;
            }
        }
    
        return allValid;
    }    

    private void saveChanges() {
        try {
            // 1️⃣ Gather all the new values from your UI:
            String strOrigUsername   = UserApplicationData.get("Username");
            String strNewPassword    = txtPasswordField.getText().trim();
            String strNewEmail       = lstTextFields.get(2).getText().trim();
            String strNewMobile      = lstTextFields.get(3).getText().trim();
    
            String strNewFullName    = lstTextFields.get(4).getText().trim();
            // parse into a java.util.Date
            java.util.Date utilBirth = new SimpleDateFormat("MM/dd/yyyy")
                                           .parse(lstTextFields.get(5).getText().trim());
            // convert into java.sql.Date for JDBC
            java.sql.Date sqlBirth = new java.sql.Date(utilBirth.getTime());
    
            String strNewGender      = (String) lstComboBoxes.get(0).getSelectedItem();
            String strNewCivilStatus = (String) lstComboBoxes.get(1).getSelectedItem();
            String strNewNationality = lstTextFields.get(6).getText().trim();
            String strNewSpouse      = lstTextFields.get(7).getText().trim();
            String strNewMotherMn    = lstTextFields.get(8).getText().trim();
    
            // 2️⃣ Update the DB (note sqlBirth)
            AccountService.updateCustomerInfoByUsername(
                strOrigUsername,
                strNewPassword,
                strNewFullName,
                sqlBirth,   
                strNewGender,
                strNewCivilStatus,
                strNewMotherMn,
                strNewSpouse,
                strNewNationality,
                strNewMobile,
                strNewEmail
            );
    
            // 3️⃣ Update session
            UserApplicationData.set("Password",     strNewPassword);
            UserApplicationData.set("Email",        strNewEmail);
            UserApplicationData.set("Mobile",       strNewMobile);
            UserApplicationData.set("CustomerName", strNewFullName);
            // store back in MM/dd/yyyy format
            UserApplicationData.set("Birthday",
                new SimpleDateFormat("MM/dd/yyyy").format(utilBirth));
            UserApplicationData.set("Gender",       strNewGender);
            UserApplicationData.set("CivilStatus",  strNewCivilStatus);
            UserApplicationData.set("Nationality",  strNewNationality);
            UserApplicationData.set("Spouse",       strNewSpouse);
            UserApplicationData.set("MaidenName",   strNewMotherMn);
    
            // 4️⃣ Notify user
            CustomDialogUtil.showStyledInfoDialog(
                this,
                "Success",
                "Your profile has been updated."
            );
        }
        catch (Exception ex) {
            ex.printStackTrace();
            CustomDialogUtil.showStyledErrorDialog(
                this,
                "Update Failed",
                ex.getMessage()
            );
        }
    }
    

    private JPanel createTextFieldWrapper(JTextField field, int arc) {
        JPanel pnlWrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = (Color) getClientProperty("validationColor");
                if (color == null) color = Color.GRAY;
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
            }
        };
        pnlWrapper.setLayout(new BorderLayout());
        pnlWrapper.setOpaque(false);
        pnlWrapper.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlWrapper.add(field, BorderLayout.CENTER);
        pnlWrapper.putClientProperty("validationColor", Color.GRAY);
        return pnlWrapper;
    }
      
    private void populateFromSession() {
        // LEFT SIDE:
        lstTextFields.get(0).setText(UserApplicationData.get("Username"));
        txtPasswordField        .setText(UserApplicationData.get("Password"));
        lstTextFields.get(2).setText(UserApplicationData.get("Email"));
        lstTextFields.get(3).setText(UserApplicationData.get("Mobile"));

        // RIGHT SIDE:
        lstTextFields.get(4).setText(UserApplicationData.get("CustomerName"));
        lstTextFields.get(5).setText(UserApplicationData.get("Birthday"));
        lstComboBoxes.get(0).setSelectedItem(UserApplicationData.get("Gender"));
        lstComboBoxes.get(1).setSelectedItem(UserApplicationData.get("CivilStatus"));
        lstTextFields.get(6).setText(UserApplicationData.get("Nationality"));
        String spouse = UserApplicationData.get("Spouse").trim();
        lstTextFields.get(7).setText(spouse.isEmpty() ? "N/A" : spouse);
        lstTextFields.get(8).setText(UserApplicationData.get("MaidenName"));
    }

    

    private void styleButton(JButton cmdBtn) {
        cmdBtn.setBackground(new Color(45, 2, 67));
        cmdBtn.setForeground(Color.WHITE);
        cmdBtn.setFocusPainted(false);
        cmdBtn.setFont(FontUtil.getOutfitBoldFont(14f));
        cmdBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }  

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountDetailsPage().setVisible(true));
    }
}