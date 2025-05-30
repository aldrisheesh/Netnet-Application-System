package com.group_9.project;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class SignUp5 extends JFrame {
    // Sets up the main frame
    public SignUp5() {
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

        JLabel subtitle = new JLabel("SECURE YOUR PAYMENT", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Secure your application by completing the payment using your preferred method.");
        subNote.setFont(FontUtil.getOutfitFont(12f));
        subNote.setForeground(subColor);

        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);

        infoPanel.add(leftLabels, BorderLayout.NORTH);
        innerContent.add(infoPanel);

        // adds horizontal separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(826, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        innerContent.add(separator);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));
 
        // Add plan and payment panels side by side
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowPanel.add(createPlanSummaryPanel());
        rowPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        rowPanel.add(createPaymentSectionPanel());
        innerContent.add(rowPanel);

        innerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout()); 
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        // NEXT button (right)
        RoundedComponents.RoundedButton nextButton = new RoundedComponents.RoundedButton("NEXT", 25);
        nextButton.setPreferredSize(new Dimension(148, 41));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));

        // BACK button (left)
        RoundedComponents.RoundedButton backButton = new RoundedComponents.RoundedButton("BACK", 25);
        backButton.setPreferredSize(new Dimension(148, 41));
        backButton.setBackground(Color.decode("#FFF1FF"));
        backButton.setForeground(Color.decode("#2B0243"));
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setBorderColor(Color.decode("#2B0243"));

        // Add buttons to the left and right edges
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);

        innerContent.add(buttonPanel);

        // NEXT button action
        nextButton.addActionListener(e -> {
            new SignUp6(); 
            dispose(); 
        });

        // BACK button action
        backButton.addActionListener(e -> {
            new SignUp3(); 
            dispose(); 
        });

        container.add(innerContent);

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
                
                // Draw shadow first (offset to bottom-right)
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

    private JPanel createPlanSummaryPanel() {

        Color txtColor = Color.decode("#1E1E1E");

        JPanel parent = new JPanel();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setOpaque(false);
        parent.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0)); // 20px left padding

        JLabel summaryTitle = new JLabel("Your Plan Summary");
        summaryTitle.setFont(FontUtil.getOutfitFont(15f));
        summaryTitle.setForeground(txtColor);
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(summaryTitle);

        parent.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedPanel summaryPanel = new RoundedPanel(20);
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0,0, 0)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        summaryPanel.setPreferredSize(new Dimension(375, 210));
        summaryPanel.setMaximumSize(new Dimension(375, 210));
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setOpaque(false);

        JLabel col1 = new JLabel("Product and Service");
        col1.setFont(FontUtil.getInterFont(16f));
        col1.setForeground(txtColor);
        JLabel col2 = new JLabel("Amount");
        col2.setFont(FontUtil.getInterFont(16f));
        col2.setForeground(txtColor);
        col2.setHorizontalAlignment(JLabel.RIGHT);

        header.add(col1);
        header.add(col2);
        summaryPanel.add(header);

        summaryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // adds horizontal separator
        JSeparator separate = new JSeparator(SwingConstants.HORIZONTAL);
        separate.setMaximumSize(new Dimension(331, 2));
        separate.setForeground(Color.decode("#B2B2B2"));
        separate.setAlignmentX(Component.CENTER_ALIGNMENT);

        summaryPanel.add(separate);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel plan = new JPanel(new GridLayout(1, 1));
        plan.setOpaque(false);

        JLabel planLabel = new JLabel("FIBERX 1500");
        planLabel.setFont(FontUtil.getOutfitBoldFont(18f));
        planLabel.setForeground(Color.decode("#2B0243"));
        planLabel.setHorizontalAlignment(SwingConstants.LEFT); // Optional: aligns text to the left

        plan.add(planLabel);
        summaryPanel.add(plan);


        JPanel msfRow = new JPanel(new GridLayout(1, 2));
        msfRow.setOpaque(false);

        JLabel msfTxt = (new JLabel("Monthly Service Fee"));
        msfTxt.setFont(FontUtil.getInterFont(16f));
        msfTxt.setForeground(txtColor);
        JLabel msfPrice = new JLabel("P 1500");
        msfPrice.setFont(FontUtil.getInterFont(16f));
        msfPrice.setForeground(txtColor);
        msfPrice.setHorizontalAlignment(JLabel.RIGHT);

        msfRow.add(msfTxt);
        msfRow.add(msfPrice);
        summaryPanel.add(msfRow);

        JPanel installRow = new JPanel(new GridLayout(1, 2));
        installRow.setOpaque(false);

        JLabel installTxt = (new JLabel("Installation Fee"));
        installTxt.setFont(FontUtil.getInterFont(16f));
        installTxt.setForeground(txtColor);
        JLabel installPrice = new JLabel("P 125/24mo.");
        installPrice.setFont(FontUtil.getInterFont(16f));
        installPrice.setForeground(txtColor);
        installPrice.setHorizontalAlignment(JLabel.RIGHT);

        installRow.add(installTxt);
        installRow.add(installPrice);
        summaryPanel.add(installRow);

        parent.add(summaryPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel noteLine1 = new JLabel("*Full Payment means paying the installation fee upfront.");
        noteLine1.setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
        noteLine1.setForeground(txtColor);
        noteLine1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel noteLine2 = new JLabel("MSF is billed separately every month.");
        noteLine2.setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
        noteLine2.setForeground(txtColor);
        noteLine2.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(noteLine1);
        parent.add(noteLine2);

        return parent;
    }

    private JPanel createPaymentSectionPanel() {

        Color txtColor = Color.decode("#1E1E1E");

        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 0)); // Increased left padding from 30 to 50
        paymentPanel.setMaximumSize(new Dimension(400, 400));

        JLabel paymentTitle = new JLabel("Payment Section");
        paymentTitle.setFont(FontUtil.getOutfitFont(15f));
        paymentTitle.setForeground(txtColor);
        paymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(paymentTitle);

        paymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        ButtonGroup paymentGroup = new ButtonGroup();
        JRadioButton full = new JRadioButton("Full Payment");
        full.setFont(FontUtil.getInterFont(15f));
        full.setForeground(txtColor);
        JRadioButton install = new JRadioButton("Installment");
        install.setFont(FontUtil.getInterFont(15f));
        install.setForeground(txtColor);

        full.setOpaque(false);
        full.setFocusPainted(false);
        full.setBorder(BorderFactory.createEmptyBorder());

        install.setOpaque(false);
        install.setFocusPainted(false);
        install.setBorder(BorderFactory.createEmptyBorder());

        paymentGroup.add(full);
        paymentGroup.add(install);

        // Panel to hold radio buttons side by side
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setOpaque(false);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        radioPanel.add(full);
        radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        radioPanel.add(install);

        paymentPanel.add(radioPanel);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Create text fields with better alignment
        RoundedComponents.RoundedTextField accNum = createRoundedTextField("Account Number");
        accNum.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(accNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        RoundedComponents.RoundedTextField refNum = createRoundedTextField("Reference Number");
        refNum.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(refNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Use RoundedComponents.RoundedButton with proper rounded border
        RoundedComponents.RoundedButton confirmBtn = new RoundedComponents.RoundedButton("CONFIRM PAYMENT", 20);
        confirmBtn.setBackground(Color.decode("#623CBB"));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(FontUtil.getOutfitBoldFont(16f));
        confirmBtn.setPreferredSize(new Dimension(194, 40));
        confirmBtn.setMaximumSize(new Dimension(194, 40));
        confirmBtn.setBorderColor(Color.decode("#623CBB"));
        confirmBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFocusable(false);
        
        // Add hover effect using mouse listeners for the rounded button
        confirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(Color.decode("#4B278F")); 
                confirmBtn.setBorderColor(Color.decode("#4B278F"));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(Color.decode("#623CBB")); 
                confirmBtn.setBorderColor(Color.decode("#623CBB"));
            }
        });
        
        paymentPanel.add(confirmBtn);

        return paymentPanel;
    }

    // method to create a rounded text field using RoundedComponents
    private RoundedComponents.RoundedTextField createRoundedTextField(String placeholder) {
        RoundedComponents.RoundedTextField field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(175, 35));
        field.setMaximumSize(new Dimension(175, 35));
        return field;
    }

    // class to create a rounded border
    static class RoundedBorder implements Border {
        private int radius;
        public RoundedBorder(int radius) { this.radius = radius; }
        public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp5::new);
    }
}