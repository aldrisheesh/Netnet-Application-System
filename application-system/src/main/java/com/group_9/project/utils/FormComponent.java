package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class FormComponent {

    private static final int RADIUS = 15; // Adjust based on your desired corner radius

    public static JComboBox<String> createStyledComboBox(String placeholder, String[] options) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String option : options) {
            model.addElement(option);
        }

        JComboBox<String> box = new JComboBox<>(model);
        box.setFont(FontUtil.getOutfitFont(15f));
        box.setPreferredSize(new Dimension(375, 35));
        box.setBorder(new RoundedComponents.RoundedBorder(RADIUS));
        box.setFocusable(false);
        box.setSelectedIndex(-1); // no selection initially
        box.setForeground(Color.GRAY); // placeholder color

        // Renderer for placeholder and proper hover highlights
        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                boolean isPlaceholder = (box.getSelectedIndex() == -1 && index == -1);

                if (!isPlaceholder) {
                    setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // add left padding
                } else {
                    setBorder(BorderFactory.createEmptyBorder()); // no padding for placeholder
                }

                if (isPlaceholder) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                } else {
                    setForeground(isSelected ? new Color(43, 43, 43) : Color.BLACK);
                    setBackground(isSelected ? new Color(240, 240, 240) : Color.WHITE);
                }

                // Force normal appearance even when disabled
                setEnabled(true);

                return this;
            }
        });

        // Change text color when selection is made
        box.addActionListener(e -> {
            if (box.getSelectedIndex() != -1) {
                box.setForeground(Color.BLACK);
            } else {
                box.setForeground(Color.GRAY);
            }
        });

        box.setBackground(Color.WHITE);
        box.setOpaque(true);

        // Custom dropdown icon
        ImageIcon dropdownIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(FormComponent.class.getResource("/icons/dropdown-icn.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            dropdownIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Failed to load dropdown icon: " + e.getMessage());
        }

        ImageIcon finalDropdownIcon = dropdownIcon;
        box.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton();
                if (finalDropdownIcon != null) button.setIcon(finalDropdownIcon);
                button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(25, 35));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setEnabled(true); // Always appear enabled
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        return box;
    }

    public static JPanel createPairPanel(JComponent left, JComponent right) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);
        panel.add(left);
        panel.add(right);
        return panel;
    }
}
