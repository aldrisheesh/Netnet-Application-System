package com.group_9.project;

import java.awt.*;
import javax.swing.*;

public class SignUp6 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp6() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color txtColor = Color.decode("#302E2E");

        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        innerContent.add(title);

        // adds spacing
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(3)); // Active index is 0 for first step
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add confirmation icon right after step tracker
        ImageIcon confirmIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/confirmation-icn.png"));
        Image confirmImg = confirmIconRaw.getImage().getScaledInstance(61, 61, Image.SCALE_SMOOTH);
        JLabel confirmLabel = new JLabel(new ImageIcon(confirmImg));
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the icon
        innerContent.add(confirmLabel);

        // Add some spacing after the icon
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel msgSuccess = new JLabel("APPLICATION SUBMITTED SUCCESSFULLY!", SwingConstants.CENTER);
        msgSuccess.setFont(FontUtil.getOutfitFont(16f));
        msgSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        msgSuccess.setForeground(txtColor);
        innerContent.add(msgSuccess);

        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel tyMsg = new JLabel("Thank you for choosing NETNET!", SwingConstants.CENTER);
        tyMsg.setFont(FontUtil.getInterFont(15f));
        tyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        tyMsg.setForeground(txtColor);
        innerContent.add(tyMsg);

        JLabel msg = new JLabel("Your application has been submitted and is now being processed.", SwingConstants.CENTER);
        msg.setFont(FontUtil.getInterFont(15f));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setForeground(txtColor);
        innerContent.add(msg);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel trackTxt = new JLabel("Track Your Application Status", SwingConstants.CENTER);
        trackTxt.setFont(FontUtil.getOutfitFont(15f));
        trackTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackTxt.setForeground(txtColor);
        innerContent.add(trackTxt);

        // Add spacing after trackTxt
        innerContent.add(Box.createRigidArea(new Dimension(0, 5)));

        // Create rounded panel container with RoundedBorder
        JPanel roundedPanel = new JPanel();
        roundedPanel.setOpaque(true);
        roundedPanel.setBackground(Color.WHITE);
        roundedPanel.setBorder(new RoundedComponents.RoundedBorder(RADIUS)); // Apply RoundedBorder instead of custom paint
        roundedPanel.setPreferredSize(new Dimension(375, 118));
        roundedPanel.setMaximumSize(new Dimension(375, 118));
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
        innerContent.add(roundedPanelWrapper);

        // Add flexible space to push buttons to bottom
        innerContent.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton doneButton = new RoundedComponents.RoundedButton("DONE", 25);
        doneButton.setPreferredSize(new Dimension(148, 41));
        doneButton.setBackground(Color.decode("#2A0243"));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(FontUtil.getOutfitBoldFont(16f));
        doneButton.setBorderColor(Color.decode("#2A0243"));

        buttonPanel.add(doneButton);
        innerContent.add(buttonPanel);

        container.add(innerContent);
        setVisible(true);
    }

    // Custom content panel with rounded corners and shadow - now also using RoundedBorder
    private JPanel createContentPanel() { //main container
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow first (offset to bottom-right)
                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20)); // Semi-transparent black for shadow
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                
                // Draw main panel
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.dispose();
            }
        };
        content.setBounds(235, 165, 970, 695);
        content.setOpaque(false);
        // Add a subtle rounded border to the main content panel
        content.setBorder(new RoundedComponents.RoundedBorder(25));
        return content;
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp6::new);
    }
}