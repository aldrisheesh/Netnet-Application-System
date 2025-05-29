package com.group_9.project;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class SignUp5 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp5() {
        setTitle("Service Application");
        setSize(1440, 1024); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background panel
        BackgroundPanel  background = new BackgroundPanel(1);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // creates a container panel for the form
        JPanel container = new JPanel();
        container.setBackground(Color.decode("#fdf5fe"));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(800, 650));
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 38))
        ));

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color subColor = Color.decode("#302E2E");

        // creates title label
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        container.add(title);

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(2)); 
        container.add(stepWrapper);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // personal info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(700, 60));

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
        container.add(infoPanel);

        // adds horizontal separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(700, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(separator);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        container.add(Box.createRigidArea(new Dimension(0, 20)));
 
        // Add plan and payment panels side by side
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowPanel.add(createPlanSummaryPanel());
        rowPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        rowPanel.add(createPaymentSectionPanel());
        container.add(rowPanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));


        // button panel for action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        // BACK button
        JButton backButton = new JButton("BACK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(43, 2, 67)); // border color #2B0243
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        Color defaultBg = new Color(255, 241, 255);   // #FFF1FF
        Color hoverBg = new Color(255, 248, 255);     // lightened
        Color clickBg = new Color(240, 220, 240);     // darkened

        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setForeground(new Color(43, 2, 67)); // text color to match border
        backButton.setBackground(defaultBg);
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setPreferredSize(new Dimension(140, 40));

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(defaultBg);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                backButton.setBackground(clickBg);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }
        });

        JButton nextButton = new JButton("NEXT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        // button properties
        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(50, 0, 90));
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setPreferredSize(new Dimension(140, 40));

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            Color nextDefaultBg = new Color(50, 0, 90);
            Color nextHoverBg = new Color(80, 0, 130);
            Color nextClickBg = new Color(30, 0, 60);

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextDefaultBg);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextClickBg);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(nextButton);
        container.add(buttonPanel);

        background.add(container);
        setContentPane(background);
        setVisible(true);
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
;

        return parent;
    }

    private JPanel createPaymentSectionPanel() {

        Color txtColor = Color.decode("#1E1E1E");

        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 0));
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
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedComponents.RoundedTextField accNum = new RoundedComponents.RoundedTextField("Account Number", 30);
        accNum.setFont(FontUtil.getInterFont(14f));
        accNum.setPreferredSize(new Dimension(250, 35)); 
        accNum.setMaximumSize(new Dimension(250, 35));   

        paymentPanel.add(accNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        RoundedComponents.RoundedTextField refNum = new RoundedComponents.RoundedTextField("Reference Number", 30);
        refNum.setFont(FontUtil.getInterFont(14f));
        refNum.setPreferredSize(new Dimension(250, 35)); 
        refNum.setMaximumSize(new Dimension(250, 35));   

        paymentPanel.add(refNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton confirmBtn = new JButton("CONFIRM PAYMENT");
        confirmBtn.setBackground(Color.decode("#623CBB"));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(FontUtil.getOutfitBoldFont(16f));
        confirmBtn.setMaximumSize(new Dimension(194, 40));
        confirmBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setBorderPainted(false);
        paymentPanel.add(confirmBtn);

        return paymentPanel;
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

