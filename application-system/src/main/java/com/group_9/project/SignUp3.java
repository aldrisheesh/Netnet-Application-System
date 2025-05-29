package com.group_9.project;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class SignUp3 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp3() {
        setTitle("Service Application");
        setSize(1440, 1024); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background panel
        BackgroundPanel  background = new BackgroundPanel(1);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

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

        Color titleColor = Color.decode("#2B0243");
        Color subColor = Color.decode("#302E2E");

        // creates title label
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        container.add(title);

        // adds spacing
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(1)); 
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

        JLabel subtitle = new JLabel("CHOOSE YOUR PLAN", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Choose one or more plans to get started. You can also add more later.");
        subNote.setFont(FontUtil.getOutfitFont(12f));
        subNote.setForeground(subColor);

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
        
        JPanel planPanel = new JPanel(new GridBagLayout());
        planPanel.setOpaque(false);
        planPanel.setMaximumSize(new Dimension(700, 350));
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
                        "*With outright Payment Option of Php 2,500 for\n" +
                        "Plans 1500 & 2500, and Php 1,250 for Plan 3500.\n" +  
                        "Waived Installation Fee for Plans 4500 and 7000.\n" +
                        "*Prices are VAT Inclusive"
                );
                note.setFont(FontUtil.getOutfitFont(14f));
                note.setEditable(false);
                note.setOpaque(false);
                planPanel.add(note, gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        container.add(planPanel);

        container.add(Box.createRigidArea(new Dimension(0, 40)));

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
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
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

        // button properties
        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(50, 0, 90));
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
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

    class SelectablePlanPanel extends JPanel {
        private boolean selected = false;
        private final Color borderColorDefault = Color.LIGHT_GRAY;
        private final Color borderColorHover = Color.GRAY;
        private final Color borderColorSelected = Color.decode("#7E4CA5");
        private final Color squareColorSelected = Color.decode("#2B0243");
        private final Color squareColorUnselected = Color.WHITE;

        private final JPanel checkboxPanel;

        public SelectablePlanPanel(String title, String price, String fee) {
            setLayout(new BorderLayout(10, 0)); // Add space between square and content
            setBackground(Color.WHITE);
            setBorder(defaultBorder());

            // Custom square checkbox
            checkboxPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(selected ? squareColorSelected : squareColorUnselected);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(borderColorSelected);
                    g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
            };
            checkboxPanel.setPreferredSize(new Dimension(50, 50));
            checkboxPanel.setMinimumSize(new Dimension(50, 50));
            checkboxPanel.setMaximumSize(new Dimension(50, 50));
            checkboxPanel.setOpaque(false);

            // Vertically center the checkbox inside a fixed height wrapper (70)
            JPanel checkboxWrapper = new JPanel(new GridBagLayout());
            checkboxWrapper.setOpaque(false);
            checkboxWrapper.setPreferredSize(new Dimension(70, 70));
            checkboxWrapper.setMinimumSize(new Dimension(70, 70));
            checkboxWrapper.setMaximumSize(new Dimension(70, 70));
            checkboxWrapper.add(checkboxPanel);

            add(checkboxWrapper, BorderLayout.WEST);

            // Right side: Plan details
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);

            JPanel topRow = new JPanel(new BorderLayout());
            topRow.setOpaque(false);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(FontUtil.getOutfitBoldFont(16f));

            JLabel priceLabel = new JLabel(price);
            priceLabel.setFont(FontUtil.getInterFont(14f));
            priceLabel.setForeground(Color.decode("#1E1E1E"));

            topRow.add(titleLabel, BorderLayout.WEST);
            topRow.add(priceLabel, BorderLayout.EAST);

            JLabel feeLabel = new JLabel(fee);
            feeLabel.setFont(FontUtil.getInterFont(14f));
            feeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            contentPanel.add(topRow);
            contentPanel.add(Box.createVerticalStrut(5));
            contentPanel.add(feeLabel);

            add(contentPanel, BorderLayout.CENTER);

                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(hoverBorder());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(selected ? selectedBorder() : defaultBorder());
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selected = !selected;
                    setBorder(selected ? selectedBorder() : defaultBorder());
                    repaint();
                }
            });
        }

        private Border defaultBorder() {
            return BorderFactory.createLineBorder(borderColorDefault, 1);
        }

        private Border hoverBorder() {
            return BorderFactory.createLineBorder(borderColorHover, 2);
        }

        private Border selectedBorder() {
            return BorderFactory.createLineBorder(borderColorSelected, 3);
        }

        public boolean isSelected() {
            return selected;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp3::new);
    }
}
