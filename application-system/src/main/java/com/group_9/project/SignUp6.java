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

public class SignUp6 extends JFrame {
    private static final int intRadius = 15;

    public SignUp6() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        pnlBackground.add(pnlContainer);

        JPanel pnlInner = new JPanel();
        pnlInner.setLayout(new BoxLayout(pnlInner, BoxLayout.Y_AXIS));
        pnlInner.setOpaque(false);
        pnlInner.setBounds(40, 40, 890, 615);
        pnlContainer.add(pnlInner);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setForeground(Color.decode("#2B0243"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblTitle);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlSteps = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlSteps.setOpaque(false);
        pnlSteps.add(CreateStepTracker.createStepTracker(3));
        pnlInner.add(pnlSteps);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        ImageIcon imgConfirmRaw = new ImageIcon(
            getClass().getClassLoader().getResource("images/confirmation-icn.png")
        );
        Image imgConfirm = imgConfirmRaw.getImage()
            .getScaledInstance(61, 61, Image.SCALE_SMOOTH);
        JLabel lblConfirmIcon = new JLabel(new ImageIcon(imgConfirm));
        lblConfirmIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblConfirmIcon);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        Color clrTxt = Color.decode("#302E2E");

        JLabel lblSuccess = new JLabel(
            "APPLICATION SUBMITTED SUCCESSFULLY!", SwingConstants.CENTER
        );
        lblSuccess.setFont(FontUtil.getOutfitFont(16f));
        lblSuccess.setForeground(clrTxt);
        lblSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblSuccess);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblThanks = new JLabel(
            "Thank you for choosing NETNET!", SwingConstants.CENTER
        );
        lblThanks.setFont(FontUtil.getInterFont(15f));
        lblThanks.setForeground(clrTxt);
        lblThanks.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblThanks);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblInfo = new JLabel(
            "Your application has been submitted and is now being processed.", 
            SwingConstants.CENTER
        );
        lblInfo.setFont(FontUtil.getInterFont(15f));
        lblInfo.setForeground(clrTxt);
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblInfo);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblTrackTxt = new JLabel(
            "Track Your Application Status", SwingConstants.CENTER
        );
        lblTrackTxt.setFont(FontUtil.getOutfitFont(15f));
        lblTrackTxt.setForeground(clrTxt);
        lblTrackTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInner.add(lblTrackTxt);

        pnlInner.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel pnlRounded = new JPanel();
        pnlRounded.setBackground(Color.WHITE);
        pnlRounded.setBorder(new RoundedComponents.RoundedBorder(intRadius));
        pnlRounded.setPreferredSize(new Dimension(375, 118));
        pnlRounded.setMaximumSize(new Dimension(375, 118));
        pnlRounded.setLayout(new BoxLayout(pnlRounded, BoxLayout.Y_AXIS));
        pnlRounded.add(Box.createVerticalGlue());

        String strUsername = UserApplicationData.get("Username");
        ApplicationInfo objInfo = ApplicationService.getLatestApplicationFor(strUsername);

        String strAppNumber = (objInfo != null)
            ? objInfo.strApplicationNo
            : "N/A";
        String strAppDate = (objInfo != null)
            ? objInfo.strApplicationDate
            : LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        UserApplicationData.set("ApplicationNo", strAppNumber);
        UserApplicationData.set("ApplicationDate", strAppDate);

        JLabel lblAppNum = new JLabel(
            "Application No.  " + strAppNumber, SwingConstants.CENTER
        );
        lblAppNum.setFont(FontUtil.getOutfitFont(15f));
        lblAppNum.setForeground(clrTxt);
        lblAppNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRounded.add(lblAppNum);

        pnlRounded.add(Box.createRigidArea(new Dimension(0, 9)));

        JLabel lblDate = new JLabel(
            "Date Submitted:  " + strAppDate, SwingConstants.CENTER
        );
        lblDate.setFont(FontUtil.getOutfitFont(15f));
        lblDate.setForeground(clrTxt);
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRounded.add(lblDate);

        pnlRounded.add(Box.createVerticalGlue());

        JPanel pnlWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlWrapper.setOpaque(false);
        pnlWrapper.add(pnlRounded);
        pnlInner.add(pnlWrapper);

        pnlInner.add(Box.createVerticalGlue());

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setOpaque(false);
        pnlBtn.setMaximumSize(new Dimension(826, 50));
        RoundedComponents.RoundedButton cmdDone =
            new RoundedComponents.RoundedButton("DONE", 25);
        cmdDone.setPreferredSize(new Dimension(148, 41));
        cmdDone.setBackground(Color.decode("#2A0243"));
        cmdDone.setForeground(Color.WHITE);
        cmdDone.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdDone.setBorderColor(Color.decode("#2A0243"));
        pnlBtn.add(cmdDone);
        pnlInner.add(pnlBtn);

        cmdDone.addActionListener((ActionEvent e) -> {
            new TrackingPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp6::new);
    }
}
