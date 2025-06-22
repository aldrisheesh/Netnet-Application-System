package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverEffect {

    public static void apply(JButton cmdButton, Color clrHoverBg, Color clrHoverFg, Color clrNormalBg, Color clrNormalFg,
                             Color clrHoverBorder, Color clrNormalBorder) {
        cmdButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdButton.setBackground(clrNormalBg);
        cmdButton.setForeground(clrNormalFg);
        cmdButton.setBorder(new CustomLineBorder(clrNormalBorder, 2));

        cmdButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cmdButton.setBackground(clrHoverBg);
                cmdButton.setForeground(clrHoverFg);
                cmdButton.setBorder(new CustomLineBorder(clrHoverBorder, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cmdButton.setBackground(clrNormalBg);
                cmdButton.setForeground(clrNormalFg);
                cmdButton.setBorder(new CustomLineBorder(clrNormalBorder, 2));
            }
        });
    }

    // Static inner class for the custom line border
    private static class CustomLineBorder extends AbstractBorder {
        private final Color clrColor;
        private final int intThickness;

        public CustomLineBorder(Color clrColor, int intThickness) {
            this.clrColor = clrColor;
            this.intThickness = intThickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(clrColor);
            g2.setStroke(new BasicStroke(intThickness));
            g2.drawRect(x + intThickness / 2, y + intThickness / 2, width - intThickness, height - intThickness);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(intThickness, intThickness, intThickness, intThickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(intThickness, intThickness, intThickness, intThickness);
            return insets;
        }
    }
}
