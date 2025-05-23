package com.group_9.project;

import javax.swing.*;
import java.awt.*;

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
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Template().setVisible(true));
    }
}
