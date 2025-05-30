package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class FormComponent {

    private static final int RADIUS = 15; // Adjust based on your desired corner radius

    public static JComboBox<String> createStyledComboBox(String placeholder, String[] options) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setFont(FontUtil.getOutfitFont(15f));
        box.setPreferredSize(new Dimension(375, 35));
        box.setBorder(new RoundedComponents.RoundedBorder(RADIUS));
        box.setFocusable(false);
        box.setSelectedIndex(-1); // No initial selection

        // Custom renderer
        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder());

                if (value == null && index == -1 && box.getSelectedIndex() == -1) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    setBackground(Color.WHITE);
                } else if (!isSelected) {
                    setForeground(Color.BLACK);
                    setBackground(Color.WHITE);
                }

                return this;
            }
        });

        box.setBackground(Color.decode("#FFFFFF"));
        box.setOpaque(true);

        // Load and scale the dropdown icon
        ImageIcon dropdownIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(FormComponent.class.getResource("/icons/dropdown-icn.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
            dropdownIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Failed to load dropdown icon: " + e.getMessage());
        }

        // Custom UI
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
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.decode("#FFFFFF")); 
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
