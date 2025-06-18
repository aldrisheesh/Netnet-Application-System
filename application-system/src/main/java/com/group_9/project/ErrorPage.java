package com.group_9.project;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorPage extends JFrame {

    public ErrorPage() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 3);

        // 404 Message

        int yPos = 350;

        JLabel errorCode = new JLabel("<html><div style='font-weight:800;'>404</div></html>", SwingConstants.CENTER);
        errorCode.setFont(FontUtil.getOutfitBoldFont(120f).deriveFont(Font.BOLD));
        errorCode.setForeground(new Color(42, 2, 67));
        errorCode.setBounds(0, yPos, 1424, 100);
        background.add(errorCode);

        JLabel message = new JLabel("Oops... This page is not found.", SwingConstants.CENTER);
        message.setFont(FontUtil.getOutfitFont(40f));
        message.setForeground(new Color(42, 2, 67));
        message.setBounds(0, yPos + 100, 1424, 45);
        background.add(message);

        // Container panel to center both labels
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        returnPanel.setOpaque(false);
        returnPanel.setBounds(0, yPos + 165, 1424, 30);

        // "Return to " (normal text)
        JLabel returnText = new JLabel("Return to ");
        returnText.setFont(FontUtil.getInterFont(18f));
        returnText.setForeground(Color.DARK_GRAY);
        returnPanel.add(returnText);

        // "home page" (clickable link)
        JLabel homeLink = new JLabel("<html><u>home page</u></html>");
        homeLink.setFont(FontUtil.getInterFont(18f));
        homeLink.setForeground(new Color(62, 10, 118));
        homeLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Open Homepage and close ErrorPage when clicked
        homeLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String appNo = UserApplicationData.get("ApplicationNo");
                if (appNo != null && !appNo.isEmpty()) {
                    new TrackingPage().setVisible(true);
                } else {
                    new Homepage().setVisible(true);
                }
            }
        });

        returnPanel.add(homeLink);

        // Add combined panel
        background.add(returnPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ErrorPage().setVisible(true));
    }
}
