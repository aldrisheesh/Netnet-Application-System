package com.group_9.project;

import com.group_9.project.database.ApplicationService;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import com.group_9.project.utils.CustomScrollBarUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Predicate;

public class SignUp5 extends JFrame {

    private RoundedComponents.RoundedTextField txtCardholderName, txtCardNumber, txtExpiryDate, txtCvv;
    private final List<JTextComponent> lstPaymentFields = new ArrayList<>();
    private JRadioButton rbtnFull, rbtnInstall;

    public SignUp5() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        pnlBackground.add(pnlContainer);

        JPanel pnlInnerContent = new JPanel();
        pnlInnerContent.setLayout(new BoxLayout(pnlInnerContent, BoxLayout.Y_AXIS));
        pnlInnerContent.setOpaque(false);
        pnlInnerContent.setBounds(40, 40, 890, 615);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color clrTitle = Color.decode("#2B0243");
        Color clrSub = Color.decode("#302E2E");

        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(clrTitle);
        pnlInnerContent.add(lblTitle);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlStepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlStepWrapper.setOpaque(false);
        pnlStepWrapper.add(CreateStepTracker.createStepTracker(2));
        pnlInnerContent.add(pnlStepWrapper);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new BorderLayout());
        pnlInfo.setMaximumSize(new Dimension(826, 60));
 
        JPanel pnlLeftLabels = new JPanel();
        pnlLeftLabels.setLayout(new BoxLayout(pnlLeftLabels, BoxLayout.Y_AXIS));
        pnlLeftLabels.setOpaque(false);
        pnlLeftLabels.setBorder(BorderFactory.createEmptyBorder(9, 0, 0, 0)); 

        JLabel lblSubtitle = new JLabel("SECURE YOUR PAYMENT", SwingConstants.LEFT);
        lblSubtitle.setFont(FontUtil.getOutfitFont(16f));
        lblSubtitle.setForeground(clrSub);

        JLabel lblSubNote = new JLabel("Secure your application by completing the payment using your preferred method.");
        lblSubNote.setFont(FontUtil.getInterFont(14f));
        lblSubNote.setForeground(clrSub);

        pnlLeftLabels.add(lblSubtitle);
        pnlLeftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlLeftLabels.add(lblSubNote);

        pnlInfo.add(pnlLeftLabels, BorderLayout.WEST);
        pnlInnerContent.add(pnlInfo);

        JSeparator sepHorizontal = new JSeparator(SwingConstants.HORIZONTAL);
        sepHorizontal.setMaximumSize(new Dimension(826, 2));
        sepHorizontal.setForeground(Color.decode("#B2B2B2"));
        sepHorizontal.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlInnerContent.add(sepHorizontal);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlRow = new JPanel();
        pnlRow.setLayout(new BoxLayout(pnlRow, BoxLayout.X_AXIS));
        pnlRow.setOpaque(false);
        pnlRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRow.add(createPlanSummaryPanel());
        pnlRow.add(Box.createRigidArea(new Dimension(30, 0)));
        pnlRow.add(createPaymentSectionPanel());
        pnlInnerContent.add(pnlRow);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel pnlButton = new JPanel(new BorderLayout());
        pnlButton.setOpaque(false);
        pnlButton.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton cmdBack = new RoundedComponents.RoundedButton("BACK", 25);
        cmdBack.setPreferredSize(new Dimension(148, 41));
        cmdBack.setBackground(Color.decode("#FFF1FF"));
        cmdBack.setForeground(Color.decode("#2B0243"));
        cmdBack.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdBack.setBorderColor(Color.decode("#2B0243"));

        cmdBack.addActionListener(evtAction -> {
            UserApplicationData.set("strCardholderName", txtCardholderName.getText());
            UserApplicationData.set("strCardNumber", txtCardNumber.getText());
            UserApplicationData.set("strExpiryDate", txtExpiryDate.getText());
            UserApplicationData.set("strCVV", txtCvv.getText());
            UserApplicationData.set("strPaymentOption", rbtnFull.isSelected() ? "full" : (rbtnInstall.isSelected() ? "installment" : ""));

            new SignUp3();
            dispose();    
        });

        RoundedComponents.RoundedButton cmdConfirm = new RoundedComponents.RoundedButton("CONFIRM PAYMENT", 25);
        cmdConfirm.setPreferredSize(new Dimension(194, 41));
        cmdConfirm.setBackground(Color.decode("#623CBB"));
        cmdConfirm.setForeground(Color.WHITE);
        cmdConfirm.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdConfirm.setBorderColor(Color.decode("#623CBB"));
        cmdConfirm.setFocusPainted(false);
        cmdConfirm.setFocusable(false);

        cmdConfirm.addActionListener((ActionEvent evtAction) -> {
            boolean boolAllFilled = true;
        
            for (JTextComponent txtField : lstPaymentFields) {
                if (txtField.getText().trim().isEmpty()) {
                    boolAllFilled = false;
                    if (txtField instanceof RoundedComponents.RoundedTextField txtRoundedField) {
                        txtRoundedField.setValidationBorderColor(Color.RED);
                    }
                } else {
                    if (txtField instanceof RoundedComponents.RoundedTextField txtRoundedField) {
                        txtRoundedField.setValidationBorderColor(Color.GRAY);
                    }
                }
            }

            if (!boolAllFilled) {
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Missing Information", "Please fill in all required fields before proceeding.");
                return;
            }

            if (!rbtnFull.isSelected() && !rbtnInstall.isSelected()) {
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Payment Option Required", "Please select a payment option before confirming.");
                return;
            }            

            String strRawCard = txtCardNumber.getText().replaceAll("\\s", "");

            if (!strRawCard.matches("\\d{16}")) {
                txtCardNumber.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid Card Number", "Card number must be exactly 16 digits.");
                return;
            }

            if (!txtExpiryDate.getText().matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
                txtExpiryDate.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid Expiry", "Expiry date must be valid in MM/YY format.");
                return;
            }

            String[] arrParts = txtExpiryDate.getText().split("/");
            int intMonth = Integer.parseInt(arrParts[0]);
            int intYear = 2000 + Integer.parseInt(arrParts[1]);

            Calendar calNow = Calendar.getInstance();
            int intCurrYear = calNow.get(Calendar.YEAR);
            int intCurrMonth = calNow.get(Calendar.MONTH) + 1;
            if (intYear < intCurrYear || (intYear == intCurrYear && intMonth < intCurrMonth)) {
                txtExpiryDate.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Card Expired", "The expiry date has already passed.");
                return;
            }

            if (!txtCvv.getText().matches("\\d{3,4}")) {
                txtCvv.setValidationBorderColor(Color.RED);
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Invalid CVV", "CVV must be 3 or 4 digits.");
                return;
            }

            String strPaymentOption = rbtnFull.isSelected() ? "full" : "installment";
            UserApplicationData.set("strPaymentOption", strPaymentOption);
    
            UserApplicationData.set("strCardNumber", strRawCard);
            UserApplicationData.set("strExpiryDate", txtExpiryDate.getText());
            UserApplicationData.set("strCVV", txtCvv.getText());
            UserApplicationData.set("strCardholderName", txtCardholderName.getText());

            ApplicationService svcApplication = new ApplicationService();
            boolean boolSuccess = svcApplication.processApplication();
    
            if (boolSuccess) {
                CustomDialogUtil.showStyledInfoDialog(SignUp5.this, "Success", "Application submitted successfully!");
                
                new SignUp6();
                dispose();
            } else {
                CustomDialogUtil.showStyledErrorDialog(SignUp5.this, "Error", "Failed to process application. Please try again.");
            }
        });

        cmdConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evtMouse) {
                cmdConfirm.setBackground(Color.decode("#4B278F"));
                cmdConfirm.setBorderColor(Color.decode("#4B278F"));
            }

            public void mouseExited(java.awt.event.MouseEvent evtMouse) {
                cmdConfirm.setBackground(Color.decode("#623CBB"));
                cmdConfirm.setBorderColor(Color.decode("#623CBB"));
            }
        });

        pnlButton.add(cmdBack, BorderLayout.WEST);
        pnlButton.add(cmdConfirm, BorderLayout.EAST);
        pnlInnerContent.add(pnlButton);

        pnlContainer.add(pnlInnerContent);
        setVisible(true);
        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());

        String strSavedName = UserApplicationData.get("strCardholderName");
        if (strSavedName != null) txtCardholderName.setText(strSavedName);

        String strSavedCard = UserApplicationData.get("strCardNumber");
        if (strSavedCard != null) txtCardNumber.setText(strSavedCard);

        String strSavedExpiry = UserApplicationData.get("strExpiryDate");
        if (strSavedExpiry != null) txtExpiryDate.setText(strSavedExpiry);

        String strSavedCVV = UserApplicationData.get("strCVV");
        if (strSavedCVV != null) txtCvv.setText(strSavedCVV);

        String strPaymentOption = UserApplicationData.get("strPaymentOption");
        if ("full".equals(strPaymentOption)) rbtnFull.setSelected(true);
        else if ("installment".equals(strPaymentOption)) rbtnInstall.setSelected(true);
    }


    private JPanel createPlanSummaryPanel() {
        Color clrTxt = Color.decode("#1E1E1E");
    
        JPanel pnlParent = new JPanel();
        pnlParent.setLayout(new BoxLayout(pnlParent, BoxLayout.Y_AXIS));
        pnlParent.setOpaque(false);
        pnlParent.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlParent.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0));
    
        JLabel lblSummaryTitle = new JLabel("Your Plan Summary");
        lblSummaryTitle.setFont(FontUtil.getOutfitFont(15f));
        lblSummaryTitle.setForeground(clrTxt);
        pnlParent.add(lblSummaryTitle);
        pnlParent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        RoundedPanel pnlSummaryContent = new RoundedPanel(20);
        pnlSummaryContent.setLayout(new BoxLayout(pnlSummaryContent, BoxLayout.Y_AXIS));
        pnlSummaryContent.setBackground(Color.WHITE);
        pnlSummaryContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JPanel pnlHeader = new JPanel(new GridLayout(1, 2));
        pnlHeader.setOpaque(false);
        pnlHeader.add(new JLabel("Product and Service") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(clrTxt);
        }});
        pnlHeader.add(new JLabel("Amount") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(clrTxt);
            setHorizontalAlignment(SwingConstants.RIGHT);
        }});
        pnlSummaryContent.add(pnlHeader);
        pnlSummaryContent.add(Box.createRigidArea(new Dimension(0, 10)));
    
        pnlSummaryContent.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setMaximumSize(new Dimension(331, 2));
            setForeground(Color.decode("#B2B2B2"));
        }});
        pnlSummaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        String strSavedPlans = UserApplicationData.get("strSelectedPlans");
        if (strSavedPlans != null && !strSavedPlans.isEmpty()) {
            String[] arrPlans = strSavedPlans.split(",");
            for (int intIndex = 0; intIndex < arrPlans.length; intIndex++) {
                String strPlan = arrPlans[intIndex].trim();
    
                pnlSummaryContent.add(new GridPanel(strPlan, "") {{
                    ((JLabel) getComponent(0)).setFont(FontUtil.getOutfitBoldFont(18f));
                    ((JLabel) getComponent(0)).setForeground(Color.decode("#2B0243"));
                    ((JLabel) getComponent(1)).setText("");
                }});
                pnlSummaryContent.add(new GridPanel("Monthly Service Fee", getPriceForPlan(strPlan)));
                pnlSummaryContent.add(new GridPanel("Installation Fee", getInstallationFeeForPlan(strPlan)));
    
                if (intIndex < arrPlans.length - 1) {
                    pnlSummaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
                }
            }
        }
    
        JScrollPane scrPane = new JScrollPane(pnlSummaryContent);
        scrPane.setPreferredSize(new Dimension(375, 210));
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        scrPane.setBorder(null);
        scrPane.setOpaque(false);
        scrPane.getViewport().setOpaque(false);
        scrPane.setBackground(Color.WHITE);
    
        JScrollBar scrVertical = scrPane.getVerticalScrollBar();
        scrVertical.setUI(new CustomScrollBarUI());
        scrVertical.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        scrVertical.setUnitIncrement(16);
        scrVertical.setVisible(false); 
    
        scrPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evtMouse) {
                scrVertical.setVisible(true);
                scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrPane.revalidate();
                scrPane.repaint();
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evtMouse) {
                scrVertical.setVisible(false);
                scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrPane.revalidate();
                scrPane.repaint();
            }
        });
    
        RoundedScrollContainer pnlRoundedWrapper = new RoundedScrollContainer(scrPane, 20);
        pnlRoundedWrapper.setPreferredSize(new Dimension(375, 210));
        pnlRoundedWrapper.setBackground(Color.WHITE);
        
        pnlParent.add(pnlRoundedWrapper);        
        pnlParent.add(Box.createRigidArea(new Dimension(0, 20)));
    
        pnlParent.add(new JLabel("*Full Payment means paying the installation fee upfront.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(clrTxt);
        }});
        pnlParent.add(new JLabel("MSF is billed separately every month.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(clrTxt);
        }});
    
        return pnlParent;
    }
    

    private static class GridPanel extends JPanel {
        public GridPanel(String strLeft, String strRight) {
            super(new GridLayout(1, 2));
            setOpaque(false);
            JLabel lblLeft = new JLabel(strLeft);
            JLabel lblRight = new JLabel(strRight);
            lblLeft.setFont(FontUtil.getInterFont(16f));
            lblRight.setFont(FontUtil.getInterFont(16f));
            lblLeft.setForeground(Color.decode("#1E1E1E"));
            lblRight.setForeground(Color.decode("#1E1E1E"));
            lblRight.setHorizontalAlignment(SwingConstants.RIGHT);
            add(lblLeft);
            add(lblRight);
        }
    }


    private static class RoundedScrollContainer extends JPanel {
        private final int intRadius;

        public RoundedScrollContainer(Component ctlContent, int intRadius) {
            super(new BorderLayout());
            this.intRadius = intRadius;
            setOpaque(false);
            add(ctlContent, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape shpClip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), intRadius, intRadius);
            g2d.setClip(shpClip);

            g2d.setColor(getBackground());
            g2d.fill(shpClip);

            super.paintComponent(g2d);
            g2d.dispose();
        }
    }


    private String getPriceForPlan(String strPlanName) {
        return switch (strPlanName.toUpperCase()) {
            case "FIBERX 1500" -> "₱1500";
            case "FIBERX 2500" -> "₱2500";
            case "FIBERX 3500" -> "₱3500";
            case "FIBER XTREAM 4500" -> "₱4500";
            case "FIBER XTREAM 7000" -> "₱7000";
            default -> "₱0";
        };
    }

    private String getInstallationFeeForPlan(String strPlanName) {
        return switch (strPlanName.toUpperCase()) {
            case "FIBER XTREAM 4500", "FIBER XTREAM 7000" -> "WAIVED";
            case "FIBERX 1500", "FIBERX 2500" -> "₱125/24mo.";
            case "FIBERX 3500" -> "₱125/12mo.";
            default -> "₱0";
        };
    }

    private RoundedComponents.RoundedTextField createValidatedField(String strPlaceholder, Predicate<String> prdValidator) {
        var txtField = new RoundedComponents.RoundedTextField(strPlaceholder, 15);
        txtField.setFont(FontUtil.getOutfitFont(15f));
        txtField.setPreferredSize(new Dimension(175, 38));
        txtField.setMaximumSize(new Dimension(175, 38));
        lstPaymentFields.add(txtField);
        ValidationUtil.addTextValidation(txtField, prdValidator);
        return txtField;
    }

    private JPanel createPaymentSectionPanel() {
        Color clrTxt = Color.decode("#1E1E1E");

        JPanel pnlPayment = new JPanel();
        pnlPayment.setLayout(new BoxLayout(pnlPayment, BoxLayout.Y_AXIS));
        pnlPayment.setOpaque(false);
        pnlPayment.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 0));
        pnlPayment.setMaximumSize(new Dimension(400, 450));

        JLabel lblPaymentTitle = new JLabel("Payment Section");
        lblPaymentTitle.setFont(FontUtil.getOutfitFont(15f));
        lblPaymentTitle.setForeground(clrTxt);
        lblPaymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPayment.add(lblPaymentTitle);
        pnlPayment.add(Box.createRigidArea(new Dimension(0, 20)));

        ButtonGroup grpPayment = new ButtonGroup();
        ImageIcon icoOff = new ImageIcon(getClass().getResource("/icons/radio_off.png"));
        ImageIcon icoOn = new ImageIcon(getClass().getResource("/icons/radio_on.png"));
        int intIconSize = 18;
        icoOff = new ImageIcon(icoOff.getImage().getScaledInstance(intIconSize, intIconSize, Image.SCALE_SMOOTH));
        icoOn = new ImageIcon(icoOn.getImage().getScaledInstance(intIconSize, intIconSize, Image.SCALE_SMOOTH));

        rbtnFull = new JRadioButton("Full Payment");
        rbtnFull.setFont(FontUtil.getInterFont(15f));
        rbtnFull.setForeground(clrTxt);
        rbtnFull.setOpaque(false);
        rbtnFull.setFocusPainted(false);
        rbtnFull.setContentAreaFilled(false);
        rbtnFull.setBorderPainted(false);
        rbtnFull.setIcon(icoOff);
        rbtnFull.setSelectedIcon(icoOn);

        rbtnInstall = new JRadioButton("Installment");
        rbtnInstall.setFont(FontUtil.getInterFont(15f));
        rbtnInstall.setForeground(clrTxt);
        rbtnInstall.setOpaque(false);
        rbtnInstall.setFocusPainted(false);
        rbtnInstall.setContentAreaFilled(false);
        rbtnInstall.setBorderPainted(false);
        rbtnInstall.setIcon(icoOff);
        rbtnInstall.setSelectedIcon(icoOn);

        grpPayment.add(rbtnFull);
        grpPayment.add(rbtnInstall);

        JPanel pnlRadio = new JPanel();
        pnlRadio.setLayout(new BoxLayout(pnlRadio, BoxLayout.X_AXIS));
        pnlRadio.setOpaque(false);
        pnlRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlRadio.add(rbtnFull);
        pnlRadio.add(Box.createRigidArea(new Dimension(10, 0)));
        pnlRadio.add(rbtnInstall);

        pnlPayment.add(pnlRadio);
        pnlPayment.add(Box.createRigidArea(new Dimension(0, 25)));

        txtCardholderName = createValidatedField("Cardholder Name", strInput -> !strInput.trim().isEmpty());
        ToolTipUtil.attachCustomTooltip(txtCardholderName, "Enter name as shown on card");
        txtCardholderName.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPayment.add(txtCardholderName);
        pnlPayment.add(Box.createRigidArea(new Dimension(0, 10)));

        txtCardNumber = createValidatedField("Card Number", strInput -> strInput.matches("(\\d{4} ){3}\\d{4}"));
        SmartFieldFormatter.attachCardNumberFormatter(txtCardNumber);
        ((AbstractDocument) txtCardNumber.getDocument()).setDocumentFilter(new LengthLimitFilter(19));
        ToolTipUtil.attachCustomTooltip(txtCardNumber, "Enter 16-digit card number");        
        txtCardNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPayment.add(txtCardNumber);
        pnlPayment.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pnlExpCvv = new JPanel();
        pnlExpCvv.setLayout(new BoxLayout(pnlExpCvv, BoxLayout.X_AXIS));
        pnlExpCvv.setOpaque(false);
        pnlExpCvv.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtExpiryDate = createValidatedField("MM/YY", strInput -> {
            if (!strInput.matches("^(0[1-9]|1[0-2])/\\d{2}$")) return false;
            try {
                String[] arrParts = strInput.split("/");
                int intMonth = Integer.parseInt(arrParts[0]);
                int intYear = 2000 + Integer.parseInt(arrParts[1]);
        
                Calendar calNow = Calendar.getInstance();
                int intCurrYear = calNow.get(Calendar.YEAR);
                int intCurrMonth = calNow.get(Calendar.MONTH) + 1;
                        
                    return !(intYear < intCurrYear || (intYear == intCurrYear && intMonth < intCurrMonth));
            } catch (Exception e) {
                return false;
            }
        });        
        SmartFieldFormatter.attachExpiryDateFormatter(txtExpiryDate);
        ((AbstractDocument) txtExpiryDate.getDocument()).setDocumentFilter(new LengthLimitFilter(5));
        ToolTipUtil.attachCustomTooltip(txtExpiryDate, "Enter expiry date (MM/YY)");        
        txtExpiryDate.setMaximumSize(new Dimension(100, 38));
        txtExpiryDate.setPreferredSize(new Dimension(100, 38));
        pnlExpCvv.add(txtExpiryDate);
        pnlExpCvv.add(Box.createRigidArea(new Dimension(20, 0)));

        txtCvv = createValidatedField("CVV", s -> s.matches("\\d{3,4}"));
        ((AbstractDocument) txtCvv.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        ToolTipUtil.attachCustomTooltip(txtCvv, "Enter CVV (3-digit or 4-digit)");
        txtCvv.setMaximumSize(new Dimension(80, 38));
        txtCvv.setPreferredSize(new Dimension(80, 38));
        pnlExpCvv.add(txtCvv);

        pnlPayment.add(pnlExpCvv);
        pnlPayment.add(Box.createRigidArea(new Dimension(0, 40)));

        return pnlPayment;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp5::new);
    }
}
