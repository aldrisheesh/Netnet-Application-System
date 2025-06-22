package com.group_9.project;

import com.group_9.project.database.DatabaseConnection;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import com.group_9.project.utils.CustomScrollBarUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class AddConfirm extends JFrame {

    private RoundedComponents.RoundedTextField txtCardholderName, txtCardNumber, txtExpiryDate, txtCvv;
    private final List<JTextComponent> lstPaymentFields = new ArrayList<>();
    private JRadioButton rbtnFull, rbtnInstall;

    public AddConfirm() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        // — White rounded container
        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        pnlBackground.add(pnlContainer);

        // — Inner content
        JPanel pnlInnerContent = new JPanel();
        pnlInnerContent.setLayout(new BoxLayout(pnlInnerContent, BoxLayout.Y_AXIS));
        pnlInnerContent.setOpaque(false);
        pnlInnerContent.setBounds(40, 40, 890, 615);
        pnlContainer.add(pnlInnerContent);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setForeground(Color.decode("#2B0243"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(lblTitle);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel pnlStepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlStepWrapper.setOpaque(false);
        pnlStepWrapper.add(CreateStepTracker.createStepTracker(2));
        pnlInnerContent.add(pnlStepWrapper);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Subtitle + Note
        Color subColor = Color.decode("#302E2E");
        JLabel lblSubtitle = new JLabel("SECURE YOUR PAYMENT", SwingConstants.LEFT);
        lblSubtitle.setFont(FontUtil.getOutfitFont(16f));
        lblSubtitle.setForeground(subColor);
        JLabel lblSubNote = new JLabel("Complete your application by confirming payment for the selected plans.");
        lblSubNote.setFont(FontUtil.getInterFont(14f));
        lblSubNote.setForeground(subColor);

        JPanel pnlInfoPanel = new JPanel(new BorderLayout());
        pnlInfoPanel.setOpaque(false);
        pnlInfoPanel.setMaximumSize(new Dimension(826, 60));
        JPanel pnlLeftLabels = new JPanel();
        pnlLeftLabels.setOpaque(false);
        pnlLeftLabels.setLayout(new BoxLayout(pnlLeftLabels, BoxLayout.Y_AXIS));
        pnlLeftLabels.add(lblSubtitle);
        pnlLeftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlLeftLabels.add(lblSubNote);
        pnlInfoPanel.add(pnlLeftLabels, BorderLayout.WEST);
        pnlInnerContent.add(pnlInfoPanel);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        JSeparator sepDivider = new JSeparator(SwingConstants.HORIZONTAL);
        sepDivider.setMaximumSize(new Dimension(826, 2));
        sepDivider.setForeground(Color.decode("#B2B2B2"));
        sepDivider.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(sepDivider);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Plan summary + payment section
        JPanel pnlRow = new JPanel();
        pnlRow.setOpaque(false);
        pnlRow.setLayout(new BoxLayout(pnlRow, BoxLayout.X_AXIS));
        pnlRow.add(createPlanSummaryPanel());
        pnlRow.add(Box.createRigidArea(new Dimension(30, 0)));
        pnlRow.add(createPaymentSectionPanel());
        pnlInnerContent.add(pnlRow);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        JPanel pnlBtnPanel = new JPanel(new BorderLayout());
        pnlBtnPanel.setOpaque(false);
        pnlBtnPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton cmdBack = new RoundedComponents.RoundedButton("BACK", 25);
        styleBackButton(cmdBack);
        cmdBack.addActionListener(e -> {
            saveFormState();
            new AddPlansPage().setVisible(true);
            dispose();
        });

        RoundedComponents.RoundedButton cmdConfirm = new RoundedComponents.RoundedButton("CONFIRM PAYMENT", 25);
        styleConfirmButton(cmdConfirm);
        cmdConfirm.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {
                cmdConfirm.setBackground(Color.decode("#4B278F"));
                cmdConfirm.setBorderColor(Color.decode("#4B278F"));
            }
            public void mouseExited(MouseEvent e) {
                cmdConfirm.setBackground(Color.decode("#623CBB"));
                cmdConfirm.setBorderColor(Color.decode("#623CBB"));
            }
        });
        cmdConfirm.addActionListener(e -> onConfirm());

        pnlBtnPanel.add(cmdBack, BorderLayout.WEST);
        pnlBtnPanel.add(cmdConfirm, BorderLayout.EAST);
        pnlInnerContent.add(pnlBtnPanel);

        setVisible(true);
        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());

        // restore any saved inputs
        loadFormState();
    }

    private void onConfirm() {
        // 1) Validate inputs
        if (!validateAllFields()) return;

        // 2) Gather data
        String strAppNo = UserApplicationData.get("strApplicationNo");
        if (strAppNo == null) {
            CustomDialogUtil.showStyledErrorDialog(this,
                "Missing Application",
                "No application number found. Please start over."
            );
            return;
        }

        String strPaymentOpt = rbtnFull.isSelected() ? "full" : "installment";
        String[] arrPlanIds = Optional.ofNullable(UserApplicationData.get("strSelectedPlanIDs"))
                                    .map(s -> s.split(","))
                                   .orElse(new String[0]);
       if (arrPlanIds.length == 0) {
            CustomDialogUtil.showStyledErrorDialog(this,
                "No Plans",
                "No plan IDs found. Please go back and select plans."
            );
            return;
        }

        // 3) Insert into tbl_payment off the EDT
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                String strSql = """
                    INSERT INTO tbl_payment(application_no, plan_ID, payment_option)
                    VALUES(?, ?, ?)
                """;
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(strSql)) {
                    for (String strPid : arrPlanIds) {
                        ps.setString(1, strAppNo.trim());
                        ps.setString(2, strPid.trim());
                        ps.setString(3, strPaymentOpt);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    return true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void done() {
                boolean success = false;
                try { success = get(); }
                catch (Exception ignored) {}

                if (success) {
                    CustomDialogUtil.showStyledInfoDialog(
                        AddConfirm.this,
                        "Payment Recorded",
                        "Your payment has been recorded successfully!"
                    );
                    new SignUp6().setVisible(true);
                    dispose();
                } else {
                    CustomDialogUtil.showStyledErrorDialog(
                        AddConfirm.this,
                        "Database Error",
                        "Could not save payment. Please try again."
                    );
                }
            }
        }.execute();
    }

    // — Utility methods to DRY up the code

    private void styleBackButton(RoundedComponents.RoundedButton b) {
        b.setPreferredSize(new Dimension(148, 41));
        b.setBackground(Color.decode("#FFF1FF"));
        b.setForeground(Color.decode("#2B0243"));
        b.setFont(FontUtil.getOutfitBoldFont(16f));
        b.setBorderColor(Color.decode("#2B0243"));
    }

    private void styleConfirmButton(RoundedComponents.RoundedButton b) {
        b.setPreferredSize(new Dimension(194, 41));
        b.setBackground(Color.decode("#623CBB"));
        b.setForeground(Color.WHITE);
        b.setFont(FontUtil.getOutfitBoldFont(16f));
        b.setBorderColor(Color.decode("#623CBB"));
        b.setFocusPainted(false);
        b.setFocusable(false);
    }

    private boolean validateAllFields() {
        boolean ok = true;
        for (JTextComponent fld : lstPaymentFields) {
            if (fld.getText().trim().isEmpty()) {
                ok = false;
                if (fld instanceof RoundedComponents.RoundedTextField tf) tf.setValidationBorderColor(Color.RED);
            } else if (fld instanceof RoundedComponents.RoundedTextField tf) {
                tf.setValidationBorderColor(Color.GRAY);
            }
        }
        if (!ok) {
            CustomDialogUtil.showStyledErrorDialog(this,
                "Missing Information",
                "Please fill in all required fields."
            );
            return false;
        }
        if (!rbtnFull.isSelected() && !rbtnInstall.isSelected()) {
            CustomDialogUtil.showStyledErrorDialog(this,
                "Payment Option Required",
                "Please select a payment option."
            );
            return false;
        }
        // Individual field format checks (card number, expiry, CVV) can be added here
        return true;
    }

    private void saveFormState() {
        UserApplicationData.set("strCardholderName", txtCardholderName.getText());
        UserApplicationData.set("strCardNumber",     txtCardNumber.getText());
        UserApplicationData.set("strExpiryDate",     txtExpiryDate.getText());
        UserApplicationData.set("strCVV",            txtCvv.getText());
        UserApplicationData.set("strPaymentOption", rbtnFull.isSelected()? "full":"installment");
    }

    private void loadFormState() {
        Optional.ofNullable(UserApplicationData.get("strCardholderName"))
                .ifPresent(txtCardholderName::setText);
        Optional.ofNullable(UserApplicationData.get("strCardNumber"))
                .ifPresent(txtCardNumber::setText);
        Optional.ofNullable(UserApplicationData.get("strExpiryDate"))
                .ifPresent(txtExpiryDate::setText);
        Optional.ofNullable(UserApplicationData.get("strCVV"))
                .ifPresent(txtCvv::setText);
        String strOpt = UserApplicationData.get("strPaymentOption");
        if ("full".equals(strOpt)) rbtnFull.setSelected(true);
        else if ("installment".equals(strOpt)) rbtnInstall.setSelected(true);
    }

    // --- All helper/UI methods below ---


    private JPanel createPlanSummaryPanel() {
        Color txtColor = Color.decode("#1E1E1E");

        JPanel parent = new JPanel();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setOpaque(false);
        parent.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0));

        JLabel lblSummaryTitle = new JLabel("Your Plan Summary");
        lblSummaryTitle.setFont(FontUtil.getOutfitFont(15f));
        lblSummaryTitle.setForeground(txtColor);
        parent.add(lblSummaryTitle);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedPanel pnlSummaryContent = new RoundedPanel(20);
        pnlSummaryContent.setLayout(new BoxLayout(pnlSummaryContent, BoxLayout.Y_AXIS));
        pnlSummaryContent.setBackground(Color.WHITE);
        pnlSummaryContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlHeader = new JPanel(new GridLayout(1, 2));
        pnlHeader.setOpaque(false);
        pnlHeader.add(new JLabel("Product and Service") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
        }});
        pnlHeader.add(new JLabel("Amount") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
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
            for (int i = 0; i < arrPlans.length; i++) {
                String strPlan = arrPlans[i].trim();
                pnlSummaryContent.add(new GridPanel(strPlan, ""));
                pnlSummaryContent.add(new GridPanel("Monthly Service Fee", getPriceForPlan(strPlan)));
                pnlSummaryContent.add(new GridPanel("Installation Fee", getInstallationFeeForPlan(strPlan)));
                if (i < arrPlans.length - 1) pnlSummaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }

        JScrollPane scrSummary = new JScrollPane(pnlSummaryContent);
        scrSummary.setPreferredSize(new Dimension(375, 210));
        scrSummary.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrSummary.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrSummary.setBorder(null);
        scrSummary.setOpaque(false);
        scrSummary.getViewport().setOpaque(false);

        JScrollBar sbVertical = scrSummary.getVerticalScrollBar();
        sbVertical.setUI(new CustomScrollBarUI());
        sbVertical.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        sbVertical.setUnitIncrement(16);
        sbVertical.setVisible(false);

        scrSummary.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                sbVertical.setVisible(true);
                scrSummary.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrSummary.revalidate();
                scrSummary.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sbVertical.setVisible(false);
                scrSummary.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrSummary.revalidate();
                scrSummary.repaint();
            }
        });

        RoundedScrollContainer wrapper = new RoundedScrollContainer(scrSummary, 20);
        wrapper.setPreferredSize(new Dimension(375, 210));
        wrapper.setBackground(Color.WHITE);

        parent.add(wrapper);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
        parent.add(new JLabel("*Full Payment means paying the installation fee upfront.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(txtColor);
        }});
        parent.add(new JLabel("MSF is billed separately every month.") {{
            setFont(FontUtil.getOutfitFont(13f).deriveFont(Font.ITALIC));
            setForeground(txtColor);
        }});

        return parent;
    }

    private JPanel createPaymentSectionPanel() {
        Color txtColor = Color.decode("#1E1E1E");

        JPanel pnlPaymentPanel = new JPanel();
        pnlPaymentPanel.setLayout(new BoxLayout(pnlPaymentPanel, BoxLayout.Y_AXIS));
        pnlPaymentPanel.setOpaque(false);
        pnlPaymentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 0));
        pnlPaymentPanel.setMaximumSize(new Dimension(400, 450));

        JLabel lblPaymentTitle = new JLabel("Payment Section");
        lblPaymentTitle.setFont(FontUtil.getOutfitFont(15f));
        lblPaymentTitle.setForeground(txtColor);
        lblPaymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPaymentPanel.add(lblPaymentTitle);
        pnlPaymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        ButtonGroup paymentGroup = new ButtonGroup();
        ImageIcon iconOff = new ImageIcon(getClass().getResource("/icons/radio_off.png"));
        ImageIcon iconOn  = new ImageIcon(getClass().getResource("/icons/radio_on.png"));
        int iconSize = 18;
        iconOff = new ImageIcon(iconOff.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        iconOn  = new ImageIcon(iconOn.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        rbtnFull = new JRadioButton("Full Payment");
        rbtnFull.setFont(FontUtil.getInterFont(15f));
        rbtnFull.setForeground(txtColor);
        rbtnFull.setOpaque(false);
        rbtnFull.setFocusPainted(false);
        rbtnFull.setContentAreaFilled(false);
        rbtnFull.setBorderPainted(false);
        rbtnFull.setIcon(iconOff);
        rbtnFull.setSelectedIcon(iconOn);

        rbtnInstall = new JRadioButton("Installment");
        rbtnInstall.setFont(FontUtil.getInterFont(15f));
        rbtnInstall.setForeground(txtColor);
        rbtnInstall.setOpaque(false);
        rbtnInstall.setFocusPainted(false);
        rbtnInstall.setContentAreaFilled(false);
        rbtnInstall.setBorderPainted(false);
        rbtnInstall.setIcon(iconOff);
        rbtnInstall.setSelectedIcon(iconOn);

        paymentGroup.add(rbtnFull);
        paymentGroup.add(rbtnInstall);

        JPanel pnlRadioPanel = new JPanel();
        pnlRadioPanel.setLayout(new BoxLayout(pnlRadioPanel, BoxLayout.X_AXIS));
        pnlRadioPanel.setOpaque(false);
        pnlRadioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlRadioPanel.add(rbtnFull);
        pnlRadioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pnlRadioPanel.add(rbtnInstall);

        pnlPaymentPanel.add(pnlRadioPanel);
        pnlPaymentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Cardholder Name
        txtCardholderName = createValidatedField("Cardholder Name", s -> !s.trim().isEmpty());
        ToolTipUtil.attachCustomTooltip(txtCardholderName, "Enter name as shown on card");
        txtCardholderName.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPaymentPanel.add(txtCardholderName);
        pnlPaymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Card Number
        txtCardNumber = createValidatedField("Card Number", s -> s.matches("(\\d{4} ){3}\\d{4}"));
        SmartFieldFormatter.attachCardNumberFormatter(txtCardNumber);
        ((AbstractDocument) txtCardNumber.getDocument()).setDocumentFilter(new LengthLimitFilter(19));
        ToolTipUtil.attachCustomTooltip(txtCardNumber, "Enter 16-digit card number");
        txtCardNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPaymentPanel.add(txtCardNumber);
        pnlPaymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Expiry & CVV Panel
        JPanel pnlExpCvvPanel = new JPanel();
        pnlExpCvvPanel.setLayout(new BoxLayout(pnlExpCvvPanel, BoxLayout.X_AXIS));
        pnlExpCvvPanel.setOpaque(false);
        pnlExpCvvPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtExpiryDate = createValidatedField("MM/YY", s -> {
            if (!s.matches("^(0[1-9]|1[0-2])/\\d{2}$")) return false;
            try {
                String[] parts = s.split("/");
                int month = Integer.parseInt(parts[0]);
                int year  = 2000 + Integer.parseInt(parts[1]);
                Calendar now = Calendar.getInstance();
                int currYear  = now.get(Calendar.YEAR);
                int currMonth = now.get(Calendar.MONTH) + 1;
                return !(year < currYear || (year == currYear && month < currMonth));
            } catch (Exception e) {
                return false;
            }
        });
        SmartFieldFormatter.attachExpiryDateFormatter(txtExpiryDate);
        ((AbstractDocument) txtExpiryDate.getDocument()).setDocumentFilter(new LengthLimitFilter(5));
        ToolTipUtil.attachCustomTooltip(txtExpiryDate, "Enter expiry date (MM/YY)");
        txtExpiryDate.setMaximumSize(new Dimension(100, 38));
        txtExpiryDate.setPreferredSize(new Dimension(100, 38));
        pnlExpCvvPanel.add(txtExpiryDate);
        pnlExpCvvPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        txtCvv = createValidatedField("CVV", s -> s.matches("\\d{3,4}"));
        ((AbstractDocument) txtCvv.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        ToolTipUtil.attachCustomTooltip(txtCvv, "Enter CVV (3 or 4 digits)");
        txtCvv.setMaximumSize(new Dimension(80, 38));
        txtCvv.setPreferredSize(new Dimension(80, 38));
        pnlExpCvvPanel.add(txtCvv);

        pnlPaymentPanel.add(pnlExpCvvPanel);
        pnlPaymentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        return pnlPaymentPanel;
    }

    private RoundedComponents.RoundedTextField createValidatedField(
        String placeholder,
        Predicate<String> validator
    ) {
        var field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(175, 38));
        field.setMaximumSize(new Dimension(175, 38));
        lstPaymentFields.add(field);
        ValidationUtil.addTextValidation(field, validator);
        return field;
    }

    private static class GridPanel extends JPanel {
        public GridPanel(String left, String right) {
            super(new GridLayout(1, 2));
            setOpaque(false);
            JLabel l = new JLabel(left);
            JLabel r = new JLabel(right);
            l.setFont(FontUtil.getInterFont(16f));
            r.setFont(FontUtil.getInterFont(16f));
            l.setForeground(Color.decode("#1E1E1E"));
            r.setForeground(Color.decode("#1E1E1E"));
            r.setHorizontalAlignment(SwingConstants.RIGHT);
            add(l);
            add(r);
        }
    }


    private static class RoundedScrollContainer extends JPanel {
        private final int radius;
        public RoundedScrollContainer(Component content, int radius) {
            super(new BorderLayout());
            this.radius = radius;
            setOpaque(false);
            add(content, BorderLayout.CENTER);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setClip(clip);
            g2.setColor(getBackground());
            g2.fill(clip);
            super.paintComponent(g2);
            g2.dispose();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddConfirm::new);
    }
}

