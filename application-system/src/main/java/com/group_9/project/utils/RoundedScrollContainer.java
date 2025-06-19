package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/** Wrapper panel that rounds its child scroll pane. */
public class RoundedScrollContainer extends JPanel {
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
