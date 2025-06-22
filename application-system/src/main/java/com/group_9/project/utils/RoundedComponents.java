package com.group_9.project.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedComponents {

    public static class RoundedBorder implements Border {
        private final int intRadius;
        private Color clrBorderColor;

        public RoundedBorder(int intRadius) {
            this.intRadius = intRadius;
            this.clrBorderColor = Color.GRAY;
        }

        public void setBorderColor(Color color) {
            this.clrBorderColor = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(intRadius + 1, intRadius + 1, intRadius + 1, intRadius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(clrBorderColor);
            g2d.drawRoundRect(x, y, width - 1, height - 1, intRadius, intRadius);
        }
    }

    public static class RoundedTextField extends JTextField {
        private final String strPlaceholder;
        private final RoundedBorder bdrBorder;

        public RoundedTextField(String strPlaceholder, int columns) {
            super(columns);
            this.strPlaceholder = strPlaceholder;
            this.bdrBorder = new RoundedBorder(15);
            setBorder(bdrBorder);
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
            bdrBorder.setBorderColor(color);
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
                g2.drawString(strPlaceholder, x, y);
            }

            g2.dispose();
        }

        @Override
        public Insets getInsets() {
            return new Insets(10, 14, 10, 14);
        }
    }

    public static class RoundedPasswordField extends JPasswordField {
        private final String strPlaceholder;
        private final RoundedBorder bdrBorder;
        private boolean bolShowPassword = false;
        private Rectangle recEyeIconBounds;
    
        private final Image imgEyeIcon;
        private final Image imgEyeOffIcon;
    
        public RoundedPasswordField(String strPlaceholder, int columns) {
            super(columns);
            this.strPlaceholder = strPlaceholder;
            this.bdrBorder = new RoundedBorder(15);
            setBorder(bdrBorder);
            setOpaque(false);
            setBackground(Color.decode("#FFFFFF"));
            setEchoChar('•');
    
            imgEyeIcon = loadIcon("/icons/eye.png", 18, 18);
            imgEyeOffIcon = loadIcon("/icons/eye-off.png", 18, 18);
    
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { repaint(); }
                public void focusLost(FocusEvent e) { repaint(); }
            });
    
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (recEyeIconBounds != null && recEyeIconBounds.contains(e.getPoint())) {
                        togglePasswordVisibility();
                    }
                }
            });
    
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    if (recEyeIconBounds != null && recEyeIconBounds.contains(e.getPoint())) {
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
            bolShowPassword = !bolShowPassword;
            setEchoChar(bolShowPassword ? (char) 0 : '•');
            repaint();
        }
    
        public void setValidationBorderColor(Color color) {
            bdrBorder.setBorderColor(color);
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
            recEyeIconBounds = new Rectangle(iconX, iconY, iconSize, iconSize);
    
            Image iconToDraw = bolShowPassword ? imgEyeOffIcon : imgEyeIcon;
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
            if (recEyeIconBounds != null && recEyeIconBounds.contains(event.getPoint())) {
                return bolShowPassword ? "Hide password" : "Show password";
            }
            return null;
        }
    
        @Override
        public JToolTip createToolTip() {
            return new ToolTipUtil.CustomToolTip();
        }
    }
    

    public static class RoundedComboBox<T> extends JComboBox<T> {
        private final RoundedBorder bdrBorder;

        public RoundedComboBox(DefaultComboBoxModel<T> model, int radius) {
            super(model);
            this.bdrBorder = new RoundedBorder(radius);
            setBorder(bdrBorder);
            setBackground(Color.WHITE);
        }

        public void setValidationBorderColor(Color color) {
            bdrBorder.setBorderColor(color);
            repaint();
        }
    }

    public static class RoundedButton extends JButton {
        private int intRadius;
        private Color clrBorderColor = new Color(0, 0, 0, 0);

        public RoundedButton(String text, int intRadius) {
            super(text);
            this.intRadius = intRadius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public void setBorderColor(Color color) {
            this.clrBorderColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getModel().isPressed() ? getBackground().darker()
                    : getModel().isRollover() ? getBackground().brighter() : getBackground());

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), intRadius, intRadius);

            if (clrBorderColor.getAlpha() > 0) {
                g2.setColor(clrBorderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, intRadius, intRadius);
            }

            super.paintComponent(g2);
            g2.dispose();
        }
    }

    /** Panel with rounded corners and a subtle shadow for reuse across pages. */
    public static class RoundedShadowPanel extends JPanel {
        private final int intRadius;
        private final int intShadowOffset;

        public RoundedShadowPanel(int intRadius, int intShadowOffset) {
            super(null);
            this.intRadius = intRadius;
            this.intShadowOffset = intShadowOffset;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(intShadowOffset, intShadowOffset,
                    getWidth() - intShadowOffset, getHeight() - intShadowOffset,
                    intRadius, intRadius);

            g2.setColor(new Color(255, 241, 255));
            g2.fillRoundRect(0, 0,
                    getWidth() - intShadowOffset, getHeight() - intShadowOffset,
                    intRadius, intRadius);

            g2.setColor(new Color(220, 200, 230));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0,
                    getWidth() - intShadowOffset - 1, getHeight() - intShadowOffset - 1,
                    intRadius, intRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
