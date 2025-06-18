package com.group_9.project;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;
import com.group_9.project.utils.AccountNavigationUtil;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;

import com.group_9.project.utils.RoundedComponents.RoundedTextField;

public class HelpSupportPage extends JFrame {

    public HelpSupportPage() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

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
        searchField.setForeground(Color.BLACK);
        searchField.setBounds(0, 0, 775, 60);
        searchField.setBackground(new Color(255, 255, 255, 180));
        searchPanel.add(searchField);

        searchField.setFocusable(false);
        SwingUtilities.invokeLater(() -> searchField.setFocusable(true));
        
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
