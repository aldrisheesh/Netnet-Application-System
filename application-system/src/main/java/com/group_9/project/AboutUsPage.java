package com.group_9.project;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutUsPage extends Template {

    public AboutUsPage() {
        BaseFrameSetup.applyAppIcon(this);
        BaseFrameSetup.setupFrame(this);
        BackgroundPanel background = BaseFrameSetup.createBackgroundPanel(5);
        background.setPreferredSize(new Dimension(1440, 1354));
        JScrollPane scrollPane = new JScrollPane(background);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());
        verticalScrollBar.setOpaque(false);
        verticalScrollBar.setPreferredSize(new Dimension(10,0));
        setContentPane(scrollPane);
        BaseFrameSetup.createLogo(background);
        BaseFrameSetup.createNavigation(background, this);
        BaseFrameSetup.createLoginButton(background, this);

        JLabel header = new JLabel("About Us."); //header
        header.setFont(FontUtil.getOutfitBoldFont(50f));
        header.setForeground(new Color(43, 2, 67));
        Dimension textSize = header.getPreferredSize();
        header.setBounds((1440 - textSize.width) / 2, 130, textSize.width, textSize.height);
        background.add(header);

        JLabel title = new JLabel("<html><b>Made for People,<br>Built for Ease</html>"); //title
        title.setFont(FontUtil.getOutfitBoldFont(35f));
        title.setForeground(new Color(42, 2, 67));
        title.setBounds(150, 250, 400, 80);
        background.add(title);

        JLabel body = new JLabel("<html>Welcome to NETNET: Wi-Finally Yours!, where convenience meets care. We're<br>all about creating a smoother, smarter service journey for everyone. We are a<br>team of BSIT Sophomores from the Polytechnic University of the Philippines.</html>");
        body.setFont(FontUtil.getOutfitFont(17f));
        body.setForeground(new Color(30, 30, 30));
        body.setBounds(150, 340, 800, 100);
        background.add(body);

        JLabel teamIntro = new JLabel("MEET OUR TEAM"); //Group 9 members introduction
        teamIntro.setFont(FontUtil.getOutfitBoldFont(25f));
        teamIntro.setForeground(new Color(42, 2, 67));
        Dimension teamTextSize = teamIntro.getPreferredSize();
        teamIntro.setBounds((1440 - teamTextSize.width) / 2, 480, teamTextSize.width, teamTextSize.height);
        background.add(teamIntro);

        String[][] teamMembers = {
            {"images/Cabalin.jpg", "Cabalin, Hailey Jade P."},
            {"images/Estalilla.jpg", "Estalilla, Johanna Angela P."},
            {"images/Magpantay.jpg", "Magpantay, Reina Chloe D."},
            {"images/Santos.png", "Santos, Roi Aldrich S."},
            {"images/Ramiro.png", "Ramiro, Mika Ella T."}
        };

        int cardWidth = 350;
        int cardHeight = 350;
        int cardSpacing = 10;

        int startY = 520;
        int totalWidth2 = (2 * cardWidth) + cardSpacing;
        int startX2 = (1440 - totalWidth2) / 2;

        for (int i = 0; i < 2; i++) {
            String[] member = teamMembers[i];
            RoundedPanel panel = createTeamMemberPanel(
                member[0],
                member[1],
                startX2 + i * (cardWidth + cardSpacing),
                startY
            );
            background.add(panel);
        }

        int startY2 = startY + cardHeight + 50;
        int totalWidth3 = (3 * cardWidth) + (2 * cardSpacing);
        int startX3 = (1440 - totalWidth3) / 2;

        for (int i = 2; i < 5; i++) {
            String[] member = teamMembers[i];
            RoundedPanel panel = createTeamMemberPanel(
                member[0],
                member[1],
                startX3 + (i - 2) * (cardWidth + cardSpacing),
                startY2
            );
            background.add(panel);
        }

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    private RoundedPanel createTeamMemberPanel(String imagePath, String fullName, int x, int y) {
        RoundedPanel panel = new RoundedPanel(30);
        panel.setLayout(null);
        panel.setBounds(x, y, 320, 350);
        panel.setBackground(new Color(255, 241, 255));

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            CircularImagePanel imagePanel = new CircularImagePanel(scaledImage);
            imagePanel.setBounds(70, 50, 180, 180);
            panel.add(imagePanel);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("<html><center>IMAGE<br>PLACEHOLDER</center></html>", SwingConstants.CENTER);
            placeholder.setBounds(70, 50, 180, 180);
            placeholder.setFont(FontUtil.getOutfitFont(10f));
            placeholder.setForeground(new Color(150, 150, 150));
            placeholder.setOpaque(true);
            placeholder.setBackground(new Color(240, 240, 240));
            panel.add(placeholder);
        }

        String[] parts = fullName.split(",", 2); //split surname from full name
        String surname = parts[0].trim().toUpperCase();
        String givenNames = parts.length > 1 ? parts[1].trim() : "";

        JLabel surnameLabel = new JLabel(surname, SwingConstants.CENTER);
        surnameLabel.setFont(FontUtil.getOutfitBoldFont(20f));
        surnameLabel.setForeground(new Color(42, 2, 67));
        surnameLabel.setBounds(10, 260, 300, 25);

        JLabel givenNameLabel = new JLabel(givenNames, SwingConstants.CENTER);
        givenNameLabel.setFont(FontUtil.getOutfitFont(18f));
        givenNameLabel.setForeground(new Color(42, 2, 67));
        givenNameLabel.setBounds(10, 285, 300, 25);

        panel.add(surnameLabel);
        panel.add(givenNameLabel);

        return panel;
    }

    private static class CircularImagePanel extends JPanel { //make image circular
        private final Image image;
        private final int borderThickness = 1;
        private final Color borderColor = new Color(42, 2, 67);

        public CircularImagePanel(Image image) {
            this.image = image;
            setPreferredSize(new Dimension(180, 180));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int diameter = Math.min(getWidth(), getHeight());
                int inset = borderThickness;

                // Clip the image to a circle
                Shape clip = new Ellipse2D.Float(inset, inset, diameter - 2 * inset, diameter - 2 * inset);
                g2.setClip(clip);

                g2.drawImage(image, inset, inset, diameter - 2 * inset, diameter - 2 * inset, this);
                g2.setClip(null); // Remove clip

                // Draw circular border
                g2.setStroke(new BasicStroke(borderThickness));
                g2.setColor(borderColor);
                g2.drawOval(inset / 2, inset / 2, diameter - inset, diameter - inset);

                g2.dispose();
            }
        }
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
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutUsPage().setVisible(true));
    }
}
