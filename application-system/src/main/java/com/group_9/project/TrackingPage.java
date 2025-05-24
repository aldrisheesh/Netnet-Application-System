package com.group_9.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrackingPage extends JFrame {

    public TrackingPage() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Gradient background panel
        BackgroundPanel background = new BackgroundPanel(4);
        background.setLayout(null);
        setContentPane(background);

        // Logo image
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        // Navigation Menu with Clickable Labels
        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"};
        int xPos = 900;
        int spacing = 30;

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(new Color(22, 6, 48, 128));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            FontMetrics fm = label.getFontMetrics(label.getFont());
            int textWidth = fm.stringWidth(item);
            label.setBounds(xPos, 30, textWidth + 10, 40);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openNewWindow(item);
                    dispose();
                }
            });

            background.add(label);
            xPos += textWidth + spacing + 10;
        }

        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(1300, 30, 80, 35);
        loginBtn.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                loginBtn,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        background.add(loginBtn);

        // Headline
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
        ButtonHoverEffect.apply(viewPlans,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        background.add(viewPlans);

        JButton checkAvailability = new JButton("CHECK AVAILABILITY");
        checkAvailability.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        checkAvailability.setBounds(700, 400, 220, 45);
        checkAvailability.setFocusPainted(false);
        checkAvailability.setContentAreaFilled(false);
        ButtonHoverEffect.apply(checkAvailability,
                new Color(62, 10, 118),
                new Color(62, 10, 118),
                new Color(0, 0, 0, 0),
                new Color(38, 6, 67),
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        background.add(checkAvailability);

        // Wi-Fi Image
        ImageIcon wifiIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/wifi.png"));
        Image wifiImg = wifiIconRaw.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon wifiIcon = new ImageIcon(wifiImg);
        JLabel wifiLabel = new JLabel(wifiIcon);
        wifiLabel.setBounds(302, 495, 200, 200);
        background.add(wifiLabel);

        int xPosLeft = 140;
        int yPosLeft = 780;

        // Upgrade section
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

        JButton morePlansBtn = new JButton("GET MORE PLANS");
        morePlansBtn.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
        morePlansBtn.setBounds(xPosLeft, yPosLeft + 60, 160, 40);
        morePlansBtn.setFocusPainted(false);
        ButtonHoverEffect.apply(morePlansBtn,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        background.add(morePlansBtn);

        // Dynamic Application Tracker
        JLabel trackerTitle = new JLabel("<html><div style='color:#2A0243;font-weight:700;'>APPLICATION TRACKER</div></html>", SwingConstants.CENTER);
        trackerTitle.setFont(FontUtil.getOutfitFont(26f));
        trackerTitle.setForeground(new Color(43, 2, 67));

        JTextField searchField = new JTextField();
        searchField.setFont(FontUtil.getInterFont(14f));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 255), 1));

        JPanel cardPanel = new JPanel(null);
        cardPanel.setBackground(new Color(92, 12, 168));

        JLabel appNumber = new JLabel("Application No. A00001");
        appNumber.setFont(FontUtil.getOutfitFont(14f));
        appNumber.setForeground(Color.WHITE);
        appNumber.setBounds(10, 10, 350, 20);
        cardPanel.add(appNumber);

        JLabel appStatus = new JLabel("Status: Pending Review");
        appStatus.setFont(FontUtil.getInterFont(12f));
        appStatus.setForeground(Color.WHITE);
        appStatus.setBounds(10, 35, 350, 20);
        cardPanel.add(appStatus);

        JLabel appDate = new JLabel("Date Submitted: 3/15/2010");
        appDate.setFont(FontUtil.getInterFont(12f));
        appDate.setForeground(Color.WHITE);
        appDate.setBounds(10, 55, 350, 20);
        cardPanel.add(appDate);

        JLabel viewSummary = new JLabel("<html><u>View Plan Summary</u></html>");
        viewSummary.setFont(FontUtil.getInterFont(12f));
        viewSummary.setForeground(Color.WHITE);
        viewSummary.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewSummary.setBounds(200, 75, 210, 20);
        viewSummary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Plan Summary clicked.");
            }
        });
        cardPanel.add(viewSummary);

        JPanel trackerPanel = new JPanel(null) {
            @Override
            public void doLayout() {
                int padding = 30;
                int width = getWidth();
                int innerWidth = width - 2 * padding;

                trackerTitle.setBounds(0, 10, width, 30);
                searchField.setBounds(padding, 50, innerWidth, 30);
                cardPanel.setBounds(padding, 100, innerWidth, 100);
            }
        };
        trackerPanel.setBackground(new Color(255, 255, 255, 180));
        trackerPanel.setBounds(800, 515, 484, 300);
        trackerPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 190, 255), 1));

        trackerPanel.add(trackerTitle);
        trackerPanel.add(searchField);
        trackerPanel.add(cardPanel);
        background.add(trackerPanel);
    }

    private void openNewWindow(String title) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(600, 400);
        newFrame.setLocationRelativeTo(null);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel message = new JLabel("This is the " + title + " window", SwingConstants.CENTER);
        message.setFont(FontUtil.getOutfitFont(18f));
        newFrame.add(message);
        newFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrackingPage().setVisible(true));
    }
}
