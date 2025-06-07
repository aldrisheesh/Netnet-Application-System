package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.ToolTipUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ToolTipUtil {

    public static class CustomToolTip extends JToolTip {
        public CustomToolTip() {
            setFont(FontUtil.getInterFont(13f));
            setForeground(Color.WHITE);
            setBackground(new Color(60, 60, 60));
            setOpaque(false);
        }

        @Override
        public void updateUI() {
            setUI(new CustomToolTipUI());
        }
    }

    public static class CustomToolTipUI extends ToolTipUI {
        private final int arc = 10;

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            String tipText = ((JToolTip) c).getTipText();
            Font font = FontUtil.getInterFont(13f);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();

            int width = fm.stringWidth(tipText) + 16;
            int height = fm.getHeight() + 10;

            g2.setColor(new Color(60, 60, 60));
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            g2.setColor(Color.WHITE);
            g2.drawString(tipText, 8, fm.getAscent() + 5);
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            String tipText = ((JToolTip) c).getTipText();
            Font font = FontUtil.getInterFont(13f);
            FontMetrics fm = c.getFontMetrics(font);
            return new Dimension(fm.stringWidth(tipText) + 16, fm.getHeight() + 10);
        }
    }

    /**
     * Attaches a custom tooltip to a field that shows only when typing and hides on focus lost.
     */
    public static void attachCustomTooltip(JComponent target, String message) {
        CustomToolTip tooltip = new CustomToolTip();
        tooltip.setTipText(message);
        tooltip.setComponent(target);
        tooltip.setSize(tooltip.getPreferredSize());
    
        JWindow popupWindow = new JWindow();
        popupWindow.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        popupWindow.add(tooltip);
        popupWindow.pack();
    
        final boolean[] shown = {false};
    
        // Show tooltip when user types
        target.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!shown[0]) {
                    try {
                        Point location = target.getLocationOnScreen();
                        SwingUtilities.invokeLater(() -> {
                            popupWindow.setLocation(
                                location.x,
                                location.y - popupWindow.getHeight() - 4 // Position above
                            );
                            popupWindow.setVisible(true);
                            shown[0] = true;
                        });
                    } catch (IllegalComponentStateException ignored) {
                        // Component not yet shown on screen
                    }
                }
            }
        });
    
        // Hide tooltip on focus lost
        target.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                popupWindow.setVisible(false);
                shown[0] = false;
            }
    
            @Override
            public void focusGained(FocusEvent e) {
                shown[0] = false; // Reset flag
            }
        });
    }
    
}
