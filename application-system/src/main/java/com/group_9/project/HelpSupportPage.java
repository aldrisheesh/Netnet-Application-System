package com.group_9.project;
import com.group_9.project.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group_9.project.utils.RoundedComponents.RoundedTextField;

public class HelpSupportPage extends JFrame {

    public HelpSupportPage() {
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
        

        // Login button
        JButton loginBtn = new RoundedComponents.RoundedButton("Log In", 20);
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

        // Page headline
        JLabel headline = new JLabel("Hello, How Can We Help You?", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitBoldFont(50f).deriveFont(Font.BOLD));
        headline.setForeground(new Color(42, 2, 67));
        headline.setBounds(0, 160, 1424, 50);
        background.add(headline);

        // Search Field Panel
        JPanel searchPanel = new JPanel(null);
        searchPanel.setBounds(325, 230, 775, 60); 
        searchPanel.setOpaque(false);
        searchPanel.setLayout(null);

        // Rounded Search Field (shortened to leave space for icon)
        RoundedTextField searchField = new RoundedTextField("Search your keyword here...", 20);
        searchField.setFont(FontUtil.getInterFont(20f));
        searchField.setForeground(new Color(100, 100, 100));
        searchField.setBounds(0, 0, 775, 60);
        searchField.setBackground(new Color(255, 255, 255, 180));
        searchPanel.add(searchField);

        ImageIcon rawIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/search.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaled);

        // Create icon label
        JLabel searchIcon = new JLabel(resizedIcon);
        int iconSize = 30;
        int iconX = 775 - iconSize - 20; 
        int iconY = (60 - iconSize) / 2; 

        searchIcon.setBounds(iconX, iconY, iconSize, iconSize);
        searchPanel.add(searchIcon);


        background.add(searchPanel);


        // Cards container
        String[] titles = {"Getting Started", "FAQs", "Choosing the Right Plan"};
        String[] descriptions = {
                "<html>New here? This article walks you through everything you need to know to get started, from signing up, to choosing your first plan.</html>",
                "<html>Answers to the most common questions new users ask! Quick, clear, and all in one place.</html>",
                "<html>Not sure where to start? Here's how to find the perfect plan based on your needs, budget, and lifestyle.</html>"
        };
        String[] iconPaths = {
                "images/help_getting_started.png",
                "images/help_faqs.png",
                "images/help_choosing_plan.png"
        };
        
        int cardWidth = 380;
        int cardHeight = 310;
        int spacingBetweenCards = 50;
        int startX = 92; 
        
        for (int i = 0; i < 3; i++) {
            RoundedPanel card = new RoundedPanel(30);
            card.setBounds(startX + i * (cardWidth + spacingBetweenCards), 350, cardWidth, cardHeight);
            card.setBackground(Color.WHITE);
            card.setLayout(null);
            
            // Icon (120x120 centered)
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPaths[i]));
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            iconLabel.setBounds((cardWidth - 120) / 2, 15, 120, 120);
            card.add(iconLabel);
        
            // Title
            JLabel titleLabel = new JLabel(titles[i], SwingConstants.CENTER);
            titleLabel.setFont(FontUtil.getOutfitBoldFont(26f).deriveFont(Font.BOLD));
            titleLabel.setForeground(new Color(42, 2, 67));
            titleLabel.setBounds(0, 145, cardWidth, 35);
            card.add(titleLabel);
        
            // Description
            JLabel descLabel = new JLabel(descriptions[i], SwingConstants.CENTER);
            descLabel.setFont(FontUtil.getInterFont(15f));
            descLabel.setForeground(Color.DARK_GRAY);
            descLabel.setBounds(25, 185, cardWidth - 50, 100); // horizontal padding
            card.add(descLabel);
        
            background.add(card);
        }
        
        

        // Footer link
        JLabel footer = new JLabel(
            "<html><div style='text-align:center;'>" +
            "<span style='font-weight:500;color:#1E1E1E;'>Have more questions? </span>" +
            "<span style='font-weight:500;color:#623CBB;'>Submit a Request</span>" +
            "</div></html>",
            SwingConstants.CENTER
        );
        footer.setFont(FontUtil.getInterFont(26f));
        footer.setBounds(0, 750, 1440, 30);
        footer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        background.add(footer);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HelpSupportPage page = new HelpSupportPage();
            page.setVisible(true);
    
            page.requestFocusInWindow();
        });
    }
}

class RoundedPanel extends JPanel {
    private final int cornerRadius;

    public RoundedPanel(int radius) {
        super(null); // null layout
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Draw rounded border
        g2.setColor(new Color(126, 76, 165, 180)); // border color
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g); // Paint children
    }
}
