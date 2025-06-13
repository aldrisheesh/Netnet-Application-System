package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(int imageNumber) {
        try {
            String imageName = "images/background" + imageNumber + ".png";
            URL imageUrl = getClass().getClassLoader().getResource(imageName);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("Background image not found: " + imageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
