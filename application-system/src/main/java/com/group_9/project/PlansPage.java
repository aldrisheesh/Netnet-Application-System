package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class PlansPage extends JFrame {
    public PlansPage() {
        BaseFrameSetup.applyAppIcon(this);
        BaseFrameSetup.setupFrame(this);

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        BackgroundPanel navbarPanel = new BackgroundPanel(4) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.decode("#FFF1FF"));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        navbarPanel.setLayout(null);
        navbarPanel.setPreferredSize(new Dimension(1440, 80));
        navbarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        navbarPanel.setMinimumSize(new Dimension(1440, 80));

        BaseFrameSetup.createLogo(navbarPanel);
        BaseFrameSetup.createNavigation(navbarPanel, this);
        BaseFrameSetup.createLoginButton(navbarPanel, this);

        BackgroundPanel contentBackground = BaseFrameSetup.createBackgroundPanel(2);
        contentBackground.setLayout(new BoxLayout(contentBackground, BoxLayout.Y_AXIS));
        contentBackground.setPreferredSize(new Dimension(1440, 1100));
        contentBackground.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel title = new JLabel("Power Your Experience Your Way");
        title.setFont(FontUtil.getOutfitBoldFont(50f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.decode("#2B0243"));
        contentBackground.add(title);
        contentBackground.add(Box.createRigidArea(new Dimension(0, 16)));

        JLabel note = new JLabel("Start with what you need, add more when you're ready.");
        note.setFont(FontUtil.getInterFont(16f));
        note.setAlignmentX(Component.CENTER_ALIGNMENT);
        note.setForeground(Color.decode("#2B0243"));
        contentBackground.add(note);
        contentBackground.add(Box.createRigidArea(new Dimension(0, 58)));

        createPlansSection(contentBackground);

        JScrollPane scrollPane = new JScrollPane(contentBackground);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());
        verticalScrollBar.setOpaque(false);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));

        mainContainer.add(navbarPanel);
        mainContainer.add(scrollPane);

        setContentPane(mainContainer);
        setVisible(true);
    }

    private void createPlansSection(BackgroundPanel contentBackground) {
        JPanel firstRowPanel = createPlansRow();
        firstRowPanel.add(createPlanCard("FIBERX 1500", "P1500", "/month",
                "Installation Fee: P125/24mo.",
                "Seamless streaming with blazing fast, ready to surf the web at lightning speed with our 4tra boosted 300Mbps internet plan - fast, reliable, and pocket friendly.",
                "One Month Advance Payment"));
        firstRowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        firstRowPanel.add(createPlanCard("FIBERX 2500", "P2500", "/month",
                "Installation Fee: P125/24mo.",
                "Ultra-HD streaming and faster uploads. Enjoy uninterrupted video calls, 4K streaming, and file transfers with our 500Mbps boosted plan.",
                "One Month Advance Payment"));
        firstRowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        firstRowPanel.add(createPlanCard("FIBERX 3500", "P3500", "/month",
                "Installation Fee: P125/24mo.",
                "Power without limits. Our robust 700Mbps speed delivers seamless streaming, fast downloads, and reliable performance for work and play.",
                "One Month Advance Payment"));
        contentBackground.add(firstRowPanel);
        contentBackground.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel secondRowPanel = createPlansRow();
        secondRowPanel.add(createPlanCard("FIBER Xtreme 4500", "P4500", "/month",
                "Installation Fee: WAIVED",
                "Unleash enterprise-level speed at home. Enjoy blazing 1Gbps internet with zero interruptions - ideal for smart homes and high-demand digital lifestyles.",
                "Two Months Advance Payment"));
        secondRowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        secondRowPanel.add(createPlanCard("FIBER Xtreme 7000", "P7000", "/month",
                "Installation Fee: WAIVED",
                "Unparalleled 2Gbps speed designed for high-tech homes and digital studios. Enjoy ultra-low latency, zero downtime, and massive bandwidth.",
                "Two Months Advance Payment"));
        secondRowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        secondRowPanel.add(Box.createRigidArea(new Dimension(375, 413)));

        contentBackground.add(secondRowPanel);
    }

    private JPanel createPlansRow() {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(new Dimension(1175, 413));
        row.add(Box.createHorizontalGlue());
        return row;
    }

    private JPanel createPlanCard(
            String planName,
            String price,
            String period,
            String installationFee,
            String description,
            String upfrontFee
    ) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(375, 413));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedComponents.RoundedBorder(15),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel nameLabel = new JLabel(planName);
        nameLabel.setFont(FontUtil.getOutfitBoldFont(18f));
        nameLabel.setForeground(Color.decode("#402F84"));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
        pricePanel.setOpaque(false);
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pricePanel.add(new JLabel(price) {{
            setFont(FontUtil.getOutfitBoldFont(50f));
            setForeground(Color.decode("#1E1E1E"));
        }});
        pricePanel.add(new JLabel(period) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        }});
        card.add(pricePanel);

        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(new JLabel(installationFee) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextArea descArea = new JTextArea(description);
        descArea.setFont(FontUtil.getInterFont(16f));
        descArea.setForeground(Color.decode("#1E1E1E"));
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(descArea);

        card.add(Box.createVerticalGlue());
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(new JLabel("Required Upfront Fee:") {{
            setFont(FontUtil.getInterFont(16f).deriveFont(Font.BOLD));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        card.add(new JLabel(upfrontFee) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // ——— HERE: wire GET PLAN to SignUp1 ———
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        RoundedComponents.RoundedButton nextButton =
            new RoundedComponents.RoundedButton("GET PLAN", 25);
        nextButton.setPreferredSize(new Dimension(186, 39));
        nextButton.setBackground(Color.decode("#2A0243"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        nextButton.setBorderColor(Color.decode("#2A0243"));
        // on click → go to SignUp1
        nextButton.addActionListener(e -> {
            String appNo = UserApplicationData.get("ApplicationNo");
            if (appNo != null && !appNo.isEmpty()) {
                new AddPlansPage().setVisible(true);
            } else {
                new SignUp1().setVisible(true);
            }
            dispose();
        });
        buttonPanel.add(nextButton);
        card.add(buttonPanel);

        return card;
    }

    private static class CustomScrollBarUI extends BasicScrollBarUI {
        private static final Color THUMB_COLOR = new Color(42, 2, 67);

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = THUMB_COLOR;
            this.trackColor = new Color(0, 0, 0, 0);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (!scrollbar.isEnabled() || thumbBounds.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(THUMB_COLOR);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // No track painting
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlansPage::new);
    }
}