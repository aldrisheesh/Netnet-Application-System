package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class SignUp3 extends JFrame {

    public SignUp3() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

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

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(1));
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setMaximumSize(new Dimension(826, 60));

        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("CHOOSE YOUR PLAN", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Choose one or more plans to get started. You can also add more later.");
        subNote.setFont(FontUtil.getInterFont(14f));
        subNote.setForeground(subColor);

        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);

        infoPanel.add(leftLabels, BorderLayout.WEST);
        innerContent.add(infoPanel);

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

        // Restore selection from UserApplicationData
        String savedPlans = UserApplicationData.get("selectedPlans");
        if (!savedPlans.isEmpty()) {
            String[] selectedPlanNames = savedPlans.split(",");
            for (SelectablePlanPanel panel : planBoxes) {
                JLabel titleLabel = (JLabel) ((JPanel)((JPanel)panel.getComponent(1)).getComponent(0)).getComponent(0);
                String planName = titleLabel.getText();
                for (String selected : selectedPlanNames) {
                    if (planName.equalsIgnoreCase(selected.trim())) {
                        panel.setSelected(true);
                        break;
                    }
                }
            }
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        innerContent.add(planPanel);

        innerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton nextButton = new RoundedComponents.RoundedButton("NEXT", 25);
        nextButton.setPreferredSize(new Dimension(148, 41));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));

        RoundedComponents.RoundedButton backButton = new RoundedComponents.RoundedButton("BACK", 25);
        backButton.setPreferredSize(new Dimension(148, 41));
        backButton.setBackground(Color.decode("#FFF1FF"));
        backButton.setForeground(Color.decode("#2B0243"));
        backButton.setFont(FontUtil.getOutfitBoldFont(16f));
        backButton.setBorderColor(Color.decode("#2B0243"));

        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);

        innerContent.add(buttonPanel);

        nextButton.addActionListener(e -> {
            ArrayList<String> selectedPlans = new ArrayList<>();

            for (SelectablePlanPanel panel : planBoxes) {
                if (panel.isSelected()) {
                    JLabel titleLabel = (JLabel) ((JPanel)((JPanel)panel.getComponent(1)).getComponent(0)).getComponent(0);
                    selectedPlans.add(titleLabel.getText());
                }
            }

            if (selectedPlans.isEmpty()) {
                CustomDialogUtil.showStyledErrorDialog(SignUp3.this, "No Plan Selected", "Please select at least one plan to proceed.");
            } else {
                String joinedPlans = String.join(",", selectedPlans);
                UserApplicationData.set("selectedPlans", joinedPlans);
                new SignUp5();
                dispose();
            }
        });

        backButton.addActionListener(e -> {
            ArrayList<String> selectedPlans = new ArrayList<>();
        
            for (SelectablePlanPanel panel : planBoxes) {
                if (panel.isSelected()) {
                    JLabel titleLabel = (JLabel) ((JPanel)((JPanel)panel.getComponent(1)).getComponent(0)).getComponent(0);
                    selectedPlans.add(titleLabel.getText());
                }
            }
        
            String joinedPlans = String.join(",", selectedPlans);
            UserApplicationData.set("selectedPlans", joinedPlans);
        
            new SignUp2();
            dispose();
        });        

        container.add(innerContent);

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    private JPanel createContentPanel() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);

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
        private final int borderRadius = 12;

        private final JPanel checkboxPanel;

        public SelectablePlanPanel(String title, String price, String fee) {
            setLayout(new BorderLayout(10, 0));
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(370, 75));
            setBorder(createRoundedBorder(borderColorDefault, 1));

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
            checkboxPanel.setOpaque(false);

            JPanel checkboxWrapper = new JPanel(new GridBagLayout());
            checkboxWrapper.setOpaque(false);
            checkboxWrapper.setPreferredSize(new Dimension(70, 70));
            checkboxWrapper.add(checkboxPanel);

            add(checkboxWrapper, BorderLayout.WEST);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 15));

            JPanel titlePriceRow = new JPanel();
            titlePriceRow.setLayout(new BoxLayout(titlePriceRow, BoxLayout.X_AXIS));
            titlePriceRow.setOpaque(false);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(FontUtil.getOutfitBoldFont(16f));
            titlePriceRow.add(titleLabel);
            titlePriceRow.add(Box.createRigidArea(new Dimension(70, 0)));
            titlePriceRow.add(Box.createHorizontalGlue());

            JLabel priceLabel = new JLabel(price);
            priceLabel.setFont(FontUtil.getInterFont(14f));
            priceLabel.setForeground(Color.decode("#1E1E1E"));
            titlePriceRow.add(priceLabel);

            JPanel feeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            feeRow.setOpaque(false);
            JLabel feeLabel = new JLabel(fee);
            feeLabel.setFont(FontUtil.getInterFont(14f));
            feeLabel.setForeground(Color.decode("#1E1E1E"));
            feeRow.add(feeLabel);

            contentPanel.add(titlePriceRow);
            contentPanel.add(Box.createVerticalStrut(5));
            contentPanel.add(feeRow);

            add(contentPanel, BorderLayout.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(createRoundedBorder(borderColorHover, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(selected ? createRoundedBorder(borderColorSelected, 2) : createRoundedBorder(borderColorDefault, 1));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selected = !selected;
                    setBorder(selected ? createRoundedBorder(borderColorSelected, 2) : createRoundedBorder(borderColorDefault, 1));
                    repaint();
                }
            });
        }

        private Border createRoundedBorder(Color color, int visualThickness) {
            return new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(color);
                    g2d.setStroke(new BasicStroke(1.5f));
                    for (int i = 0; i < visualThickness; i++) {
                        g2d.drawRoundRect(x + i, y + i, width - 1 - 2 * i, height - 1 - 2 * i, borderRadius, borderRadius);
                    }
                    g2d.dispose();
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(5, 5, 5, 5);
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

        public void setSelected(boolean value) {
            this.selected = value;
            setBorder(selected ? createRoundedBorder(borderColorSelected, 2) : createRoundedBorder(borderColorDefault, 1));
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp3::new);
    }
}
