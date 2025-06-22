package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import com.group_9.project.utils.CustomScrollBarUI;
import java.awt.*;

public class PlansPage extends JFrame {
    public PlansPage() {
        BaseFrameSetup.applyAppIcon(this);
        BaseFrameSetup.setupFrame(this);

        JPanel pnlMainContainer = new JPanel();
        pnlMainContainer.setLayout(new BoxLayout(pnlMainContainer, BoxLayout.Y_AXIS));

        BackgroundPanel pnlNavbar = new BackgroundPanel(4) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.decode("#FFF1FF"));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pnlNavbar.setLayout(null);
        pnlNavbar.setPreferredSize(new Dimension(1440, 80));
        pnlNavbar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        pnlNavbar.setMinimumSize(new Dimension(1440, 80));

        BaseFrameSetup.createLogo(pnlNavbar);
        BaseFrameSetup.createNavigation(pnlNavbar, this);
        BaseFrameSetup.createLoginButton(pnlNavbar, this);

        BackgroundPanel pnlContentBackground = BaseFrameSetup.createBackgroundPanel(2);
        pnlContentBackground.setLayout(new BoxLayout(pnlContentBackground, BoxLayout.Y_AXIS));
        pnlContentBackground.setPreferredSize(new Dimension(1440, 1100));
        pnlContentBackground.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel lblTitle = new JLabel("Power Your Experience Your Way");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(50f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.decode("#2B0243"));
        pnlContentBackground.add(lblTitle);
        pnlContentBackground.add(Box.createRigidArea(new Dimension(0, 16)));

        JLabel lblNote = new JLabel("Start with what you need, add more when you're ready.");
        lblNote.setFont(FontUtil.getInterFont(16f));
        lblNote.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNote.setForeground(Color.decode("#2B0243"));
        pnlContentBackground.add(lblNote);
        pnlContentBackground.add(Box.createRigidArea(new Dimension(0, 58)));

        createPlansSection(pnlContentBackground);

        JScrollPane scrContent = new JScrollPane(pnlContentBackground);
        scrContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrContent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrContent.getVerticalScrollBar().setUnitIncrement(16);
        scrContent.setBorder(null);

        JScrollBar sbVertical = scrContent.getVerticalScrollBar();
        sbVertical.setUI(new CustomScrollBarUI());
        sbVertical.setOpaque(false);
        sbVertical.setPreferredSize(new Dimension(10, 0));

        pnlMainContainer.add(pnlNavbar);
        pnlMainContainer.add(scrContent);

