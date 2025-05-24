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
        BackgroundPanel  background = new BackgroundPanel(1);
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
        loginBtn.setFont(FontUtil.getOutfitFont(16f));
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                                loginBtn, 
                                new Color(62, 10, 118),          //hover bg
                                Color.WHITE,                           //hover fg
                                new Color(42, 2, 67),            //normal bg
                                Color.WHITE,                           //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        background.add(loginBtn);
        

        // Main headline - enlarged and repositioned
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
        viewPlans.setFont(FontUtil.getOutfitFont(16f));
        viewPlans.setBounds(530, 400, 160, 45);
        viewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(
                                viewPlans, 
                                new Color(62, 10, 118),          //hover bg
                                Color.WHITE,                           //hover fg
                                new Color(42, 2, 67),            //normal bg
                                Color.WHITE,                           //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        background.add(viewPlans);

        JButton checkAvailability = new JButton("CHECK AVAILABILITY");
        checkAvailability.setFont(FontUtil.getOutfitFont(16f));
        checkAvailability.setBounds(700, 400, 220, 45);
        checkAvailability.setFocusPainted(false);
        checkAvailability.setContentAreaFilled(false);
        ButtonHoverEffect.apply(
                                checkAvailability, 
                                new Color(62, 10, 118),          //hover bg
                                new Color(62, 10, 118),          //hover fg
                                new Color(0, 0, 0, 0),         //normal bg
                                new Color(38, 6, 67),            //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        background.add(checkAvailability);
        
        // Wi-Fi Image Centered Top
        ImageIcon wifiIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/wifi.png"));
        Image wifiImg = wifiIconRaw.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        ImageIcon wifiIcon = new ImageIcon(wifiImg);
        JLabel wifiLabel = new JLabel(wifiIcon);
        wifiLabel.setBounds(160, 580, 256, 256); // Center horizontally in ~500px block
        background.add(wifiLabel);

        int xPosLeft = 190;
        int yPosLeft = 750;

        // "Ready to Upgrade Your Setup?" Text
        JLabel upgradeTitle = new JLabel("Ready to Upgrade Your Setup?");
        upgradeTitle.setFont(FontUtil.getOutfitFont(24f));
        upgradeTitle.setForeground(new Color(43, 2, 67));
        upgradeTitle.setBounds(xPosLeft, yPosLeft, 400, 30); // center within ~500px
        upgradeTitle.setHorizontalAlignment(SwingConstants.LEFT);
        background.add(upgradeTitle);

        // Description
        JLabel upgradeDesc = new JLabel("Start with one, then add more plans as your needs grow.");
        upgradeDesc.setFont(FontUtil.getInterFont(14f));
        upgradeDesc.setForeground(new Color(43, 2, 67));
        upgradeDesc.setBounds(xPosLeft, yPosLeft + 30, 400, 30);
        upgradeDesc.setHorizontalAlignment(SwingConstants.LEFT);
        background.add(upgradeDesc);

        // Button
        JButton morePlansBtn = new JButton("GET MORE PLANS");
        morePlansBtn.setFont(FontUtil.getOutfitFont(14f));
        morePlansBtn.setBounds(xPosLeft, yPosLeft + 40, 160, 40);
        morePlansBtn.setFocusPainted(false);
        ButtonHoverEffect.apply(
                                morePlansBtn, 
                                new Color(62, 10, 118), 
                                Color.WHITE, 
                                new Color(42, 2, 67), 
                                Color.WHITE, 
                                new Color(62, 10, 118), 
                                new Color(42, 2, 67)
        );
        background.add(morePlansBtn);



        // Application Tracker Section
        JPanel trackerPanel = new JPanel();
        trackerPanel.setLayout(null);
        trackerPanel.setBackground(new Color(255, 255, 255, 180));
        trackerPanel.setBounds(700, 550, 550, 300);
        trackerPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 190, 255), 1));
        background.add(trackerPanel);

        JLabel trackerTitle = new JLabel("APPLICATION TRACKER", SwingConstants.CENTER);
        trackerTitle.setFont(FontUtil.getOutfitFont(20f));
        trackerTitle.setForeground(new Color(43, 2, 67));
        trackerTitle.setBounds(0, 10, 500, 30);
        trackerPanel.add(trackerTitle);

        JTextField searchField = new JTextField();
        searchField.setBounds(40, 50, 420, 30);
        searchField.setFont(FontUtil.getInterFont(14f));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 255), 1));
        trackerPanel.add(searchField);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(new Color(92, 12, 168));
        cardPanel.setBounds(40, 100, 420, 100);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        trackerPanel.add(cardPanel);

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