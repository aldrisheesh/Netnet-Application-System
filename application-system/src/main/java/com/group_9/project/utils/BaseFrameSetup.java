package com.group_9.project.utils;
import com.group_9.project.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BaseFrameSetup {
    
    // Constants for consistent styling
    public static final String WINDOW_TITLE = "Converge FiberX";
    public static final int WINDOW_WIDTH = 1440;
    public static final int WINDOW_HEIGHT = 1024;
    public static final Color NAV_NORMAL_COLOR = new Color(22, 6, 48, 128);
    public static final Color NAV_HOVER_COLOR = new Color(62, 10, 118);
    public static final Color LOGIN_BTN_PRIMARY = new Color(62, 10, 118);
    public static final Color LOGIN_BTN_PRESSED = new Color(42, 2, 67);
    

    public static void setupFrame(JFrame frame) {
        frame.setTitle(WINDOW_TITLE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
    }
    

    public static BackgroundPanel createBackgroundPanel(int gradientType) {
        BackgroundPanel background = new BackgroundPanel(gradientType);
        background.setLayout(null);
        return background;
    }
    
    public static JLabel createLogo(BackgroundPanel background) {
        ImageIcon originalIcon = new ImageIcon(BaseFrameSetup.class.getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);
        return logo;
    }
    
    public static void createNavigation(BackgroundPanel background, JFrame currentFrame) {
        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"};
        int xPos = 900;
        int spacing = 30;
        
        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(NAV_NORMAL_COLOR);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    label.setForeground(NAV_HOVER_COLOR);
                }
                
                public void mouseExited(java.awt.event.MouseEvent e) {
                    label.setForeground(NAV_NORMAL_COLOR);
                }
                
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    navigateToPage(item, currentFrame);
                }
            });
            
            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;
        }
    }
    
    // Fixed: Added currentFrame parameter to properly dispose of the current frame
    public static JButton createLoginButton(BackgroundPanel background, JFrame currentFrame) {
        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(1300, 30, 80, 35);
        loginBtn.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        loginBtn.setBorder(new RoundedComponents.RoundedBorder(10));
        
        ButtonHoverEffect.apply(loginBtn,
                LOGIN_BTN_PRIMARY,
                Color.WHITE,
                LOGIN_BTN_PRESSED,
                Color.WHITE,
                LOGIN_BTN_PRIMARY,
                LOGIN_BTN_PRESSED);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                currentFrame.dispose();
            }
        });
        
        background.add(loginBtn);
        return loginBtn;
    }
    
    // Fixed: Updated to pass currentFrame parameter to createLoginButton
    public static BackgroundPanel setupCompleteFrame(JFrame frame, int gradientType) {
        setupFrame(frame);
        BackgroundPanel background = createBackgroundPanel(gradientType);
        frame.setContentPane(background);
        
        createLogo(background);
        createNavigation(background, frame);
        createLoginButton(background, frame); 
        
        return background;
    }
    
    private static void navigateToPage(String destination, JFrame currentFrame) {
        switch (destination) {
            case "Home" -> {
                new Homepage().setVisible(true);
                currentFrame.dispose();
            }
            case "Plans" -> {
                new PlansPage().setVisible(true);
                currentFrame.dispose();
            }
            case "Help & Support" -> {
                new HelpSupportPage().setVisible(true);
                currentFrame.dispose();
            }
            case "About Us" -> {
                new ErrorPage().setVisible(true);
                currentFrame.dispose();
            }
        }
    }
    
    public static void createCustomNavigation(BackgroundPanel background, JFrame currentFrame, 
                                            String[] navItems, int startX) {
        int xPos = startX;
        int spacing = 30;
        
        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(NAV_NORMAL_COLOR);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    label.setForeground(NAV_HOVER_COLOR);
                }
                
                public void mouseExited(java.awt.event.MouseEvent e) {
                    label.setForeground(NAV_NORMAL_COLOR);
                }
                
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    navigateToPage(item, currentFrame);
                }
            });
            
            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;
        }
    }
}