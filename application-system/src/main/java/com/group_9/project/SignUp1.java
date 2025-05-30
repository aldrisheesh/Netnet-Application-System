package com.group_9.project;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;

import com.group_9.project.utils.BackgroundPanel;

public class SignUp1 extends JFrame {
    // Sets up the main frame
    public SignUp1() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
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

        // adds spacing
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(0)); // Active index is 0 for first step
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // personal info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(826, 60));

        // left labels for the info panel 
        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("PERSONAL INFORMATION", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Provide the necessary details to register your information with us");
        subNote.setFont(FontUtil.getOutfitFont(12f));
        subNote.setForeground(subColor);

        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);

        infoPanel.add(leftLabels, BorderLayout.WEST);
        innerContent.add(infoPanel);

        // adds horizontal separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(826, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        innerContent.add(separator);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // form panel for input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        // input fields to the form panel using RoundedComponents
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createRoundedTextField("Username"), gbc);
        gbc.gridx = 1;
        formPanel.add(createRoundedPasswordField("Password"), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createRoundedTextField("Customer Name"), gbc);
        gbc.gridx = 1;
        formPanel.add(FormComponent.createPairPanel(
            createRoundedTextField("Birthday"),
            FormComponent.createStyledComboBox("Select Gender", new String[]{"Male", "Female"})
        ), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(FormComponent.createPairPanel(
            FormComponent.createStyledComboBox("Select Civil Status", new String[]{"Single", "Married", "Divorced", "Widowed"}),
            createRoundedTextField("Nationality")
        ), gbc);

        gbc.gridx = 1;
        formPanel.add(FormComponent.createPairPanel(
            createRoundedTextField("Mobile No."),
            createRoundedTextField("Email")
        ), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createRoundedTextField("Full Mother's Maiden Name"), gbc);
        gbc.gridx = 1;
        formPanel.add(createRoundedTextField("Spouse Name (if married)"), gbc);

        innerContent.add(formPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

        // NEXT button action
        nextButton.addActionListener(e -> {
            dispose(); 
            new SignUp2(); 
        });

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    // Custom content panel with rounded corners and shadow
    private JPanel createContentPanel() { 
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20)); 
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                
                // Draw main panel
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

    // method to create a rounded text field using RoundedComponents
    private RoundedComponents.RoundedTextField createRoundedTextField(String placeholder) {
        RoundedComponents.RoundedTextField field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 35));
        return field;
    }

    // method to create a rounded password field using RoundedComponents
    private RoundedComponents.RoundedPasswordField createRoundedPasswordField(String placeholder) {
        RoundedComponents.RoundedPasswordField field = new RoundedComponents.RoundedPasswordField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 35));
        return field;
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp1::new);
    }
}