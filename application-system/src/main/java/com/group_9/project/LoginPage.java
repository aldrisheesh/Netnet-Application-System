package com.group_9.project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LoginPage extends JFrame {

    public LoginPage() {
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
        }

        // Main headlines
        JLabel headline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        int yPosi = 435;

        // Call to action form
        JLabel letsLabel = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:600;'>Let’s make things happen.</div></html>");
        letsLabel.setFont(FontUtil.getOutfitFont(16f));
        letsLabel.setBounds(562, yPosi, 300, 30);
        letsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(letsLabel);

        RoundedComponents.RoundedTextField emailField = new RoundedComponents.RoundedTextField("Email or phone number", 20);
        emailField.setFont(FontUtil.getInterFont(14f));
        emailField.setBounds(524, yPosi + 40, 375, 60);
        background.add(emailField);

        RoundedComponents.RoundedPasswordField passwordField = new RoundedComponents.RoundedPasswordField("Password", 20);
        passwordField.setFont(FontUtil.getInterFont(14f));
        passwordField.setBounds(524, yPosi + 117, 375, 60);
        background.add(passwordField);

        JButton loginBtn = new JButton("LOG IN");
        loginBtn.setFont(FontUtil.getOutfitFont(16f));
        loginBtn.setBounds(525, yPosi + 195, 130, 40);
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                loginBtn, 
                new Color(62, 10, 118),          //hover bg
                Color.WHITE,                      //hover fg
                new Color(42, 2, 67),            //normal bg
                Color.WHITE,                      //normal fg
                new Color(62, 10, 118),          //hover border
                new Color(42, 2, 67)             //normal border
        );
        background.add(loginBtn);

        JLabel forgotLabel = new JLabel("<html><div style='color:#7E4CA5;font-weight:600;'>Forgotten your password?</div></html>");
        forgotLabel.setFont(FontUtil.getOutfitFont(16f));
        forgotLabel.setBounds(679, yPosi + 195 + 3, 200, 30);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        background.add(forgotLabel);

        JCheckBox keepSignedIn = new JCheckBox("Keep me signed in");
        keepSignedIn.setFont(FontUtil.getOutfitFont(16f));
        keepSignedIn.setForeground(new Color(140, 140, 140));
        keepSignedIn.setOpaque(false);
        keepSignedIn.setFocusPainted(false);
        keepSignedIn.setBorderPainted(false);
        keepSignedIn.setContentAreaFilled(false);
        
        // flat custom icons
        keepSignedIn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_unchecked.png")));
        keepSignedIn.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_checked.png")));
        
        keepSignedIn.setBounds(523, yPosi + 195 + 45, 250, 40);
        background.add(keepSignedIn);

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}