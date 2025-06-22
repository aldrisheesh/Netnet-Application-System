package com.group_9.project;

import com.group_9.project.utils.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class AboutUsPage extends Template {

    public AboutUsPage() {
        // Frame setup
        BaseFrameSetup.applyAppIcon(this);
        BaseFrameSetup.setupFrame(this);
        
        // Background and scrolling
        BackgroundPanel bgPanel = BaseFrameSetup.createBackgroundPanel(5);
        bgPanel.setPreferredSize(new Dimension(1440, 1354));
        JScrollPane scrPane = new JScrollPane(bgPanel);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrPane.getVerticalScrollBar().setUnitIncrement(16);
        scrPane.setBorder(null);
        
        // Custom scrollbar
        JScrollBar vrtScrollBar = scrPane.getVerticalScrollBar();
        vrtScrollBar.setUI(new CustomScrollBarUI());
        vrtScrollBar.setOpaque(false);
        vrtScrollBar.setPreferredSize(new Dimension(10, 0));
        setContentPane(scrPane);
        
        // Header components
        BaseFrameSetup.createLogo(bgPanel);
        BaseFrameSetup.createNavigation(bgPanel, this);
        BaseFrameSetup.createLoginButton(bgPanel, this);

        // About Us header
        JLabel lblHeader = new JLabel("About Us.");
        lblHeader.setFont(FontUtil.getOutfitBoldFont(50f));
        lblHeader.setForeground(new Color(43, 2, 67));
        Dimension dimTextSize = lblHeader.getPreferredSize();
        lblHeader.setBounds((1440 - dimTextSize.width) / 2, 130, dimTextSize.width, dimTextSize.height);
        bgPanel.add(lblHeader);

        // Title section
        JLabel lblTitle = new JLabel("<html><b>Made for People,<br>Built for Ease</html>");
        lblTitle.setFont(FontUtil.getOutfitBoldFont(35f));
        lblTitle.setForeground(new Color(42, 2, 67));
        lblTitle.setBounds(150, 250, 400, 80);
        bgPanel.add(lblTitle);

        // Body text
        JLabel lblBody = new JLabel("<html>Welcome to NETNET: Wi-Finally Yours!, where convenience meets care. We're<br>all about creating a smoother, smarter service journey for everyone. We are a<br>team of BSIT Sophomores from the Polytechnic University of the Philippines.</html>");
        lblBody.setFont(FontUtil.getOutfitFont(17f));
        lblBody.setForeground(new Color(30, 30, 30));
        lblBody.setBounds(150, 340, 800, 100);
        bgPanel.add(lblBody);

        // Team introduction
        JLabel lblTeamIntro = new JLabel("MEET OUR TEAM");
        lblTeamIntro.setFont(FontUtil.getOutfitBoldFont(25f));
        lblTeamIntro.setForeground(new Color(42, 2, 67));
        Dimension dimTeamTextSize = lblTeamIntro.getPreferredSize();
        lblTeamIntro.setBounds((1440 - dimTeamTextSize.width) / 2, 480, dimTeamTextSize.width, dimTeamTextSize.height);
        bgPanel.add(lblTeamIntro);

        // Team member data
        String[][] arrTeamMembers = {
            {"images/Cabalin.jpg", "Cabalin, Hailey Jade P."},
            {"images/Estalilla.jpg", "Estalilla, Johanna Angela P."},
            {"images/Magpantay.jpg", "Magpantay, Reina Chloe D."},
            {"images/Santos.png", "Santos, Roi Aldrich S."},
            {"images/Ramiro.png", "Ramiro, Mika Ella T."}
        };

        createTeamMemberCards(bgPanel, arrTeamMembers);
        SwingUtilities.invokeLater(() -> bgPanel.requestFocusInWindow());
    }

    private void createTeamMemberCards(BackgroundPanel bgPanel, String[][] arrTeamMembers) {
        final int intCardWidth = 350;
        final int intCardHeight = 350;
        final int intCardSpacing = 10;

        // First row (2 members)
        int intStartY = 520;
        int intTotalWidth2 = (2 * intCardWidth) + intCardSpacing;
        int intStartX2 = (1440 - intTotalWidth2) / 2;

        for (int i = 0; i < 2; i++) {
            RoundedPanel pnlMember = createTeamMemberPanel(
                arrTeamMembers[i][0],
                arrTeamMembers[i][1],
                intStartX2 + i * (intCardWidth + intCardSpacing),
                intStartY
            );
            bgPanel.add(pnlMember);
        }

        // Second row (3 members)
        int intStartY2 = intStartY + intCardHeight + 50;
        int intTotalWidth3 = (3 * intCardWidth) + (2 * intCardSpacing);
        int intStartX3 = (1440 - intTotalWidth3) / 2;

        for (int i = 2; i < 5; i++) {
            RoundedPanel pnlMember = createTeamMemberPanel(
                arrTeamMembers[i][0],
                arrTeamMembers[i][1],
                intStartX3 + (i - 2) * (intCardWidth + intCardSpacing),
                intStartY2
            );
            bgPanel.add(pnlMember);
        }
    }

    private RoundedPanel createTeamMemberPanel(String strImagePath, String strFullName, int x, int y) {
        RoundedPanel pnlMember = new RoundedPanel(30);
        pnlMember.setLayout(null);
        pnlMember.setBounds(x, y, 320, 350);
        pnlMember.setBackground(new Color(255, 241, 255));

        try {
            ImageIcon imgIcon = new ImageIcon(getClass().getClassLoader().getResource(strImagePath));
            Image imgScaled = imgIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            CircularImagePanel pnlImage = new CircularImagePanel(imgScaled);
            pnlImage.setBounds(70, 50, 180, 180);
            pnlMember.add(pnlImage);
        } catch (Exception e) {
            JLabel lblPlaceholder = createImagePlaceholder();
            pnlMember.add(lblPlaceholder);
        }

        addNameLabels(pnlMember, strFullName);
        return pnlMember;
    }

    private JLabel createImagePlaceholder() {
        JLabel lblPlaceholder = new JLabel("<html><center>IMAGE<br>PLACEHOLDER</center></html>", SwingConstants.CENTER);
        lblPlaceholder.setBounds(70, 50, 180, 180);
        lblPlaceholder.setFont(FontUtil.getOutfitFont(10f));
        lblPlaceholder.setForeground(new Color(150, 150, 150));
        lblPlaceholder.setOpaque(true);
        lblPlaceholder.setBackground(new Color(240, 240, 240));
        return lblPlaceholder;
    }

    private void addNameLabels(RoundedPanel pnlMember, String strFullName) {
        String[] arrParts = strFullName.split(",", 2);
        String strSurname = arrParts[0].trim().toUpperCase();
        String strGivenNames = arrParts.length > 1 ? arrParts[1].trim() : "";

        JLabel lblSurname = new JLabel(strSurname, SwingConstants.CENTER);
        lblSurname.setFont(FontUtil.getOutfitBoldFont(20f));
        lblSurname.setForeground(new Color(42, 2, 67));
        lblSurname.setBounds(10, 260, 300, 25);

        JLabel lblGivenNames = new JLabel(strGivenNames, SwingConstants.CENTER);
        lblGivenNames.setFont(FontUtil.getOutfitFont(18f));
        lblGivenNames.setForeground(new Color(42, 2, 67));
        lblGivenNames.setBounds(10, 285, 300, 25);

        pnlMember.add(lblSurname);
        pnlMember.add(lblGivenNames);
    }

    private static class CircularImagePanel extends JPanel {
        private final Image imgMember;
        private final int intBorderThickness = 1;
        private final Color clrBorder = new Color(42, 2, 67);

        public CircularImagePanel(Image image) {
            this.imgMember = image;
            setPreferredSize(new Dimension(180, 180));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imgMember != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int intDiameter = Math.min(getWidth(), getHeight());
                int intInset = intBorderThickness;

                Shape shpClip = new Ellipse2D.Float(intInset, intInset, 
                    intDiameter - 2 * intInset, intDiameter - 2 * intInset);
                g2.setClip(shpClip);
                g2.drawImage(imgMember, intInset, intInset, 
                    intDiameter - 2 * intInset, intDiameter - 2 * intInset, this);
                g2.setClip(null);

                g2.setStroke(new BasicStroke(intBorderThickness));
                g2.setColor(clrBorder);
                g2.drawOval(intInset / 2, intInset / 2, 
                    intDiameter - intInset, intDiameter - intInset);

                g2.dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutUsPage().setVisible(true));
    }
}