        setContentPane(pnlMainContainer);
        setVisible(true);
    }

    private void createPlansSection(BackgroundPanel pnlContentBackground) {
        JPanel pnlFirstRow = createPlansRow();
        pnlFirstRow.add(createPlanCard("FIBERX 1500", "P1500", "/month",
                "Installation Fee: P125/24mo.",
                "Seamless streaming with blazing fast, ready to surf the web at lightning speed with our 4tra boosted 300Mbps internet plan - fast, reliable, and pocket friendly.",
                "One Month Advance Payment"));
        pnlFirstRow.add(Box.createRigidArea(new Dimension(20, 0)));
        pnlFirstRow.add(createPlanCard("FIBERX 2500", "P2500", "/month",
                "Installation Fee: P125/24mo.",
                "Ultra-HD streaming and faster uploads. Enjoy uninterrupted video calls, 4K streaming, and file transfers with our 500Mbps boosted plan.",
                "One Month Advance Payment"));
        pnlFirstRow.add(Box.createRigidArea(new Dimension(20, 0)));
        pnlFirstRow.add(createPlanCard("FIBERX 3500", "P3500", "/month",
                "Installation Fee: P125/24mo.",
                "Power without limits. Our robust 700Mbps speed delivers seamless streaming, fast downloads, and reliable performance for work and play.",
                "One Month Advance Payment"));
        pnlContentBackground.add(pnlFirstRow);
        pnlContentBackground.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel pnlSecondRow = createPlansRow();
        pnlSecondRow.add(createPlanCard("FIBER Xtreme 4500", "P4500", "/month",
                "Installation Fee: WAIVED",
                "Unleash enterprise-level speed at home. Enjoy blazing 1Gbps internet with zero interruptions - ideal for smart homes and high-demand digital lifestyles.",
                "Two Months Advance Payment"));
        pnlSecondRow.add(Box.createRigidArea(new Dimension(20, 0)));
        pnlSecondRow.add(createPlanCard("FIBER Xtreme 7000", "P7000", "/month",
                "Installation Fee: WAIVED",
                "Unparalleled 2Gbps speed designed for high-tech homes and digital studios. Enjoy ultra-low latency, zero downtime, and massive bandwidth.",
                "Two Months Advance Payment"));
        pnlSecondRow.add(Box.createRigidArea(new Dimension(20, 0)));
        pnlSecondRow.add(Box.createRigidArea(new Dimension(375, 413)));

        pnlContentBackground.add(pnlSecondRow);
    }

    private JPanel createPlansRow() {
        JPanel pnlRow = new JPanel();
        pnlRow.setLayout(new BoxLayout(pnlRow, BoxLayout.X_AXIS));
        pnlRow.setOpaque(false);
        pnlRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlRow.setMaximumSize(new Dimension(1175, 413));
        pnlRow.add(Box.createHorizontalGlue());
        return pnlRow;
    }

    private JPanel createPlanCard(
            String strPlanName,
            String strPrice,
            String strPeriod,
            String strInstallationFee,
            String strDescription,
            String strUpfrontFee
    ) {
        JPanel pnlCard = new JPanel();
        pnlCard.setLayout(new BoxLayout(pnlCard, BoxLayout.Y_AXIS));
        pnlCard.setPreferredSize(new Dimension(375, 413));
        pnlCard.setOpaque(false);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
                new RoundedComponents.RoundedBorder(15),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblName = new JLabel(strPlanName);
        lblName.setFont(FontUtil.getOutfitBoldFont(18f));
        lblName.setForeground(Color.decode("#402F84"));
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCard.add(lblName);
        pnlCard.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pnlPrice = new JPanel();
        pnlPrice.setLayout(new BoxLayout(pnlPrice, BoxLayout.X_AXIS));
        pnlPrice.setOpaque(false);
        pnlPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlPrice.add(new JLabel(strPrice) {{
            setFont(FontUtil.getOutfitBoldFont(50f));
            setForeground(Color.decode("#1E1E1E"));
        }});
        pnlPrice.add(new JLabel(strPeriod) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        }});
        pnlCard.add(pnlPrice);

        pnlCard.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlCard.add(new JLabel(strInstallationFee) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        pnlCard.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextArea txtaDesc = new JTextArea(strDescription);
        txtaDesc.setFont(FontUtil.getInterFont(16f));
        txtaDesc.setForeground(Color.decode("#1E1E1E"));
        txtaDesc.setOpaque(false);
        txtaDesc.setEditable(false);
        txtaDesc.setLineWrap(true);
        txtaDesc.setWrapStyleWord(true);
        txtaDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCard.add(txtaDesc);

        pnlCard.add(Box.createVerticalGlue());
        pnlCard.add(Box.createRigidArea(new Dimension(0, 10)));

        pnlCard.add(new JLabel("Required Upfront Fee:") {{
            setFont(FontUtil.getInterFont(16f).deriveFont(Font.BOLD));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        pnlCard.add(new JLabel(strUpfrontFee) {{
            setFont(FontUtil.getInterFont(16f));
            setForeground(Color.decode("#1E1E1E"));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        pnlCard.add(Box.createRigidArea(new Dimension(0, 15)));

        // ——— HERE: wire GET PLAN to SignUp1 ———
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlButton.setOpaque(false);
        RoundedComponents.RoundedButton cmdNextButton =
            new RoundedComponents.RoundedButton("GET PLAN", 25);
        cmdNextButton.setPreferredSize(new Dimension(186, 39));
        cmdNextButton.setBackground(Color.decode("#2A0243"));
        cmdNextButton.setForeground(Color.WHITE);
        cmdNextButton.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdNextButton.setBorderColor(Color.decode("#2A0243"));
        // on click → go to SignUp1
        cmdNextButton.addActionListener(e -> {
            String appNo = UserApplicationData.get("strApplicationNo");
            if (appNo != null && !appNo.isEmpty()) {
                new AddPlansPage().setVisible(true);
            } else {
                new SignUp1().setVisible(true);
            }
            dispose();
        });
        pnlButton.add(cmdNextButton);
        pnlCard.add(pnlButton);

        return pnlCard;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlansPage::new);
    }
}