package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.ToolTipUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ToolTipUtil {

    public static JWindow wndActivePopup = null;

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

    public static void attachCustomTooltip(JComponent cmpTarget, String strMessage) {
        CustomToolTip ttpTooltip = new CustomToolTip();
        ttpTooltip.setTipText(strMessage);
        ttpTooltip.setComponent(cmpTarget);
        ttpTooltip.setSize(ttpTooltip.getPreferredSize());

        JWindow wndPopup = new JWindow();
        wndPopup.setBackground(new Color(0, 0, 0, 0));
        wndPopup.add(ttpTooltip);
        wndPopup.pack();

        final boolean[] bolShown = {false};
        final boolean[] bolPersistent = {false}; // 👈 true = user typed, don't auto-hide
        final Timer[] arrAutoHideTimer = {null};

        Runnable rnbHideTip = () -> {
            wndPopup.setVisible(false);
            if (wndActivePopup == wndPopup) {
                wndActivePopup = null;
            }
            bolShown[0] = false;
            bolPersistent[0] = false;
        };

        Runnable rnbShowTip = () -> {
            try {
                Point ptLocation = cmpTarget.getLocationOnScreen();

                // Close any other tooltip
                if (wndActivePopup != null && wndActivePopup.isVisible()) {
                    wndActivePopup.setVisible(false);
                }

                SwingUtilities.invokeLater(() -> {
                    wndPopup.setLocation(
                        ptLocation.x,
                        ptLocation.y - wndPopup.getHeight() - 4
                    );
                    wndPopup.setVisible(true);
                    wndActivePopup = wndPopup;
                    bolShown[0] = true;
                });

            } catch (IllegalComponentStateException ignored) {}
        };

        // 🔤 Typed → show persistently
        cmpTarget.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                bolPersistent[0] = true;

                if (!bolShown[0]) {
                    rnbShowTip.run();
                }

                // Cancel auto-hide timer if any
                if (arrAutoHideTimer[0] != null) {
                    arrAutoHideTimer[0].cancel();
                    arrAutoHideTimer[0] = null;
                }
            }
        });

        // 🖱️ Click → show, then auto-hide after 1.5s unless user types
        cmpTarget.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rnbShowTip.run();
                bolPersistent[0] = false;

                // Reset and schedule timer
                if (arrAutoHideTimer[0] != null) {
                    arrAutoHideTimer[0].cancel();
                }

                Timer tmrTimer = new Timer();
                arrAutoHideTimer[0] = tmrTimer;
                tmrTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!bolPersistent[0]) {
                            SwingUtilities.invokeLater(rnbHideTip);
                        }
                    }
                }, 5000);
            }
        });

        cmpTarget.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                rnbHideTip.run();
            }
        });
    }
}
