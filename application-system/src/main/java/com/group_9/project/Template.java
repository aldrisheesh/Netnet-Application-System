package com.group_9.project;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Template extends JFrame {

    public Template() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Gradient background panel
        BackgroundPanel background = new BackgroundPanel(1);
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

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });

        background.add(loginBtn);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Template().setVisible(true));
    }
}
