package com.group_9.project.utils;

import java.awt.*;
import javax.swing.*;

public class CreateStepTracker {
    
    public static JPanel createStepTracker(int intActiveIndex) {
        Color clrStepText = Color.decode("#2B0243");
        Color clrStep = Color.decode("#FFF1FF");
        Color clrBorder = Color.decode("#7E4CA5");

        String[] arrSteps = {"YOUR INFO", "CHOOSE A PLAN", "PAY HERE", "CHECK STATUS"};
        int intCircleDiameter = 41;
        int intSpacingBetweenCenters = 163;
        int intBarWidth = intSpacingBetweenCenters - intCircleDiameter;

        // main container
        JPanel pnlStepTracker = new JPanel();
        pnlStepTracker.setLayout(new BoxLayout(pnlStepTracker, BoxLayout.Y_AXIS));
        pnlStepTracker.setOpaque(false);

        // circle + bar
        JPanel pnlTopRow = new JPanel(new GridBagLayout());
        pnlTopRow.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < arrSteps.length; i++) {
            boolean boolActive = (i == intActiveIndex);
            Color clrCircleBg = boolActive ? clrStep : clrStepText;
            Color clrNumberFg = boolActive ? clrStepText : clrStep;

            // Create circle panel
            JPanel pnlCircle = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int intStrokeWidth = 1;
                    int intInset = intStrokeWidth / 2;
                    int intDiameter = intCircleDiameter - intStrokeWidth;

                    g2.setColor(clrCircleBg);
                    g2.fillOval(intInset, intInset, intDiameter, intDiameter);
                    g2.setColor(clrBorder);
                    g2.setStroke(new BasicStroke(intStrokeWidth));
                    g2.drawOval(intInset, intInset, intDiameter, intDiameter);
                }
            };
            pnlCircle.setPreferredSize(new Dimension(intCircleDiameter, intCircleDiameter));
            pnlCircle.setOpaque(false);
            pnlCircle.setLayout(new BorderLayout());

            JLabel lblNumber = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            lblNumber.setFont(FontUtil.getOutfitBoldFont(16f));
            lblNumber.setForeground(clrNumberFg);
            pnlCircle.add(lblNumber, BorderLayout.CENTER);

            // Add circle to layout
            gbc.gridx = i * 2;
            pnlTopRow.add(pnlCircle, gbc);

            // Add bar between circles
            if (i < arrSteps.length - 1) {
                JPanel pnlBar = new JPanel();
                pnlBar.setBackground(clrBorder);
                pnlBar.setPreferredSize(new Dimension(intBarWidth, 2));
                pnlBar.setMaximumSize(new Dimension(intBarWidth, 2));
                pnlBar.setMinimumSize(new Dimension(intBarWidth, 2));
                gbc.gridx = i * 2 + 1;
                pnlTopRow.add(pnlBar, gbc);
            }
        }

        // Bottom: Step Labels aligned under each circle
        JPanel pnlBottomRow = new JPanel(new GridBagLayout());
        pnlBottomRow.setOpaque(false);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridy = 0;
        gbcLabel.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < arrSteps.length; i++) {
            JLabel lblLabel = new JLabel(arrSteps[i], SwingConstants.CENTER);
            lblLabel.setFont(FontUtil.getOutfitFont(13f));
            lblLabel.setForeground(clrStepText);

            JPanel pnlLabelPanel = new JPanel(new BorderLayout());
            pnlLabelPanel.setOpaque(false);
            pnlLabelPanel.setPreferredSize(new Dimension(intSpacingBetweenCenters, 20));
            pnlLabelPanel.add(lblLabel, BorderLayout.CENTER);

            gbcLabel.gridx = i * 2;
            pnlBottomRow.add(pnlLabelPanel, gbcLabel);
        }

        // === Assemble tracker ===
        pnlStepTracker.add(pnlTopRow);
        pnlStepTracker.add(Box.createVerticalStrut(8));
        pnlStepTracker.add(pnlBottomRow);

        return pnlStepTracker;
    }
}