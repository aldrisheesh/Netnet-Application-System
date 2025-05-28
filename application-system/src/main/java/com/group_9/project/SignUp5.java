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

        // step tracker panel
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(createStepTracker());
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

        // Create a panel to hold both columns side by side
        // === Container for both panels ===
        JPanel paymentWrapper = new JPanel();
        paymentWrapper.setLayout(new BoxLayout(paymentWrapper, BoxLayout.X_AXIS));
        paymentWrapper.setOpaque(false);
        paymentWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        paymentWrapper.setMaximumSize(new Dimension(800, 250));

        JLabel sub = new JLabel("avsvad", SwingConstants.LEFT);
        sub.setFont(FontUtil.getOutfitFont(16f));
        sub.setForeground(subColor);

        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // === LEFT: Plan Summary Panel ===
        RoundedPanel summaryPanel = new RoundedPanel(20);
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        summaryPanel.setMaximumSize(new Dimension(380, 220));

        // Section title
        JLabel summaryTitle = new JLabel("Your Plan Summary");
        summaryTitle.setFont(FontUtil.getOutfitFont(13f));
        summaryTitle.setForeground(new Color(90, 90, 90));
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.add(summaryTitle);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Table header
        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setOpaque(false);
        JLabel col1 = new JLabel("Product and Service");
        col1.setFont(FontUtil.getOutfitFont(13f));
        JLabel col2 = new JLabel("Amount");
        col2.setFont(FontUtil.getOutfitFont(13f));
        col2.setHorizontalAlignment(JLabel.RIGHT);
        header.add(col1);
        header.add(col2);
        summaryPanel.add(header);

        // Plan content
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel planName = new JLabel("FIBERX 1500");
        planName.setFont(FontUtil.getOutfitBoldFont(14f));
        summaryPanel.add(planName);

        JPanel msfRow = new JPanel(new GridLayout(1, 2));
        msfRow.setOpaque(false);
        msfRow.add(new JLabel("Monthly Service Fee"));
        JLabel msfPrice = new JLabel("P 1500");
        msfPrice.setHorizontalAlignment(JLabel.RIGHT);
        msfRow.add(msfPrice);
        summaryPanel.add(msfRow);

        JPanel installRow = new JPanel(new GridLayout(1, 2));
        installRow.setOpaque(false);
        installRow.add(new JLabel("Installation Fee"));
        JLabel installPrice = new JLabel("P 125/24mo.");
        installPrice.setHorizontalAlignment(JLabel.RIGHT);
        installRow.add(installPrice);
        summaryPanel.add(installRow);

        summaryPanel.add(Box.createVerticalGlue());

        // Note
        JLabel note = new JLabel("<html><i>*Full Payment means paying the installation fee upfront.<br>MSF is billed separately every month.</i></html>");
        note.setFont(FontUtil.getOutfitFont(11f));
        note.setForeground(new Color(100, 100, 100));
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        summaryPanel.add(note);

        // === RIGHT: Payment Section ===
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 0));
        paymentPanel.setMaximumSize(new Dimension(400, 220));

        // Section title
        JLabel paymentTitle = new JLabel("Payment Section");
        paymentTitle.setFont(FontUtil.getOutfitFont(13f));
        paymentTitle.setForeground(new Color(90, 90, 90));
        paymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(paymentTitle);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Payment method
        ButtonGroup paymentGroup = new ButtonGroup();
        JRadioButton full = new JRadioButton("Full Payment");
        JRadioButton install = new JRadioButton("Installment");
        full.setOpaque(false);
        install.setOpaque(false);
        paymentGroup.add(full);
        paymentGroup.add(install);
        paymentPanel.add(full);
        paymentPanel.add(install);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Account Number
        JTextField accNum = new JTextField();
        accNum.setMaximumSize(new Dimension(200, 30));
        accNum.setFont(FontUtil.getOutfitFont(13f));
        accNum.setBorder(BorderFactory.createTitledBorder("Account Number"));
        paymentPanel.add(accNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Reference Number
        JTextField refNum = new JTextField();
        refNum.setMaximumSize(new Dimension(200, 30));
        refNum.setFont(FontUtil.getOutfitFont(13f));
        refNum.setBorder(BorderFactory.createTitledBorder("Reference Number"));
        paymentPanel.add(refNum);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirm Button
        JButton confirmBtn = new JButton("CONFIRM PAYMENT");
        confirmBtn.setBackground(new Color(110, 57, 190));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(FontUtil.getOutfitBoldFont(14f));
        confirmBtn.setMaximumSize(new Dimension(200, 40));
        confirmBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(confirmBtn);

        // === Add to wrapper ===
        paymentWrapper.add(summaryPanel);
        paymentWrapper.add(paymentPanel);

        // === Add to main container ===
        container.add(paymentWrapper);

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

    // method to create the step tracker for the form
    private JPanel createStepTracker() {
        Color stepTextColor = Color.decode("#2B0243");
        Color stepColor = Color.decode("#FFF1FF");
        Color borderColor = Color.decode("#7E4CA5");

        String[] steps = {"YOUR INFO", "CHOOSE A PLAN", "PAY HERE", "CHECK STATUS"};
        int circleDiameter = 41;
        int spacingBetweenCenters = 163;
        int barWidth = spacingBetweenCenters - circleDiameter;

        // === Main container ===
        JPanel stepTracker = new JPanel();
        stepTracker.setLayout(new BoxLayout(stepTracker, BoxLayout.Y_AXIS));
        stepTracker.setOpaque(false);

        // === Top: Circles + Bars ===
        JPanel topRow = new JPanel(new GridBagLayout());
        topRow.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < steps.length; i++) {
            boolean isActive = (i == 2);
            Color circleBgColor = isActive ? stepColor : stepTextColor;
            Color numberFgColor = isActive ? stepTextColor : stepColor;

            // Create circle panel
            JPanel circlePanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int strokeWidth = 2;
                    int inset = strokeWidth / 2;
                    int diameter = circleDiameter - strokeWidth;

                    g2.setColor(circleBgColor);
                    g2.fillOval(inset, inset, diameter, diameter);
                    g2.setColor(borderColor);
                    g2.setStroke(new BasicStroke(strokeWidth));
                    g2.drawOval(inset, inset, diameter, diameter);
                }
            };
            circlePanel.setPreferredSize(new Dimension(circleDiameter, circleDiameter));
            circlePanel.setOpaque(false);
            circlePanel.setLayout(new BorderLayout());

            JLabel number = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            number.setFont(FontUtil.getOutfitBoldFont(16f));
            number.setForeground(numberFgColor);
            circlePanel.add(number, BorderLayout.CENTER);

            // Add circle to layout
            gbc.gridx = i * 2;
            topRow.add(circlePanel, gbc);

            // Add bar between circles
            if (i < steps.length - 1) {
                JPanel bar = new JPanel();
                bar.setBackground(borderColor);
                bar.setPreferredSize(new Dimension(barWidth, 2));
                bar.setMaximumSize(new Dimension(barWidth, 2));
                bar.setMinimumSize(new Dimension(barWidth, 2));
                gbc.gridx = i * 2 + 1;
                topRow.add(bar, gbc);
            }
        }

        // === Bottom: Step Labels aligned under each circle ===
        JPanel bottomRow = new JPanel(new GridBagLayout());
        bottomRow.setOpaque(false);
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridy = 0;
        labelGbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < steps.length; i++) {
            JLabel label = new JLabel(steps[i], SwingConstants.CENTER);
            label.setFont(FontUtil.getOutfitFont(13f));
            label.setForeground(stepTextColor);

            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setOpaque(false);
            labelPanel.setPreferredSize(new Dimension(spacingBetweenCenters, 20));
            labelPanel.add(label, BorderLayout.CENTER);

            labelGbc.gridx = i * 2;
            bottomRow.add(labelPanel, labelGbc);
        }

        // === Assemble tracker ===
        stepTracker.add(topRow);
        stepTracker.add(Box.createVerticalStrut(8));
        stepTracker.add(bottomRow);

        return stepTracker;
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

