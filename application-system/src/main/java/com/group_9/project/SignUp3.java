package com.group_9.project;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class SignUp3 extends JFrame {
    private static final int RADIUS = 15;

    public SignUp3() {
        setTitle("Service Application");
        setSize(1440, 1024);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GradientBackground background = new GradientBackground();
        background.setLayout(new GridBagLayout());

        JPanel container = new JPanel();
        container.setBackground(Color.decode("#fdf5fe"));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(800, 700));
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 38))
        ));

        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.DARK_GRAY);
        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(createStepTracker());
        container.add(stepWrapper);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(700, 60));

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

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(700, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(separator);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel planPanel = new JPanel(new GridBagLayout());
        planPanel.setOpaque(false);
        planPanel.setMaximumSize(new Dimension(800, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<SelectablePlanPanel> planBoxes = new ArrayList<>();

        planBoxes.add(new SelectablePlanPanel("FIBERX 1500", "₱1500", "Installation Fee: ₱125/24mo."));
        planBoxes.add(new SelectablePlanPanel("FIBER Xtream 4500", "₱4500", "Installation Fee: WAIVED"));
        planBoxes.add(new SelectablePlanPanel("FIBERX 2500", "₱2500", "Installation Fee: ₱125/24mo."));
        planBoxes.add(new SelectablePlanPanel("FIBER Xtream 7000", "₱7000", "Installation Fee: WAIVED"));
        planBoxes.add(new SelectablePlanPanel("FIBERX 3500", "₱3500", "Installation Fee: ₱125/12mo."));

        for (int i = 0; i < planBoxes.size(); i++) {
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            planPanel.add(planBoxes.get(i), gbc);

            if (i == 4) { // Add note beside FIBERX 3500
                gbc.gridx = 1;
                JTextArea note = new JTextArea(
                        "* With outright Payment Option of ₱2,500 for Plans 1500 & 2500,\n" +
                                "  and ₱1,250 for Plan 3500.\n" +
                                "* Prices are VAT Inclusive."
                );
                note.setFont(new Font("SansSerif", Font.PLAIN, 12));
                note.setEditable(false);
                note.setOpaque(false);
                planPanel.add(note, gbc);
            }
        }

        // Add note under the plans (under FIBER Xtream 7000)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        container.add(planPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        JButton backButton = new JButton("BACK") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(43, 2, 67));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
                g2.dispose();
            }
        };

        Color defaultBg = new Color(255, 241, 255);
        Color hoverBg = new Color(255, 248, 255);
        Color clickBg = new Color(240, 220, 240);

        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setForeground(new Color(43, 2, 67));
        backButton.setBackground(defaultBg);
        backButton.setFont(new Font("Outfit", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(140, 40));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }

            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(defaultBg);
            }

            public void mousePressed(MouseEvent evt) {
                backButton.setBackground(clickBg);
            }

            public void mouseReleased(MouseEvent evt) {
                backButton.setBackground(hoverBg);
            }
        });

        JButton nextButton = new JButton("NEXT") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
                super.paintComponent(g);
                g2.dispose();
            }

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

        nextButton.addMouseListener(new MouseAdapter() {
            Color nextDefaultBg = new Color(50, 0, 90);
            Color nextHoverBg = new Color(80, 0, 130);
            Color nextClickBg = new Color(30, 0, 60);

            public void mouseEntered(MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }

            public void mouseExited(MouseEvent evt) {
                nextButton.setBackground(nextDefaultBg);
            }

            public void mousePressed(MouseEvent evt) {
                nextButton.setBackground(nextClickBg);
            }

            public void mouseReleased(MouseEvent evt) {
                nextButton.setBackground(nextHoverBg);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(nextButton);
        container.add(Box.createVerticalStrut(20));
        container.add(buttonPanel);

        background.add(container);
        setContentPane(background);
        setVisible(true);
    }

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
                    g2.setColor(new Color(126, 76, 165));
                    g2.fillOval(0, 0, 35, 35);
                }
            };
            circlePanel.setPreferredSize(new Dimension(35, 35));
            circlePanel.setMaximumSize(new Dimension(35, 35));
            circlePanel.setLayout(new BorderLayout());
            circlePanel.setOpaque(false);

            JLabel number = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            number.setFont(new Font("Outfit", Font.BOLD, 14));
            number.setForeground(Color.WHITE);
            number.setHorizontalAlignment(SwingConstants.CENTER);
            circlePanel.add(number, BorderLayout.CENTER);

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

    class SelectablePlanPanel extends JPanel {
        private boolean selected = false;

        public SelectablePlanPanel(String title, String price, String fee) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            setBorder(defaultBorder());

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            JLabel priceLabel = new JLabel(price);
            priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            JLabel feeLabel = new JLabel(fee);
            feeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

            add(titleLabel);
            add(Box.createVerticalStrut(5));
            add(priceLabel);
            add(feeLabel);

            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMaximumSize(new Dimension(300, 100));
            setPreferredSize(new Dimension(300, 100));

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    toggleSelection();
                }

                public void mouseEntered(MouseEvent e) {
                    setBorder(hoverBorder());
                }

                public void mouseExited(MouseEvent e) {
                    setBorder(selected ? selectedBorder() : defaultBorder());
                }
            });
        }

        private void toggleSelection() {
            selected = !selected;
            setBorder(selected ? selectedBorder() : defaultBorder());
        }

        private Border defaultBorder() {
            return BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        }

        private Border selectedBorder() {
            return BorderFactory.createLineBorder(new Color(100, 0, 200), 3);
        }

        private Border hoverBorder() {
            return BorderFactory.createLineBorder(Color.GRAY, 2);
        }

        public boolean isSelected() {
            return selected;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp3::new);
    }
}
