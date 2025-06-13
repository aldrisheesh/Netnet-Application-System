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
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);

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
        stepWrapper.add(CreateStepTracker.createStepTracker(0));
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(826, 60));

        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("PERSONAL INFORMATION", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Provide the necessary details to register your information with us");
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        RoundedComponents.RoundedTextField usernameField = createRoundedTextField("Username");
        usernameField.setName("Username");
        ValidationUtil.addTextValidation(usernameField, s -> !s.trim().isEmpty());
        formPanel.add(usernameField, gbc);
        ToolTipUtil.attachCustomTooltip(usernameField, "Create a username");

        gbc.gridx = 1;
        RoundedComponents.RoundedPasswordField passwordField = createRoundedPasswordField("Password");
        passwordField.setName("Password");
        ValidationUtil.addTextValidation(passwordField, s -> s.length() >= 8);
        formPanel.add(passwordField, gbc);
        ToolTipUtil.attachCustomTooltip(passwordField, "Create a password");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedTextField nameField = createRoundedTextField("Customer Name");
        nameField.setName("CustomerName");
        ValidationUtil.addTextValidation(nameField, s -> !s.trim().isEmpty());
        formPanel.add(nameField, gbc);
        ToolTipUtil.attachCustomTooltip(nameField, "Enter your full name");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField birthdayField = createRoundedTextField("Birthday (MM/dd/yyyy)");
        birthdayField.setName("Birthday");
        ValidationUtil.addTextValidation(birthdayField, s -> {
            if (!s.matches("^\\d{2}/\\d{2}/\\d{4}$")) return false;
        
            try {
                int month = Integer.parseInt(s.substring(0, 2));
                int day = Integer.parseInt(s.substring(3, 5));
                if (month < 1 || month > 12) return false;
                if (day < 1 || day > 31) return false;
        
                // Age check
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);
                Date dob = sdf.parse(s);
                Calendar minAdult = Calendar.getInstance();
                minAdult.add(Calendar.YEAR, -18);
                return !dob.after(minAdult.getTime());
        
            } catch (NumberFormatException | ParseException e) {
                return false;
            }
        });                       
        SmartFieldFormatter.attachDateFormatter(birthdayField);

        RoundedComponents.RoundedComboBox<String> genderCombo = (RoundedComponents.RoundedComboBox<String>)
                FormComponent.createStyledComboBox("Select Gender", new String[]{"Male", "Female"});
        ValidationUtil.addComboBoxValidation(genderCombo);

        formPanel.add(createPairPanel(birthdayField, genderCombo), gbc);

        ToolTipUtil.attachCustomTooltip(genderCombo, "Select your gender");
        ToolTipUtil.attachCustomTooltip(birthdayField, "Enter your birthday (MM/dd/yyyy)");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedComboBox<String> civilCombo = (RoundedComponents.RoundedComboBox<String>)
                FormComponent.createStyledComboBox("Select Civil Status", new String[]{"Single", "Married", "Divorced", "Widowed"});
        ValidationUtil.addComboBoxValidation(civilCombo);

        RoundedComponents.RoundedTextField nationalityField = createRoundedTextField("Nationality");
        nationalityField.setName("Nationality");
        ValidationUtil.addTextValidation(nationalityField, s -> !s.trim().isEmpty());
        formPanel.add(createPairPanel(civilCombo, nationalityField), gbc);

        ToolTipUtil.attachCustomTooltip(civilCombo, "Select your civil status");
        ToolTipUtil.attachCustomTooltip(nationalityField, "Enter your nationality");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField mobileField = createRoundedTextField("Mobile No.");
        mobileField.setName("Mobile");
        ValidationUtil.addTextValidation(mobileField, s -> s.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$"));
        ((AbstractDocument) mobileField.getDocument()).setDocumentFilter(new LengthLimitFilter(17));
        SmartFieldFormatter.attachMobileFormatter(mobileField);

        RoundedComponents.RoundedTextField emailField = createRoundedTextField("Email");
        emailField.setName("Email");
        ValidationUtil.addTextValidation(emailField, s -> s.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$"));

        formPanel.add(createPairPanel(mobileField, emailField), gbc);
        ToolTipUtil.attachCustomTooltip(mobileField, "Enter your mobile No. (9XX-XXX-XXXX)");
        ToolTipUtil.attachCustomTooltip(emailField, "Enter a valid email address");

        gbc.gridx = 0;
        gbc.gridy++;
        RoundedComponents.RoundedTextField maidenField = createRoundedTextField("Full Mother's Maiden Name");
        maidenField.setName("MaidenName");
        ValidationUtil.addTextValidation(maidenField, s -> !s.trim().isEmpty());
        formPanel.add(maidenField, gbc);
        ToolTipUtil.attachCustomTooltip(maidenField, "Enter your mother's maiden name");

        gbc.gridx = 1;
        RoundedComponents.RoundedTextField spouseField = createRoundedTextField("Spouse Name (if married)");
        spouseField.setName("Spouse");
        formPanel.add(spouseField, gbc);
        ToolTipUtil.attachCustomTooltip(spouseField, "Enter your spouse's name (if married)");

        innerContent.add(formPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton nextButton = new RoundedComponents.RoundedButton("NEXT", 25);
        nextButton.setPreferredSize(new Dimension(148, 41));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));

        buttonPanel.add(nextButton);
        innerContent.add(buttonPanel);
        container.add(innerContent);

        nextButton.addActionListener((ActionEvent e) -> {
            List<JTextComponent> inputFields = new ArrayList<>();
            String password = "", email = "", mobile = "", birthdate = "";
        
            for (Component comp : formPanel.getComponents()) {
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
        
            boolean allValid = true;
        
            for (JTextComponent field : inputFields) {
                String name = field.getName();
                boolean optional = name != null && name.equalsIgnoreCase("Spouse");
                boolean valid = optional || !field.getText().trim().isEmpty();
        
                if (field instanceof RoundedComponents.RoundedTextField textField) {
                    textField.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                } else if (field instanceof RoundedComponents.RoundedPasswordField pwdField) {
                    pwdField.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                }
        
                if (!valid) allValid = false;
            }
        
            List<RoundedComponents.RoundedComboBox<String>> comboBoxes = List.of(genderCombo, civilCombo);
            boolean comboValid = true;
        
            for (var combo : comboBoxes) {
                boolean valid = combo.getSelectedIndex() != -1;
                combo.setValidationBorderColor(valid ? Color.GRAY : Color.RED);
                if (!valid) comboValid = false;
            }
        
            if (!allValid || !comboValid) {
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Missing Information", "Please complete all required fields and selections.");
                return;
            }
        
            for (JTextComponent field : inputFields) {
                if (field.getName() == null) continue;
                switch (field.getName()) {
                    case "Password": password = field.getText(); break;
                    case "Mobile": mobile = field.getText().trim(); break;
                    case "Email": email = field.getText().trim(); break;
                    case "Birthday": birthdate = field.getText().trim(); break;
                }
            }
            
            // ✅ Recolor border if invalid before dialog
            if (password.length() < 8) {
                passwordField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Weak Password", "Password must be at least 8 characters long.");
                return;
            }
            
            try {
                if (!birthdate.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    birthdayField.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Format", "Please use the format MM/dd/yyyy.");
                    return;
                }
            
                int month = Integer.parseInt(birthdate.substring(0, 2));
                int day = Integer.parseInt(birthdate.substring(3, 5));
                if (month < 1 || month > 12) {
                    birthdayField.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "Month must be between 01 and 12.");
                    return;
                }
                if (day < 1 || day > 31) {
                    birthdayField.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "Day must be between 01 and 31.");
                    return;
                }
            
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false); // Enforce exact match
                Date dob = sdf.parse(birthdate);
            
                Calendar parsed = Calendar.getInstance();
                parsed.setTime(dob);
                if ((parsed.get(Calendar.MONTH) + 1) != month || parsed.get(Calendar.DAY_OF_MONTH) != day) {
                    birthdayField.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Invalid Birthdate", "The date you entered does not exist.");
                    return;
                }
            
                Calendar minAdult = Calendar.getInstance();
                minAdult.add(Calendar.YEAR, -18);
                if (dob.after(minAdult.getTime())) {
                    birthdayField.setValidationBorderColor(Color.RED);
                    CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                            "Underage", "You must be at least 18 years old to register.");
                    return;
                }
            
            } catch (ParseException ex) {
                birthdayField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Birthdate", "Please enter a valid date in MM/dd/yyyy format.");
                return;
            }
            

            if (!mobile.matches("^\\+63\\s9\\d{2}-\\d{3}-\\d{4}$")) {
                mobileField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Mobile Number", "Mobile number must be in the format +63 9XX-XXX-XXXX.");
                return;
            }
        
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$")) {
                emailField.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp1.this,
                        "Invalid Email", "Please enter a valid email address.");
                return;
            }
        
            // ✅ Everything passed, proceed and store data
            UserApplicationData.set("Username", usernameField.getText().trim());
            UserApplicationData.set("Password", passwordField.getText());
            UserApplicationData.set("CustomerName", nameField.getText().trim());
            UserApplicationData.set("Birthday", birthdayField.getText().trim());
            UserApplicationData.set("Gender", (String) genderCombo.getSelectedItem());
            UserApplicationData.set("CivilStatus", (String) civilCombo.getSelectedItem());
            UserApplicationData.set("Nationality", nationalityField.getText().trim());
            UserApplicationData.set("Mobile", mobileField.getText().trim());
            UserApplicationData.set("Email", emailField.getText().trim());
            UserApplicationData.set("MaidenName", maidenField.getText().trim());
            UserApplicationData.set("Spouse", spouseField.getText().trim());
        
            new SignUp2();
            dispose();
        });
        

        // Restore Data
        usernameField.setText(UserApplicationData.get("Username"));
        passwordField.setText(UserApplicationData.get("Password"));
        nameField.setText(UserApplicationData.get("CustomerName"));
        birthdayField.setText(UserApplicationData.get("Birthday"));
        genderCombo.setSelectedItem(UserApplicationData.get("Gender"));
        civilCombo.setSelectedItem(UserApplicationData.get("CivilStatus"));
        nationalityField.setText(UserApplicationData.get("Nationality"));
        mobileField.setText(UserApplicationData.get("Mobile"));
        emailField.setText(UserApplicationData.get("Email"));
        maidenField.setText(UserApplicationData.get("MaidenName"));
        spouseField.setText(UserApplicationData.get("Spouse"));

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
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

    private RoundedComponents.RoundedTextField createRoundedTextField(String placeholder) {
        RoundedComponents.RoundedTextField field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        return field;
    }

    private RoundedComponents.RoundedPasswordField createRoundedPasswordField(String placeholder) {
        RoundedComponents.RoundedPasswordField field = new RoundedComponents.RoundedPasswordField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 50));
        return field;
    }

    private JPanel createPairPanel(JComponent left, JComponent right) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp1::new);
    }
}
