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
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // ─── Headline & Subheadline ───────────────────────────────────────────────
        JLabel headline = new JLabel(
            "<html><div style='text-align:center;color:#2B0243;font-weight:700;'>"
          + "Supercharge your home with<br>ultra-fast internet and endless entertainment."
          + "</div></html>",
            SwingConstants.CENTER
        );
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel(
            "Enjoy faster speed, and incredible value with our plans.",
            SwingConstants.CENTER
        );
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        // ─── CTA Buttons ─────────────────────────────────────────────────────────
        JButton viewPlans = new JButton("VIEW PLANS");
        viewPlans.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        viewPlans.setBounds(530, 400, 160, 45);
        viewPlans.setFocusPainted(false);
        ButtonHoverEffect.apply(
            viewPlans,
            new Color(62, 10, 118), Color.WHITE,
            new Color(42, 2, 67),  Color.WHITE,
            new Color(62, 10, 118), new Color(42, 2, 67)
        );
        viewPlans.addActionListener(e -> { new PlansPage().setVisible(true); dispose(); });
        background.add(viewPlans);

        JButton checkAvail = new JButton("CHECK AVAILABILITY");
        checkAvail.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        checkAvail.setBounds(700, 400, 220, 45);
        checkAvail.setFocusPainted(false);
        checkAvail.setContentAreaFilled(false);
        ButtonHoverEffect.apply(
            checkAvail,
            new Color(62, 10, 118), new Color(62, 10, 118),
            new Color(0,0,0,0),    new Color(38,6,67),
            new Color(62,10,118),  new Color(42,2,67)
        );
        checkAvail.addActionListener(e -> { new ErrorPage().setVisible(true); dispose(); });
        background.add(checkAvail);

        // ─── WiFi Icon ───────────────────────────────────────────────────────────
        ImageIcon wifiRaw = new ImageIcon(
            getClass().getClassLoader().getResource("images/wifi.png")
        );
        Image wifiImg2 = wifiRaw.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel wifiLabel = new JLabel(new ImageIcon(wifiImg2));
        wifiLabel.setBounds(302, 495, 200, 200);
        background.add(wifiLabel);

        // ─── Upgrade CTA ─────────────────────────────────────────────────────────
        int xLeft = 140, yLeft = 780;
        JLabel upgradeTitle = new JLabel(
            "<html><div style='text-align:left;color:#2B0243;font-weight:700;'>"
          + "Ready to Upgrade Your Setup?</div></html>"
        );
        upgradeTitle.setFont(FontUtil.getInterFont(35f));
        upgradeTitle.setForeground(new Color(43, 2, 67));
        upgradeTitle.setBounds(xLeft, yLeft - 50, 600, 45);
        background.add(upgradeTitle);

        JLabel upgradeDesc = new JLabel(
            "<html>Start with one, then add more plans as your<br>needs grow.</html>"
        );
        upgradeDesc.setFont(FontUtil.getInterFont(16f));
        upgradeDesc.setForeground(new Color(43, 2, 67));
        upgradeDesc.setBounds(xLeft, yLeft, 450, 50);
        background.add(upgradeDesc);

        RoundedComponents.RoundedButton morePlans = new RoundedComponents.RoundedButton("GET MORE PLANS", 20);
        morePlans.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
        morePlans.setBounds(xLeft, yLeft + 60, 160, 40);
        morePlans.setFocusPainted(false);
        ButtonHoverEffect.apply(
            morePlans,
            new Color(62, 10, 118), Color.WHITE,
            new Color(42, 2, 67),  Color.WHITE,
            new Color(62, 10, 118), new Color(42, 2, 67)
        );
        morePlans.addActionListener(e -> { new PlansPage().setVisible(true); dispose(); });
        background.add(morePlans);

        // ─────────────────────────────────────────────────────────────────────────
        // APPLICATION TRACKER panel (dynamic, one card per application)
        // ─────────────────────────────────────────────────────────────────────────

        // Title + search field
        JLabel trackerTitle = new JLabel(
          "<html><div style='color:#2A0243;font-weight:700;'>APPLICATION TRACKER</div></html>",
          SwingConstants.CENTER
        );
        trackerTitle.setFont(FontUtil.getOutfitFont(26f));

        RoundedComponents.RoundedTextField searchField =
            new RoundedComponents.RoundedTextField("Enter application number", 20);
        searchField.setFont(FontUtil.getInterFont(14f));
        searchField.setBackground(Color.WHITE);

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
            unique.putIfAbsent(s.applicationNo, s);
        }
        List<Subscription> apps = new ArrayList<>(unique.values());

        // 3) Build cards container
        JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setOpaque(false);

        if (apps.isEmpty()) {
            JLabel none = new JLabel("You have no applications to track.");
            none.setFont(FontUtil.getOutfitFont(16f));
            none.setForeground(new Color(80,80,80));
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardsContainer.add(Box.createVerticalGlue());
            cardsContainer.add(none);
            cardsContainer.add(Box.createVerticalGlue());
        } else {
            for (Subscription s : apps) {
                RoundedGradientPanel card = new RoundedGradientPanel(20);
                card.setLayout(null);
                card.setPreferredSize(new Dimension(887, 90));
                card.setMaximumSize(new Dimension(887, 90));

                JLabel appNumberLbl = new JLabel("Application No. " + s.applicationNo);
                appNumberLbl.setFont(FontUtil.getOutfitFont(14f).deriveFont(Font.BOLD));
                appNumberLbl.setForeground(Color.WHITE);
                appNumberLbl.setBounds(20, 15, 300, 20);
                card.add(appNumberLbl);

                JLabel appStatusLbl = new JLabel("Status: Pending");
                appStatusLbl.setFont(FontUtil.getInterFont(12f));
                appStatusLbl.setForeground(Color.WHITE);
                appStatusLbl.setBounds(20, 38, 300, 18);
                card.add(appStatusLbl);

                JLabel appDateLbl = new JLabel("Date Submitted: " + s.dateSubmitted);
                appDateLbl.setFont(FontUtil.getInterFont(12f));
                appDateLbl.setForeground(Color.WHITE);
                appDateLbl.setBounds(20, 58, 300, 18);
                card.add(appDateLbl);

                JLabel viewSummary = new JLabel("<html><u>View Plan Summary</u></html>");
                viewSummary.setFont(FontUtil.getInterFont(12f));
                viewSummary.setForeground(Color.WHITE);
                viewSummary.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                viewSummary.setBounds(250, 58, 130, 18);
                card.add(viewSummary);

                cardsContainer.add(card);
                cardsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        // 4) Wrap in scroll pane
        JScrollPane scroll = new JScrollPane(
            cardsContainer,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        // 5) Put everything in your tracker panel
        RoundedPanel trackerPanel = new RoundedPanel(30) {
            @Override public void doLayout() {
                int pad = 20;
                int w   = getWidth() - 2 * pad;
                trackerTitle.setBounds(pad, 20, w, 30);
                searchField .setBounds(pad, 60, w, 35);
                scroll      .setBounds(pad, 110, w, getHeight() - 110 - pad);
            }
        };
        trackerPanel.setBackground(new Color(255, 255, 255, 180));
        trackerPanel.setBounds(800, 515, 484, 350);
        trackerPanel.setLayout(null);

        trackerPanel.add(trackerTitle);
        trackerPanel.add(searchField);
        trackerPanel.add(scroll);
        background.add(trackerPanel);

        SwingUtilities.invokeLater(background::requestFocusInWindow);
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
