package com.group_9.project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

public class AccountSubs extends Template {
    public AccountSubs() {
        BackgroundPanel background = new BackgroundPanel(3); //bg
        background.setLayout(null);
        setContentPane(background);

        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledImage));
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"}; //navbar
        int xPos = 900;
        int spacing = 30;
        Color normalColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(normalColor);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    label.setForeground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    label.setForeground(normalColor);
                }

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "Home" -> {
                            new Homepage().setVisible(true);
                            dispose();
                        }
                        case "Plans", "About Us" -> {
                            new AboutUs().setVisible(true);
                            dispose();
                        }
                        case "Help & Support" -> {
                            new HelpSupportPage().setVisible(true);
                            dispose();
                        }
                    }
                }
            });

            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;
        }

        JPanel sidebar = createSidebar();
        background.add(sidebar);

        JPanel content = createContentPanel();
        background.add(content);

        JPanel detailsContainer = createDetailsContainer();
        content.add(detailsContainer);

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    JPanel createSidebar() { //sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBounds(50, 125, 200, 300);
        sidebar.setBackground(new Color(0, 0, 0, 0));
        sidebar.setOpaque(false);

        JLabel title = new JLabel("MY ACCOUNT");
        title.setFont(FontUtil.getOutfitBoldFont(25f));
        title.setForeground(new Color(42, 2, 67, 255));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(15));

        String[] items = {"My details", "My address", "My subscriptions"};
        Color selectedColor = new Color(132, 0, 159, 255);
        Color defaultColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : items) {
            Color color = item.equals("My subscriptions") ? selectedColor : defaultColor;
            JLabel label = makeSidebarLabel("   " + item, color);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "My details" -> {
                            new AccountDetails().setVisible(true);
                            dispose();
                        }
                        case "My address" -> {
                            new AccountAddress().setVisible(true);
                            dispose();
                        }
                        case "My subscriptions" -> {
                            new AccountSubs().setVisible(true);
                            dispose();
                        }
                    }
                }

                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!item.equals("My subscriptions")) label.setForeground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!item.equals("My subscriptions")) label.setForeground(defaultColor);
                }
            });

            sidebar.add(label);
            sidebar.add(Box.createVerticalStrut(30));
        }

        return sidebar;
    }

    JPanel createContentPanel() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - shadowOffset - 1, getHeight() - shadowOffset - 1, 25, 25);
                g2.dispose();
            }
        };
        content.setBounds(290, 150, 1020, 720);
        content.setOpaque(false);
        return content;
    }

    JPanel createDetailsContainer() {
        JPanel container = new JPanel(null);
        container.setBackground(new Color(0, 0, 0, 0));
        container.setBounds(0, 0, 1250, 700);

        JLabel titleLabel = new JLabel("My subscriptions"); //header1
        titleLabel.setFont(FontUtil.getOutfitBoldFont(26f));
        titleLabel.setForeground(new Color(42, 2, 67, 255));
        titleLabel.setBounds(70, 50, 300, 30);
        container.add(titleLabel);

        JLabel sectionLabel = new JLabel("SERVICE AND PLAN SUBSCRIPTIONS"); //header2
        sectionLabel.setFont(FontUtil.getOutfitFont(16f));
        sectionLabel.setBounds(70, 100, 400, 20);
        container.add(sectionLabel);

        JSeparator sep = new JSeparator(); //line separator
        sep.setBounds(70, 130, 880, 1);
        sep.setForeground(new Color(180, 180, 180));
        container.add(sep);

        container.add(createWhiteBox(110, 180, "FIBERX 1500", "P 1500", "P 125/24mo.", "A00001", "03/15/2010", "PRODUCT PAID IN FULL")); //applicant 1 details
        container.add(createWhiteBox(530, 180, "FIBER Xtreme 4500", "P 4500", "WAIVED", "A00002", "01/10/2012", "PRODUCT PAID IN INSTALLMENT")); //applicant 2 details

        JLabel showMore = new JLabel("<html><u>Show More</u></html>"); //redirect to plans page
        showMore.setFont(FontUtil.getOutfitFont(14f));
        showMore.setForeground(new Color(132, 0, 159));
        showMore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showMore.setBounds(400, 420, 100, 20);
        container.add(showMore);

        showMore.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new ErrorPage().setVisible(true);
                SwingUtilities.getWindowAncestor(showMore).dispose(); 
            }
        });

        return container;
    }

    JPanel createWhiteBox(int x, int y, String product, String monthlyFee, String installFee,
                                  String appNo, String submittedDate, String status) {
        JPanel applicantBox = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }

            public boolean isOpaque() {
                return false;
            }
        };
        applicantBox.setBounds(x, y, 380, 220);

        JLabel header = new JLabel("Product and Service");
        header.setFont(FontUtil.getOutfitFont(17f));
        header.setBounds(20, 15, 200, 20);
        applicantBox.add(header);

        JLabel amount = new JLabel("Amount");
        amount.setFont(FontUtil.getOutfitFont(17f));
        amount.setBounds(275, 15, 80, 20);
        applicantBox.add(amount);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 50, 340, 1);
        sep.setForeground(Color.BLACK);
        applicantBox.add(sep);

        JLabel plan = new JLabel("<html><b>" + product + "</b><br>Monthly Service Fee<br>Installation Fee</html>");
        plan.setFont(FontUtil.getOutfitFont(18f));
        plan.setBounds(20, 65, 200, 60);
        applicantBox.add(plan);

        JLabel price = new JLabel("<html><br>" + monthlyFee + "<br>" + installFee + "</html>");
        price.setFont(FontUtil.getOutfitFont(18f));
        price.setBounds(260, 65, 100, 60);
        applicantBox.add(price);

        JLabel details = new JLabel("<html><br><br>APPLICATION NO. " + appNo +
                "<br>DATE SUBMITTED: " + submittedDate + "<br>" + status + "</html>");
        details.setFont(FontUtil.getOutfitBoldFont(12f));
        details.setBounds(20, 130, 300, 75);
        applicantBox.add(details);

        return applicantBox;
    }

    JLabel makeSidebarLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontUtil.getOutfitFont(18f));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountSubs().setVisible(true));
    }
}