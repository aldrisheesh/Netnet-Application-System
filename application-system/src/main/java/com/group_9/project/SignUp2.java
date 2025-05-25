package com.group_9.project;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class SignUp2 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp2() {
        setTitle("Service Application");
        setSize(1440, 1024); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // creates a gradient background
        GradientBackground background = new GradientBackground();
        background.setLayout(new GridBagLayout());

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

        // creates title label
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.DARK_GRAY);
        container.add(title);

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(createStepTracker());
        container.add(stepWrapper);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // personal info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(700, 60));

        // left labels for the info panel 
        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("SERVICE ADDRESS", SwingConstants.LEFT);
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        subtitle.setForeground(Color.DARK_GRAY);

        JLabel subNote = new JLabel("Your service address cannot be changed after registration.");
        subNote.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subNote.setForeground(Color.GRAY);

        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);

        infoPanel.add(leftLabels, BorderLayout.WEST);
        container.add(infoPanel);

        // adds horizontal separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(700, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(separator);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // form panel for input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridy = 0; // Initialize gbc.gridy

        // input fields to the form panel
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(styleComboBoxField(new String[]{"Owned", "Living with Relatives", "Mortgaged", "Rented"}), gbc);
        gbc.gridx = 1;
        formPanel.add(createPairPanel(styleComboBoxField(new String[]{"Yes", "No"}), createPlaceholderField("Years of Residency")), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createPlaceholderField("Name of Owner"), gbc);
        gbc.gridx = 1;
        formPanel.add(createPlaceholderField("Contact No."), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createPlaceholderField("House/Room No./Floor"), gbc);
        gbc.gridx = 1;
        formPanel.add(createPlaceholderField("Apartment/Compund/Building"), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createPlaceholderField("Subdivision"), gbc);
        gbc.gridx = 1;
        formPanel.add(createPlaceholderField("Barangay"), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createPairPanel(createPlaceholderField("Street"), createPlaceholderField("Municipality/City")), gbc);
        gbc.gridx = 1;
        formPanel.add(createPairPanel(createPlaceholderField("Province"), createPlaceholderField("Zip Code")), gbc);

        container.add(formPanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

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
        backButton.setFont(new Font("Outfit", Font.BOLD, 14));
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

        // NEXT button
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

        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(50, 0, 90));
        nextButton.setFont(new Font("Outfit", Font.BOLD, 14));
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

    // method to create the step tracker for the form
    private JPanel createStepTracker() {
        JPanel pagination = new JPanel();
        pagination.setLayout(new BoxLayout(pagination, BoxLayout.X_AXIS));
        pagination.setOpaque(false);

        String[] steps = {"YOUR INFO", "CHOOSE A PLAN", "PAY HERE", "CHECK STATUS"};

        for (int i = 0; i < steps.length; i++) {
            JPanel stepPanel = new JPanel();
            stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.Y_AXIS));
            stepPanel.setOpaque(false);

            JPanel circlePanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(126, 76, 165)); // #7E4CA5
                    g2.fillOval(0, 0, 35, 35);
                }
            };
            circlePanel.setPreferredSize(new Dimension(35, 35));
            circlePanel.setMaximumSize(new Dimension(35, 35));
            circlePanel.setLayout(new BorderLayout());
            circlePanel.setOpaque(false);

            // label for step number
            JLabel number = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            number.setFont(new Font("Outfit", Font.BOLD, 14));
            number.setForeground(Color.WHITE);
            number.setOpaque(false);
            number.setHorizontalAlignment(SwingConstants.CENTER);
            number.setVerticalAlignment(SwingConstants.CENTER);
            circlePanel.add(number, BorderLayout.CENTER);

            // label for step description
            JLabel label = new JLabel(steps[i]);
            label.setFont(new Font("Outfit", Font.PLAIN, 12));
            label.setForeground(new Color(80, 0, 150));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            stepPanel.add(circlePanel);
            stepPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            stepPanel.add(label);

            pagination.add(stepPanel);

            if (i < steps.length - 1) {
                JPanel bar = new JPanel();
                bar.setPreferredSize(new Dimension(100, 2));
                bar.setMaximumSize(new Dimension(100, 2));
                bar.setBackground(new Color(200, 150, 200));
                bar.setAlignmentY(Component.CENTER_ALIGNMENT);
                pagination.add(bar);
            }
        }

        return pagination;
    }

    // method to create a text field with placeholder functionality
    private JTextField createPlaceholderField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Outfit", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setBorder(new RoundedBorder(RADIUS));
        field.setMargin(new Insets(5, 10, 5, 10));
        field.setPreferredSize(new Dimension(200, 30));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        return field;
    }

    // method to create a styled combo box
    private JComboBox<String> styleComboBoxField(String[] options) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setFont(new Font("Outfit", Font.PLAIN, 14));
        box.setForeground(Color.BLACK);
        box.setBackground(Color.WHITE);
        box.setBorder(new RoundedBorder(RADIUS));
        box.setPreferredSize(new Dimension(200, 30));
        box.setFocusable(false);

        box.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                super.paintCurrentValueBackground(g, bounds, false);
            }
        });

        return box;
    }

    // method to create a panel with two components side by side
    private JPanel createPairPanel(JComponent left, JComponent right) {
    JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
    panel.setOpaque(false);

    panel.add(left);
    panel.add(right);

    return panel;
    }


    // class to create a rounded border
    static class RoundedBorder implements Border {
        private int radius;
        public RoundedBorder(int radius) { this.radius = radius; }
        public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp2::new);
    }
}

