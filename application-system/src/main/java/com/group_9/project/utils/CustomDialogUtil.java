package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CustomDialogUtil {

    public static void showStyledErrorDialog(JFrame parent, String title, String message) {
        final int WIDTH = 440;
        final int HEIGHT = 260;
        final int BORDER_RADIUS = 30;

        // Create a transparent background overlay window
        JDialog overlay = new JDialog(parent, true);
        overlay.setUndecorated(true);
        overlay.setBackground(new Color(0, 0, 0, 80)); // semi-transparent black
        overlay.setLayout(null);
        overlay.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        overlay.setLocationRelativeTo(null);

        // Dialog panel with rounded border and background
        JPanel dialogPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = BORDER_RADIUS;

                // Fill background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

                // Draw border
                g2.setColor(new Color(124, 58, 189));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
                g2.dispose();
            }
        };
        dialogPanel.setOpaque(false);
        dialogPanel.setBounds(
                (overlay.getWidth() - WIDTH) / 2,
                (overlay.getHeight() - HEIGHT) / 2,
                WIDTH,
                HEIGHT
        );

        // Inner content panel using vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0, 0, 0, 0));
        contentPanel.setBounds(0, 0, WIDTH, HEIGHT);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        contentPanel.setOpaque(false);

        ImageIcon errorIcon = new ImageIcon(CustomDialogUtil.class.getResource("/icons/error.png"));
        Image scaledImage = errorIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(scaledImage));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);        


        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontUtil.getOutfitBoldFont(20f));
        titleLabel.setForeground(new Color(43, 2, 67));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(FontUtil.getInterFont(14f));
        messageLabel.setForeground(new Color(50, 46, 46));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // OK Button
        RoundedComponents.RoundedButton okButton = new RoundedComponents.RoundedButton("OK", 25);
        okButton.setFont(FontUtil.getOutfitBoldFont(15f));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(new Color(160, 108, 213));
        okButton.setPreferredSize(new Dimension(110, 42));
        okButton.setMaximumSize(new Dimension(110, 42));
        okButton.setBorder(new RoundedComponents.RoundedBorder(25));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover Effect
        ButtonHoverEffect.apply(
                okButton,
                new Color(138, 74, 194),
                Color.WHITE,
                new Color(160, 108, 213),
                Color.WHITE,
                new Color(189, 160, 224),
                new Color(160, 108, 213)
        );

        okButton.addActionListener((ActionEvent e) -> overlay.dispose());

        // Key Binding: Enter key triggers OK
        JRootPane rootPane = overlay.getRootPane();
        rootPane.setDefaultButton(okButton); // This will handle Enter key

        // Add components with spacing
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15))); // top padding

        contentPanel.add(icon);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(okButton);

        dialogPanel.add(contentPanel);
        overlay.add(dialogPanel);
        overlay.setVisible(true);
    }

    public static void showStyledInfoDialog(JFrame parent, String title, String message) {
        final int WIDTH = 440;
        final int HEIGHT = 260;
        final int BORDER_RADIUS = 30;

        // Create a transparent background overlay window
        JDialog overlay = new JDialog(parent, true);
        overlay.setUndecorated(true);
        overlay.setBackground(new Color(0, 0, 0, 80)); // semi-transparent black
        overlay.setLayout(null);
        overlay.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        overlay.setLocationRelativeTo(null);

        // Dialog panel with rounded border and background
        JPanel dialogPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = BORDER_RADIUS;

                // Fill background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

                // Draw border
                g2.setColor(new Color(124, 58, 189));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
                g2.dispose();
            }
        };
        dialogPanel.setOpaque(false);
        dialogPanel.setBounds(
                (overlay.getWidth() - WIDTH) / 2,
                (overlay.getHeight() - HEIGHT) / 2,
                WIDTH,
                HEIGHT
        );

        // Inner content panel using vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0, 0, 0, 0));
        contentPanel.setBounds(0, 0, WIDTH, HEIGHT);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        contentPanel.setOpaque(false);

        ImageIcon errorIcon = new ImageIcon(CustomDialogUtil.class.getResource("/icons/info.png"));
        Image scaledImage = errorIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(scaledImage));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);        


        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontUtil.getOutfitBoldFont(20f));
        titleLabel.setForeground(new Color(43, 2, 67));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(FontUtil.getInterFont(14f));
        messageLabel.setForeground(new Color(50, 46, 46));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // OK Button
        RoundedComponents.RoundedButton okButton = new RoundedComponents.RoundedButton("OK", 25);
        okButton.setFont(FontUtil.getOutfitBoldFont(15f));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(new Color(160, 108, 213));
        okButton.setPreferredSize(new Dimension(110, 42));
        okButton.setMaximumSize(new Dimension(110, 42));
        okButton.setBorder(new RoundedComponents.RoundedBorder(25));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover Effect
        ButtonHoverEffect.apply(
                okButton,
                new Color(138, 74, 194),
                Color.WHITE,
                new Color(160, 108, 213),
                Color.WHITE,
                new Color(189, 160, 224),
                new Color(160, 108, 213)
        );

        okButton.addActionListener((ActionEvent e) -> overlay.dispose());

        // Key Binding: Enter key triggers OK
        JRootPane rootPane = overlay.getRootPane();
        rootPane.setDefaultButton(okButton); // This will handle Enter key

        // Add components with spacing
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15))); // top padding

        contentPanel.add(icon);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(okButton);

        dialogPanel.add(contentPanel);
        overlay.add(dialogPanel);
        overlay.setVisible(true);
    }
}