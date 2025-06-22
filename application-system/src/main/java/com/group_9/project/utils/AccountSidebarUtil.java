package com.group_9.project.utils;

import com.group_9.project.*;
import com.group_9.project.session.UserApplicationData;

import javax.swing.*;
import java.awt.*;

/** Utility for creating the sidebar in account pages. */
public final class AccountSidebarUtil {
    private AccountSidebarUtil() {}

    private static final Color SELECTED_COLOR = new Color(132, 0, 159, 255);
    private static final Color DEFAULT_COLOR  = new Color(22, 6, 48, 128);
    private static final Color HOVER_COLOR    = new Color(62, 10, 118);

    public static JPanel createSidebar(JFrame frmParent, String strActiveItem) {
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBounds(50, 125, 200, 300);
        pnlSidebar.setBackground(new Color(0, 0, 0, 0));
        pnlSidebar.setOpaque(false);

        JLabel lblTitle = new JLabel("MY ACCOUNT");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(25f));
        lblTitle.setForeground(new Color(42, 2, 67, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblTitle.setOpaque(false);
        pnlSidebar.add(lblTitle);
        pnlSidebar.add(Box.createVerticalStrut(15));

        String[] arrItems = {"My Details", "My Address", "My Subscriptions", "Sign Out"};
        for (String strItem : arrItems) {
            Color colCurrent = strItem.equals(strActiveItem) ? SELECTED_COLOR : DEFAULT_COLOR;
            JLabel lblOption = createLabel("   " + strItem, colCurrent);
            lblOption.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            lblOption.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (strItem) {
                        case "My Details" -> {
                            if (!strActiveItem.equals("My Details")) {
                                SwingUtilities.invokeLater(() -> {
                                    new AccountDetailsPage().setVisible(true);
                                    frmParent.dispose();
                                });
                            }
                        }
                        case "My Address" -> {
                            if (!strActiveItem.equals("My Address")) {
                                SwingUtilities.invokeLater(() -> {
                                    new AccountAddressPage().setVisible(true);
                                    frmParent.dispose();
                                });
                            }
                        }
                        case "My Subscriptions" -> {
                            if (!strActiveItem.equals("My Subscriptions")) {
                                SwingUtilities.invokeLater(() -> {
                                    new AccountSubsPage().setVisible(true);
                                    frmParent.dispose();
                                });
                            }
                        }
                        case "Sign Out" -> {
                            boolean confirm = CustomDialogUtil.showStyledConfirmDialog(
                                frmParent,
                                "Sign Out",
                                "Are you sure you want to sign out?"
                            );
                            if (confirm) {
                                UserApplicationData.clear();
                                new Homepage().setVisible(true);
                                frmParent.dispose();
                            }
                        }
                    }
                }

                @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!strItem.equals(strActiveItem)) {
                        lblOption.setForeground(HOVER_COLOR);
                        lblOption.repaint();
                    }
                }

                @Override public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!strItem.equals(strActiveItem)) {
                        lblOption.setForeground(DEFAULT_COLOR);
                        lblOption.repaint();
                    }
                }
            });

            pnlSidebar.add(lblOption);
            pnlSidebar.add(Box.createVerticalStrut(30));
        }

        return pnlSidebar;
    }

    private static JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontUtil.getOutfitFont(18f));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }
}
