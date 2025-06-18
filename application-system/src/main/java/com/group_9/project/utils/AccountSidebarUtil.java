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

    public static JPanel createSidebar(JFrame frame, String activeItem) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBounds(50, 125, 200, 300);
        sidebar.setBackground(new Color(0, 0, 0, 0));
        sidebar.setOpaque(false);

        JLabel title = new JLabel("MY ACCOUNT");
        title.setFont(FontUtil.getOutfitBoldFont(25f));
        title.setForeground(new Color(42, 2, 67, 255));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        title.setOpaque(false);
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(15));

        String[] items = {"My Details", "My Address", "My Subscriptions", "Sign Out"};
        for (String item : items) {
            Color color = item.equals(activeItem) ? SELECTED_COLOR : DEFAULT_COLOR;
            JLabel label = createLabel("   " + item, color);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "My Details" -> {
                            if (!activeItem.equals("My Details")) {
                                new AccountDetailsPage().setVisible(true);
                                frame.dispose();
                            }
                        }
                        case "My Address" -> {
                            if (!activeItem.equals("My Address")) {
                                new AccountAddressPage().setVisible(true);
                                frame.dispose();
                            }
                        }
                        case "My Subscriptions" -> {
                            if (!activeItem.equals("My Subscriptions")) {
                                new AccountSubsPage().setVisible(true);
                                frame.dispose();
                            }
                        }
                        case "Sign Out" -> {
                            boolean confirm = CustomDialogUtil.showStyledConfirmDialog(
                                frame,
                                "Sign Out",
                                "Are you sure you want to sign out?"
                            );
                            if (confirm) {
                                UserApplicationData.clear();
                                new Homepage().setVisible(true);
                                frame.dispose();
                            }
                        }
                    }
                }

                @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!item.equals(activeItem)) {
                        label.setForeground(HOVER_COLOR);
                        label.repaint();
                    }
                }

                @Override public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!item.equals(activeItem)) {
                        label.setForeground(DEFAULT_COLOR);
                        label.repaint();
                    }
                }
            });

            sidebar.add(label);
            sidebar.add(Box.createVerticalStrut(30));
        }

        return sidebar;
    }

    private static JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontUtil.getOutfitFont(18f));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }
}
