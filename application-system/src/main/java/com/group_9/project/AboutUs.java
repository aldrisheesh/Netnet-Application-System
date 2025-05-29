package com.group_9.project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class AboutUs extends Template {
    public AboutUs() {
        setSize(1440, 1354);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel background = new BackgroundPanel(2);
        background.setLayout(null);
        setContentPane(background);

        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"};
        int xPos = 900;
        int spacing = 30;
        Color normalColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(normalColor);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    label.setForeground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    label.setForeground(normalColor);
                }

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "Home" -> {
                            new Homepage().setVisible(true);
                            dispose();
                        }
                        case "Plans" -> {
                            new ErrorPage().setVisible(true);
                            dispose();
                        }
                        case "Help & Support" -> {
                            new HelpSupportPage().setVisible(true);
                            dispose();
                        }
                        case "About Us" -> {
                            new AboutUs().setVisible(true);
                            dispose();
                        }
                    }
                }
            });

            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;

            // Login button
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

            loginBtn.addActionListener(e -> {
                new LoginPage().setVisible(true);
                dispose();
            });

            background.add(loginBtn);
        }

        JLabel header = new JLabel("About Us.");
        header.setFont(FontUtil.getOutfitBoldFont(50f));
        header.setForeground(new Color(42, 2, 67, 255));

        Dimension textSize = header.getPreferredSize();
        int frameWidth = 1440;
        int x = (frameWidth - textSize.width) / 2;

        header.setBounds(x, 100, textSize.width, textSize.height);
        background.add(header);

        JLabel title = new JLabel("<html><b>Made for People,<br>Built for Ease</html>");
        title.setFont(FontUtil.getOutfitBoldFont(35f));
        title.setForeground(new Color(42, 2, 67, 255));
        title.setBounds(150, 220, 400, 80);
        background.add(title);

        JLabel body = new JLabel("<html>Welcome to NETNET: Wi-Finally Yours!, where convenience meets care. We're<br>all about creating a smoother, smarter service journey for everyone. We are a<br>team of BSIT Sophomores from the Polytechnic University of the Philippines.</html>");
        body.setFont(FontUtil.getOutfitFont(17f));
        body.setForeground(new Color(30, 30, 30, 1));
        body.setBounds(150, 310, 800, 100);
        background.add(body);

        JLabel teamIntro = new JLabel("MEET OUR TEAM");
        teamIntro.setFont(FontUtil.getOutfitBoldFont(25f));
        teamIntro.setForeground(new Color(42, 2, 67, 255));

        Dimension teamTextSize = teamIntro.getPreferredSize();
        int teamX = (1440 - teamTextSize.width) / 2;

        teamIntro.setBounds(teamX, 450, teamTextSize.width, teamTextSize.height);
        background.add(teamIntro);

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutUs().setVisible(true));
    }
}
