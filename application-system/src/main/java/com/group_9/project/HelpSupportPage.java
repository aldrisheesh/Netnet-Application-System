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
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Page headline
        JLabel lblHeadline = new JLabel("Hello, How Can We Help You?", SwingConstants.CENTER);
        lblHeadline.setFont(FontUtil.getOutfitBoldFont(50f).deriveFont(Font.BOLD));
        lblHeadline.setForeground(new Color(42, 2, 67));
        lblHeadline.setBounds(0, 160, 1424, 50);
        pnlBackground.add(lblHeadline);

        // Search Field Panel
        JPanel pnlSearchPanel = new JPanel(null);
        pnlSearchPanel.setBounds(325, 230, 775, 60);
        pnlSearchPanel.setOpaque(false);
        pnlSearchPanel.setLayout(null);

        // Rounded Search Field (shortened to leave space for icon)
        RoundedTextField txtSearchField = new RoundedTextField("Search your keyword here...", 20);
        txtSearchField.setFont(FontUtil.getInterFont(20f));
        txtSearchField.setForeground(Color.BLACK);
        txtSearchField.setBounds(0, 0, 775, 60);
        txtSearchField.setBackground(new Color(255, 255, 255, 180));
        pnlSearchPanel.add(txtSearchField);

        txtSearchField.setFocusable(false);
        SwingUtilities.invokeLater(() -> txtSearchField.setFocusable(true));
        
        ImageIcon rawIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/search.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaled);

        // Create icon label
        JLabel lblSearchIcon = new JLabel(resizedIcon);
        int iconSize = 30;
        int iconX = 775 - iconSize - 20; 
        int iconY = (60 - iconSize) / 2; 

        lblSearchIcon.setBounds(iconX, iconY, iconSize, iconSize);
        pnlSearchPanel.add(lblSearchIcon);


        pnlBackground.add(pnlSearchPanel);


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
            RoundedPanel pnlCard = new RoundedPanel(30);
            pnlCard.setBounds(startX + i * (cardWidth + spacingBetweenCards), 350, cardWidth, cardHeight);
            pnlCard.setBackground(Color.WHITE);
            pnlCard.setLayout(null);
            
            // Icon (120x120 centered)
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPaths[i]));
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel lblIcon = new JLabel(new ImageIcon(img));
            lblIcon.setBounds((cardWidth - 120) / 2, 15, 120, 120);
            pnlCard.add(lblIcon);
        
            // Title
            JLabel lblTitle = new JLabel(titles[i], SwingConstants.CENTER);
            lblTitle.setFont(FontUtil.getOutfitBoldFont(26f).deriveFont(Font.BOLD));
            lblTitle.setForeground(new Color(42, 2, 67));
            lblTitle.setBounds(0, 145, cardWidth, 35);
            pnlCard.add(lblTitle);
        
            // Description
            JLabel lblDesc = new JLabel(descriptions[i], SwingConstants.CENTER);
            lblDesc.setFont(FontUtil.getInterFont(15f));
            lblDesc.setForeground(Color.DARK_GRAY);
            lblDesc.setBounds(25, 185, cardWidth - 50, 100); // horizontal padding
            pnlCard.add(lblDesc);
        
            pnlBackground.add(pnlCard);
        }
        
        

        // Footer link
        JLabel lblFooter = new JLabel(
            "<html><div style='text-align:center;'>" +
            "<span style='font-weight:500;color:#1E1E1E;'>Have more questions? </span>" +
            "<span style='font-weight:500;color:#623CBB;'>Submit a Request</span>" +
            "</div></html>",
            SwingConstants.CENTER
        );
        lblFooter.setFont(FontUtil.getInterFont(26f));
        lblFooter.setBounds(0, 750, 1440, 30);
        lblFooter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pnlBackground.add(lblFooter);
        
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
