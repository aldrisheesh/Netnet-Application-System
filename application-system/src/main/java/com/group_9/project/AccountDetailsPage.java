package com.group_9.project;
import com.group_9.project.utils.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.group_9.project.utils.RoundedComponents.*;

public class AccountDetailsPage extends Template {
    
    private boolean isEditMode = false;
    private JButton actionButton;
    private List<JTextField> textFields = new ArrayList<>();
    private List<JComboBox<String>> comboBoxes = new ArrayList<>();

    public AccountDetailsPage() {
        BackgroundPanel background = new BackgroundPanel(3);
        background.setLayout(null);
        setContentPane(background);

        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("images/converge_logo.png")); //background image
        Image scaledImage = originalIcon.getImage().getScaledInstance(123, 44, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(40, 30, 123, 44);
        background.add(logo);

        String[] navItems = {"Home", "Plans", "Help & Support", "About Us"}; //navbar
        int xPos = 900; 
        int spacing = 30;
        Color normalColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : navItems) {
            JLabel label = new JLabel(item);
            label.setFont(FontUtil.getOutfitFont(16f));
            label.setForeground(normalColor);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    label.setForeground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    label.setForeground(normalColor);
                }

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "Home" -> {
                            new Homepage().setVisible(true);
                            dispose();
                        }
                        case "Plans" -> {
                            new PlansPage().setVisible(true);
                            dispose();
                        }
                        case "Help & Support" -> {
                            new HelpSupportPage().setVisible(true);
                            dispose();
                        }
                        case "About Us" -> {
                            new AboutUsPage().setVisible(true);
                            dispose();
                        }
                    }
                }
            });

            int textWidth = label.getPreferredSize().width;
            label.setBounds(xPos, 30, textWidth + 10, 40);
            background.add(label);
            xPos += textWidth + spacing + 10;
        }

        JPanel sidebar = createSidebar();
        background.add(sidebar);

        JPanel content = createContentPanel();
        background.add(content);

        JPanel detailsContainer = createDetailsContainer();
        content.add(detailsContainer);
        
        SwingUtilities.invokeLater(() -> {
            background.requestFocusInWindow();
        });
    }

    private JPanel createSidebar() { //sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBounds(50, 125, 200, 300);
        sidebar.setBackground(new Color(0, 0, 0, 0));
        sidebar.setOpaque(false); 

        JLabel title = new JLabel("MY ACCOUNT");
        title.setFont(FontUtil.getOutfitBoldFont(25f));
        title.setForeground(new Color(42, 2, 67, 255));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        title.setOpaque(false);
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(15));

        String[] items = {"My details", "My address", "My subscriptions"};
        Color selectedColor = new Color(132, 0, 159, 255);
        Color defaultColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : items) {
            Color color = item.equals("My details") ? selectedColor : defaultColor;
            JLabel label = makeSidebarLabel("   " + item, color);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            label.setOpaque(false);

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "My details" -> {
                            new AccountDetailsPage().setVisible(true);
                            dispose();
                        }
                        case "My address" -> {
                            new AccountAddressPage().setVisible(true);
                            dispose();
                        }
                        case "My subscriptions" -> {
                            new AccountSubsPage().setVisible(true);
                            dispose();
                        }
                    }
                }

                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!item.equals("My details")) {
                        label.setForeground(hoverColor);
                        label.setOpaque(false);
                        label.repaint();
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!item.equals("My details")) {
                        label.setForeground(defaultColor);
                        label.setOpaque(false);
                        label.repaint();
                    }
                }
            });

            sidebar.add(label);
            sidebar.add(Box.createVerticalStrut(30));
        }

        return sidebar;
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
        content.setBounds(290, 150, 1020, 720);
        content.setOpaque(false);
        return content;
    }

    private JPanel createDetailsContainer() {
        JPanel detailsContainer = new JPanel(null);
        detailsContainer.setBackground(new Color(0, 0, 0, 0));
        detailsContainer.setBounds(0, 0, 1250, 700);
        detailsContainer.setOpaque(false);

        JLabel titleLabel = new JLabel("My details"); //header1
        titleLabel.setFont(FontUtil.getOutfitBoldFont(26f));
        titleLabel.setForeground(new Color(42, 2, 67, 255));
        titleLabel.setBounds(70, 50, 300, 30);
        detailsContainer.add(titleLabel);

        JLabel sectionLabel = new JLabel("PERSONAL INFORMATION"); //header2
        sectionLabel.setFont(FontUtil.getOutfitFont(16f));
        sectionLabel.setBounds(70, 100, 300, 20);
        detailsContainer.add(sectionLabel);

        JSeparator sep = new JSeparator(); //line separator
        sep.setBounds(70, 130, 880, 1);
        sep.setForeground(new Color(180, 180, 180));
        detailsContainer.add(sep);

        String[] leftLabels = { "USERNAME", "PASSWORD", "EMAIL ADDRESS", "MOBILE NO. / TEL. NO." }; //first column attributes
        String[] rightLabels = { "FULL NAME", "BIRTHDAY", "GENDER", "CIVIL STATUS", "NATIONALITY", "NAME OF SPOUSE (IF MARRIED)", "FULL MOTHER'S MAIDEN NAME" }; //second column attributes

        int yLeft = 150;
        for (int i = 0; i < leftLabels.length; i++) {
            JLabel label = new JLabel(leftLabels[i]);
            label.setFont(FontUtil.getOutfitBoldFont(13f));
            label.setForeground(new Color(42, 2, 67));
            label.setBounds(95, yLeft, 200, 40);
            detailsContainer.add(label);

            JTextField field = leftLabels[i].equals("PASSWORD")
                    ? new RoundedPasswordField("Enter your password", 20)
                    : new RoundedTextField("Enter your " + leftLabels[i].toLowerCase(), 20);

            field.setFont(FontUtil.getOutfitFont(15f));
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
            field.setCaretColor(Color.BLACK);
            field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            field.setEditable(false); 
            field.setFocusable(false);
            textFields.add(field); 

            JPanel wrapper = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY);
                    g2.setStroke(new BasicStroke(.8f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                }
            };
            wrapper.setLayout(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
            wrapper.add(field);

            wrapper.setBounds(95, yLeft + 33, 330, 47);
            detailsContainer.add(wrapper);

            yLeft += 74;
            if (i == 1) yLeft += 130;
        }

        JLabel contactLabel = new JLabel("Contact Information");
        contactLabel.setFont(FontUtil.getOutfitBoldFont(17f));
        contactLabel.setBounds(95, 350, 300, 20);
        detailsContainer.add(contactLabel);

        JLabel contactInfo = new JLabel("<html>Keep your contact info up to date so we can reach you<br>with important updates.</html>");
        contactInfo.setFont(FontUtil.getOutfitFont(14f));
        contactInfo.setBounds(95, 375, 350, 40);
        detailsContainer.add(contactInfo);

        int yRight = 150;
        for (int i = 0; i < rightLabels.length; i++) {
            String labelText = rightLabels[i];
            JLabel label = new JLabel(labelText);
            label.setFont(FontUtil.getOutfitBoldFont(13f));
            label.setForeground(new Color(42, 2, 67));

            if (labelText.equals("BIRTHDAY")) {
                label.setBounds(490, yRight + 5, 150, 28);
                detailsContainer.add(label);

                JTextField bdayField = new RoundedTextField("MM/DD/YYYY", 15);
                bdayField.setFont(FontUtil.getOutfitFont(15f));
                bdayField.setBackground(Color.WHITE);
                bdayField.setForeground(Color.BLACK);
                bdayField.setCaretColor(Color.BLACK);
                bdayField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                bdayField.setEditable(false); 
                bdayField.setFocusable(false); 
                textFields.add(bdayField); 

                JPanel bdayWrapper = createTextFieldWrapper(bdayField, 15);
                bdayWrapper.setBounds(490, yRight + 31, 210, 47);
                detailsContainer.add(bdayWrapper);

                JLabel genderLabel = new JLabel("GENDER");
                genderLabel.setFont(FontUtil.getOutfitBoldFont(13f));
                genderLabel.setForeground(new Color(42, 2, 67));
                genderLabel.setBounds(730, yRight + 5, 150, 28);
                detailsContainer.add(genderLabel);

                JComboBox<String> genderCombo = FormComponent.createStyledComboBox("Choose Gender", new String[]{"Male", "Female"});
                genderCombo.setEnabled(false);
                comboBoxes.add(genderCombo);
                genderCombo.setBounds(730, yRight + 30, 190, 45);
                detailsContainer.add(genderCombo);

                yRight += 77;
                i++;
            } else if (labelText.equals("CIVIL STATUS")) {
                label.setBounds(490, yRight + 5, 150, 20);
                detailsContainer.add(label);

                JComboBox<String> civilCombo = FormComponent.createStyledComboBox("Choose Civil Status", new String[]{"Single", "Married", "Separated", "Widow"});
                civilCombo.setEnabled(false);
                comboBoxes.add(civilCombo);                
                civilCombo.setBounds(490, yRight + 25, 210, 45);
                detailsContainer.add(civilCombo);

                JLabel natLabel = new JLabel("NATIONALITY");
                natLabel.setFont(FontUtil.getOutfitBoldFont(13f));
                natLabel.setForeground(new Color(42, 2, 67));
                natLabel.setBounds(730, yRight + 5, 150, 20);
                detailsContainer.add(natLabel);

                JTextField natField = new RoundedTextField("e.g., Filipino", 25);
                natField.setFont(FontUtil.getOutfitFont(15f));
                natField.setBackground(Color.WHITE);
                natField.setForeground(Color.BLACK);
                natField.setCaretColor(Color.BLACK);
                natField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                natField.setEditable(false);
                natField.setFocusable(false); 
                textFields.add(natField); 

                JPanel natWrapper = createTextFieldWrapper(natField, 15);
                natWrapper.setBounds(730, yRight + 25, 190, 47);
                detailsContainer.add(natWrapper);

                yRight += 74;
                i++;
            } else {
                label.setBounds(490, yRight + 10, 270, 20);
                detailsContainer.add(label);

                JTextField field = new RoundedTextField("Enter " + labelText.toLowerCase(), 20);
                field.setFont(FontUtil.getOutfitFont(15f));
                field.setBackground(Color.WHITE);
                field.setForeground(Color.BLACK);
                field.setCaretColor(Color.BLACK);
                field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                field.setEditable(false); 
                field.setFocusable(false); 
                textFields.add(field);

                JPanel wrapper = createTextFieldWrapper(field, 15);
                wrapper.setBounds(490, yRight + 33, 430, 47);
                detailsContainer.add(wrapper);

                yRight += 75;
            }
        }

        actionButton = new RoundedComponents.RoundedButton("UPDATE", 20); //action button for update and save changes toggle
        actionButton.setFont(FontUtil.getOutfitBoldFont(18f));
        actionButton.setBounds(740, 590, 150, 45);
        styleButton(actionButton);
        
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEditMode) {
                    isEditMode = true;
                    actionButton.setText("SAVE CHANGES");
                    enableEditing(true);
                } else {
                    isEditMode = false;
                    actionButton.setText("UPDATE");
                    enableEditing(false);
                }
            }
        });
        
        detailsContainer.add(actionButton);

        return detailsContainer;
    }

    private void enableEditing(boolean enabled) {
        for (JTextField field : textFields) {
            field.setEditable(enabled);
            field.setFocusable(enabled); 
            field.setBackground(Color.WHITE);
        }
        for (JComboBox<String> combo : comboBoxes) {
            combo.setEnabled(enabled);
        }
    }

    private JPanel createTextFieldWrapper(JTextField field, int arc) {
        JPanel wrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(140, 140, 140));
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
            }
        };
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        wrapper.add(field, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel makeSidebarLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontUtil.getOutfitFont(18f));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(45, 2, 67));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(FontUtil.getOutfitBoldFont(14f));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountDetailsPage().setVisible(true));
    }
}