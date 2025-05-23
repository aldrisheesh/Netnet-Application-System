package com.group_9.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseEvent;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Converge FiberX");
        setSize(1440, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Gradient background panel
        BackgroundPanel background = new BackgroundPanel(4);
        background.setLayout(null);
        setContentPane(background);

        // Logo image
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        // Navigation Menu
        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"};
        int xPos = 900;
        int spacing = 30;

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(new Color(22, 6, 48, 128));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FontMetrics fm = label.getFontMetrics(label.getFont());
            int textWidth = fm.stringWidth(item);
            label.setBounds(xPos, 30, textWidth + 10, 40);

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openNewWindow(item);
                    dispose();
                }
            });

            background.add(label);
            xPos += textWidth + spacing + 10;
        }

        // Main headlines
        JLabel headline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        int yPosi = 435;

        // Call to action form
        JLabel letsLabel = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:600;'>Let’s make things happen.</div></html>");
        letsLabel.setFont(FontUtil.getOutfitFont(16f));
        letsLabel.setBounds(562, yPosi, 300, 30);
        letsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(letsLabel);

        RoundedTextField emailField = new RoundedTextField("Email or phone number", 20);
        emailField.setFont(FontUtil.getInterFont(14f));
        emailField.setBounds(524, yPosi + 40, 375, 60);
        background.add(emailField);

        RoundedPasswordField passwordField = new RoundedPasswordField("Password", 20);
        passwordField.setFont(FontUtil.getInterFont(14f));
        passwordField.setBounds(524, yPosi + 117, 375, 60);
        background.add(passwordField);

        JButton loginBtn = new JButton("LOG IN");
        loginBtn.setFont(FontUtil.getOutfitFont(16f));
        loginBtn.setBounds(525, yPosi + 195, 130, 40);
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                                loginBtn, 
                                new Color(62, 10, 118),          //hover bg
                                Color.WHITE,                           //hover fg
                                new Color(42, 2, 67),            //normal bg
                                Color.WHITE,                           //normal fg
                                new Color(62, 10, 118),          //hover border
                                new Color(42, 2, 67)             //normal border
        );
        background.add(loginBtn);

        JLabel forgotLabel = new JLabel("<html><div style='color:#7E4CA5;font-weight:600;'>Forgotten your password?</div></html>");
        forgotLabel.setFont(FontUtil.getOutfitFont(16f));
        forgotLabel.setBounds(679, yPosi + 195 + 3, 200, 30);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        background.add(forgotLabel);

        JCheckBox keepSignedIn = new JCheckBox("Keep me signed in");
        keepSignedIn.setFont(FontUtil.getOutfitFont(16f));
        keepSignedIn.setForeground(new Color(140, 140, 140));
        keepSignedIn.setOpaque(false);
        keepSignedIn.setFocusPainted(false);
        keepSignedIn.setBorderPainted(false);
        keepSignedIn.setContentAreaFilled(false);
        
        // flat custom icons
        keepSignedIn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_unchecked.png")));
        keepSignedIn.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_checked.png")));
        
        keepSignedIn.setBounds(523, yPosi + 195 + 45, 250, 40);
        background.add(keepSignedIn);
        
        

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    private void openNewWindow(String title) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(600, 400);
        newFrame.setLocationRelativeTo(null);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel message = new JLabel("This is the " + title + " window", SwingConstants.CENTER);
        message.setFont(FontUtil.getOutfitFont(18f));
        newFrame.add(message);
        newFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }

    class RoundedBorder implements Border {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    class RoundedTextField extends JTextField {
        private final String placeholder;

        public RoundedTextField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(15));
            setBackground(new Color(255, 242, 255));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g2);

            if (getText().isEmpty() && !isFocusOwner()) {
                g2.setColor(Color.GRAY);
                FontMetrics fm = g2.getFontMetrics();
                int x = getInsets().left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2.drawString(placeholder, x, y);
            }

            g2.dispose();
        }
    }

    class RoundedPasswordField extends JPasswordField {
        private final String placeholder;

        public RoundedPasswordField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(15));
            setBackground(new Color(255, 242, 255));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g2);

            if (getPassword().length == 0 && !isFocusOwner()) {
                g2.setColor(Color.GRAY);
                FontMetrics fm = g2.getFontMetrics();
                int x = getInsets().left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2.drawString(placeholder, x, y);
            }

            g2.dispose();
        }
    }
}
