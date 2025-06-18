package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;

public class FormUIUtil {
    private static final int SHADOW_OFFSET = 4;

    public static JPanel createRoundedShadowPanel(int x, int y, int width, int height) {
        return createRoundedShadowPanel(x, y, width, height, 25);
    }

    public static JPanel createRoundedShadowPanel(int x, int y, int width, int height, int radius) {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int s = SHADOW_OFFSET;
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(s, s, getWidth() - s, getHeight() - s, radius, radius);
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - s, getHeight() - s, radius, radius);
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - s - 1, getHeight() - s - 1, radius, radius);
                g2.dispose();
            }
        };
        panel.setBounds(x, y, width, height);
        panel.setOpaque(false);
        return panel;
    }
}
