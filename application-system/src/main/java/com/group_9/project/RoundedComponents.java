package com.group_9.project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class RoundedComponents {

    public static class RoundedBorder implements Border {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static class RoundedTextField extends JTextField {
        private final String placeholder;

        public RoundedTextField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(15));
            setBackground(Color.decode("#FFFFFF"));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g2);

            if (getText().isEmpty() && !isFocusOwner()) {
                g2.setColor(Color.GRAY);
                FontMetrics fm = g2.getFontMetrics();
                int x = getInsets().left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2.drawString(placeholder, x, y);
            }

            g2.dispose();
        }
    }

    public static class RoundedPasswordField extends JPasswordField {
        private final String placeholder;

        public RoundedPasswordField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new RoundedBorder(15));
            setBackground(Color.decode("#FFFFFF"));
            setMargin(new Insets(5, 10, 5, 10));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            super.paintComponent(g2);

            if (getPassword().length == 0 && !isFocusOwner()) {
                g2.setColor(Color.GRAY);
                FontMetrics fm = g2.getFontMetrics();
                int x = getInsets().left;
                int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                g2.drawString(placeholder, x, y);
            }

            g2.dispose();
        }
    }

    //For CIVIL STATUS and GENDER in ACCOUNT DETAILS
    public static JComboBox<String> createRoundedComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setUI(new RoundedComboBoxUI());
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(FontUtil.getOutfitFont(13f));
        comboBox.setBorder(new RoundedBorder(15));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if (isSelected) {
                    setBackground(new Color(255, 255, 255));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
                return c;
            }
        });
        return comboBox;
    }

    private static class RoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton("▼");
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            comboBox.setFont(FontUtil.getOutfitBoldFont(30f));
            button.setForeground(new Color(80, 0, 80));
            return button;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255));
            g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15);
            g2.dispose();
        }
    }

    //For UPDATE and SAVE CHANGES button in ACCOUNT DETAILS
    public static class RoundedButton extends JButton {
        private int radius;
        private Color borderColor = new Color(0, 0, 0, 0); // Transparent border by default

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());

            // Change cursor to pointer when hovering
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            if (borderColor.getAlpha() > 0) {
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            }

            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Do nothing
        }
    }
}