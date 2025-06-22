package com.group_9.project;

import com.group_9.project.database.AccountService;
import com.group_9.project.database.AccountService.Subscription;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrackingPage extends JFrame {

    public TrackingPage() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        // ─── Headline & Subheadline ───────────────────────────────────────────────
        JLabel lblHeadline = new JLabel(
            "<html><div style='text-align:center;color:#2B0243;font-weight:700;'>"
          + "Supercharge your home with<br>ultra-fast internet and endless entertainment."
          + "</div></html>",
            SwingConstants.CENTER
        );
        lblHeadline.setFont(FontUtil.getOutfitFont(50f));
        lblHeadline.setForeground(new Color(0x2B0243));
        lblHeadline.setBounds(112, 220, 1200, 120);
        pnlBackground.add(lblHeadline);

        JLabel lblSubHeadline = new JLabel(
            "Enjoy faster speed, and incredible value with our plans.",
            SwingConstants.CENTER
        );
        lblSubHeadline.setFont(FontUtil.getInterFont(16f));
        lblSubHeadline.setBounds(420, 350, 600, 30);
        pnlBackground.add(lblSubHeadline);

        // ─── CTA Buttons ─────────────────────────────────────────────────────────
        JButton cmdViewPlans = new JButton("VIEW PLANS");
        cmdViewPlans.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdViewPlans.setBounds(530, 400, 160, 45);
        cmdViewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(
            cmdViewPlans,
            new Color(62, 10, 118), Color.WHITE,
            new Color(42, 2, 67),  Color.WHITE,
            new Color(62, 10, 118), new Color(42, 2, 67)
        );
        cmdViewPlans.addActionListener(e -> { new PlansPage().setVisible(true); dispose(); });
        pnlBackground.add(cmdViewPlans);

        JButton cmdCheckAvail = new JButton("CHECK AVAILABILITY");
        cmdCheckAvail.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdCheckAvail.setBounds(700, 400, 220, 45);
        cmdCheckAvail.setFocusPainted(false);
        cmdCheckAvail.setContentAreaFilled(false);
        ButtonHoverEffect.apply(
            cmdCheckAvail,
            new Color(62, 10, 118), new Color(62, 10, 118),
            new Color(0,0,0,0),    new Color(38,6,67),
            new Color(62,10,118),  new Color(42,2,67)
        );
        cmdCheckAvail.addActionListener(e -> { new ErrorPage().setVisible(true); dispose(); });
        pnlBackground.add(cmdCheckAvail);

        // ─── WiFi Icon ───────────────────────────────────────────────────────────
        ImageIcon wifiRaw = new ImageIcon(
            getClass().getClassLoader().getResource("images/wifi.png")
        );
        Image wifiImg2 = wifiRaw.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel lblWifi = new JLabel(new ImageIcon(wifiImg2));
        lblWifi.setBounds(302, 495, 200, 200);
        pnlBackground.add(lblWifi);

        // ─── Upgrade CTA ─────────────────────────────────────────────────────────
        int xLeft = 140, yLeft = 780;
        JLabel lblUpgradeTitle = new JLabel(
            "<html><div style='text-align:left;color:#2B0243;font-weight:700;'>"
          + "Ready to Upgrade Your Setup?</div></html>"
        );
        lblUpgradeTitle.setFont(FontUtil.getInterFont(35f));
        lblUpgradeTitle.setForeground(new Color(43, 2, 67));
        lblUpgradeTitle.setBounds(xLeft, yLeft - 50, 600, 45);
        pnlBackground.add(lblUpgradeTitle);

        JLabel lblUpgradeDesc = new JLabel(
            "<html>Start with one, then add more plans as your<br>needs grow.</html>"
        );
        lblUpgradeDesc.setFont(FontUtil.getInterFont(16f));
        lblUpgradeDesc.setForeground(new Color(43, 2, 67));
        lblUpgradeDesc.setBounds(xLeft, yLeft, 450, 50);
        pnlBackground.add(lblUpgradeDesc);

        RoundedComponents.RoundedButton cmdMorePlans = new RoundedComponents.RoundedButton("GET MORE PLANS", 20);
        cmdMorePlans.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
        cmdMorePlans.setBounds(xLeft, yLeft + 60, 160, 40);
        cmdMorePlans.setFocusPainted(false);
        ButtonHoverEffect.apply(
            cmdMorePlans,
            new Color(62, 10, 118), Color.WHITE,
            new Color(42, 2, 67),  Color.WHITE,
            new Color(62, 10, 118), new Color(42, 2, 67)
        );
        cmdMorePlans.addActionListener(e -> { new PlansPage().setVisible(true); dispose(); });
        pnlBackground.add(cmdMorePlans);

        // ─────────────────────────────────────────────────────────────────────────
        // APPLICATION TRACKER panel (dynamic, one card per application)
        // ─────────────────────────────────────────────────────────────────────────

        // Title + search field
        JLabel lblTrackerTitle = new JLabel(
          "<html><div style='color:#2A0243;font-weight:700;'>APPLICATION TRACKER</div></html>",
          SwingConstants.CENTER
        );
        lblTrackerTitle.setFont(FontUtil.getOutfitFont(26f));

        RoundedComponents.RoundedTextField txtSearchField =
            new RoundedComponents.RoundedTextField("Enter application number", 20);
        txtSearchField.setFont(FontUtil.getInterFont(14f));
        txtSearchField.setBackground(Color.WHITE);

        // 1) Load all subscriptions (one per plan)
        String username = UserApplicationData.get("Username");
        List<Subscription> subs;
        try {
            subs = AccountService.getSubscriptionsByUsername(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            subs = List.of();
        }

        // 2) Deduplicate by applicationNo
        Map<String,Subscription> unique = new LinkedHashMap<>();
        for(Subscription s: subs) {
            unique.putIfAbsent(s.strApplicationNo, s);
        }
        List<Subscription> apps = new ArrayList<>(unique.values());

        // 3) Build cards container
        JPanel pnlCardsContainer = new JPanel();
        pnlCardsContainer.setLayout(new BoxLayout(pnlCardsContainer, BoxLayout.Y_AXIS));
        pnlCardsContainer.setOpaque(false);

        if (apps.isEmpty()) {
            JLabel lblNone = new JLabel("You have no applications to track.");
            lblNone.setFont(FontUtil.getOutfitFont(16f));
            lblNone.setForeground(new Color(80,80,80));
            lblNone.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlCardsContainer.add(Box.createVerticalGlue());
            pnlCardsContainer.add(lblNone);
            pnlCardsContainer.add(Box.createVerticalGlue());
        } else {
            for (Subscription s : apps) {
                RoundedGradientPanel pnlCard = new RoundedGradientPanel(20);
                pnlCard.setLayout(null);
                pnlCard.setPreferredSize(new Dimension(887, 90));
                pnlCard.setMaximumSize(new Dimension(887, 90));

                JLabel lblAppNumber = new JLabel("Application No. " + s.strApplicationNo);
                lblAppNumber.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
                lblAppNumber.setForeground(Color.WHITE);
                lblAppNumber.setBounds(20, 15, 300, 20);
                pnlCard.add(lblAppNumber);

                JLabel lblAppStatus = new JLabel("Status: Pending");
                lblAppStatus.setFont(FontUtil.getInterFont(12f));
                lblAppStatus.setForeground(Color.WHITE);
                lblAppStatus.setBounds(20, 38, 300, 18);
                pnlCard.add(lblAppStatus);

                JLabel lblAppDate = new JLabel("Date Submitted: " + s.strDateSubmitted);
                lblAppDate.setFont(FontUtil.getInterFont(12f));
                lblAppDate.setForeground(Color.WHITE);
                lblAppDate.setBounds(20, 58, 300, 18);
                pnlCard.add(lblAppDate);

                JLabel lblViewSummary = new JLabel("<html><u>View Plan Summary</u></html>");
                lblViewSummary.setFont(FontUtil.getInterFont(12f));
                lblViewSummary.setForeground(Color.WHITE);
                lblViewSummary.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblViewSummary.setBounds(250, 58, 130, 18);
                pnlCard.add(lblViewSummary);

                pnlCardsContainer.add(pnlCard);
                pnlCardsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        // 4) Wrap in scroll pane
        JScrollPane scrCards = new JScrollPane(
            pnlCardsContainer,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrCards.setBorder(null);
        scrCards.setOpaque(false);
        scrCards.getViewport().setOpaque(false);

        // 5) Put everything in your tracker panel
        RoundedPanel pnlTracker = new RoundedPanel(30) {
            @Override public void doLayout() {
                int pad = 20;
                int w   = getWidth() - 2 * pad;
                lblTrackerTitle.setBounds(pad, 20, w, 30);
                txtSearchField.setBounds(pad, 60, w, 35);
                scrCards.setBounds(pad, 110, w, getHeight() - 110 - pad);
            }
        };
        pnlTracker.setBackground(new Color(255, 255, 255, 180));
        pnlTracker.setBounds(800, 515, 484, 350);
        pnlTracker.setLayout(null);

        pnlTracker.add(lblTrackerTitle);
        pnlTracker.add(txtSearchField);
        pnlTracker.add(scrCards);
        pnlBackground.add(pnlTracker);

        SwingUtilities.invokeLater(pnlBackground::requestFocusInWindow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrackingPage().setVisible(true));
    }
}

// ─── RoundedPanel ─────────────────────────────────────────────────────────────
class RoundedPanel extends JPanel {
    private final int cornerRadius;
    public RoundedPanel(int radius) {
        super(null);
        cornerRadius = radius;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);
        g2.setColor(new Color(210, 190, 255));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
    }
}

// ─── RoundedGradientPanel ─────────────────────────────────────────────────────
class RoundedGradientPanel extends JPanel {
    private final int cornerRadius;
    public RoundedGradientPanel(int radius) {
        super(null);
        cornerRadius = radius;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, new Color(40,0,80), w, h, new Color(125,0,255));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
    }
}
