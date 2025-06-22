package com.group_9.project;

import com.group_9.project.database.AccountService;
import com.group_9.project.database.AccountService.Subscription;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AccountSubsPage extends Template {
    public AccountSubsPage() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 3);

        JPanel sidebar = AccountSidebarUtil.createSidebar(this, "My Subscriptions");
        background.add(sidebar);

        JPanel content = new RoundedComponents.RoundedShadowPanel(25, 4);
        content.setBounds(290, 150, 1020, 720);
        background.add(content);

        JPanel detailsContainer = createDetailsContainer();
        content.add(detailsContainer);

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    


    private JPanel createDetailsContainer() {
        JPanel container = new JPanel(null);
        container.setOpaque(false);
        container.setBounds(0, 0, 1250, 700);
    
        // ─── Headers ─────────────────────────────────────────
        JLabel titleLabel = new JLabel("MY SUBSCRIPTIONS");
        titleLabel.setFont(FontUtil.getOutfitBoldFont(26f));
        titleLabel.setForeground(new Color(42,2,67));
        titleLabel.setBounds(70, 50, 300, 30);
        container.add(titleLabel);
    
        JLabel sectionLabel = new JLabel("SERVICE AND PLAN SUBSCRIPTIONS");
        sectionLabel.setFont(FontUtil.getOutfitFont(16f));
        sectionLabel.setBounds(70, 100, 400, 20);
        container.add(sectionLabel);
    
        JSeparator sep = new JSeparator();
        sep.setBounds(70, 130, 880, 1);
        sep.setForeground(new Color(180,180,180));
        container.add(sep);
    
        // ─── Fetch subscriptions ────────────────────────────
        String username = UserApplicationData.get("Username");
        List<Subscription> subs;
        try {
            subs = AccountService.getSubscriptionsByUsername(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            subs = List.of();
        }
    
        if (subs.isEmpty()) {
            JLabel none = new JLabel("You have no active subscriptions.");
            none.setFont(FontUtil.getOutfitFont(18f));
            none.setForeground(new Color(80,80,80));
            none.setBounds(100, 200, 400, 30);
            container.add(none);
            return container;
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
                    s.planDetails.servicePlan,
                    String.format("₱%,.2f", s.planDetails.serviceFee),
                    s.planDetails.installFee,
                    s.applicationNo,
                    s.dateSubmitted
                );
                container.add(card);
            }
        } else {
            // ─── 5 or more: scrollable 2-column grid ───────────
            // 1) Build a JPanel with GridLayout(0,2)
            JPanel grid = new JPanel(new GridLayout(0, 2, hGap, vGap));
            grid.setOpaque(false);
    
            for (Subscription s : subs) {
                // each card will auto–size via GridLayout
                JPanel card = createWhiteBox(
                    0, 0, 
                    s.planDetails.servicePlan,
                    String.format("₱%,.2f", s.planDetails.serviceFee),
                    s.planDetails.installFee,
                    s.applicationNo,
                    s.dateSubmitted
                );
                grid.add(card);
            }
    
            // 2) Wrap it in a JScrollPane sized to show exactly 4 cards (2×2)
            int viewportWidth  = 2 * boxW + hGap;
            int viewportHeight = 2 * boxH + vGap;
            JScrollPane scroll = new JScrollPane(grid,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setBounds(startX, startY, viewportWidth, viewportHeight);
            scroll.setBorder(null);
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);
    
            container.add(scroll);
        }
    
        return container;
    }
    

    JPanel createWhiteBox(int x, int y, String product, String monthlyFee, String installFee,
                                  String appNo, String submittedDate) {
        JPanel applicantBox = new JPanel(null) {
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
        applicantBox.setBounds(x, y, 380, 220);

        JLabel header = new JLabel("Product and Service");
        header.setFont(FontUtil.getOutfitFont(17f));
        header.setBounds(20, 15, 200, 20);
        applicantBox.add(header);

        JLabel amount = new JLabel("Amount");
        amount.setFont(FontUtil.getOutfitFont(17f));
        amount.setBounds(275, 15, 80, 20);
        applicantBox.add(amount);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 50, 340, 1);
        sep.setForeground(Color.BLACK);
        applicantBox.add(sep);

        JLabel plan = new JLabel("<html><b>" + product + "</b><br>Monthly Service Fee<br>Installation Fee</html>");
        plan.setFont(FontUtil.getOutfitFont(18f));
        plan.setBounds(20, 65, 200, 60);
        applicantBox.add(plan);

        JLabel price = new JLabel("<html><br>" + monthlyFee + "<br>" + installFee + "</html>");
        price.setFont(FontUtil.getOutfitFont(18f));
        price.setBounds(260, 65, 100, 60);
        applicantBox.add(price);

        JLabel details = new JLabel("<html><br><br>APPLICATION NO. " + appNo +
                "<br>DATE SUBMITTED: " + submittedDate + "</html>");
        details.setFont(FontUtil.getOutfitBoldFont(12f));
        details.setBounds(20, 130, 300, 75);
        applicantBox.add(details);

        return applicantBox;
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountSubsPage().setVisible(true));
    }
}