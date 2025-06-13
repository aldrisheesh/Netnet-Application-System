package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedComponents {

    public static class RoundedBorder implements Border {
        private final int radius;
        private Color borderColor;

        public RoundedBorder(int radius) {
            this.radius = radius;
            this.borderColor = Color.GRAY;
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
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
            g2d.setColor(borderColor);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static class RoundedTextField extends JTextField {
        private final String placeholder;
        private final RoundedBorder border;

        public RoundedTextField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            this.border = new RoundedBorder(15);
            setBorder(border);
            setOpaque(false);
            setBackground(Color.decode("#FFFFFF"));

            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    repaint();
                }

                public void focusLost(FocusEvent e) {
                    repaint();
                }
            });
        }

        public void setValidationBorderColor(Color color) {
            border.setBorderColor(color);
            repaint();
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

        @Override
        public Insets getInsets() {
            return new Insets(10, 14, 10, 14);
        }
    }

    public static class RoundedPasswordField extends JPasswordField {
        private final String placeholder;
        private final RoundedBorder border;
        private boolean showPassword = false;
        private Rectangle eyeIconBounds;
    
        private final Image eyeIcon;
        private final Image eyeOffIcon;
    
        public RoundedPasswordField(String placeholder, int columns) {
            super(columns);
            this.placeholder = placeholder;
            this.border = new RoundedBorder(15);
            setBorder(border);
            setOpaque(false);
            setBackground(Color.decode("#FFFFFF"));
            setEchoChar('•');
    
            eyeIcon = loadIcon("/icons/eye.png", 18, 18);
            eyeOffIcon = loadIcon("/icons/eye-off.png", 18, 18);
    
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
    
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (eyeIconBounds != null && eyeIconBounds.contains(e.getPoint())) {
                        togglePasswordVisibility();
                    }
                }
            });
    
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    if (eyeIconBounds != null && eyeIconBounds.contains(e.getPoint())) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    }
                }
            });
    
            ToolTipManager.sharedInstance().registerComponent(this);
        }
    
        private Image loadIcon(String path, int width, int height) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                System.err.println("Failed to load icon: " + path);
                return null;
            }
        }
    
        private void togglePasswordVisibility() {
            showPassword = !showPassword;
            setEchoChar(showPassword ? (char) 0 : '•');
            repaint();
        }
    
        public void setValidationBorderColor(Color color) {
            border.setBorderColor(color);
            repaint();
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
    
            int iconSize = 18;
            int iconPaddingRight = 20;
            int iconX = getWidth() - iconSize - iconPaddingRight;
            int iconY = (getHeight() - iconSize) / 2;
            eyeIconBounds = new Rectangle(iconX, iconY, iconSize, iconSize);
    
            Image iconToDraw = showPassword ? eyeOffIcon : eyeIcon;
            if (iconToDraw != null) {
                g2.drawImage(iconToDraw, iconX, iconY, this);
            }
    
            g2.dispose();
        }
    
        @Override
        public Insets getInsets() {
            return new Insets(10, 14, 10, 40); // Leave space for icon
        }
    
        @Override
        public String getToolTipText(MouseEvent event) {
            if (eyeIconBounds != null && eyeIconBounds.contains(event.getPoint())) {
                return showPassword ? "Hide password" : "Show password";
            }
            return null;
        }
    
        @Override
        public JToolTip createToolTip() {
            return new ToolTipUtil.CustomToolTip();
        }
    }
    

    public static class RoundedComboBox<T> extends JComboBox<T> {
        private final RoundedBorder border;

        public RoundedComboBox(DefaultComboBoxModel<T> model, int radius) {
            super(model);
            this.border = new RoundedBorder(radius);
            setBorder(border);
            setBackground(Color.WHITE);
        }

        public void setValidationBorderColor(Color color) {
            border.setBorderColor(color);
            repaint();
        }
    }

    public static class RoundedButton extends JButton {
        private int radius;
        private Color borderColor = new Color(0, 0, 0, 0);

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
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

            g2.setColor(getModel().isPressed() ? getBackground().darker()
                    : getModel().isRollover() ? getBackground().brighter() : getBackground());

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            if (borderColor.getAlpha() > 0) {
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            }

            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
