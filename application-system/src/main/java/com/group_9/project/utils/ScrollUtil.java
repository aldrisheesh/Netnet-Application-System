package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ScrollUtil {
    public static class PurpleScrollBarUI extends BasicScrollBarUI {
        private static final Color THUMB_COLOR = new Color(42, 2, 67);

        @Override
        protected void configureScrollBarColors() {
            thumbColor = THUMB_COLOR;
            trackColor = new Color(0, 0, 0, 0);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (!scrollbar.isEnabled() || thumbBounds.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(THUMB_COLOR);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}
    }

    public static class RoundedScrollContainer extends JPanel {
        private final int radius;

        public RoundedScrollContainer(Component content, int radius) {
            super(new BorderLayout());
            this.radius = radius;
            setOpaque(false);
            add(content, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setClip(clip);
            g2.setColor(getBackground());
            g2.fill(clip);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
