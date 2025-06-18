package com.group_9.project;

import com.group_9.project.database.DatabaseConnection;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddPlansPage extends JFrame {

    public AddPlansPage() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // 2) White rounded container
        JPanel container = FormUIUtil.createRoundedShadowPanel(235, 165, 970, 695);
        background.add(container);

        // 3) Inner content
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        inner.setBounds(40, 40, 890, 615);
        container.add(inner);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Title
        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setForeground(Color.decode("#2B0243"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(title);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Step tracker (step 1)
        JPanel steps = new JPanel(new FlowLayout(FlowLayout.CENTER));
        steps.setOpaque(false);
        steps.add(CreateStepTracker.createStepTracker(1));
        inner.add(steps);

        inner.add(Box.createRigidArea(new Dimension(0, 20)));

        // Subtitle + Note
        Color subColor = Color.decode("#302E2E");
        JLabel subtitle = new JLabel("CHOOSE YOUR PLAN", SwingConstants.LEFT);
        subtitle.setFont(FontUtil.getOutfitFont(16f));
        subtitle.setForeground(subColor);

        JLabel subNote = new JLabel(
            "Choose one or more plans to get started. You can also add more later."
        );
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

        // 4) Plan grid
        JPanel planGrid = new JPanel(new GridBagLayout());
        planGrid.setOpaque(false);
        planGrid.setMaximumSize(new Dimension(826, 350));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // build the panels
        ArrayList<SelectablePlanPanel> plans = new ArrayList<>();
        plans.add(new SelectablePlanPanel("P001", "FIBERX 1500",       "₱1500", "Installation Fee: ₱125/24mo."));
        plans.add(new SelectablePlanPanel("P002", "FIBER Xtream 4500", "₱4500", "Installation Fee: WAIVED"));
        plans.add(new SelectablePlanPanel("P003", "FIBERX 2500",       "₱2500", "Installation Fee: ₱125/24mo."));
        plans.add(new SelectablePlanPanel("P004", "FIBER Xtream 7000", "₱7000", "Installation Fee: WAIVED"));
        plans.add(new SelectablePlanPanel("P005", "FIBERX 3500",       "₱3500", "Installation Fee: ₱125/12mo."));

        // fetch which ones the user already has
        List<String> subscribedPlanIDs = fetchSubscribedPlanIDs(
            UserApplicationData.get("Username")
        );

        // lay them out, pre-select & disable those already subscribed
        for (int i = 0; i < plans.size(); i++) {
            SelectablePlanPanel p = plans.get(i);

            if (subscribedPlanIDs.contains(p.getPlanID())) {
                p.setDisabled(true);
            }

            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            planGrid.add(p, gbc);

            if (i == 4) {
                gbc.gridx = 1;
                JTextArea note = new JTextArea(
                    "*With outright Payment Option of Php 2,500 for\n" +
                    " Plans 1500 & 2500, and Php 1,250 for Plan 3500.\n" +
                    " Waived Installation Fee for Plans 4500 and 7000.\n" +
                    " *Prices are VAT Inclusive"
                );
                note.setFont(FontUtil.getOutfitFont(14f));
                note.setOpaque(false);
                note.setEditable(false);
                planGrid.add(note, gbc);
            }
        }

        gbc.gridx      = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        inner.add(planGrid);

        inner.add(Box.createRigidArea(new Dimension(0, 40)));

        // 5) NEXT button
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(826, 50));
        RoundedComponents.RoundedButton next = new RoundedComponents.RoundedButton("NEXT", 25);
        next.setPreferredSize(new Dimension(148, 41));
        next.setBackground(Color.decode("#2B0243"));
        next.setForeground(Color.WHITE);
        next.setFont(FontUtil.getOutfitBoldFont(16f));
        next.setBorderColor(Color.decode("#2B0243"));
        btnPanel.add(next, BorderLayout.EAST);
        inner.add(btnPanel);

        next.addActionListener(e -> {
            // only gather *new* selections (skip disabled ones)
            ArrayList<String> newPlanTitles = new ArrayList<>();
            ArrayList<String> newPlanIDs    = new ArrayList<>();
            for (SelectablePlanPanel panel : plans) {
                if (panel.isSelected() && !panel.isDisabled()) {
                    newPlanTitles.add(panel.getPlanTitle());
                    newPlanIDs.add(panel.getPlanID());
                }
            }

            if (newPlanIDs.isEmpty()) {
                CustomDialogUtil.showStyledErrorDialog(
                    AddPlansPage.this,
                    "No New Plan Selected",
                    "Please select at least one new plan to proceed."
                );
                return;
            }

            // re-check DB for conflicts (just in case)
            String username = UserApplicationData.get("Username");
            String sql = """
                SELECT s.service_plan
                  FROM tbl_payment p
                  JOIN tbl_application a ON p.application_no = a.application_no
                  JOIN tbl_customer  c ON a.customer_ID    = c.customer_ID
                  JOIN tbl_service   s ON p.plan_ID         = s.plan_ID
                 WHERE c.username = ?
                   AND p.plan_ID  = ?
            """;

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, username);
                for (String planId : newPlanIDs) {
                    ps.setString(2, planId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String servicePlanName = rs.getString("service_plan");
                            CustomDialogUtil.showStyledErrorDialog(
                                AddPlansPage.this,
                                "Plan Already Subscribed",
                                "You already have the \"" + servicePlanName + "\" plan."
                            );
                            return;
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                CustomDialogUtil.showStyledErrorDialog(
                    AddPlansPage.this,
                    "Database Error",
                    "Could not verify existing plans. Please try again."
                );
                return;
            }

            // save *only* the new ones
            UserApplicationData.set("selectedPlans",   String.join(",", newPlanTitles));
            UserApplicationData.set("selectedPlanIDs", String.join(",", newPlanIDs));

            new AddConfirm().setVisible(true);
            dispose();
        });

        setVisible(true);
        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    /** Helper to fetch all plan_IDs this user already has paid for. */
    private List<String> fetchSubscribedPlanIDs(String username) {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT p.plan_ID " +
            "  FROM tbl_payment p " +
            "  JOIN tbl_application a ON p.application_no = a.application_no " +
            "  JOIN tbl_customer  c ON a.customer_ID    = c.customer_ID " +
            " WHERE c.username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("plan_ID"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // optionally show a dialog here
        }
        return list;
    }

    /** The white rounded container background. */

    // --- inner class for selectable plan cards ---
    class SelectablePlanPanel extends JPanel {
        private boolean selected = false;
        private boolean disabled = false;

        private final Color borderDefault  = Color.LIGHT_GRAY;
        private final Color borderHover    = Color.GRAY;
        private final Color borderSelected = Color.decode("#7E4CA5");
        private final Color fillSelected   = Color.decode("#2B0243");
        private final Color fillDefault    = Color.WHITE;
        private final int   arc            = 12;

        private final String planID, planTitle, planPrice, planFee;
        private final JPanel box;

        public SelectablePlanPanel(String id, String title, String price, String fee) {
            this.planID    = id;
            this.planTitle = title;
            this.planPrice = price;
            this.planFee   = fee;

            setLayout(new BorderLayout(10,0));
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(370,75));
            setBorder(createBorder(borderDefault, 1));

            box = new JPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                    );
                    // if disabled, always show selected fill but greyed border
                    Color fill = fillDefault;
                    Color border = borderDefault;
                    if (selected)      fill = fillSelected;
                    if (disabled)      border = Color.DARK_GRAY;
                    else if (selected) border = borderSelected;

                    g2.setColor(fill);
                    g2.fillRect(0,0,getWidth(),getHeight());
                    g2.setColor(border);
                    g2.drawRect(0,0,getWidth()-1,getHeight()-1);
                }
            };
            box.setPreferredSize(new Dimension(50,50));
            box.setOpaque(false);

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setOpaque(false);
            wrapper.setPreferredSize(new Dimension(70,70));
            wrapper.add(box);
            add(wrapper, BorderLayout.WEST);

            JPanel content = new JPanel();
            content.setOpaque(false);
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setBorder(BorderFactory.createEmptyBorder(10,0,10,15));

            JPanel row = new JPanel();
            row.setOpaque(false);
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(FontUtil.getOutfitBoldFont(16f));
            row.add(lblTitle);
            row.add(Box.createHorizontalGlue());
            JLabel lblPrice = new JLabel(price);
            lblPrice.setFont(FontUtil.getInterFont(14f));
            row.add(lblPrice);
            content.add(row);

            content.add(Box.createVerticalStrut(5));
            JPanel feeRow = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            feeRow.setOpaque(false);
            JLabel lblFee = new JLabel(fee);
            lblFee.setFont(FontUtil.getInterFont(14f));
            feeRow.add(lblFee);
            content.add(feeRow);

            add(content, BorderLayout.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // only respond to hover/click if not disabled
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (disabled) return;
                    setBorder(createBorder(borderHover, 2));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (disabled) return;
                    setBorder(createBorder(
                        selected? borderSelected: borderDefault,
                        selected? 2:1
                    ));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (disabled) return;
                    selected = !selected;
                    setBorder(createBorder(
                        selected? borderSelected: borderDefault,
                        selected? 2:1
                    ));
                    repaint();
                }
            });
        }

        private Border createBorder(Color c, int thickness) {
            return new Border(){
                @Override
                public void paintBorder(Component cmp, Graphics g, int x, int y, int w, int h) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                    );
                    g2.setColor(c);
                    g2.setStroke(new BasicStroke(1.5f));
                    for(int i=0;i<thickness;i++){
                        g2.drawRoundRect(x+i,y+i,w-1-2*i,h-1-2*i,arc,arc);
                    }
                    g2.dispose();
                }
                @Override public Insets getBorderInsets(Component c){return new Insets(5,5,5,5);}
                @Override public boolean isBorderOpaque(){return false;}
            };
        }

        // getters & setters
        public boolean isSelected()       { return selected;   }
        public String  getPlanID()        { return planID;     }
        public String  getPlanTitle()     { return planTitle;  }
        public boolean isDisabled()       { return disabled;   }

        /** Mark this panel as already-subscribed: locks selection on, disallows clicks. */
        public void setDisabled(boolean d) {
            this.disabled = d;
            this.selected = true;
            setBorder(createBorder(d? Color.DARK_GRAY : borderSelected, d? 1 : 2));
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddPlansPage::new);
    }
}
