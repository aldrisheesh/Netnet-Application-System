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
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 3);

        // 404 Message

        int intYPos = 350;

        JLabel lblErrorCode = new JLabel("<html><div style='font-weight:800;'>404</div></html>", SwingConstants.CENTER);
        lblErrorCode.setFont(FontUtil.getOutfitBoldFont(120f).deriveFont(Font.BOLD));
        lblErrorCode.setForeground(new Color(42, 2, 67));
        lblErrorCode.setBounds(0, intYPos, 1424, 100);
        pnlBackground.add(lblErrorCode);

        JLabel lblMessage = new JLabel("Oops... This page is not found.", SwingConstants.CENTER);
        lblMessage.setFont(FontUtil.getOutfitFont(40f));
        lblMessage.setForeground(new Color(42, 2, 67));
        lblMessage.setBounds(0, intYPos + 100, 1424, 45);
        pnlBackground.add(lblMessage);

        // Container panel to center both labels
        JPanel pnlReturn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlReturn.setOpaque(false);
        pnlReturn.setBounds(0, intYPos + 165, 1424, 30);

        // "Return to " (normal text)
        JLabel lblReturnText = new JLabel("Return to ");
        lblReturnText.setFont(FontUtil.getInterFont(18f));
        lblReturnText.setForeground(Color.DARK_GRAY);
        pnlReturn.add(lblReturnText);

        // "home page" (clickable link)
        JLabel lblHomeLink = new JLabel("<html><u>home page</u></html>");
        lblHomeLink.setFont(FontUtil.getInterFont(18f));
        lblHomeLink.setForeground(new Color(62, 10, 118));
        lblHomeLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Open Homepage and close ErrorPage when clicked
        lblHomeLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String appNo = UserApplicationData.get("strApplicationNo");
                if (appNo != null && !appNo.isEmpty()) {
                    new TrackingPage().setVisible(true);
                } else {
                    new Homepage().setVisible(true);
                }
            }
        });

        pnlReturn.add(lblHomeLink);

        // Add combined panel
        pnlBackground.add(pnlReturn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ErrorPage().setVisible(true));
    }
}
