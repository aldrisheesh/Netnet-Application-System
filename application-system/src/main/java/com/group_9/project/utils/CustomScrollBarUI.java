package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/** Utility ScrollBarUI with a rounded purple thumb. */
public class CustomScrollBarUI extends BasicScrollBarUI {
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
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // No track painting
    }
}
