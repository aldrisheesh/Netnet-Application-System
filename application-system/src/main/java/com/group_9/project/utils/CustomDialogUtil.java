package com.group_9.project.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomDialogUtil {

    public static void showStyledErrorDialog(JFrame parent, String title, String message) {
        final int WIDTH = 440;
        final int HEIGHT = 260;
        final int BORDER_RADIUS = 30;

        // Create a transparent background overlay window
        JDialog dlgOverlay = new JDialog(parent, true);
        dlgOverlay.setUndecorated(true);
        dlgOverlay.setBackground(new Color(0, 0, 0, 80)); // semi-transparent black
        dlgOverlay.setLayout(null);
        dlgOverlay.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        dlgOverlay.setLocationRelativeTo(null);

        // Dialog panel with rounded border and background
        JPanel pnlDialog = new JPanel(null) {
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
        pnlDialog.setOpaque(false);
        pnlDialog.setBounds(
                (dlgOverlay.getWidth() - WIDTH) / 2,
                (dlgOverlay.getHeight() - HEIGHT) / 2,
                WIDTH,
                HEIGHT
        );

        // Inner content panel using vertical layout
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(new Color(0, 0, 0, 0));
        pnlContent.setBounds(0, 0, WIDTH, HEIGHT);
        pnlContent.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        pnlContent.setOpaque(false);

        ImageIcon icnError = new ImageIcon(CustomDialogUtil.class.getResource("/icons/error.png"));
        Image imgScaled = icnError.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel lblIcon = new JLabel(new ImageIcon(imgScaled));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(20f));
        lblTitle.setForeground(new Color(43, 2, 67));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMessage = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        lblMessage.setFont(FontUtil.getInterFont(14f));
        lblMessage.setForeground(new Color(50, 46, 46));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        // OK Button
        RoundedComponents.RoundedButton cmdOk = new RoundedComponents.RoundedButton("OK", 25);
        cmdOk.setFont(FontUtil.getOutfitBoldFont(15f));
        cmdOk.setForeground(Color.WHITE);
        cmdOk.setBackground(new Color(160, 108, 213));
        cmdOk.setPreferredSize(new Dimension(110, 42));
        cmdOk.setMaximumSize(new Dimension(110, 42));
        cmdOk.setBorder(new RoundedComponents.RoundedBorder(25));
        cmdOk.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover Effect
        ButtonHoverEffect.apply(
                cmdOk,
                new Color(138, 74, 194),
                Color.WHITE,
                new Color(160, 108, 213),
                Color.WHITE,
                new Color(189, 160, 224),
                new Color(160, 108, 213)
        );

        cmdOk.addActionListener((ActionEvent e) -> dlgOverlay.dispose());

        // Key Binding: Enter key triggers OK
        JRootPane rootPane = dlgOverlay.getRootPane();
        rootPane.setDefaultButton(cmdOk); // This will handle Enter key

        // Add components with spacing
        pnlContent.add(Box.createRigidArea(new Dimension(0, 15))); // top padding

        pnlContent.add(lblIcon);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 12)));

        pnlContent.add(lblTitle);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 8)));

        pnlContent.add(lblMessage);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 20)));

        pnlContent.add(cmdOk);

        pnlDialog.add(pnlContent);
        dlgOverlay.add(pnlDialog);
        dlgOverlay.setVisible(true);
    }

    public static void showStyledInfoDialog(JFrame parent, String title, String message) {
        final int WIDTH = 440;
        final int HEIGHT = 260;
        final int BORDER_RADIUS = 30;

        // Create a transparent background overlay window
        JDialog dlgOverlay = new JDialog(parent, true);
        dlgOverlay.setUndecorated(true);
        dlgOverlay.setBackground(new Color(0, 0, 0, 80)); // semi-transparent black
        dlgOverlay.setLayout(null);
        dlgOverlay.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        dlgOverlay.setLocationRelativeTo(null);

        // Dialog panel with rounded border and background
        JPanel pnlDialog = new JPanel(null) {
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
        pnlDialog.setOpaque(false);
        pnlDialog.setBounds(
                (dlgOverlay.getWidth() - WIDTH) / 2,
                (dlgOverlay.getHeight() - HEIGHT) / 2,
                WIDTH,
                HEIGHT
        );

        // Inner content panel using vertical layout
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(new Color(0, 0, 0, 0));
        pnlContent.setBounds(0, 0, WIDTH, HEIGHT);
        pnlContent.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        pnlContent.setOpaque(false);

        ImageIcon icnInfo = new ImageIcon(CustomDialogUtil.class.getResource("/icons/info.png"));
        Image imgScaled = icnInfo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel lblIcon = new JLabel(new ImageIcon(imgScaled));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(20f));
        lblTitle.setForeground(new Color(43, 2, 67));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMessage = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        lblMessage.setFont(FontUtil.getInterFont(14f));
        lblMessage.setForeground(new Color(50, 46, 46));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        // OK Button
        RoundedComponents.RoundedButton cmdOk = new RoundedComponents.RoundedButton("OK", 25);
        cmdOk.setFont(FontUtil.getOutfitBoldFont(15f));
        cmdOk.setForeground(Color.WHITE);
        cmdOk.setBackground(new Color(160, 108, 213));
        cmdOk.setPreferredSize(new Dimension(110, 42));
        cmdOk.setMaximumSize(new Dimension(110, 42));
        cmdOk.setBorder(new RoundedComponents.RoundedBorder(25));
        cmdOk.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover Effect
        ButtonHoverEffect.apply(
                cmdOk,
                new Color(138, 74, 194),
                Color.WHITE,
                new Color(160, 108, 213),
                Color.WHITE,
                new Color(189, 160, 224),
                new Color(160, 108, 213)
        );

        cmdOk.addActionListener((ActionEvent e) -> dlgOverlay.dispose());

        // Key Binding: Enter key triggers OK
        JRootPane rootPane = dlgOverlay.getRootPane();
        rootPane.setDefaultButton(cmdOk); // This will handle Enter key

        // Add components with spacing
        pnlContent.add(Box.createRigidArea(new Dimension(0, 15))); // top padding

        pnlContent.add(lblIcon);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 12)));

        pnlContent.add(lblTitle);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 8)));

        pnlContent.add(lblMessage);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 20)));

        pnlContent.add(cmdOk);

        pnlDialog.add(pnlContent);
        dlgOverlay.add(pnlDialog);
        dlgOverlay.setVisible(true);
    }
    public static boolean showStyledConfirmDialog(JFrame parent, String title, String message) {
        final int WIDTH = 440;
        final int HEIGHT = 260;
        final int BORDER_RADIUS = 30;
        final boolean[] arrResult = { false };

        JDialog dlgOverlay = new JDialog(parent, true);
        dlgOverlay.setUndecorated(true);
        dlgOverlay.setBackground(new Color(0, 0, 0, 80));
        dlgOverlay.setLayout(null);
        dlgOverlay.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        dlgOverlay.setLocationRelativeTo(null);

        JPanel pnlDialog = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arc = BORDER_RADIUS;
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);
                g2.setColor(new Color(124, 58, 189));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
                g2.dispose();
            }
        };
        pnlDialog.setOpaque(false);
        pnlDialog.setBounds((dlgOverlay.getWidth() - WIDTH) / 2, (dlgOverlay.getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(new Color(0, 0, 0, 0));
        pnlContent.setBounds(0, 0, WIDTH, HEIGHT);
        pnlContent.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        pnlContent.setOpaque(false);

        ImageIcon icnInfo = new ImageIcon(CustomDialogUtil.class.getResource("/icons/help.png"));
        Image imgScaled = icnInfo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel lblIcon = new JLabel(new ImageIcon(imgScaled));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(20f));
        lblTitle.setForeground(new Color(43, 2, 67));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMessage = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        lblMessage.setFont(FontUtil.getInterFont(14f));
        lblMessage.setForeground(new Color(50, 46, 46));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);

        RoundedComponents.RoundedButton cmdYes = new RoundedComponents.RoundedButton("Yes", 25);
        cmdYes.setFont(FontUtil.getOutfitBoldFont(15f));
        cmdYes.setForeground(Color.WHITE);
        cmdYes.setBackground(new Color(160, 108, 213));
        cmdYes.setPreferredSize(new Dimension(110, 42));
        cmdYes.setMaximumSize(new Dimension(110, 42));
        cmdYes.setBorder(new RoundedComponents.RoundedBorder(25));

        RoundedComponents.RoundedButton cmdNo = new RoundedComponents.RoundedButton("No", 25);
        cmdNo.setFont(FontUtil.getOutfitBoldFont(15f));
        cmdNo.setForeground(Color.WHITE);
        cmdNo.setBackground(new Color(160, 108, 213));
        cmdNo.setPreferredSize(new Dimension(110, 42));
        cmdNo.setMaximumSize(new Dimension(110, 42));
        cmdNo.setBorder(new RoundedComponents.RoundedBorder(25));

        ButtonHoverEffect.apply(cmdYes, new Color(138, 74, 194), Color.WHITE, new Color(160, 108, 213), Color.WHITE, new Color(189, 160, 224), new Color(160, 108, 213));
        ButtonHoverEffect.apply(cmdNo,  new Color(138, 74, 194), Color.WHITE, new Color(160, 108, 213), Color.WHITE, new Color(189, 160, 224), new Color(160, 108, 213));

        cmdYes.addActionListener((ActionEvent e) -> { arrResult[0] = true; dlgOverlay.dispose(); });
        cmdNo.addActionListener((ActionEvent e) -> dlgOverlay.dispose());

        pnlButtons.add(cmdYes);
        pnlButtons.add(Box.createRigidArea(new Dimension(20, 0)));
        pnlButtons.add(cmdNo);
        pnlButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRootPane rootPane = dlgOverlay.getRootPane();
        rootPane.setDefaultButton(cmdYes);

        pnlContent.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlContent.add(lblIcon);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 12)));
        pnlContent.add(lblTitle);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 8)));
        pnlContent.add(lblMessage);
        pnlContent.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlContent.add(pnlButtons);

        pnlDialog.add(pnlContent);
        dlgOverlay.add(pnlDialog);
        dlgOverlay.setVisible(true);
        return arrResult[0];
    }
}
