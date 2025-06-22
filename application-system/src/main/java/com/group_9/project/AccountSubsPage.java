package com.group_9.project;

import com.group_9.project.database.AccountService;
import com.group_9.project.database.AccountService.Subscription;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;
import com.group_9.project.database.PaymentDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class AccountSubsPage extends Template {
    public AccountSubsPage() {
        BaseFrameSetup.applyAppIcon(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 3);

        JPanel pnlSidebar = AccountSidebarUtil.createSidebar(this, "My Subscriptions");
        pnlBackground.add(pnlSidebar);

        JPanel pnlContent = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContent.setBounds(290, 150, 1020, 720);
        pnlBackground.add(pnlContent);

        JPanel pnlDetailsContainer = createDetailsContainer();
        pnlContent.add(pnlDetailsContainer);

        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());
    }

    private JPanel createDetailsContainer() {
        JPanel pnlContainer = new JPanel(null);
        pnlContainer.setOpaque(false);
        pnlContainer.setBounds(0, 0, 1250, 700);

        // ─── Headers ─────────────────────────────────────────
        JLabel lblTitle = new JLabel("MY SUBSCRIPTIONS");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setForeground(new Color(42,2,67));
        lblTitle.setBounds(70, 50, 300, 30);
        pnlContainer.add(lblTitle);

        JLabel lblSection = new JLabel("SERVICE AND PLAN SUBSCRIPTIONS");
        lblSection.setFont(FontUtil.getOutfitFont(16f));
        lblSection.setBounds(70, 100, 400, 20);
        pnlContainer.add(lblSection);

        JSeparator sepDivider = new JSeparator();
        sepDivider.setBounds(70, 130, 880, 1);
        sepDivider.setForeground(new Color(180,180,180));
        pnlContainer.add(sepDivider);

        // ─── Fetch subscriptions ────────────────────────────
        String username = UserApplicationData.get("strUsername");
        List<Subscription> subs;
        try {
            subs = AccountService.getSubscriptionsByUsername(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            subs = List.of();
        }

        if (subs.isEmpty()) {
            JLabel lblNone = new JLabel("You have no active subscriptions.");
            lblNone.setFont(FontUtil.getOutfitFont(18f));
            lblNone.setForeground(new Color(80,80,80));
            lblNone.setBounds(100, 200, 400, 30);
            pnlContainer.add(lblNone);
            return pnlContainer;
        }

        final int boxW = 380, boxH = 220;
        final int hGap = 30, vGap = 30;
        final int startX = 70, startY = 180;

        if (subs.size() < 5) {
            // ─── Fewer than 5: absolute layout in two columns ──
            for (int i = 0; i < subs.size(); i++) {
                Subscription s = subs.get(i);
                int col = i % 2, row = i / 2;
                int x = startX + col * (boxW + hGap);
                int y = startY + row * (boxH + vGap);

                JPanel card = createWhiteBox(
                    x, y,
                    s.objPlanDetails.strServicePlan,
                    String.format("₱%,.2f", s.objPlanDetails.dblServiceFee),
                    s.objPlanDetails.strInstallFee,
                    s.strApplicationNo,
                    s.strDateSubmitted,
                    s.objPlanDetails.strPlanId
                );
                pnlContainer.add(card);
            }
        } else {
            // ─── 5 or more: scrollable 2-column grid ───────────
            JPanel pnlGrid = new JPanel(new GridLayout(0, 2, hGap, vGap));
            pnlGrid.setOpaque(false);
            int rows = (int) Math.ceil(subs.size() / 2.0);
            int gridW = 2 * boxW + hGap;
            int gridH = rows * boxH + (rows - 1) * vGap;
            pnlGrid.setPreferredSize(new Dimension(gridW, gridH));

            for (Subscription s : subs) {
                JPanel card = createWhiteBox(
                    0, 0,
                    s.objPlanDetails.strServicePlan,
                    String.format("₱%,.2f", s.objPlanDetails.dblServiceFee),
                    s.objPlanDetails.strInstallFee,
                    s.strApplicationNo,
                    s.strDateSubmitted,
                    s.objPlanDetails.strPlanId
                );
                pnlGrid.add(card);
            }

            JScrollPane scrScroll = new JScrollPane(
                    pnlGrid,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrScroll.setBounds(startX, startY, 2 * boxW + hGap, 2 * boxH + vGap);
            scrScroll.setBorder(null);
            scrScroll.setOpaque(false);
            scrScroll.getViewport().setOpaque(false);

            JScrollBar sbVertical = scrScroll.getVerticalScrollBar();
            sbVertical.setUI(new CustomScrollBarUI());
            sbVertical.setOpaque(false);
            sbVertical.setPreferredSize(new Dimension(10, 0));
            sbVertical.setUnitIncrement(16);

            pnlContainer.add(scrScroll);
        }

        return pnlContainer;
    }

    JPanel createWhiteBox(int x, int y, String product, String monthlyFee, String installFee,
                                  String appNo, String submittedDate, String planId) {
        JPanel pnlApplicantBox = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }

            public boolean isOpaque() {
                return false;
            }
        };
        pnlApplicantBox.setBounds(x, y, 380, 220);
        pnlApplicantBox.setPreferredSize(new Dimension(380, 220));

        JLabel lblHeader = new JLabel("Product and Service");
        lblHeader.setFont(FontUtil.getOutfitFont(17f));
        lblHeader.setBounds(20, 15, 200, 20);
        pnlApplicantBox.add(lblHeader);

        JLabel lblAmount = new JLabel("Amount");
        lblAmount.setFont(FontUtil.getOutfitFont(17f));
        lblAmount.setBounds(275, 15, 80, 20);
        pnlApplicantBox.add(lblAmount);

        JSeparator sepSeparator = new JSeparator();
        sepSeparator.setBounds(20, 50, 340, 1);
        sepSeparator.setForeground(Color.BLACK);
        pnlApplicantBox.add(sepSeparator);

        JLabel lblPlan = new JLabel("<html><b>" + product + "</b><br>Monthly Service Fee<br>Installation Fee</html>");
        lblPlan.setFont(FontUtil.getOutfitFont(18f));
        lblPlan.setBounds(20, 65, 200, 60);
        pnlApplicantBox.add(lblPlan);

        JLabel lblPrice = new JLabel("<html><br>" + monthlyFee + "<br>" + installFee + "</html>");
        lblPrice.setFont(FontUtil.getOutfitFont(18f));
        lblPrice.setBounds(260, 65, 100, 60);
        pnlApplicantBox.add(lblPrice);

        JLabel lblDetails = new JLabel("<html><br><br>APPLICATION NO. " + appNo +
                "<br>DATE SUBMITTED: " + submittedDate + "</html>");
        lblDetails.setFont(FontUtil.getOutfitBoldFont(12f));
        lblDetails.setBounds(20, 130, 300, 75);
        pnlApplicantBox.add(lblDetails);

        RoundedComponents.RoundedButton cmdUnsub = new RoundedComponents.RoundedButton("UNSUBSCRIBE", 20);
        cmdUnsub.setFont(FontUtil.getOutfitBoldFont(14f));
        cmdUnsub.setBounds(230, 170, 130, 35);
        cmdUnsub.setBackground(new Color(98, 60, 187));
        cmdUnsub.setForeground(Color.WHITE);
        cmdUnsub.setBorderColor(new Color(98, 60, 187));
        cmdUnsub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ButtonHoverEffect.apply(
                cmdUnsub,
                new Color(75, 39, 143), Color.WHITE,
                new Color(98, 60, 187), Color.WHITE,
                new Color(120, 90, 200), new Color(98, 60, 187)
        );
        cmdUnsub.addActionListener(e -> {
            boolean confirm = CustomDialogUtil.showStyledConfirmDialog(
                    AccountSubsPage.this,
                    "Unsubscribe",
                    "Are you sure you want to unsubscribe from this plan?"
            );
            if (!confirm) return;
            try {
                boolean deleted = new PaymentDao().deletePayment(appNo, planId);
                if (deleted) {
                    CustomDialogUtil.showStyledInfoDialog(
                            AccountSubsPage.this,
                            "Unsubscribed",
                            "Plan removed from your subscriptions."
                    );
                    new AccountSubsPage().setVisible(true);
                    dispose();
                } else {
                    CustomDialogUtil.showStyledErrorDialog(
                            AccountSubsPage.this,
                            "Not Found",
                            "Subscription could not be removed."
                    );
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CustomDialogUtil.showStyledErrorDialog(
                        AccountSubsPage.this,
                        "Database Error",
                        "Failed to remove subscription."
                );
            }
        });
        pnlApplicantBox.add(cmdUnsub);

        return pnlApplicantBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountSubsPage().setVisible(true));
    }
}
