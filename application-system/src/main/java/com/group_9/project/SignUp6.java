package com.group_9.project;

import com.group_9.project.database.ApplicationService;
import com.group_9.project.database.ApplicationService.ApplicationInfo;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Final signup confirmation screen: displays your application info
 * and stores it in session so TrackingPage can pick it up.
 */
public class SignUp6 extends JFrame {
    private static final int RADIUS = 15;

    public SignUp6() {
        BaseFrameSetup.applyAppIcon(this);
        // 1) Base frame + background
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // 2) Main white rounded container
        JPanel container = createContentPanel();
        background.add(container);

        // 3) Inner vertical box for content
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        inner.setBounds(40, 40, 890, 615);
        container.add(inner);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Title
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setForeground(Color.decode("#2B0243"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(title);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Step tracker
        JPanel steps = new JPanel(new FlowLayout(FlowLayout.CENTER));
        steps.setOpaque(false);
        steps.add(CreateStepTracker.createStepTracker(3));
        inner.add(steps);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirmation icon
        ImageIcon confirmRaw = new ImageIcon(
            getClass().getClassLoader().getResource("images/confirmation-icn.png")
        );
        Image confirmImg = confirmRaw.getImage()
            .getScaledInstance(61, 61, Image.SCALE_SMOOTH);
        JLabel confirmIcon = new JLabel(new ImageIcon(confirmImg));
        confirmIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(confirmIcon);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        Color txtColor = Color.decode("#302E2E");

        // Success messages
        JLabel success = new JLabel(
            "APPLICATION SUBMITTED SUCCESSFULLY!", SwingConstants.CENTER
        );
        success.setFont(FontUtil.getOutfitFont(16f));
        success.setForeground(txtColor);
        success.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(success);

        inner.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel thanks = new JLabel(
            "Thank you for choosing NETNET!", SwingConstants.CENTER
        );
        thanks.setFont(FontUtil.getInterFont(15f));
        thanks.setForeground(txtColor);
        thanks.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(thanks);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel info = new JLabel(
            "Your application has been submitted and is now being processed.", 
            SwingConstants.CENTER
        );
        info.setFont(FontUtil.getInterFont(15f));
        info.setForeground(txtColor);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(info);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Track prompt
        JLabel trackTxt = new JLabel(
            "Track Your Application Status", SwingConstants.CENTER
        );
        trackTxt.setFont(FontUtil.getOutfitFont(15f));
        trackTxt.setForeground(txtColor);
        trackTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(trackTxt);

        inner.add(Box.createRigidArea(new Dimension(0, 5)));

        // 4) Rounded panel for application info
        JPanel roundedPanel = new JPanel();
        roundedPanel.setBackground(Color.WHITE);
        roundedPanel.setBorder(new RoundedComponents.RoundedBorder(RADIUS));
        roundedPanel.setPreferredSize(new Dimension(375, 118));
        roundedPanel.setMaximumSize(new Dimension(375, 118));
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));
        roundedPanel.add(Box.createVerticalGlue());

        // 5) Fetch application info & store in session
        String username = UserApplicationData.get("Username");
        ApplicationInfo infoObj = ApplicationService.getLatestApplicationFor(username);

        String appNumber = (infoObj != null)
            ? infoObj.applicationNo
            : "N/A";
        String appDate = (infoObj != null)
            ? infoObj.applicationDate
            : LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        // store into session for TrackingPage
        UserApplicationData.set("ApplicationNo", appNumber);
        UserApplicationData.set("ApplicationDate", appDate);

        // Display application number
        JLabel appNum = new JLabel(
            "Application No.  " + appNumber, SwingConstants.CENTER
        );
        appNum.setFont(FontUtil.getOutfitFont(15f));
        appNum.setForeground(txtColor);
        appNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(appNum);

        roundedPanel.add(Box.createRigidArea(new Dimension(0, 9)));

        // Display submission date
        JLabel dateLbl = new JLabel(
            "Date Submitted:  " + appDate, SwingConstants.CENTER
        );
        dateLbl.setFont(FontUtil.getOutfitFont(15f));
        dateLbl.setForeground(txtColor);
        dateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(dateLbl);

        roundedPanel.add(Box.createVerticalGlue());

        // center the roundedPanel
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(roundedPanel);
        inner.add(wrapper);

        inner.add(Box.createVerticalGlue());

        // 6) DONE button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(826, 50));
        RoundedComponents.RoundedButton doneBtn =
            new RoundedComponents.RoundedButton("DONE", 25);
        doneBtn.setPreferredSize(new Dimension(148, 41));
        doneBtn.setBackground(Color.decode("#2A0243"));
        doneBtn.setForeground(Color.WHITE);
        doneBtn.setFont(FontUtil.getOutfitBoldFont(16f));
        doneBtn.setBorderColor(Color.decode("#2A0243"));
        btnPanel.add(doneBtn);
        inner.add(btnPanel);

        // DONE action: go to TrackingPage
        doneBtn.addActionListener((ActionEvent e) -> {
            new TrackingPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    /** Builds the white rounded content panel with shadow. */
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                int shadow = 4;
                // shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(
                    shadow, shadow,
                    getWidth() - shadow, getHeight() - shadow,
                    25, 25
                );
                // panel
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(
                    0, 0,
                    getWidth() - shadow, getHeight() - shadow,
                    25, 25
                );
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(
                    0, 0,
                    getWidth() - shadow - 1, getHeight() - shadow - 1,
                    25, 25
                );
                g2.dispose();
            }
        };
        panel.setBounds(235, 165, 970, 695);
        panel.setOpaque(false);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp6::new);
    }
}
