package com.group_9.project;

import com.group_9.project.database.DatabaseConnection;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
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

    private RoundedComponents.RoundedTextField cardholderName, cardNumber, expiryDate, cvv;
    private final List<JTextComponent> paymentFields = new ArrayList<>();
    private JRadioButton full, install;

    public AddConfirm() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // — White rounded container
        JPanel container = createContentPanel();
        background.add(container);

        // — Inner content
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        inner.setBounds(40, 40, 890, 615);
        container.add(inner);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setForeground(Color.decode("#2B0243"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(title);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel steps = new JPanel(new FlowLayout(FlowLayout.CENTER));
        steps.setOpaque(false);
        steps.add(CreateStepTracker.createStepTracker(2));
        inner.add(steps);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Subtitle + Note
        Color subColor = Color.decode("#302E2E");
        JLabel subtitle = new JLabel("SECURE YOUR PAYMENT", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);
        JLabel subNote = new JLabel("Complete your application by confirming payment for the selected plans.");
        subNote.setFont(FontUtil.getInterFont(14f));
        subNote.setForeground(subColor);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(826, 60));
        JPanel leftLabels = new JPanel();
        leftLabels.setOpaque(false);
        leftLabels.setLayout(new BoxLayout(leftLabels, BoxLayout.Y_AXIS));
        leftLabels.add(subtitle);
        leftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        leftLabels.add(subNote);
        infoPanel.add(leftLabels, BorderLayout.WEST);
        inner.add(infoPanel);

        inner.add(Box.createRigidArea(new Dimension(0, 10)));
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(826, 2));
        sep.setForeground(Color.decode("#B2B2B2"));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(sep);
        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Plan summary + payment section
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.add(createPlanSummaryPanel());
        row.add(Box.createRigidArea(new Dimension(30, 0)));
        row.add(createPaymentSectionPanel());
        inner.add(row);

        inner.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton back = new RoundedComponents.RoundedButton("BACK", 25);
        styleBackButton(back);
        back.addActionListener(e -> {
            saveFormState();
            new AddPlansPage().setVisible(true);
            dispose();
        });

        RoundedComponents.RoundedButton confirm = new RoundedComponents.RoundedButton("CONFIRM PAYMENT", 25);
        styleConfirmButton(confirm);
        confirm.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {
                confirm.setBackground(Color.decode("#4B278F"));
                confirm.setBorderColor(Color.decode("#4B278F"));
            }
            public void mouseExited(MouseEvent e) {
                confirm.setBackground(Color.decode("#623CBB"));
                confirm.setBorderColor(Color.decode("#623CBB"));
            }
        });
        confirm.addActionListener(e -> onConfirm());

        btnPanel.add(back, BorderLayout.WEST);
        btnPanel.add(confirm, BorderLayout.EAST);
        inner.add(btnPanel);

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());

        // restore any saved inputs
        loadFormState();
    }

    private void onConfirm() {
        // 1) Validate inputs
        if (!validateAllFields()) return;

        // 2) Gather data
        String appNo = UserApplicationData.get("ApplicationNo");
        if (appNo == null) {
            CustomDialogUtil.showStyledErrorDialog(this,
                "Missing Application",
                "No application number found. Please start over."
            );
            return;
        }

        String paymentOpt = full.isSelected() ? "full" : "installment";
        String[] planIds = Optional.ofNullable(UserApplicationData.get("selectedPlanIDs"))
                                   .map(s -> s.split(","))
                                   .orElse(new String[0]);
        if (planIds.length == 0) {
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
                String sql = """
                    INSERT INTO tbl_payment(application_no, plan_ID, payment_option)
                    VALUES(?, ?, ?)
                """;
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    for (String pid : planIds) {
                        ps.setString(1, appNo.trim());
                        ps.setString(2, pid.trim());
                        ps.setString(3, paymentOpt);
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
        for (JTextComponent fld : paymentFields) {
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
        if (!full.isSelected() && !install.isSelected()) {
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
        UserApplicationData.set("cardholderName", cardholderName.getText());
        UserApplicationData.set("cardNumber",     cardNumber.getText());
        UserApplicationData.set("expiryDate",     expiryDate.getText());
        UserApplicationData.set("cvv",            cvv.getText());
        UserApplicationData.set("paymentOption", full.isSelected()? "full":"installment");
    }

    private void loadFormState() {
        Optional.ofNullable(UserApplicationData.get("cardholderName"))
                .ifPresent(cardholderName::setText);
        Optional.ofNullable(UserApplicationData.get("cardNumber"))
                .ifPresent(cardNumber::setText);
        Optional.ofNullable(UserApplicationData.get("expiryDate"))
                .ifPresent(expiryDate::setText);
        Optional.ofNullable(UserApplicationData.get("cvv"))
                .ifPresent(cvv::setText);
        String opt = UserApplicationData.get("paymentOption");
        if ("full".equals(opt)) full.setSelected(true);
        else if ("installment".equals(opt)) install.setSelected(true);
    }

    // --- All helper/UI methods below ---

    private JPanel createContentPanel() {
        JPanel c = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                int s = 4;
                g2.setColor(new Color(0,0,0,20));
                g2.fillRoundRect(s, s, getWidth()-s, getHeight()-s, 25,25);
                g2.setColor(new Color(255,241,255));
                g2.fillRoundRect(0,0,getWidth()-s,getHeight()-s,25,25);
                g2.setColor(new Color(220,200,230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0,0,getWidth()-s-1,getHeight()-s-1,25,25);
                g2.dispose();
            }
        };
        c.setBounds(235, 165, 970, 695);
        c.setOpaque(false);
        return c;
    }

    private JPanel createPlanSummaryPanel() {
        Color txtColor = Color.decode("#1E1E1E");

        JPanel parent = new JPanel();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setOpaque(false);
        parent.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0));

        JLabel summaryTitle = new JLabel("Your Plan Summary");
        summaryTitle.setFont(FontUtil.getOutfitFont(15f));
        summaryTitle.setForeground(txtColor);
        parent.add(summaryTitle);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedPanel summaryContent = new RoundedPanel(20);
        summaryContent.setLayout(new BoxLayout(summaryContent, BoxLayout.Y_AXIS));
        summaryContent.setBackground(Color.WHITE);
        summaryContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setOpaque(false);
        header.add(new JLabel("Product and Service") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
        }});
        header.add(new JLabel("Amount") {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(txtColor);
            setHorizontalAlignment(SwingConstants.RIGHT);
        }});
        summaryContent.add(header);
        summaryContent.add(Box.createRigidArea(new Dimension(0, 10)));
        summaryContent.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setMaximumSize(new Dimension(331, 2));
            setForeground(Color.decode("#B2B2B2"));
        }});
        summaryContent.add(Box.createRigidArea(new Dimension(0, 20)));

        String savedPlans = UserApplicationData.get("selectedPlans");
        if (savedPlans != null && !savedPlans.isEmpty()) {
            String[] plans = savedPlans.split(",");
            for (int i = 0; i < plans.length; i++) {
                String plan = plans[i].trim();
                summaryContent.add(new GridPanel(plan, ""));
                summaryContent.add(new GridPanel("Monthly Service Fee", getPriceForPlan(plan)));
                summaryContent.add(new GridPanel("Installation Fee", getInstallationFeeForPlan(plan)));
                if (i < plans.length - 1) summaryContent.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(summaryContent);
        scrollPane.setPreferredSize(new Dimension(375, 210));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JScrollBar vsb = scrollPane.getVerticalScrollBar();
        vsb.setUI(new CustomScrollBarUI());
        vsb.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        vsb.setUnitIncrement(16);
        vsb.setVisible(false);

        scrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                vsb.setVisible(true);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                vsb.setVisible(false);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        RoundedScrollContainer wrapper = new RoundedScrollContainer(scrollPane, 20);
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

        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 0));
        paymentPanel.setMaximumSize(new Dimension(400, 450));

        JLabel paymentTitle = new JLabel("Payment Section");
        paymentTitle.setFont(FontUtil.getOutfitFont(15f));
        paymentTitle.setForeground(txtColor);
        paymentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(paymentTitle);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        ButtonGroup paymentGroup = new ButtonGroup();
        ImageIcon iconOff = new ImageIcon(getClass().getResource("/icons/radio_off.png"));
        ImageIcon iconOn  = new ImageIcon(getClass().getResource("/icons/radio_on.png"));
        int iconSize = 18;
        iconOff = new ImageIcon(iconOff.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        iconOn  = new ImageIcon(iconOn.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        full = new JRadioButton("Full Payment");
        full.setFont(FontUtil.getInterFont(15f));
        full.setForeground(txtColor);
        full.setOpaque(false);
        full.setFocusPainted(false);
        full.setContentAreaFilled(false);
        full.setBorderPainted(false);
        full.setIcon(iconOff);
        full.setSelectedIcon(iconOn);

        install = new JRadioButton("Installment");
        install.setFont(FontUtil.getInterFont(15f));
        install.setForeground(txtColor);
        install.setOpaque(false);
        install.setFocusPainted(false);
        install.setContentAreaFilled(false);
        install.setBorderPainted(false);
        install.setIcon(iconOff);
        install.setSelectedIcon(iconOn);

        paymentGroup.add(full);
        paymentGroup.add(install);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setOpaque(false);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.add(full);
        radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        radioPanel.add(install);

        paymentPanel.add(radioPanel);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Cardholder Name
        cardholderName = createValidatedField("Cardholder Name", s -> !s.trim().isEmpty());
        ToolTipUtil.attachCustomTooltip(cardholderName, "Enter name as shown on card");
        cardholderName.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(cardholderName);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Card Number
        cardNumber = createValidatedField("Card Number", s -> s.matches("(\\d{4} ){3}\\d{4}"));
        SmartFieldFormatter.attachCardNumberFormatter(cardNumber);
        ((AbstractDocument) cardNumber.getDocument()).setDocumentFilter(new LengthLimitFilter(19));
        ToolTipUtil.attachCustomTooltip(cardNumber, "Enter 16-digit card number");
        cardNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentPanel.add(cardNumber);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Expiry & CVV Panel
        JPanel expCvvPanel = new JPanel();
        expCvvPanel.setLayout(new BoxLayout(expCvvPanel, BoxLayout.X_AXIS));
        expCvvPanel.setOpaque(false);
        expCvvPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        expiryDate = createValidatedField("MM/YY", s -> {
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
        SmartFieldFormatter.attachExpiryDateFormatter(expiryDate);
        ((AbstractDocument) expiryDate.getDocument()).setDocumentFilter(new LengthLimitFilter(5));
        ToolTipUtil.attachCustomTooltip(expiryDate, "Enter expiry date (MM/YY)");
        expiryDate.setMaximumSize(new Dimension(100, 38));
        expiryDate.setPreferredSize(new Dimension(100, 38));
        expCvvPanel.add(expiryDate);
        expCvvPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        cvv = createValidatedField("CVV", s -> s.matches("\\d{3,4}"));
        ((AbstractDocument) cvv.getDocument()).setDocumentFilter(new LengthLimitFilter(4));
        ToolTipUtil.attachCustomTooltip(cvv, "Enter CVV (3 or 4 digits)");
        cvv.setMaximumSize(new Dimension(80, 38));
        cvv.setPreferredSize(new Dimension(80, 38));
        expCvvPanel.add(cvv);

        paymentPanel.add(expCvvPanel);
        paymentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        return paymentPanel;
    }

    private RoundedComponents.RoundedTextField createValidatedField(
        String placeholder,
        Predicate<String> validator
    ) {
        var field = new RoundedComponents.RoundedTextField(placeholder, 15);
        field.setFont(FontUtil.getOutfitFont(15f));
        field.setPreferredSize(new Dimension(175, 38));
        field.setMaximumSize(new Dimension(175, 38));
        paymentFields.add(field);
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

    private static class CustomScrollBarUI extends BasicScrollBarUI {
        private static final Color THUMB_COLOR = new Color(42, 2, 67);

        @Override protected void configureScrollBarColors() {
            thumbColor = THUMB_COLOR;
            trackColor = new Color(0, 0, 0, 0);
        }

        @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            return btn;
        }

        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (!scrollbar.isEnabled() || thumbBounds.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(THUMB_COLOR);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}
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

    private String getPriceForPlan(String planName) {
        return switch (planName.toUpperCase()) {
            case "FIBERX 1500" -> "₱1500";
            case "FIBERX 2500" -> "₱2500";
            case "FIBERX 3500" -> "₱3500";
            case "FIBER XTREAM 4500" -> "₱4500";
            case "FIBER XTREAM 7000" -> "₱7000";
            default -> "₱0";
        };
    }

    private String getInstallationFeeForPlan(String planName) {
        return switch (planName.toUpperCase()) {
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

