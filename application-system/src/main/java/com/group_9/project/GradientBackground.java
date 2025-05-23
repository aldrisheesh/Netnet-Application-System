package com.group_9.project;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class GradientBackground extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Darker purple blob, shifted slightly to the left
        drawRadialGradient(g2d, new Color(90, 0, 180, 160), new Point2D.Double(width * 0.01, height * 0.5), 1000);

        // Aqua/Teal blob on the top-right
        drawRadialGradient(g2d, new Color(0, 255, 200, 120), new Point2D.Double(width * 0.90, height * 0.25), 700);

        // Soft pink glow from the bottom center
        drawRadialGradient(g2d, new Color(255, 150, 200, 100), new Point2D.Double(width * 0.5, height * 0.95), 1000);
    }

    private void drawRadialGradient(Graphics2D g2d, Color color, Point2D center, float radius) {
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {color, new Color(255, 255, 255, 0)};
        RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(rgp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
