package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.ToolTipUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ToolTipUtil {

    public static JWindow activePopup = null;

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

    public static void attachCustomTooltip(JComponent target, String message) {
        CustomToolTip tooltip = new CustomToolTip();
        tooltip.setTipText(message);
        tooltip.setComponent(target);
        tooltip.setSize(tooltip.getPreferredSize());

        JWindow popupWindow = new JWindow();
        popupWindow.setBackground(new Color(0, 0, 0, 0));
        popupWindow.add(tooltip);
        popupWindow.pack();

        final boolean[] shown = {false};
        final boolean[] persistent = {false}; // 👈 true = user typed, don't auto-hide
        final Timer[] autoHideTimer = {null};

        Runnable hideTip = () -> {
            popupWindow.setVisible(false);
            if (activePopup == popupWindow) {
                activePopup = null;
            }
            shown[0] = false;
            persistent[0] = false;
        };

        Runnable showTip = () -> {
            try {
                Point location = target.getLocationOnScreen();

                // Close any other tooltip
                if (activePopup != null && activePopup.isVisible()) {
                    activePopup.setVisible(false);
                }

                SwingUtilities.invokeLater(() -> {
                    popupWindow.setLocation(
                        location.x,
                        location.y - popupWindow.getHeight() - 4
                    );
                    popupWindow.setVisible(true);
                    activePopup = popupWindow;
                    shown[0] = true;
                });

            } catch (IllegalComponentStateException ignored) {}
        };

        // 🔤 Typed → show persistently
        target.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                persistent[0] = true;

                if (!shown[0]) {
                    showTip.run();
                }

                // Cancel auto-hide timer if any
                if (autoHideTimer[0] != null) {
                    autoHideTimer[0].cancel();
                    autoHideTimer[0] = null;
                }
            }
        });

        // 🖱️ Click → show, then auto-hide after 1.5s unless user types
        target.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showTip.run();
                persistent[0] = false;

                // Reset and schedule timer
                if (autoHideTimer[0] != null) {
                    autoHideTimer[0].cancel();
                }

                Timer timer = new Timer();
                autoHideTimer[0] = timer;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!persistent[0]) {
                            SwingUtilities.invokeLater(hideTip);
                        }
                    }
                }, 5000);
            }
        });

        target.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                hideTip.run();
            }
        });
    }
}
