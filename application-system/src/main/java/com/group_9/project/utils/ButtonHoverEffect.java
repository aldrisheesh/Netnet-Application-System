package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverEffect {

    public static void apply(JButton button, Color hoverBg, Color hoverFg, Color normalBg, Color normalFg,
                             Color hoverBorder, Color normalBorder) {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(normalBg);
        button.setForeground(normalFg);
        button.setBorder(new CustomLineBorder(normalBorder, 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
                button.setForeground(hoverFg);
                button.setBorder(new CustomLineBorder(hoverBorder, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalBg);
                button.setForeground(normalFg);
                button.setBorder(new CustomLineBorder(normalBorder, 2));
            }
        });
    }

    // Static inner class for the custom line border
    private static class CustomLineBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;

        public CustomLineBorder(Color color, int thickness) {
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(thickness, thickness, thickness, thickness);
            return insets;
        }
    }
}
