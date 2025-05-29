package com.group_9.project;

import java.awt.*;
import javax.swing.*;

public class SignUp6 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp6() {
        setTitle("Service Application");
        setSize(1440, 1024); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background panel
        BackgroundPanel  background = new BackgroundPanel(1);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // creates a container panel for the form
        JPanel container = new JPanel();
        container.setBackground(Color.decode("#fdf5fe"));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(800, 650));
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 38))
        ));

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color txtColor = Color.decode("#302E2E");

        // creates title label
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        container.add(title);

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(3)); // Active index is 3 for last step
        container.add(stepWrapper);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add confirmation icon right after step tracker
        ImageIcon confirmIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/confirmation-icn.png"));
        Image confirmImg = confirmIconRaw.getImage().getScaledInstance(61, 61, Image.SCALE_SMOOTH);
        JLabel confirmLabel = new JLabel(new ImageIcon(confirmImg));
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the icon
        container.add(confirmLabel);

        // Add some spacing after the icon
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel msgSuccess = new JLabel("APPLICATION SUBMITTED SUCCESSFULY!", SwingConstants.CENTER);
        msgSuccess.setFont(FontUtil.getOutfitFont(16f));
        msgSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        msgSuccess.setForeground(txtColor);
        container.add(msgSuccess);

        container.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel tyMsg = new JLabel("Thank you for choosing NETNET!", SwingConstants.CENTER);
        tyMsg.setFont(FontUtil.getInterFont(15f));
        tyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        tyMsg.setForeground(txtColor);
        container.add(tyMsg);

        JLabel msg = new JLabel("Your application has been submitted and is now being processed.", SwingConstants.CENTER);
        msg.setFont(FontUtil.getInterFont(15f));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setForeground(txtColor);
        container.add(msg);

        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel trackTxt = new JLabel("Track Your Application Status", SwingConstants.CENTER);
        trackTxt.setFont(FontUtil.getOutfitFont(15f));
        trackTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackTxt.setForeground(txtColor);
        container.add(trackTxt);

        // Add spacing after trackTxt
        container.add(Box.createRigidArea(new Dimension(0, 5)));

        // Create rounded panel container
        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 38)); // Light border color
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        roundedPanel.setOpaque(false);
        roundedPanel.setBackground(Color.WHITE);
        roundedPanel.setPreferredSize(new Dimension(375, 118));
        roundedPanel.setMaximumSize(new Dimension(375, 118));
        // Changed from GridBagLayout to BoxLayout with Y_AXIS for vertical stacking
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));

        // Add some padding at the top
        roundedPanel.add(Box.createVerticalGlue());

        JLabel appNum = new JLabel("Application No.  A00001", SwingConstants.CENTER);
        appNum.setFont(FontUtil.getOutfitFont(15f));
        appNum.setForeground(txtColor);
        appNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(appNum);

        roundedPanel.add(Box.createRigidArea(new Dimension(0, 9)));

        JLabel date = new JLabel("Date Submitted:  3/15/2010", SwingConstants.CENTER);
        date.setFont(FontUtil.getOutfitFont(15f));
        date.setForeground(txtColor);
        date.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(date);

        roundedPanel.add(Box.createRigidArea(new Dimension(0, 9)));

        JLabel status = new JLabel("Status: Pending Review", SwingConstants.CENTER);
        status.setFont(FontUtil.getOutfitFont(15f));
        status.setForeground(txtColor);
        status.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(status);

        // Add padding at the bottom
        roundedPanel.add(Box.createVerticalGlue());

        // Center the rounded panel
        JPanel roundedPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        roundedPanelWrapper.setOpaque(false);
        roundedPanelWrapper.add(roundedPanel);
        container.add(roundedPanelWrapper);

        // Add flexible space to push buttons to bottom
        container.add(Box.createVerticalGlue());

        // button panel for action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        // BACK button
        JButton backButton = new JButton("BACK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(43, 2, 67)); // border color #2B0243
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        Color defaultBg = new Color(255, 241, 255);   // #FFF1FF
        Color hoverBg = new Color(255, 248, 255);     // lightened
        Color clickBg = new Color(240, 220, 240);     // darkened

        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setForeground(new Color(43, 2, 67)); // text color to match border
        backButton.setBackground(defaultBg);
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setPreferredSize(new Dimension(140, 40));

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(defaultBg);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                backButton.setBackground(clickBg);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }
        });

        JButton nextButton = new JButton("NEXT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        // button properties
        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(50, 0, 90));
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setPreferredSize(new Dimension(140, 40));

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            Color nextDefaultBg = new Color(50, 0, 90);
            Color nextHoverBg = new Color(80, 0, 130);
            Color nextClickBg = new Color(30, 0, 60);

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextDefaultBg);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextClickBg);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(nextButton);
        container.add(buttonPanel);

        background.add(container);
        setContentPane(background);
        setVisible(true);
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp6::new);
    }
}