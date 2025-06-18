package com.group_9.project;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import com.group_9.project.utils.RoundedComponents.*;

public class AccountAddressPage extends Template {
    private JTextField homeOwnershipField;
    private JTextField companyPaidField;
    private JTextField yearsField;
    private JTextField nameOwnerField;
    private JTextField contactField;
    private JTextField houseField;
    private JTextField apartmentField;
    private JTextField subdivisionField;
    private JTextField barangayField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField provinceField;
    private JTextField zipField;

    public AccountAddressPage() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 3);
        
        JPanel sidebar = createSidebar();
        background.add(sidebar);

        JPanel content = FormUIUtil.createRoundedShadowPanel(290, 150, 1020, 720);
        background.add(content);

        JPanel detailsContainer = createDetailsContainer();
        content.add(detailsContainer);

        populateFromSession();  
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
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

        String[] items = {"My Details", "My Address", "My Subscriptions"}; //sidebar selection
        Color selectedColor = new Color(132, 0, 159, 255);
        Color defaultColor = new Color(22, 6, 48, 128);
        Color hoverColor = new Color(62, 10, 118);

        for (String item : items) {
            Color color = item.equals("My Address") ? selectedColor : defaultColor;
            JLabel label = makeSidebarLabel("   " + item, color);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            label.setOpaque(false);

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (item) {
                        case "My Details" -> {
                            new AccountDetailsPage().setVisible(true);
                            dispose();
                        }
                        case "My Address" -> {
                            new AccountAddressPage().setVisible(true);
                            dispose();
                        }
                        case "My Subscriptions" -> {
                            new AccountSubsPage().setVisible(true);
                            dispose();
                        }
                    }
                }

                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!item.equals("My Address")) {
                        label.setForeground(hoverColor);
                        label.setOpaque(false);
                        label.repaint();
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!item.equals("My Address")) {
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


    private JPanel createDetailsContainer() {
        JPanel detailsContainer = new JPanel(null);
        detailsContainer.setBackground(new Color(0, 0, 0, 0));
        detailsContainer.setBounds(0, 0, 1250, 700);
        detailsContainer.setOpaque(false);

        JLabel titleLabel = new JLabel("MY ADDRESS"); //header1
        titleLabel.setFont(FontUtil.getOutfitBoldFont(26f));
        titleLabel.setForeground(new Color(42, 2, 67, 255));
        titleLabel.setBounds(70, 50, 300, 30);
        detailsContainer.add(titleLabel);

        JLabel sectionLabel = new JLabel("SERVICE ADDRESS"); //header2
        sectionLabel.setFont(FontUtil.getOutfitFont(16f));
        sectionLabel.setBounds(70, 100, 300, 20);
        detailsContainer.add(sectionLabel);

        JSeparator sep = new JSeparator(); //line separator
        sep.setBounds(70, 130, 880, 1);
        sep.setForeground(new Color(180, 180, 180));
        detailsContainer.add(sep);

        JLabel reminder = new JLabel("<html>This is your registered service address. For minor corrections (e.g., spelling or formatting), please contact our support team."); //disclaimer/support notice
        reminder.setFont(FontUtil.getInterFont(13f));
        reminder.setBounds(150, 610, 800, 40);
        detailsContainer.add(reminder);

        int currentY = 160;

        currentY = createRow1(detailsContainer, currentY); //row1 - home ownership, comp paid, residence years
        
        currentY = createRow2(detailsContainer, currentY); //row2 - owner name, contact num
        
        currentY = createRow3(detailsContainer, currentY); //row3 - house num, apartment bldg
        
        currentY = createRow4(detailsContainer, currentY); //row4 - subd, brgy
        
        currentY = createRow5(detailsContainer, currentY); //row5 - street, city, province, zip

        return detailsContainer;
    }

    private int createRow1(JPanel container, int startY) {
        JLabel homeOwnershipLabel = new JLabel("HOME OWNERSHIP"); //home ownership
        homeOwnershipLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        homeOwnershipLabel.setForeground(new Color(42, 2, 67));
        homeOwnershipLabel.setBounds(95, startY, 150, 20);
        container.add(homeOwnershipLabel);

        homeOwnershipField = new RoundedTextField("  ", 20);
        homeOwnershipField.setFont(FontUtil.getOutfitFont(15f));
        homeOwnershipField.setBackground(Color.WHITE);
        homeOwnershipField.setForeground(Color.BLACK);
        homeOwnershipField.setCaretColor(Color.BLACK);
        homeOwnershipField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        homeOwnershipField.setEditable(false); 
        homeOwnershipField.setFocusable(false); 

        JPanel homeOwnershipWrapper = createTextFieldWrapper(homeOwnershipField, 15);
        homeOwnershipWrapper.setBounds(95, startY + 20, 270, 47);
        container.add(homeOwnershipWrapper);

        JLabel companyPaidLabel = new JLabel("COMPANY PAID"); //company paid
        companyPaidLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        companyPaidLabel.setForeground(new Color(42, 2, 67));
        companyPaidLabel.setBounds(395, startY, 120, 20);
        container.add(companyPaidLabel);

        companyPaidField = new RoundedTextField("  ", 20);
        companyPaidField.setFont(FontUtil.getOutfitFont(15f));
        companyPaidField.setBackground(Color.WHITE);
        companyPaidField.setForeground(Color.BLACK);
        companyPaidField.setCaretColor(Color.BLACK);
        companyPaidField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        companyPaidField.setEditable(false);
        companyPaidField.setFocusable(false); 

        JPanel companyPaidWrapper = createTextFieldWrapper(companyPaidField, 15);
        companyPaidWrapper.setBounds(395, startY + 20, 230, 47);
        container.add(companyPaidWrapper);

        JLabel yearsLabel = new JLabel("YEARS OF RESIDENCY"); //residence years
        yearsLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        yearsLabel.setForeground(new Color(42, 2, 67));
        yearsLabel.setBounds(655, startY, 180, 20);
        container.add(yearsLabel);

        yearsField = new RoundedTextField("  ", 20);
        yearsField.setFont(FontUtil.getOutfitFont(15f));
        yearsField.setBackground(Color.WHITE);
        yearsField.setForeground(Color.BLACK);
        yearsField.setCaretColor(Color.BLACK);
        yearsField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        yearsField.setEditable(false); 
        yearsField.setFocusable(false); 

        JPanel yearsWrapper = createTextFieldWrapper(yearsField, 15);
        yearsWrapper.setBounds(655, startY + 20, 270, 47);
        container.add(yearsWrapper);

        return startY + 75;
    }

    private int createRow2(JPanel container, int startY) {
        JLabel nameOwnerLabel = new JLabel("NAME OF OWNER"); //owner name
        nameOwnerLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        nameOwnerLabel.setForeground(new Color(42, 2, 67));
        nameOwnerLabel.setBounds(95, startY, 200, 20);
        container.add(nameOwnerLabel);

        nameOwnerField = new RoundedTextField("  ", 20);
        nameOwnerField.setFont(FontUtil.getOutfitFont(15f));
        nameOwnerField.setBackground(Color.WHITE);
        nameOwnerField.setForeground(Color.BLACK);
        nameOwnerField.setCaretColor(Color.BLACK);
        nameOwnerField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        nameOwnerField.setEditable(false);
        nameOwnerField.setFocusable(false);

        JPanel nameOwnerWrapper = createTextFieldWrapper(nameOwnerField, 15);
        nameOwnerWrapper.setBounds(95, startY + 20, 400, 47);
        container.add(nameOwnerWrapper);

        JLabel contactLabel = new JLabel("CONTACT NUMBER"); // contact num
        contactLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        contactLabel.setForeground(new Color(42, 2, 67));
        contactLabel.setBounds(525, startY, 200, 20);
        container.add(contactLabel);

        contactField = new RoundedTextField("  ", 20);
        contactField.setFont(FontUtil.getOutfitFont(15f));
        contactField.setBackground(Color.WHITE);
        contactField.setForeground(Color.BLACK);
        contactField.setCaretColor(Color.BLACK);
        contactField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contactField.setEditable(false); 
        contactField.setFocusable(false); 

        JPanel contactWrapper = createTextFieldWrapper(contactField, 15);
        contactWrapper.setBounds(525, startY + 20, 400, 47);
        container.add(contactWrapper);

        return startY + 75;
    }

    private int createRow3(JPanel container, int startY) {
        JLabel houseLabel = new JLabel("HOUSE/ROOM NO./FLOOR"); //house num
        houseLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        houseLabel.setForeground(new Color(42, 2, 67));
        houseLabel.setBounds(95, startY, 220, 20);
        container.add(houseLabel);

        houseField = new RoundedTextField("  ", 20);
        houseField.setFont(FontUtil.getOutfitFont(15f));
        houseField.setBackground(Color.WHITE);
        houseField.setForeground(Color.BLACK);
        houseField.setCaretColor(Color.BLACK);
        houseField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        houseField.setEditable(false); 
        houseField.setFocusable(false); 

        JPanel houseWrapper = createTextFieldWrapper(houseField, 15);
        houseWrapper.setBounds(95, startY + 20, 400, 47);
        container.add(houseWrapper);

        JLabel apartmentLabel = new JLabel("APARTMENT/COMPOUND/BUILDING"); //apartment bldg
        apartmentLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        apartmentLabel.setForeground(new Color(42, 2, 67));
        apartmentLabel.setBounds(525, startY, 280, 20);
        container.add(apartmentLabel);

        apartmentField = new RoundedTextField("  ", 20);
        apartmentField.setFont(FontUtil.getOutfitFont(15f));
        apartmentField.setBackground(Color.WHITE);
        apartmentField.setForeground(Color.BLACK);
        apartmentField.setCaretColor(Color.BLACK);
        apartmentField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        apartmentField.setEditable(false);
        apartmentField.setFocusable(false); 

        JPanel apartmentWrapper = createTextFieldWrapper(apartmentField, 15);
        apartmentWrapper.setBounds(525, startY + 20, 400, 47);
        container.add(apartmentWrapper);

        return startY + 75;
    }

    private int createRow4(JPanel container, int startY) {
        JLabel subdivisionLabel = new JLabel("SUBDIVISION"); //subd
        subdivisionLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        subdivisionLabel.setForeground(new Color(42, 2, 67));
        subdivisionLabel.setBounds(95, startY, 150, 20);
        container.add(subdivisionLabel);

        subdivisionField = new RoundedTextField("  ", 20);
        subdivisionField.setFont(FontUtil.getOutfitFont(15f));
        subdivisionField.setBackground(Color.WHITE);
        subdivisionField.setForeground(Color.BLACK);
        subdivisionField.setCaretColor(Color.BLACK);
        subdivisionField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        subdivisionField.setEditable(false); 
        subdivisionField.setFocusable(false); 

        JPanel subdivisionWrapper = createTextFieldWrapper(subdivisionField, 15);
        subdivisionWrapper.setBounds(95, startY + 20, 400, 47);
        container.add(subdivisionWrapper);

        JLabel barangayLabel = new JLabel("BARANGAY"); //brgy
        barangayLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        barangayLabel.setForeground(new Color(42, 2, 67));
        barangayLabel.setBounds(525, startY, 150, 20);
        container.add(barangayLabel);

        barangayField = new RoundedTextField("  ", 20);
        barangayField.setFont(FontUtil.getOutfitFont(15f));
        barangayField.setBackground(Color.WHITE);
        barangayField.setForeground(Color.BLACK);
        barangayField.setCaretColor(Color.BLACK);
        barangayField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        barangayField.setEditable(false); 
        barangayField.setFocusable(false); 

        JPanel barangayWrapper = createTextFieldWrapper(barangayField, 15);
        barangayWrapper.setBounds(525, startY + 20, 400, 47);
        container.add(barangayWrapper);

        return startY + 75;
    }

    private int createRow5(JPanel container, int startY) {
        JLabel streetLabel = new JLabel("STREET"); //street
        streetLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        streetLabel.setForeground(new Color(42, 2, 67));
        streetLabel.setBounds(95, startY, 100, 20);
        container.add(streetLabel);

        streetField = new RoundedTextField("  ", 20);
        streetField.setFont(FontUtil.getOutfitFont(15f));
        streetField.setBackground(Color.WHITE);
        streetField.setForeground(Color.BLACK);
        streetField.setCaretColor(Color.BLACK);
        streetField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        streetField.setEditable(false); 
        streetField.setFocusable(false); 

        JPanel streetWrapper = createTextFieldWrapper(streetField, 15);
        streetWrapper.setBounds(95, startY + 20, 185, 47);
        container.add(streetWrapper);

        JLabel cityLabel = new JLabel("MUNICIPALITY/CITY"); //city
        cityLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        cityLabel.setForeground(new Color(42, 2, 67));
        cityLabel.setBounds(310, startY, 180, 20);
        container.add(cityLabel);

        cityField = new RoundedTextField("  ", 20);
        cityField.setFont(FontUtil.getOutfitFont(15f));
        cityField.setBackground(Color.WHITE);
        cityField.setForeground(Color.BLACK);
        cityField.setCaretColor(Color.BLACK);
        cityField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        cityField.setEditable(false); 
        cityField.setFocusable(false); 

        JPanel cityWrapper = createTextFieldWrapper(cityField, 15);
        cityWrapper.setBounds(310, startY + 20, 185, 47);
        container.add(cityWrapper);

        JLabel provinceLabel = new JLabel("PROVINCE"); //province
        provinceLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        provinceLabel.setForeground(new Color(42, 2, 67));
        provinceLabel.setBounds(525, startY, 100, 20);
        container.add(provinceLabel);

        provinceField = new RoundedTextField("  ", 20);
        provinceField.setFont(FontUtil.getOutfitFont(15f));
        provinceField.setBackground(Color.WHITE);
        provinceField.setForeground(Color.BLACK);
        provinceField.setCaretColor(Color.BLACK);
        provinceField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        provinceField.setEditable(false);
        provinceField.setFocusable(false); 

        JPanel provinceWrapper = createTextFieldWrapper(provinceField, 15);
        provinceWrapper.setBounds(525, startY + 20, 185, 47);
        container.add(provinceWrapper);

        JLabel zipLabel = new JLabel("ZIP CODE"); //zip code
        zipLabel.setFont(FontUtil.getOutfitBoldFont(13f));
        zipLabel.setForeground(new Color(42, 2, 67));
        zipLabel.setBounds(740, startY, 100, 20);
        container.add(zipLabel);

        zipField = new RoundedTextField("  ", 20);
        zipField.setFont(FontUtil.getOutfitFont(15f));
        zipField.setBackground(Color.WHITE);
        zipField.setForeground(Color.BLACK);
        zipField.setCaretColor(Color.BLACK);
        zipField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        zipField.setEditable(false); 
        zipField.setFocusable(false); 

        JPanel zipWrapper = createTextFieldWrapper(zipField, 15);
        zipWrapper.setBounds(740, startY + 20, 185, 47);
        container.add(zipWrapper);

        return startY + 75;
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

    private void populateFromSession() {
        // the first three address parts
        homeOwnershipField.setText(UserApplicationData.get("HomeOwnership"));
        companyPaidField .setText(UserApplicationData.get("CompanyPaid"));
        yearsField       .setText(UserApplicationData.get("YearsOfResidency"));

        // owner info
        nameOwnerField.setText(UserApplicationData.get("NameOfOwner"));
        contactField  .setText(UserApplicationData.get("ContactNumber"));

        // split the single comma-separated address...
        String full = UserApplicationData.get("ResidenceAddress");
        if (full != null && !full.isBlank()) {
            String[] parts = full.split("\\s*,\\s*");
            if (parts.length >= 8) {
                houseField      .setText(parts[0]);
                apartmentField  .setText(parts[1]);
                subdivisionField.setText(parts[2]);
                barangayField   .setText(parts[3]);
                streetField     .setText(parts[4]);
                cityField       .setText(parts[5]);
                provinceField   .setText(parts[6]);
                zipField        .setText(parts[7]);
            } else {
                // fallback: dump entire address
                streetField.setText(full);
            }
        }
    }


    private JLabel makeSidebarLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontUtil.getOutfitFont(18f));
        label.setForeground(color);
        label.setOpaque(false);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountAddressPage().setVisible(true));
    }
}