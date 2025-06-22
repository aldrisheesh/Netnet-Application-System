package com.group_9.project.utils;
import com.group_9.project.*;
import com.group_9.project.session.UserApplicationData;

import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class BaseFrameSetup {
    
    // Constants for consistent styling
    public static final String WINDOW_TITLE = "FiberXpress";
    public static final int WINDOW_WIDTH = 1440;
    public static final int WINDOW_HEIGHT = 1024;
    public static final Color NAV_NORMAL_COLOR = new Color(22, 6, 48, 128);
    public static final Color NAV_HOVER_COLOR = new Color(62, 10, 118);
    public static final Color LOGIN_BTN_PRIMARY = new Color(62, 10, 118);
    public static final Color LOGIN_BTN_PRESSED = new Color(42, 2, 67);

    /**
     * Apply the application icon to the given frame.
     */
    public static void applyAppIcon(JFrame frmTarget) {
        ImageIcon icon = new ImageIcon(
            BaseFrameSetup.class.getClassLoader().getResource("images/app_icon.png")
        );
        frmTarget.setIconImage(icon.getImage());
    }


    public static void setupFrame(JFrame frmTarget) {
        frmTarget.setTitle(WINDOW_TITLE);
        frmTarget.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frmTarget.setResizable(false);
        frmTarget.setLocationRelativeTo(null);
        frmTarget.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTarget.setLayout(null);
    }
    

    public static BackgroundPanel createBackgroundPanel(int intGradientType) {
        BackgroundPanel pnlBackground = new BackgroundPanel(intGradientType);
        pnlBackground.setLayout(null);
        return pnlBackground;
    }
    
    public static JLabel createLogo(BackgroundPanel pnlBackground) {
        ImageIcon originalIcon = new ImageIcon(BaseFrameSetup.class.getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 70, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 200, 44);
        pnlBackground.add(logo);
        return logo;
    }
    
    public static void createNavigation(BackgroundPanel pnlBackground, JFrame frmCurrent) {
        String[] arrNavItems = {"Home", "Plans", "Help & Support", "About Us"};
        int intXPos = 900;
        int intSpacing = 30;

        for (String strItem : arrNavItems) {
            JLabel lblNav = new JLabel(strItem);
            lblNav.setFont(FontUtil.getOutfitFont(16f));
            lblNav.setForeground(NAV_NORMAL_COLOR);
            lblNav.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            lblNav.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    lblNav.setForeground(NAV_HOVER_COLOR);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    lblNav.setForeground(NAV_NORMAL_COLOR);
                }

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    navigateToPage(strItem, frmCurrent);
                }
            });

            int textWidth = lblNav.getPreferredSize().width;
            lblNav.setBounds(intXPos, 30, textWidth + 10, 40);
            pnlBackground.add(lblNav);
            intXPos += textWidth + intSpacing + 10;
        }
    }
    
    // Fixed: Added currentFrame parameter to properly dispose of the current frame
    public static JButton createLoginButton(BackgroundPanel pnlBackground, JFrame frmCurrent) {
        String strAppNo = UserApplicationData.get("ApplicationNo");
        if (strAppNo != null && !strAppNo.isEmpty()) {
            JLabel lblAccount = new JLabel("Account");
            lblAccount.setFont(FontUtil.getOutfitFont(16f));
            lblAccount.setForeground(NAV_NORMAL_COLOR);
            lblAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            int intW = lblAccount.getPreferredSize().width;
            lblAccount.setBounds(1300, 30, intW + 10, 40);
            pnlBackground.add(lblAccount);

            lblAccount.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {
                    lblAccount.setForeground(NAV_HOVER_COLOR);
                }
                @Override public void mouseExited(MouseEvent e) {
                    lblAccount.setForeground(NAV_NORMAL_COLOR);
                }

                @Override public void mouseClicked(MouseEvent e) {
                    AccountNavigationUtil.openAccountPageByApplication(frmCurrent);
                    frmCurrent.dispose();
                }
            });
            return null;
        } else {
            // Show "Log In"
            RoundedComponents.RoundedButton loginBtn =
                new RoundedComponents.RoundedButton("Log In", 20);
            loginBtn.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
            loginBtn.setBounds(1300, 30, 80, 35);
            loginBtn.setFocusPainted(false);
            ButtonHoverEffect.apply(
                loginBtn,
                new Color(62, 10, 118), Color.WHITE,
                new Color(42, 2, 67),  Color.WHITE,
                new Color(62, 10, 118), new Color(42, 2, 67)
            );
            loginBtn.addActionListener(ev -> {
                new LoginPage().setVisible(true);
                frmCurrent.dispose();
            });
            pnlBackground.add(loginBtn);
            return loginBtn;
        }
    }
    
    // Fixed: Updated to pass currentFrame parameter to createLoginButton
    public static BackgroundPanel setupCompleteFrame(JFrame frmTarget, int intGradientType) {
        setupFrame(frmTarget);
        applyAppIcon(frmTarget);
        BackgroundPanel pnlBackground = createBackgroundPanel(intGradientType);
        frmTarget.setContentPane(pnlBackground);

        createLogo(pnlBackground);
        createNavigation(pnlBackground, frmTarget);
        createLoginButton(pnlBackground, frmTarget);

        return pnlBackground;
    }
    
    private static void navigateToPage(String strDestination, JFrame frmCurrent) {
        switch (strDestination) {
            case "Home" -> {
                String strAppNo = UserApplicationData.get("ApplicationNo");
                if (strAppNo != null && !strAppNo.isEmpty()) {
                    new TrackingPage().setVisible(true);
                } else {
                    new Homepage().setVisible(true);
                }
                frmCurrent.dispose();
            }
            case "Plans" -> {
                new PlansPage().setVisible(true);
                frmCurrent.dispose();
            }
            case "Help & Support" -> {
                new HelpSupportPage().setVisible(true);
                frmCurrent.dispose();
            }
            case "About Us" -> {
                new AboutUsPage().setVisible(true);
                frmCurrent.dispose();
            }
        }
    }
    
    public static void createCustomNavigation(BackgroundPanel pnlBackground, JFrame frmCurrent,
                                            String[] arrNavItems, int intStartX) {
        int intXPos = intStartX;
        int intSpacing = 30;

        for (String strItem : arrNavItems) {
            JLabel lblNav = new JLabel(strItem);
            lblNav.setFont(FontUtil.getOutfitFont(16f));
            lblNav.setForeground(NAV_NORMAL_COLOR);
            lblNav.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            lblNav.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    lblNav.setForeground(NAV_HOVER_COLOR);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    lblNav.setForeground(NAV_NORMAL_COLOR);
                }

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    navigateToPage(strItem, frmCurrent);
                }
            });

            int textWidth = lblNav.getPreferredSize().width;
            lblNav.setBounds(intXPos, 30, textWidth + 10, 40);
            pnlBackground.add(lblNav);
            intXPos += textWidth + intSpacing + 10;
        }
    }
}