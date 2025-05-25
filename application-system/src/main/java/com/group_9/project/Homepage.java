package com.group_9.project;

import javax.swing.*;
import java.awt.*;

public class Homepage extends JFrame {

    public Homepage() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Gradient background panel
        BackgroundPanel  background = new BackgroundPanel(1);
        background.setLayout(null);
        setContentPane(background);
        
        // Logo image
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        //Navigation Menu
        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"};
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
                        case "Plans" -> {
                            new ErrorPage().setVisible(true);
                            dispose();
                        }
                        case "Help & Support" -> {
                            new HelpSupportPage().setVisible(true);
                            dispose();
                        }
                        case "About Us" -> {
                            new ErrorPage().setVisible(true);
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
        

        // Login button
        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(1300, 30, 80, 35);
        loginBtn.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                loginBtn,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );

        loginBtn.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        background.add(loginBtn);
        

        // Main headline - enlarged and repositioned
        JLabel headline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        JButton viewPlans = new JButton("VIEW PLANS");
        viewPlans.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        viewPlans.setBounds(530, 400, 160, 45);
        viewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(viewPlans, new Color(62, 10, 118), Color.WHITE,
                new Color(42, 2, 67), Color.WHITE, new Color(62, 10, 118), new Color(42, 2, 67));
        
        viewPlans.addActionListener(e -> {
            new ErrorPage().setVisible(true);
            dispose();
        });
        
        background.add(viewPlans);
        
        JButton checkAvailability = new JButton("CHECK AVAILABILITY");
        checkAvailability.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        checkAvailability.setBounds(700, 400, 220, 45);
        checkAvailability.setFocusPainted(false);
        checkAvailability.setContentAreaFilled(false);
        ButtonHoverEffect.apply(checkAvailability, new Color(62, 10, 118), new Color(62, 10, 118),
                new Color(0, 0, 0, 0), new Color(38, 6, 67), new Color(62, 10, 118), new Color(42, 2, 67));
        
        checkAvailability.addActionListener(e -> {
            new ErrorPage().setVisible(true);
            dispose();
        });
        
        background.add(checkAvailability);

        // Apply Now Section
        int applyX = 205;
        int applyY = 535;
        int applyWidth = 520;

        JLabel applyNow = new JLabel("<html><div style='font-weight:600;color:#2B0243;'>Apply Now!</div></html>", SwingConstants.LEFT);
        applyNow.setFont(FontUtil.getInterFont(35f));
        applyNow.setBounds(applyX, applyY, applyWidth, 40);
        background.add(applyNow);

        JLabel applyDesc = new JLabel("<html>The process is simple, guided, and built for you.<br>Apply at your own pace, anytime.</html>");
        applyDesc.setFont(FontUtil.getOutfitFont(16f));
        applyDesc.setBounds(applyX, applyY + 50, applyWidth, 50);
        background.add(applyDesc);

        JButton getStarted = new JButton("GET STARTED");
        getStarted.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        getStarted.setBounds(applyX, applyY + 105, 160, 45);
        getStarted.setFocusPainted(false);
        ButtonHoverEffect.apply(
                                getStarted, 
                                new Color(62, 10, 118),          //hover bg
                                Color.WHITE,                           //hover fg
                                new Color(42, 2, 67),            //normal bg
                                Color.WHITE,                           //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        background.add(getStarted);

        // Left text (not clickable)
        JLabel promptText = new JLabel("Already have an account?");
        promptText.setFont(FontUtil.getInterFont(16f));
        promptText.setBounds(applyX, applyY + 155, 200, 30);
        background.add(promptText);

        // Clickable "Log in!" part
        JLabel loginClickable = new JLabel(" Log in!");
        loginClickable.setFont(FontUtil.getInterFont(16f));
        
        loginClickable.setForeground(normalColor);
        loginClickable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginClickable.setBounds(applyX + 200, applyY + 155, 60, 30);
        
        loginClickable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginClickable.setForeground(hoverColor);
            }
        
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginClickable.setForeground(normalColor);
            }
        
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
        
        background.add(loginClickable);
             

        // Steps Panel
        RoundedPanel stepsPanel = new RoundedPanel(30);
        stepsPanel.setLayout(null);
        stepsPanel.setBounds(700, 520, 520, 320);
        stepsPanel.setBackground(new Color(255, 255, 255, 180));
        background.add(stepsPanel);
        

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
        
            JLabel number = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            number.setBounds(30, y, 43, 43);
            number.setOpaque(false); // disable default opaque rect rendering
            number.setFont(FontUtil.getOutfitFont(20f));
        
            if (i == 0) {
                number.setBackground(new Color(255, 241, 255));
                number.setForeground(new Color(80, 0, 128));
                number.setUI(new RoundedLabelUI(40, new Color(126, 76, 165)));
            } else {
                number.setBackground(new Color(42, 2, 67));
                number.setForeground(new Color(255, 241, 255));
                number.setUI(new RoundedLabelUI(40, new Color(42, 2, 67))); // purple w/ border
            }
        
            stepsPanel.add(number);
        
            if (i < stepTitles.length - 1) {
                JPanel connector = new JPanel();
                connector.setBackground(new Color(126, 76, 165));
                connector.setBounds(48, y + 40, 4, 35);
                stepsPanel.add(connector);
            }
        
            JLabel title = new JLabel(stepTitles[i]);
            title.setFont(FontUtil.getOutfitFont(18f).deriveFont(Font.BOLD));
            title.setBounds(90, y, 420, 25);
            stepsPanel.add(title);
        
            JLabel description = new JLabel(stepDescriptions[i]);
            description.setFont(FontUtil.getOutfitFont(15f));
            description.setBounds(90, y + 15, 420, 30);
            stepsPanel.add(description);
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
        String text = label.getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (diameter - textWidth) / 2;
        int y = (diameter + textHeight) / 2 - 2;
        g2.drawString(text, x, y);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(diameter, diameter);
    }
}

class RoundedPanel extends JPanel {
    private final int arc;

    public RoundedPanel(int arc) {
        this.arc = arc;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(0, 0, w, h, arc, arc);

        // Optional soft border
        g2.setColor(new Color(220, 220, 220));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}
