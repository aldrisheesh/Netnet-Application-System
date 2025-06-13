package com.group_9.project;

import com.group_9.project.utils.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TrackingPage extends JFrame {

    public TrackingPage() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        BackgroundPanel background = new BackgroundPanel(1);
        background.setLayout(null);
        setContentPane(background);

        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        // Navigation Menu
        String[] navItems = {"Home", "Plans", "Help & Support", "About Us", "Account"};
        int xPos = 900;
        int spacing = 30;
        Color normalColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(normalColor);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(hoverColor);
                }

                public void mouseExited(MouseEvent e) {
                    label.setForeground(normalColor);
                }

                public void mouseClicked(MouseEvent e) {
                    switch (item) {
                        case "Home" -> {
                            new Homepage().setVisible(true);
                            dispose();
                        }
                        case "Plans" -> {
                            new PlansPage().setVisible(true);
                            dispose();
                        }
                        case "Help & Support" -> {
                            new HelpSupportPage().setVisible(true);
                            dispose();
                        }
                        case "About Us" -> {
                            new AboutUsPage().setVisible(true);
                            dispose();
                        }
                        case "Account" -> {
                            new AccountDetailsPage().setVisible(true);
                            dispose();
                        }
                    }
                }
            });

            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;
        }

        JLabel headline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        JButton viewPlans = new JButton("VIEW PLANS");
        viewPlans.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        viewPlans.setBounds(530, 400, 160, 45);
        viewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(viewPlans, new Color(62, 10, 118), Color.WHITE,
                new Color(42, 2, 67), Color.WHITE, new Color(62, 10, 118), new Color(42, 2, 67));

        viewPlans.addActionListener(e -> {
            new PlansPage().setVisible(true);
            dispose();
        });

        background.add(viewPlans);

        JButton checkAvailability = new JButton("CHECK AVAILABILITY");
        checkAvailability.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        checkAvailability.setBounds(700, 400, 220, 45);
        checkAvailability.setFocusPainted(false);
        checkAvailability.setContentAreaFilled(false);
        ButtonHoverEffect.apply(checkAvailability, new Color(62, 10, 118), new Color(62, 10, 118),
                new Color(0, 0, 0, 0), new Color(38, 6, 67), new Color(62, 10, 118), new Color(42, 2, 67));

        checkAvailability.addActionListener(e -> {
            new ErrorPage().setVisible(true);
            dispose();
        });

        background.add(checkAvailability);

        ImageIcon wifiIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/wifi.png"));
        Image wifiImg = wifiIconRaw.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel wifiLabel = new JLabel(new ImageIcon(wifiImg));
        wifiLabel.setBounds(302, 495, 200, 200);
        background.add(wifiLabel);

        int xPosLeft = 140;
        int yPosLeft = 780;

        JLabel upgradeTitle = new JLabel("<html><div style='text-align:left;color:#2B0243;font-weight:700;'>Ready to Upgrade Your Setup?</div></html>");
        upgradeTitle.setFont(FontUtil.getInterFont(35f));
        upgradeTitle.setForeground(new Color(43, 2, 67));
        upgradeTitle.setBounds(xPosLeft, yPosLeft - 50, 600, 45);
        upgradeTitle.setHorizontalAlignment(SwingConstants.LEFT);
        background.add(upgradeTitle);

        JLabel upgradeDesc = new JLabel("<html>Start with one, then add more plans as your<br>needs grow.</html>");
        upgradeDesc.setFont(FontUtil.getInterFont(16f));
        upgradeDesc.setForeground(new Color(43, 2, 67));
        upgradeDesc.setBounds(xPosLeft, yPosLeft, 450, 50);
        upgradeDesc.setHorizontalAlignment(SwingConstants.LEFT);
        background.add(upgradeDesc);

        JButton morePlansBtn = new RoundedComponents.RoundedButton("GET MORE PLANS", 20);
        morePlansBtn.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
        morePlansBtn.setBounds(xPosLeft, yPosLeft + 60, 160, 40);
        morePlansBtn.setFocusPainted(false);
        ButtonHoverEffect.apply(morePlansBtn, new Color(62, 10, 118), Color.WHITE,
                new Color(42, 2, 67), Color.WHITE, new Color(62, 10, 118), new Color(42, 2, 67));

        morePlansBtn.addActionListener(e -> {
            new PlansPage().setVisible(true);
            dispose();
        });

        background.add(morePlansBtn);

        JLabel trackerTitle = new JLabel("<html><div style='color:#2A0243;font-weight:700;'>APPLICATION TRACKER</div></html>", SwingConstants.CENTER);
        trackerTitle.setFont(FontUtil.getOutfitFont(26f));

        RoundedComponents.RoundedTextField searchField = new RoundedComponents.RoundedTextField("Enter application number", 20);
        searchField.setFont(FontUtil.getInterFont(14f));
        searchField.setBackground(Color.WHITE);

        // 🎨 Gradient Rounded Card
        RoundedGradientPanel cardPanel = new RoundedGradientPanel(20);
        cardPanel.setBounds(50, 140, 384, 100);

        JLabel appNumber = new JLabel("Application No. A00001");
        appNumber.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
        appNumber.setForeground(Color.WHITE);
        appNumber.setBounds(20, 15, 300, 20);
        cardPanel.add(appNumber);

        JLabel appStatus = new JLabel("Status: Completed");
        appStatus.setFont(FontUtil.getInterFont(12f));
        appStatus.setForeground(Color.WHITE);
        appStatus.setBounds(20, 38, 300, 18);
        cardPanel.add(appStatus);

        JLabel appDate = new JLabel("Date Submitted: 3/15/2010");
        appDate.setFont(FontUtil.getInterFont(12f));
        appDate.setForeground(Color.WHITE);
        appDate.setBounds(20, 58, 200, 18);
        cardPanel.add(appDate);

        JLabel viewSummary = new JLabel("<html><u>View Plan Summary</u></html>");
        viewSummary.setFont(FontUtil.getInterFont(12f));
        viewSummary.setForeground(Color.WHITE);
        viewSummary.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewSummary.setBounds(250, 58, 130, 18); // aligned with date
        cardPanel.add(viewSummary);

        // 📦 Tracker Panel
        RoundedPanel trackerPanel = new RoundedPanel(30) {
            @Override
            public void doLayout() {
                int padding = 50;
                int width = getWidth();
                int innerWidth = width - 2 * padding;

                int y = 30;
                trackerTitle.setBounds(0, y, width, 30);

                y += 50;
                searchField.setBounds(padding, y, innerWidth, 40);

                y += 60;
                cardPanel.setBounds(padding, y, innerWidth, 100);
            }
        };

        trackerPanel.setBackground(new Color(255, 255, 255, 180));
        trackerPanel.setBounds(800, 515, 484, 350);

        trackerPanel.add(trackerTitle);
        trackerPanel.add(searchField);
        trackerPanel.add(cardPanel);
        background.add(trackerPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrackingPage().setVisible(true));
    }
}

// 🟪 Rounded Panel with border
class RoundedPanel extends JPanel {
    private final int cornerRadius;

    public RoundedPanel(int radius) {
        super(null);
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        g2.setColor(new Color(210, 190, 255));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}

// 🎨 Rounded Gradient Panel
class RoundedGradientPanel extends JPanel {
    private final int cornerRadius;

    public RoundedGradientPanel(int radius) {
        super(null);
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, new Color(40, 0, 80),
                                             width, height, new Color(125, 0, 255));
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        g2d.dispose();
        super.paintComponent(g);
    }
}
