package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class FormComponent {
    private static final int RADIUS = 15;

    public static JComboBox<String> createStyledComboBox(String placeholder, String[] options) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String option : options) model.addElement(option);

        RoundedComponents.RoundedComboBox<String> box = new RoundedComponents.RoundedComboBox<>(model, RADIUS);
        box.setFont(FontUtil.getOutfitFont(15f));
        box.setPreferredSize(new Dimension(375, 35));
        box.setFocusable(false);
        box.setSelectedIndex(-1);
        box.setForeground(Color.GRAY);

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                boolean isPlaceholder = (box.getSelectedIndex() == -1 && index == -1);
                setBorder(BorderFactory.createEmptyBorder(0, isPlaceholder ? 0 : 10, 0, 0));
                setText(isPlaceholder ? placeholder : value.toString());
                setForeground(isPlaceholder ? Color.GRAY : (isSelected ? new Color(43, 43, 43) : Color.BLACK));
                setBackground(isSelected ? new Color(240, 240, 240) : Color.WHITE);
                setEnabled(true);
                return this;
            }
        });

        box.addActionListener(e -> box.setForeground(box.getSelectedIndex() != -1 ? Color.BLACK : Color.GRAY));
        box.setBackground(Color.WHITE);
        box.setOpaque(true);

        box.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton();
            
                try {
                    ImageIcon originalIcon = new ImageIcon(FormComponent.class.getResource("/icons/dropdown-icn.png"));
                    Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    ImageIcon finalDropdownIcon = new ImageIcon(scaledImage);
                    button.setIcon(finalDropdownIcon);
                } catch (Exception e) {
                    System.err.println("Failed to load dropdown icon: " + e.getMessage());
                }
            
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
