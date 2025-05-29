package com.group_9.project;

import java.awt.*;
import javax.swing.*;

public class CreateStepTracker {
    
    public static JPanel createStepTracker(int activeIndex) {
        Color stepTextColor = Color.decode("#2B0243");
        Color stepColor = Color.decode("#FFF1FF");
        Color borderColor = Color.decode("#7E4CA5");

        String[] steps = {"YOUR INFO", "CHOOSE A PLAN", "PAY HERE", "CHECK STATUS"};
        int circleDiameter = 41;
        int spacingBetweenCenters = 163;
        int barWidth = spacingBetweenCenters - circleDiameter;

        // === Main container ===
        JPanel stepTracker = new JPanel();
        stepTracker.setLayout(new BoxLayout(stepTracker, BoxLayout.Y_AXIS));
        stepTracker.setOpaque(false);

        // === Top: Circles + Bars ===
        JPanel topRow = new JPanel(new GridBagLayout());
        topRow.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < steps.length; i++) {
            boolean isActive = (i == activeIndex);
            Color circleBgColor = isActive ? stepColor : stepTextColor;
            Color numberFgColor = isActive ? stepTextColor : stepColor;

            // Create circle panel
            JPanel circlePanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int intStrokeWidth = 1;
                    int intInset = intStrokeWidth / 2;
                    int intDiameter = circleDiameter - intStrokeWidth;

                    g2.setColor(circleBgColor);
                    g2.fillOval(intInset, intInset, intDiameter, intDiameter);
                    g2.setColor(borderColor);
                    g2.setStroke(new BasicStroke(intStrokeWidth));
                    g2.drawOval(intInset, intInset, intDiameter, intDiameter);
                }
            };
            circlePanel.setPreferredSize(new Dimension(circleDiameter, circleDiameter));
            circlePanel.setOpaque(false);
            circlePanel.setLayout(new BorderLayout());

            JLabel number = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            number.setFont(FontUtil.getOutfitBoldFont(16f));
            number.setForeground(numberFgColor);
            circlePanel.add(number, BorderLayout.CENTER);

            // Add circle to layout
            gbc.gridx = i * 2;
            topRow.add(circlePanel, gbc);

            // Add bar between circles
            if (i < steps.length - 1) {
                JPanel bar = new JPanel();
                bar.setBackground(borderColor);
                bar.setPreferredSize(new Dimension(barWidth, 2));
                bar.setMaximumSize(new Dimension(barWidth, 2));
                bar.setMinimumSize(new Dimension(barWidth, 2));
                gbc.gridx = i * 2 + 1;
                topRow.add(bar, gbc);
            }
        }

        // === Bottom: Step Labels aligned under each circle ===
        JPanel bottomRow = new JPanel(new GridBagLayout());
        bottomRow.setOpaque(false);
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridy = 0;
        labelGbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < steps.length; i++) {
            JLabel label = new JLabel(steps[i], SwingConstants.CENTER);
            label.setFont(FontUtil.getOutfitFont(13f));
            label.setForeground(stepTextColor);

            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setOpaque(false);
            labelPanel.setPreferredSize(new Dimension(spacingBetweenCenters, 20));
            labelPanel.add(label, BorderLayout.CENTER);

            labelGbc.gridx = i * 2;
            bottomRow.add(labelPanel, labelGbc);
        }

        // === Assemble tracker ===
        stepTracker.add(topRow);
        stepTracker.add(Box.createVerticalStrut(8));
        stepTracker.add(bottomRow);

        return stepTracker;
    }
}