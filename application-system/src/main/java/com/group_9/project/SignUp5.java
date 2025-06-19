package com.group_9.project;

import com.group_9.project.database.ApplicationService;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Predicate;

public class SignUp5 extends JFrame {

    private RoundedComponents.RoundedTextField cardholderName, cardNumber, expiryDate, cvv;
    private final List<JTextComponent> paymentFields = new ArrayList<>();
    private JRadioButton full, install;

    public SignUp5() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
        JPanel container = new RoundedComponents.RoundedShadowPanel(25, 4);
        container.setBounds(235, 165, 970, 695);
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
        stepWrapper.add(CreateStepTracker.createStepTracker(2));
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
        leftLabels.setBorder(BorderFactory.createEmptyBorder(9, 0, 0, 0)); 

        JLabel subtitle = new JLabel("SECURE YOUR PAYMENT", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Secure your application by completing the payment using your preferred method.");
        subNote.setFont(FontUtil.getInterFont(14f));
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

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowPanel.add(createPlanSummaryPanel());
        rowPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        rowPanel.add(createPaymentSectionPanel());
        innerContent.add(rowPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton backButton = new RoundedComponents.RoundedButton("BACK", 25);
        backButton.setPreferredSize(new Dimension(148, 41));
        backButton.setBackground(Color.decode("#FFF1FF"));
        backButton.setForeground(Color.decode("#2B0243"));
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setBorderColor(Color.decode("#2B0243"));

        backButton.addActionListener(e -> {
            // Save current form data
            UserApplicationData.set("cardholderName", cardholderName.getText());
            UserApplicationData.set("cardNumber", cardNumber.getText());
            UserApplicationData.set("expiryDate", expiryDate.getText());
            UserApplicationData.set("cvv", cvv.getText());
            UserApplicationData.set("paymentOption", full.isSelected() ? "full" : (install.isSelected() ? "installment" : ""));

            new SignUp3(); // Navigate back
            dispose();     // Close current form
        });

        RoundedComponents.RoundedButton confirmBtn = new RoundedComponents.RoundedButton("CONFIRM PAYMENT", 25);
        confirmBtn.setPreferredSize(new Dimension(194, 41));
        confirmBtn.setBackground(Color.decode("#623CBB"));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(FontUtil.getOutfitBoldFont(16f));
        confirmBtn.setBorderColor(Color.decode("#623CBB"));
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFocusable(false);

        confirmBtn.addActionListener((ActionEvent e) -> {
            boolean allFilled = true;
        
            for (JTextComponent field : paymentFields) {
                if (field.getText().trim().isEmpty()) {
                    allFilled = false;
                    if (field instanceof RoundedComponents.RoundedTextField tf) {
                        tf.setValidationBorderColor(Color.RED);
                    }
                } else {
                    if (field instanceof RoundedComponents.RoundedTextField tf) {
                        tf.setValidationBorderColor(Color.GRAY);
                    }
                }
            }

            if (!allFilled) {
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Missing Information", "Please fill in all required fields before proceeding.");
                return;
            }

            if (!full.isSelected() && !install.isSelected()) {
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Payment Option Required", "Please select a payment option before confirming.");
                return;
            }            

            String rawCard = cardNumber.getText().replaceAll("\\s", "");

            if (!rawCard.matches("\\d{16}")) {
                cardNumber.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid Card Number", "Card number must be exactly 16 digits.");
                return;
            }

            if (!expiryDate.getText().matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
                expiryDate.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid Expiry", "Expiry date must be valid in MM/YY format.");
                return;
            }

            String[] parts = expiryDate.getText().split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]);

            Calendar now = Calendar.getInstance();
            int currYear = now.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            if (year < currYear || (year == currYear && month < currMonth)) {
                expiryDate.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Card Expired", "The expiry date has already passed.");
                return;
            }

            if (!cvv.getText().matches("\\d{3,4}")) {
                cvv.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid CVV", "CVV must be 3 or 4 digits.");
                return;
            }

    // Set the payment option in UserApplicationData
            String paymentOption = full.isSelected() ? "full" : "installment";
            UserApplicationData.set("paymentOption", paymentOption);
    
    // Store payment details if needed (optional)
            UserApplicationData.set("card_number", rawCard);
            UserApplicationData.set("expiry_date", expiryDate.getText());
            UserApplicationData.set("cvv", cvv.getText());
            UserApplicationData.set("cardholder_name", cardholderName.getText());

    // Process the application in the database
            ApplicationService applicationService = new ApplicationService();
            boolean success = applicationService.processApplication();
    
            if (success) {
        // Show success message
                CustomDialogUtil.showStyledInfoDialog(SignUp5.this, "Success", "Application submitted successfully!");
                
        // Navigate to next page
                new SignUp6();
                dispose();
            } else {
        // Show error message
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Error", "Failed to process application. Please try again.");
            }
        });

        confirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(Color.decode("#4B278F"));
                confirmBtn.setBorderColor(Color.decode("#4B278F"));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(Color.decode("#623CBB"));
                confirmBtn.setBorderColor(Color.decode("#623CBB"));
            }
        });

        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(confirmBtn, BorderLayout.EAST);
        innerContent.add(buttonPanel);

        container.add(innerContent);
        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());

        String savedName = UserApplicationData.get("cardholderName");
        if (savedName != null) cardholderName.setText(savedName);

        String savedCard = UserApplicationData.get("cardNumber");
        if (savedCard != null) cardNumber.setText(savedCard);

        String savedExpiry = UserApplicationData.get("expiryDate");
        if (savedExpiry != null) expiryDate.setText(savedExpiry);

        String savedCVV = UserApplicationData.get("cvv");
        if (savedCVV != null) cvv.setText(savedCVV);

        String paymentOption = UserApplicationData.get("paymentOption");
        if ("full".equals(paymentOption)) full.setSelected(true);
        else if ("installment".equals(paymentOption)) install.setSelected(true);
    }


    private JPanel createPlanSummaryPanel() {
        Color txtColor = Color.decode("#1E1E1E");
    
        JPanel parent = new JPanel();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setOpaque(false);
        parent.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0));
    
        JLabel summaryTitle = new JLabel("Your Plan Summary");
        summaryTitle.setFont(FontUtil.getOutfitFont(15f));
        summaryTitle.setForeground(txtColor);
        parent.add(summaryTitle);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        // Create rounded summary content panel
        RoundedPanel summaryContent = new RoundedPanel(20);
        summaryContent.setLayout(new BoxLayout(summaryContent, BoxLayout.Y_AXIS));
        summaryContent.setBackground(Color.WHITE);
        summaryContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Header
        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setOpaque(false);
        header.add(new JLabel("Product and Service") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
        }});
        header.add(new JLabel("Amount") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
            setHorizontalAlignment(SwingConstants.RIGHT);
        }});
        summaryContent.add(header);
        summaryContent.add(Box.createRigidArea(new Dimension(0, 10)));
    
        summaryContent.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setMaximumSize(new Dimension(331, 2));
            setForeground(Color.decode("#B2B2B2"));
        }});
        summaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        // Load selected plans
        String savedPlans = UserApplicationData.get("selectedPlans");
        if (savedPlans != null && !savedPlans.isEmpty()) {
            String[] plans = savedPlans.split(",");
            for (int i = 0; i < plans.length; i++) {
                String plan = plans[i].trim();
    
                summaryContent.add(new GridPanel(plan, "") {{
                    ((JLabel) getComponent(0)).setFont(FontUtil.getOutfitBoldFont(18f));
                    ((JLabel) getComponent(0)).setForeground(Color.decode("#2B0243"));
                    ((JLabel) getComponent(1)).setText("");
                }});
                summaryContent.add(new GridPanel("Monthly Service Fee", getPriceForPlan(plan)));
                summaryContent.add(new GridPanel("Installation Fee", getInstallationFeeForPlan(plan)));
    
                if (i < plans.length - 1) {
                    summaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
                }
            }
        }
    
        // ScrollPane Setup
        JScrollPane scrollPane = new JScrollPane(summaryContent);
        scrollPane.setPreferredSize(new Dimension(375, 210));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // start with hidden
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(Color.WHITE);
    
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());
        verticalScrollBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setVisible(false); // HIDE by default
    
        // Show on hover, hide on exit
        scrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                verticalScrollBar.setVisible(true);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                verticalScrollBar.setVisible(false);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });
    
        // Rounded outer wrapper
        RoundedScrollContainer roundedWrapper = new RoundedScrollContainer(scrollPane, 20);
        roundedWrapper.setPreferredSize(new Dimension(375, 210));
        roundedWrapper.setBackground(Color.WHITE);
        
        parent.add(roundedWrapper);        
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        parent.add(new JLabel("*Full Payment means paying the installation fee upfront.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(txtColor);
        }});
        parent.add(new JLabel("MSF is billed separately every month.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(txtColor);
        }});
    
        return parent;
    }
    

    private static class GridPanel extends JPanel {
        public GridPanel(String left, String right) {
            super(new GridLayout(1, 2));
            setOpaque(false);
            JLabel l = new JLabel(left);
            JLabel r = new JLabel(right);
            l.setFont(FontUtil.getInterFont(16f));
            r.setFont(FontUtil.getInterFont(16f));
            l.setForeground(Color.decode("#1E1E1E"));
            r.setForeground(Color.decode("#1E1E1E"));
            r.setHorizontalAlignment(SwingConstants.RIGHT);
            add(l);
            add(r);
        }
    }




    private String getPriceForPlan(String planName) {
        return switch (planName.toUpperCase()) {
            case "FIBERX 1500" -> "₱1500";
            case "FIBERX 2500" -> "₱2500";
            case "FIBERX 3500" -> "₱3500";
            case "FIBER XTREAM 4500" -> "₱4500";
            case "FIBER XTREAM 7000" -> "₱7000";
            default -> "₱0";
        };
    }
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 0));
        paymentPanel.setMaximumSize(new Dimension(400, 450));

        JLabel paymentTitle = new JLabel("Payment Section");
        paymentTitle.setFont(FontUtil.getOutfitFont(15f));
        paymentTitle.setForeground(txtColor);
        paymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(paymentTitle);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        ButtonGroup paymentGroup = new ButtonGroup();
        ImageIcon iconOff = new ImageIcon(getClass().getResource("/icons/radio_off.png"));
        ImageIcon iconOn = new ImageIcon(getClass().getResource("/icons/radio_on.png"));
        int iconSize = 18;
        iconOff = new ImageIcon(iconOff.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        iconOn = new ImageIcon(iconOn.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        full = new JRadioButton("Full Payment");
        full.setFont(FontUtil.getInterFont(15f));
        full.setForeground(txtColor);
        full.setOpaque(false);
        full.setFocusPainted(false);
        full.setContentAreaFilled(false);
        full.setBorderPainted(false);
        full.setIcon(iconOff);
        full.setSelectedIcon(iconOn);

        install = new JRadioButton("Installment");
        install.setFont(FontUtil.getInterFont(15f));
        install.setForeground(txtColor);
        install.setOpaque(false);
        install.setFocusPainted(false);
        install.setContentAreaFilled(false);
        install.setBorderPainted(false);
        install.setIcon(iconOff);
        install.setSelectedIcon(iconOn);

        paymentGroup.add(full);
        paymentGroup.add(install);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setOpaque(false);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.add(full);
        radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        radioPanel.add(install);

        paymentPanel.add(radioPanel);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Cardholder Name
        cardholderName = createValidatedField("Cardholder Name", s -> !s.trim().isEmpty());
        ToolTipUtil.attachCustomTooltip(cardholderName, "Enter name as shown on card");
        cardholderName.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(cardholderName);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Card Number
        cardNumber = createValidatedField("Card Number", s -> s.matches("(\\d{4} ){3}\\d{4}"));
        SmartFieldFormatter.attachCardNumberFormatter(cardNumber);
        ((AbstractDocument) cardNumber.getDocument()).setDocumentFilter(new LengthLimitFilter(19));
        ToolTipUtil.attachCustomTooltip(cardNumber, "Enter 16-digit card number");        
        cardNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(cardNumber);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Expiry & CVV Panel
        JPanel expCvvPanel = new JPanel();
        expCvvPanel.setLayout(new BoxLayout(expCvvPanel, BoxLayout.X_AXIS));
        expCvvPanel.setOpaque(false);
        expCvvPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        expiryDate = createValidatedField("MM/YY", s -> {
            if (!s.matches("^(0[1-9]|1[0-2])/\\d{2}$")) return false;
            try {
                String[] parts = s.split("/");
                int month = Integer.parseInt(parts[0]);
                int year = 2000 + Integer.parseInt(parts[1]);
        
                Calendar now = Calendar.getInstance();
                int currYear = now.get(Calendar.YEAR);
                int currMonth = now.get(Calendar.MONTH) + 1;
        
                return !(year < currYear || (year == currYear && month < currMonth));
            } catch (Exception e) {
                return false;
            }
        });        
        SmartFieldFormatter.attachExpiryDateFormatter(expiryDate);
        ((AbstractDocument) expiryDate.getDocument()).setDocumentFilter(new LengthLimitFilter(5));
        ToolTipUtil.attachCustomTooltip(expiryDate, "Enter expiry date (MM/YY)");        
        expiryDate.setMaximumSize(new Dimension(100, 38));
        expiryDate.setPreferredSize(new Dimension(100, 38));
        expCvvPanel.add(expiryDate);
        expCvvPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        cvv = createValidatedField("CVV", s -> s.matches("\\d{3,4}"));
        ((AbstractDocument) cvv.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        ToolTipUtil.attachCustomTooltip(cvv, "Enter CVV (3-digit or 4-digit)");
        cvv.setMaximumSize(new Dimension(80, 38));
        cvv.setPreferredSize(new Dimension(80, 38));
        expCvvPanel.add(cvv);

        paymentPanel.add(expCvvPanel);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        return paymentPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp5::new);
    }
}
