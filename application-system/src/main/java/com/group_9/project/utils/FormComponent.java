package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class FormComponent {
    private static final int RADIUS = 15;

    public static JComboBox<String> createStyledComboBox(String placeholder, String[] options) {
        DefaultComboBoxModel<String> mdlOptions = new DefaultComboBoxModel<>();
        for (String strOption : options) mdlOptions.addElement(strOption);

        RoundedComponents.RoundedComboBox<String> cboBox = new RoundedComponents.RoundedComboBox<>(mdlOptions, RADIUS);
        cboBox.setFont(FontUtil.getOutfitFont(15f));
        cboBox.setPreferredSize(new Dimension(375, 35));
        cboBox.setFocusable(false);
        cboBox.setSelectedIndex(-1);
        cboBox.setForeground(Color.GRAY);

        cboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                boolean isPlaceholder = (cboBox.getSelectedIndex() == -1 && index == -1);
                setBorder(BorderFactory.createEmptyBorder(0, isPlaceholder ? 0 : 10, 0, 0));
                setText(isPlaceholder ? placeholder : value.toString());
                setForeground(isPlaceholder ? Color.GRAY : (isSelected ? new Color(43, 43, 43) : Color.BLACK));
                setBackground(isSelected ? new Color(240, 240, 240) : Color.WHITE);
                setEnabled(true);
                return this;
            }
        });

        cboBox.addActionListener(e -> cboBox.setForeground(cboBox.getSelectedIndex() != -1 ? Color.BLACK : Color.GRAY));
        cboBox.setBackground(Color.WHITE);
        cboBox.setOpaque(true);

        cboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton cmdButton = new JButton();
            
                try {
                    ImageIcon icnOriginal = new ImageIcon(FormComponent.class.getResource("/icons/dropdown-icn.png"));
                    Image imgScaled = icnOriginal.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    ImageIcon icnDropdown = new ImageIcon(imgScaled);
                    cmdButton.setIcon(icnDropdown);
                } catch (Exception e) {
                    System.err.println("Failed to load dropdown icon: " + e.getMessage());
                }
            
                cmdButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                cmdButton.setContentAreaFilled(false);
                cmdButton.setFocusPainted(false);
                cmdButton.setPreferredSize(new Dimension(25, 35));
                cmdButton.setHorizontalAlignment(SwingConstants.CENTER);
                cmdButton.setVerticalAlignment(SwingConstants.CENTER);
                cmdButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
                return cmdButton;
            }
            

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        return cboBox;
    }

    public static JPanel createPairPanel(JComponent left, JComponent right) {
        JPanel pnlPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlPanel.setOpaque(false);
        pnlPanel.add(left);
        pnlPanel.add(right);
        return pnlPanel;
    }
}
