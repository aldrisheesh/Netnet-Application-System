package com.group_9.project;
import com.group_9.project.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorPage extends JFrame {

    public ErrorPage() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Gradient background panel
        BackgroundPanel background = new BackgroundPanel(3);
        background.setLayout(null);
        setContentPane(background);

        // Logo image
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        //Navigation Menu
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
                            new ErrorPage().setVisible(true);
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
        

        // Login button
        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(1300, 30, 80, 35);
        loginBtn.setFont(FontUtil.getOutfitFont(16f));
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

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
        

        background.add(loginBtn);

        // 404 Message

        int yPos = 350;

        JLabel errorCode = new JLabel("<html><div style='font-weight:800;'>404</div></html>", SwingConstants.CENTER);
        errorCode.setFont(FontUtil.getOutfitBoldFont(120f).deriveFont(Font.BOLD));
        errorCode.setForeground(new Color(42, 2, 67));
        errorCode.setBounds(0, yPos, 1424, 100);
        background.add(errorCode);

        JLabel message = new JLabel("Oops... This page is not found.", SwingConstants.CENTER);
        message.setFont(FontUtil.getOutfitFont(40f));
        message.setForeground(new Color(42, 2, 67));
        message.setBounds(0, yPos + 100, 1424, 45);
        background.add(message);

        // Container panel to center both labels
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        returnPanel.setOpaque(false);
        returnPanel.setBounds(0, yPos + 165, 1424, 30);

        // "Return to " (normal text)
        JLabel returnText = new JLabel("Return to ");
        returnText.setFont(FontUtil.getInterFont(18f));
        returnText.setForeground(Color.DARK_GRAY);
        returnPanel.add(returnText);

        // "home page" (clickable link)
        JLabel homeLink = new JLabel("<html><u>home page</u></html>");
        homeLink.setFont(FontUtil.getInterFont(18f));
        homeLink.setForeground(new Color(62, 10, 118));
        homeLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Open Homepage and close ErrorPage when clicked
        homeLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new Homepage().setVisible(true); // Open Homepage
                dispose(); // Close current ErrorPage
            }
        });

        returnPanel.add(homeLink);

        // Add combined panel
        background.add(returnPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ErrorPage().setVisible(true));
    }
}
