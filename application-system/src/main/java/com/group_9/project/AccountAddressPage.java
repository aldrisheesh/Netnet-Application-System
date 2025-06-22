package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;
import com.group_9.project.utils.RoundedComponents.*;
import java.awt.*;
import javax.swing.*;

public class AccountAddressPage extends Template {
    // Field declarations with Reddick-style prefixes
    private JTextField txtHomeOwnership;
    private JTextField txtCompanyPaid;
    private JTextField txtYearsResidency;
    private JTextField txtOwnerName;
    private JTextField txtContactNumber;
    private JTextField txtHouseNumber;
    private JTextField txtApartment;
    private JTextField txtSubdivision;
    private JTextField txtBarangay;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtProvince;
    private JTextField txtZipCode;

    public AccountAddressPage() {
        BaseFrameSetup.applyAppIcon(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 3);
        
        JPanel pnlSidebar = AccountSidebarUtil.createSidebar(this, "My Address");
        pnlBackground.add(pnlSidebar);

        JPanel pnlContent = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContent.setBounds(290, 150, 1020, 720);
        pnlBackground.add(pnlContent);

        JPanel pnlDetailsContainer = createDetailsContainer();
        pnlContent.add(pnlDetailsContainer);

        populateFromSession();  
        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());
    }

    private JPanel createDetailsContainer() {
        JPanel pnlContainer = new JPanel(null);
        pnlContainer.setBackground(new Color(0, 0, 0, 0));
        pnlContainer.setBounds(0, 0, 1250, 700);
        pnlContainer.setOpaque(false);

        // Header section
        JLabel lblTitle = new JLabel("MY ADDRESS");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setForeground(new Color(42, 2, 67, 255));
        lblTitle.setBounds(70, 50, 300, 30);
        pnlContainer.add(lblTitle);

        JLabel lblSection = new JLabel("SERVICE ADDRESS");
        lblSection.setFont(FontUtil.getOutfitFont(16f));
        lblSection.setBounds(70, 100, 300, 20);
        pnlContainer.add(lblSection);

        JSeparator sepDivider = new JSeparator();
        sepDivider.setBounds(70, 130, 880, 1);
        sepDivider.setForeground(new Color(180, 180, 180));
        pnlContainer.add(sepDivider);

        JLabel lblReminder = new JLabel("<html>This is your registered service address. For minor corrections (e.g., spelling or formatting), please contact our support team.");
        lblReminder.setFont(FontUtil.getInterFont(13f));
        lblReminder.setBounds(150, 610, 800, 40);
        pnlContainer.add(lblReminder);

        int intCurrentY = 160;
        intCurrentY = createOwnershipRow(pnlContainer, intCurrentY);
        intCurrentY = createOwnerInfoRow(pnlContainer, intCurrentY);
        intCurrentY = createResidenceRow(pnlContainer, intCurrentY);
        intCurrentY = createLocationRow(pnlContainer, intCurrentY);
        intCurrentY = createAddressDetailsRow(pnlContainer, intCurrentY);

        return pnlContainer;
    }

    private int createOwnershipRow(JPanel pnlContainer, int intStartY) {
        // Home ownership field
        JLabel lblHomeOwnership = createLabel("HOME OWNERSHIP", 95, intStartY, 150);
        pnlContainer.add(lblHomeOwnership);
        
        txtHomeOwnership = createDisabledTextField();
        JPanel pnlHomeWrapper = createTextFieldWrapper(txtHomeOwnership, 15);
        pnlHomeWrapper.setBounds(95, intStartY + 20, 270, 47);
        pnlContainer.add(pnlHomeWrapper);

        // Company paid field
        JLabel lblCompanyPaid = createLabel("COMPANY PAID", 395, intStartY, 120);
        pnlContainer.add(lblCompanyPaid);
        
        txtCompanyPaid = createDisabledTextField();
        JPanel pnlCompanyWrapper = createTextFieldWrapper(txtCompanyPaid, 15);
        pnlCompanyWrapper.setBounds(395, intStartY + 20, 230, 47);
        pnlContainer.add(pnlCompanyWrapper);

        // Years of residency field
        JLabel lblYearsResidency = createLabel("YEARS OF RESIDENCY", 655, intStartY, 180);
        pnlContainer.add(lblYearsResidency);
        
        txtYearsResidency = createDisabledTextField();
        JPanel pnlYearsWrapper = createTextFieldWrapper(txtYearsResidency, 15);
        pnlYearsWrapper.setBounds(655, intStartY + 20, 270, 47);
        pnlContainer.add(pnlYearsWrapper);

        return intStartY + 75;
    }

    private int createOwnerInfoRow(JPanel pnlContainer, int intStartY) {
        // Owner name field
        JLabel lblOwnerName = createLabel("NAME OF OWNER", 95, intStartY, 200);
        pnlContainer.add(lblOwnerName);
        
        txtOwnerName = createDisabledTextField();
        JPanel pnlNameWrapper = createTextFieldWrapper(txtOwnerName, 15);
        pnlNameWrapper.setBounds(95, intStartY + 20, 400, 47);
        pnlContainer.add(pnlNameWrapper);

        // Contact number field
        JLabel lblContactNumber = createLabel("CONTACT NUMBER", 525, intStartY, 200);
        pnlContainer.add(lblContactNumber);
        
        txtContactNumber = createDisabledTextField();
        JPanel pnlContactWrapper = createTextFieldWrapper(txtContactNumber, 15);
        pnlContactWrapper.setBounds(525, intStartY + 20, 400, 47);
        pnlContainer.add(pnlContactWrapper);

        return intStartY + 75;
    }

    private int createResidenceRow(JPanel pnlContainer, int intStartY) {
        // House number field
        JLabel lblHouseNumber = createLabel("HOUSE/ROOM NO./FLOOR", 95, intStartY, 220);
        pnlContainer.add(lblHouseNumber);
        
        txtHouseNumber = createDisabledTextField();
        JPanel pnlHouseWrapper = createTextFieldWrapper(txtHouseNumber, 15);
        pnlHouseWrapper.setBounds(95, intStartY + 20, 400, 47);
        pnlContainer.add(pnlHouseWrapper);

        // Apartment field
        JLabel lblApartment = createLabel("APARTMENT/COMPOUND/BUILDING", 525, intStartY, 280);
        pnlContainer.add(lblApartment);
        
        txtApartment = createDisabledTextField();
        JPanel pnlApartmentWrapper = createTextFieldWrapper(txtApartment, 15);
        pnlApartmentWrapper.setBounds(525, intStartY + 20, 400, 47);
        pnlContainer.add(pnlApartmentWrapper);

        return intStartY + 75;
    }

    private int createLocationRow(JPanel pnlContainer, int intStartY) {
        // Subdivision field
        JLabel lblSubdivision = createLabel("SUBDIVISION", 95, intStartY, 150);
        pnlContainer.add(lblSubdivision);
        
        txtSubdivision = createDisabledTextField();
        JPanel pnlSubdivisionWrapper = createTextFieldWrapper(txtSubdivision, 15);
        pnlSubdivisionWrapper.setBounds(95, intStartY + 20, 400, 47);
        pnlContainer.add(pnlSubdivisionWrapper);

        // Barangay field
        JLabel lblBarangay = createLabel("BARANGAY", 525, intStartY, 150);
        pnlContainer.add(lblBarangay);
        
        txtBarangay = createDisabledTextField();
        JPanel pnlBarangayWrapper = createTextFieldWrapper(txtBarangay, 15);
        pnlBarangayWrapper.setBounds(525, intStartY + 20, 400, 47);
        pnlContainer.add(pnlBarangayWrapper);

        return intStartY + 75;
    }

    private int createAddressDetailsRow(JPanel pnlContainer, int intStartY) {
        // Street field
        JLabel lblStreet = createLabel("STREET", 95, intStartY, 100);
        pnlContainer.add(lblStreet);
        
        txtStreet = createDisabledTextField();
        JPanel pnlStreetWrapper = createTextFieldWrapper(txtStreet, 15);
        pnlStreetWrapper.setBounds(95, intStartY + 20, 185, 47);
        pnlContainer.add(pnlStreetWrapper);

        // City field
        JLabel lblCity = createLabel("MUNICIPALITY/CITY", 310, intStartY, 180);
        pnlContainer.add(lblCity);
        
        txtCity = createDisabledTextField();
        JPanel pnlCityWrapper = createTextFieldWrapper(txtCity, 15);
        pnlCityWrapper.setBounds(310, intStartY + 20, 185, 47);
        pnlContainer.add(pnlCityWrapper);

        // Province field
        JLabel lblProvince = createLabel("PROVINCE", 525, intStartY, 100);
        pnlContainer.add(lblProvince);
        
        txtProvince = createDisabledTextField();
        JPanel pnlProvinceWrapper = createTextFieldWrapper(txtProvince, 15);
        pnlProvinceWrapper.setBounds(525, intStartY + 20, 185, 47);
        pnlContainer.add(pnlProvinceWrapper);

        // Zip code field
        JLabel lblZipCode = createLabel("ZIP CODE", 740, intStartY, 100);
        pnlContainer.add(lblZipCode);
        
        txtZipCode = createDisabledTextField();
        JPanel pnlZipWrapper = createTextFieldWrapper(txtZipCode, 15);
        pnlZipWrapper.setBounds(740, intStartY + 20, 185, 47);
        pnlContainer.add(pnlZipWrapper);

        return intStartY + 75;
    }

    private JLabel createLabel(String strText, int x, int y, int width) {
        JLabel lbl = new JLabel(strText);
        lbl.setFont(FontUtil.getOutfitBoldFont(13f));
        lbl.setForeground(new Color(42, 2, 67));
        lbl.setBounds(x, y, width, 20);
        return lbl;
    }

    private JTextField createDisabledTextField() {
        JTextField txtField = new RoundedTextField("  ", 20);
        txtField.setFont(FontUtil.getOutfitFont(15f));
        txtField.setBackground(Color.WHITE);
        txtField.setForeground(Color.BLACK);
        txtField.setCaretColor(Color.BLACK);
        txtField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtField.setEditable(false);
        txtField.setFocusable(false);
        return txtField;
    }

    private JPanel createTextFieldWrapper(JTextField txtField, int arc) {
        JPanel pnlWrapper = new JPanel() {
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
        pnlWrapper.setLayout(new BorderLayout());
        pnlWrapper.setOpaque(false);
        pnlWrapper.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlWrapper.add(txtField, BorderLayout.CENTER);
        return pnlWrapper;
    }

    private void populateFromSession() {
        // Basic address info
        txtHomeOwnership.setText(UserApplicationData.get("strHomeOwnership"));
        txtCompanyPaid.setText(UserApplicationData.get("strCompanyPaid"));
        txtYearsResidency.setText(UserApplicationData.get("intYearsOfResidency"));

        // Owner info
        txtOwnerName.setText(UserApplicationData.get("strNameOfOwner"));
        txtContactNumber.setText(UserApplicationData.get("strContactNumber"));

        // Parse address components
        String strFullAddress = UserApplicationData.get("strResidenceAddress");
        if (strFullAddress != null && !strFullAddress.isBlank()) {
            String[] arrParts = strFullAddress.split("\\s*,\\s*");
            if (arrParts.length >= 8) {
                txtHouseNumber.setText(arrParts[0]);
                txtApartment.setText(arrParts[1]);
                txtSubdivision.setText(arrParts[2]);
                txtBarangay.setText(arrParts[3]);
                txtStreet.setText(arrParts[4]);
                txtCity.setText(arrParts[5]);
                txtProvince.setText(arrParts[6]);
                txtZipCode.setText(arrParts[7]);
            } else {
                txtStreet.setText(strFullAddress);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountAddressPage().setVisible(true));
    }
}