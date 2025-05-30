package com.group_9.project;

import java.awt.*;
import javax.swing.*;

public class accdet extends JFrame {
    private static final int RADIUS = 15;

    public accdet() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);
        container.add(innerContent);

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
        stepWrapper.add(CreateStepTracker.createStepTracker(0));
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(826, 60));

        JPanel leftLabels = new JPanel();
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.setOpaque(false);

        JLabel subtitle = new JLabel("PERSONAL INFORMATION", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel("Provide the necessary details to register your information with us");
        subNote.setFont(FontUtil.getOutfitFont(12f));
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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridy = 0;

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(styleComboBoxField("Home Ownership", new String[]{"Owned", "Living with Relatives", "Mortgaged", "Rented"}), gbc);
        gbc.gridx = 1;
        formPanel.add(createPairPanel(styleComboBoxField("Company Paid?", new String[]{"Yes", "No"}), createRoundedTextField("Years of Residency")), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createRoundedTextField("Name of Owner"), gbc);
        gbc.gridx = 1;
        formPanel.add(createRoundedTextField("Contact No."), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createRoundedTextField("House/Room No./Floor"), gbc);
        gbc.gridx = 1;
        formPanel.add(createRoundedTextField("Apartment/Compound/Building"), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createRoundedTextField("Subdivision"), gbc);
        gbc.gridx = 1;
        formPanel.add(createRoundedTextField("Barangay"), gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createPairPanel(createRoundedTextField("Street"), createRoundedTextField("Municipality/City")), gbc);
        gbc.gridx = 1;
        formPanel.add(createPairPanel(createRoundedTextField("Province"), createRoundedTextField("Zip Code")), gbc);

        innerContent.add(formPanel);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

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

        setVisible(true);
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

    private RoundedComponents.RoundedTextField createRoundedTextField(String placeholder) {
        RoundedComponents.RoundedTextField field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(375, 35));
        return field;
    }

    private JComboBox<String> styleComboBoxField(String placeholder, String[] options) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setFont(FontUtil.getOutfitFont(15f));
        box.setPreferredSize(new Dimension(200, 30));
        box.setBorder(new RoundedComponents.RoundedBorder(RADIUS));
        box.setFocusable(false);
        box.setSelectedIndex(-1);

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

                if (value == null && index == -1 && box.getSelectedIndex() == -1) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    setBackground(Color.WHITE);
                } else {
                    if (!isSelected) {
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                    }
                }
                return this;
            }
        });

        box.addActionListener(event -> {
            if (box.getSelectedIndex() == -1) {
                box.setForeground(Color.GRAY);
            } else {
                box.setForeground(Color.BLACK);
            }
        });

        box.setBackground(new Color(255, 242, 255));
        box.setOpaque(true);

        ImageIcon dropdownIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icons/dropdown-icn.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
            dropdownIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            dropdownIcon = createFallbackArrowIcon();
        }

        final ImageIcon finalDropdownIcon = dropdownIcon;

        box.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton();
                button.setIcon(finalDropdownIcon);
                button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(25, 30));
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(new Color(255, 242, 255));
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        return box;
    }

    private ImageIcon createFallbackArrowIcon() {
        int size = 12;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        int[] xPoints = {size/4, size*3/4, size/2};
        int[] yPoints = {size/3, size/3, size*2/3};
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.dispose();
        return new ImageIcon(img);
    }

    private JPanel createPairPanel(JComponent left, JComponent right) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);
        panel.add(left);
        panel.add(right);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(accdet::new);
    }
}
