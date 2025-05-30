package com.group_9.project;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class SignUp3 extends JFrame {
    // Sets up the main frame
    public SignUp3() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color subColor = Color.decode("#302E2E");

        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        innerContent.add(title);

        // adds spacing
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(1));
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // personal info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(826, 60));

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
        innerContent.add(infoPanel);

        // adds horizontal separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(826, 2));
        separator.setForeground(Color.decode("#B2B2B2"));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        innerContent.add(separator);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel planPanel = new JPanel(new GridBagLayout());
        planPanel.setOpaque(false);
        planPanel.setMaximumSize(new Dimension(826, 350));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
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

            if (i == 4) {
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
        innerContent.add(planPanel);

        innerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout()); // Use BorderLayout to position buttons to the edges
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        // NEXT button (right)
        RoundedComponents.RoundedButton nextButton = new RoundedComponents.RoundedButton("NEXT", 25);
        nextButton.setPreferredSize(new Dimension(148, 41));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));

        // BACK button (left)
        RoundedComponents.RoundedButton backButton = new RoundedComponents.RoundedButton("BACK", 25);
        backButton.setPreferredSize(new Dimension(148, 41));
        backButton.setBackground(Color.decode("#FFF1FF"));
        backButton.setForeground(Color.decode("#2B0243"));
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setBorderColor(Color.decode("#2B0243"));

        // Add buttons to the left and right edges
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);

        innerContent.add(buttonPanel);

        // NEXT button action
        nextButton.addActionListener(e -> {
            dispose(); 
            new SignUp5();
        });

        // BACK button action
        backButton.addActionListener(e -> {
            dispose();
            new SignUp2(); 
        });

        container.add(innerContent);

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    // Custom content panel with rounded corners and shadow
    private JPanel createContentPanel() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow first (offset to bottom-right)
                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20)); 
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                
                // Draw main panel
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - shadowOffset - 1, getHeight() - shadowOffset - 1, 25, 25);
                g2.dispose();
            }
        };
        content.setBounds(235, 165, 970, 695);
        content.setOpaque(false);
        return content;
    }

    class SelectablePlanPanel extends JPanel {
        private boolean selected = false;
        private final Color borderColorDefault = Color.LIGHT_GRAY;
        private final Color borderColorHover = Color.GRAY;
        private final Color borderColorSelected = Color.decode("#7E4CA5");
        private final Color squareColorSelected = Color.decode("#2B0243");
        private final Color squareColorUnselected = Color.WHITE;
        private final int borderRadius = 12; // Radius for rounded corners

        private final JPanel checkboxPanel;

        public SelectablePlanPanel(String title, String price, String fee) {
            setLayout(new BorderLayout(10, 0)); 
            setBackground(Color.WHITE);
            setBorder(createRoundedBorder(borderColorDefault, 1));

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
                    setBorder(createRoundedBorder(borderColorHover, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(selected ? createRoundedBorder(borderColorSelected, 3) : createRoundedBorder(borderColorDefault, 1));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selected = !selected;
                    setBorder(selected ? createRoundedBorder(borderColorSelected, 3) : createRoundedBorder(borderColorDefault, 1));
                    repaint();
                }
            });
        }

        // Custom rounded border method
        private Border createRoundedBorder(Color color, int thickness) {
            return new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(color);
                    g2d.setStroke(new BasicStroke(thickness));
                    g2d.drawRoundRect(x + thickness/2, y + thickness/2, 
                                    width - thickness, height - thickness, 
                                    borderRadius, borderRadius);
                    g2d.dispose();
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
                }

                @Override
                public boolean isBorderOpaque() {
                    return false;
                }
            };
        }

        public boolean isSelected() {
            return selected;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp3::new);
    }
}
