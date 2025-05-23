package com.group_9.project;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.Border;

public class NetnetLogin extends JFrame {

    public NetnetLogin() {
        setTitle("NETNET: Wi-Finally Yours!");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Makes the frame unresizable

        // Add top navigation bar
        add(NavigationBar(), BorderLayout.NORTH);

        // Custom JPanel with circular gradient background
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(new Color(255, 241, 255));
                g2d.fillRect(0, 0, width, height);
                drawRadialGradient(g2d, new Color(90, 0, 180, 160), new Point2D.Double(width * 0.01, height * 0.5), 1000);
                drawRadialGradient(g2d, new Color(0, 255, 200, 120), new Point2D.Double(width * 0.90, height * 0.25), 700);
                drawRadialGradient(g2d, new Color(255, 150, 200, 100), new Point2D.Double(width * 0.5, height * 0.95), 1000);
            }

            private void drawRadialGradient(Graphics2D g2d, Color color, Point2D center, float radius) {
                float[] dist = {0.0f, 1.0f};
                Color[] colors = {color, new Color(255, 255, 255, 0)};
                RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dist, colors);
                g2d.setPaint(rgp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Title using HTML with a line break
        JLabel title = new JLabel("<html><div style='text-align: center;'>"
                + "Supercharge your home with<br>"
                + "ultra-fast internet and endless entertainment."
                + "</div></html>");
        title.setFont(new Font("Outfit", Font.BOLD, 40));
        title.setForeground(new Color(43, 2, 67));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(title, gbc);

        // Subtitle
        JLabel subtitle = new JLabel("Enjoy faster speed, and incredible value with our plans.");
        subtitle.setFont(new Font("Outfit", Font.PLAIN, 15));
        subtitle.setForeground(new Color(70, 0, 90));
        gbc.gridy++;
        panel.add(subtitle, gbc);

        // Section
        JLabel section = new JLabel("Let's make things happen.");
        section.setFont(new Font("Outfit", Font.BOLD, 18));
        section.setForeground(new Color(43, 2, 67));
        gbc.gridy++;
        gbc.insets = new Insets(40, 0, 10, 0);
        panel.add(section, gbc);

        // Email/Phone Number Field
        EmailField emailField = new EmailField("Email or phone number", 10);
        emailField.setFont(new Font("Outfit", Font.PLAIN, 16));
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 275, 5, 275);
        panel.add(emailField, gbc);

        // Password Field
        PasswordField passwordField = new PasswordField("Password", 10);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy++;
        gbc.insets = new Insets(5, 275, 20, 275);
        panel.add(passwordField, gbc);

        // Login Button and Forgot Label Row
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        RoundedButton loginButton = new RoundedButton("LOG IN");
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        rowPanel.add(loginButton, BorderLayout.WEST);

        JLabel forgotLabel = new JLabel("Forgotten your password?");
        forgotLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        forgotLabel.setForeground(new Color(120, 70, 180));
        rowPanel.add(forgotLabel, BorderLayout.EAST);
        gbc.gridy++;
        gbc.insets = new Insets(5, 275, 10, 275);
        panel.add(rowPanel, gbc);

        // Checkbox for "Keep me signed in"
        JCheckBox keepSignedIn = new JCheckBox("Keep me signed in");
        keepSignedIn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        keepSignedIn.setOpaque(false);
        gbc.gridy++;
        gbc.insets = new Insets(5, 275, 20, 275);
        panel.add(keepSignedIn, gbc);

        // Add the login panel to the center
        add(panel, BorderLayout.CENTER);
    }

    // Creates the navigation bar panel at the top
    private JPanel NavigationBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setOpaque(false);
        navBar.setBackground(new Color(0, 0, 0, 0));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);
        rightPanel.setBackground(new Color(0, 0, 0, 0));

        String[] menuItems = {"Home", "Plans", "Help & Support", "About Us"};
        for (String item : menuItems) {
            JLabel label = new JLabel(item);
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            label.setForeground(new Color(60, 0, 80));
            rightPanel.add(label);
        }
        
        JButton loginBtn = new JButton("Log In");
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(42, 2, 67));
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        loginBtn.setPreferredSize(new Dimension(100, 35));
        loginBtn.setBorder(new RoundedBorder(20));
        rightPanel.add(loginBtn);

        navBar.add(rightPanel, BorderLayout.EAST);
        return navBar;
    }

    // Custom border with rounded corners
    static class RoundedBorder implements Border {
        private int radius;
        public RoundedBorder(int radius) { this.radius = radius; }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius + 1);
        }

        @Override
        public boolean isBorderOpaque() { return false; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Custom EmailField
    class EmailField extends JTextField {
        private static final int RADIUS = 15;
        private String placeholder;

        public EmailField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(RADIUS));
            setBackground(new Color(255, 242, 255));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            g2d.setClip(clip);
            super.paintComponent(g2d);
            if (getText().isEmpty() && !isFocusOwner() && placeholder != null) {
                g2d.setColor(Color.GRAY);
                FontMetrics fm = g2d.getFontMetrics();
                Insets insets = getInsets();
                int x = insets.left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2d.drawString(placeholder, x, y);
            }
            g2d.dispose();
        }
    }

    // Custom PasswordField
    class PasswordField extends JPasswordField {
        private static final int RADIUS = 15;
        private String placeholder;

        public PasswordField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(RADIUS));
            setBackground(new Color(255, 242, 255));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            g2d.setClip(clip);
            super.paintComponent(g2d);
            if (getPassword().length == 0 && !isFocusOwner() && placeholder != null) {
                g2d.setColor(Color.GRAY);
                FontMetrics fm = g2d.getFontMetrics();
                Insets insets = getInsets();
                int x = insets.left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2d.drawString(placeholder, x, y);
            }
            g2d.dispose();
        }
    }

    // Custom rounded JButton
    class RoundedButton extends JButton {
        private static final int RADIUS = 25;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setBackground(new Color(35, 0, 60));
            setBorder(new RoundedBorder(RADIUS));
            setPreferredSize(new Dimension(120, 35));
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetnetLogin().setVisible(true));
    }
}
