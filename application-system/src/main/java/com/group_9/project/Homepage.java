package com.group_9.project;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Homepage extends JFrame {

    public Homepage() {
    BaseFrameSetup.applyAppIcon(this);
    BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);
                

        // Main headline - enlarged and repositioned
        JLabel lblHeadline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        lblHeadline.setFont(FontUtil.getOutfitFont(50f));
        lblHeadline.setForeground(new Color(0x2B0243));
        lblHeadline.setBounds(112, 220, 1200, 120);
        pnlBackground.add(lblHeadline);

        JLabel lblSubHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        lblSubHeadline.setFont(FontUtil.getInterFont(16f));
        lblSubHeadline.setBounds(420, 350, 600, 30);
        pnlBackground.add(lblSubHeadline);

        JButton cmdViewPlans = new JButton("VIEW PLANS");
        cmdViewPlans.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdViewPlans.setBounds(530, 400, 160, 45);
        cmdViewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(cmdViewPlans, new Color(62, 10, 118), Color.WHITE,
                new Color(42, 2, 67), Color.WHITE, new Color(62, 10, 118), new Color(42, 2, 67));

        cmdViewPlans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlansPage().setVisible(true);
                dispose();
            }
        });
                
        
        pnlBackground.add(cmdViewPlans);
        
        JButton cmdCheckAvailability = new JButton("CHECK AVAILABILITY");
        cmdCheckAvailability.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdCheckAvailability.setBounds(700, 400, 220, 45);
        cmdCheckAvailability.setFocusPainted(false);
        cmdCheckAvailability.setContentAreaFilled(false);
        ButtonHoverEffect.apply(
                                cmdCheckAvailability,
                                new Color(62, 10, 118),
                                new Color(62, 10, 118),
                                new Color(0, 0, 0, 0),
                                new Color(38, 6, 67),
                                new Color(62, 10, 118),
                                new Color(42, 2, 67)
        );

        cmdCheckAvailability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ErrorPage().setVisible(true);
                dispose();
            }
        });
        
        
        pnlBackground.add(cmdCheckAvailability);

        // Apply Now Section
        int intApplyX = 205;
        int intApplyY = 535;
        int intApplyWidth = 520;

        JLabel lblApplyNow = new JLabel("<html><div style='font-weight:600;color:#2B0243;'>Apply Now!</div></html>", SwingConstants.LEFT);
        lblApplyNow.setFont(FontUtil.getInterFont(35f));
        lblApplyNow.setBounds(intApplyX, intApplyY, intApplyWidth, 40);
        pnlBackground.add(lblApplyNow);

        JLabel lblApplyDesc = new JLabel("<html>The process is simple, guided, and built for you.<br>Apply at your own pace, anytime.</html>");
        lblApplyDesc.setFont(FontUtil.getOutfitFont(16f));
        lblApplyDesc.setBounds(intApplyX, intApplyY + 50, intApplyWidth, 50);
        pnlBackground.add(lblApplyDesc);

        JButton cmdGetStarted = new RoundedComponents.RoundedButton("GET STARTED", 20);
        cmdGetStarted.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdGetStarted.setBounds(intApplyX, intApplyY + 105, 160, 45);
        cmdGetStarted.setFocusPainted(false);
        ButtonHoverEffect.apply(
                                cmdGetStarted,
                                new Color(62, 10, 118),          //hover bg
                                Color.WHITE,                           //hover fg
                                new Color(42, 2, 67),            //normal bg
                                Color.WHITE,                           //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        pnlBackground.add(cmdGetStarted);

        cmdGetStarted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String appNo = UserApplicationData.get("strApplicationNo");
                if (appNo != null && !appNo.isEmpty()) {
                    new TrackingPage().setVisible(true);
                } else {
                    new SignUp1().setVisible(true);
                }
                dispose();
            }
        });
        

        // Left text (not clickable)
        JLabel lblPromptText = new JLabel("Already have an account?");
        lblPromptText.setFont(FontUtil.getInterFont(16f));
        lblPromptText.setBounds(intApplyX, intApplyY + 155, 200, 30);
        pnlBackground.add(lblPromptText);

        // Clickable "Log in!" part
        JLabel lblLoginClickable = new JLabel(" Log in!");
        lblLoginClickable.setFont(FontUtil.getInterFont(16f));
        
        Color normalColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        lblLoginClickable.setForeground(normalColor);
        lblLoginClickable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoginClickable.setBounds(intApplyX + 200, intApplyY + 155, 60, 30);
        
        lblLoginClickable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblLoginClickable.setForeground(hoverColor);
            }
        
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblLoginClickable.setForeground(normalColor);
            }
        
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
        
        pnlBackground.add(lblLoginClickable);
             

        // Steps Panel
        RoundedPanel pnlSteps = new RoundedPanel(30);
        pnlSteps.setLayout(null);
        pnlSteps.setBounds(700, 520, 520, 320);
        pnlSteps.setBackground(new Color(255, 255, 255, 180));
        pnlBackground.add(pnlSteps);
        

        String[] stepTitles = {
            "YOUR INFO", "CHOOSE A PLAN", "PAY HERE", "CHECK STATUS"
        };

        String[] stepDescriptions = {
            "Take the first step—apply in just a few minutes!",
            "Select the plan that suits your needs.",
            "You're almost there! Securely settle your fees.",
            "Stay updated on your application every step of the way."
        };

        for (int i = 0; i < stepTitles.length; i++) {
            int y = i * 75 + 25;
        
            JLabel lblNumber = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            lblNumber.setBounds(30, y, 43, 43);
            lblNumber.setOpaque(false); // disable default opaque rect rendering
            lblNumber.setFont(FontUtil.getOutfitFont(20f));
        
            if (i == 0) {
                lblNumber.setBackground(new Color(255, 241, 255));
                lblNumber.setForeground(new Color(80, 0, 128));
                lblNumber.setUI(new RoundedLabelUI(40, new Color(126, 76, 165)));
            } else {
                lblNumber.setBackground(new Color(42, 2, 67));
                lblNumber.setForeground(new Color(255, 241, 255));
                lblNumber.setUI(new RoundedLabelUI(40, new Color(42, 2, 67))); // purple w/ border
            }

            pnlSteps.add(lblNumber);
        
            if (i < stepTitles.length - 1) {
                JPanel pnlConnector = new JPanel();
                pnlConnector.setBackground(new Color(126, 76, 165));
                pnlConnector.setBounds(48, y + 40, 4, 35);
                pnlSteps.add(pnlConnector);
            }

            JLabel lblStepTitle = new JLabel(stepTitles[i]);
            lblStepTitle.setFont(FontUtil.getOutfitFont(18f).deriveFont(Font.BOLD));
            lblStepTitle.setBounds(90, y, 420, 25);
            pnlSteps.add(lblStepTitle);

            JLabel lblStepDescription = new JLabel(stepDescriptions[i]);
            lblStepDescription.setFont(FontUtil.getOutfitFont(15f));
            lblStepDescription.setBounds(90, y + 15, 420, 30);
            pnlSteps.add(lblStepDescription);
        }
        
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Homepage().setVisible(true));
    }
}

class RoundedLabelUI extends javax.swing.plaf.basic.BasicLabelUI {
    private final int diameter;
    private final Color borderColor;

    public RoundedLabelUI(int diameter, Color borderColor) {
        this.diameter = diameter;
        this.borderColor = borderColor;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cast to JLabel
        JLabel label = (JLabel) c;

        // Background circle
        g2.setColor(c.getBackground());
        g2.fillOval(0, 0, diameter, diameter);

        // Border
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(1, 1, diameter - 2, diameter - 2);
        }

        // Text centering
        g2.setColor(label.getForeground());
        g2.setFont(label.getFont());
        FontMetrics fm = g2.getFontMetrics();
        String strText = label.getText();
        int intTextWidth = fm.stringWidth(strText);
        int textHeight = fm.getAscent();
        int intX = (diameter - intTextWidth) / 2;
        int intY = (diameter + textHeight) / 2 - 2;
        g2.drawString(strText, intX, intY);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(diameter, diameter);
    }
}