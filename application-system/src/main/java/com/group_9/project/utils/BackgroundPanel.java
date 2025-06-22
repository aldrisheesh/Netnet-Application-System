package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private Image imgBackground;

    public BackgroundPanel(int intImageNumber) {
        try {
            String strImageName = "images/background" + intImageNumber + ".png";
            URL urlImage = getClass().getClassLoader().getResource(strImageName);
            if (urlImage != null) {
                imgBackground = new ImageIcon(urlImage).getImage();
            } else {
                System.err.println("Background image not found: " + strImageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgBackground != null) {
            g.drawImage(imgBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
