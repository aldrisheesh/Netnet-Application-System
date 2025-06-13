package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final int cornerRadius;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false); // Important for rounded corners
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with rounded corners
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g); // Paint children
    }
}
