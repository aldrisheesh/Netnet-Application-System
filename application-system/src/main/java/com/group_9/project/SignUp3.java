package com.group_9.project;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class SignUp3 extends JFrame {

    public SignUp3() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel pnlBackground = BaseFrameSetup.setupCompleteFrame(this, 1);

        JPanel pnlContainer = new RoundedComponents.RoundedShadowPanel(25, 4);
        pnlContainer.setBounds(235, 165, 970, 695);
        pnlBackground.add(pnlContainer);

        JPanel pnlInnerContent = new JPanel();
        pnlInnerContent.setLayout(new BoxLayout(pnlInnerContent, BoxLayout.Y_AXIS));
        pnlInnerContent.setOpaque(false);
        pnlInnerContent.setBounds(40, 40, 890, 615);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color clrTitle = Color.decode("#2B0243");
        Color clrSub = Color.decode("#302E2E");

        JLabel lblTitle = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        lblTitle.setFont(FontUtil.getOutfitBoldFont(26f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(clrTitle);
        pnlInnerContent.add(lblTitle);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlStepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlStepWrapper.setOpaque(false);
        pnlStepWrapper.add(CreateStepTracker.createStepTracker(1));
        pnlInnerContent.add(pnlStepWrapper);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new BorderLayout());
        pnlInfo.setMaximumSize(new Dimension(826, 60));

        JPanel pnlLeftLabels = new JPanel();
        pnlLeftLabels.setLayout(new BoxLayout(pnlLeftLabels, BoxLayout.Y_AXIS));
        pnlLeftLabels.setOpaque(false);

        JLabel lblSubtitle = new JLabel("CHOOSE YOUR PLAN", SwingConstants.LEFT);
        lblSubtitle.setFont(FontUtil.getOutfitFont(16f));
        lblSubtitle.setForeground(clrSub);

        JLabel lblSubNote = new JLabel("Choose one or more plans to get started. You can also add more later.");
        lblSubNote.setFont(FontUtil.getInterFont(14f));
        lblSubNote.setForeground(clrSub);

        pnlLeftLabels.add(lblSubtitle);
        pnlLeftLabels.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlLeftLabels.add(lblSubNote);

        pnlInfo.add(pnlLeftLabels, BorderLayout.WEST);
        pnlInnerContent.add(pnlInfo);

        JSeparator sepHorizontal = new JSeparator(SwingConstants.HORIZONTAL);
        sepHorizontal.setMaximumSize(new Dimension(826, 2));
        sepHorizontal.setForeground(Color.decode("#B2B2B2"));
        sepHorizontal.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlInnerContent.add(sepHorizontal);
        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnlPlan = new JPanel(new GridBagLayout());
        pnlPlan.setOpaque(false);
        pnlPlan.setMaximumSize(new Dimension(826, 350));
        GridBagConstraints gbcLayout = new GridBagConstraints();
        gbcLayout.insets = new Insets(10, 20, 10, 20);
        gbcLayout.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<SelectablePlanPanel> lstPlanBoxes = new ArrayList<>();

        lstPlanBoxes.add(new SelectablePlanPanel("P001", "FIBERX 1500", "₱1500", "Installation Fee: ₱125/24mo."));
        lstPlanBoxes.add(new SelectablePlanPanel("P002", "FIBER Xtream 4500", "₱4500", "Installation Fee: WAIVED"));
        lstPlanBoxes.add(new SelectablePlanPanel("P003", "FIBERX 2500", "₱2500", "Installation Fee: ₱125/24mo."));
        lstPlanBoxes.add(new SelectablePlanPanel("P004", "FIBER Xtream 7000", "₱7000", "Installation Fee: WAIVED"));
        lstPlanBoxes.add(new SelectablePlanPanel("P005", "FIBERX 3500", "₱3500", "Installation Fee: ₱125/12mo."));

        for (int intIndex = 0; intIndex < lstPlanBoxes.size(); intIndex++) {
            gbcLayout.gridx = intIndex % 2;
            gbcLayout.gridy = intIndex / 2;
            pnlPlan.add(lstPlanBoxes.get(intIndex), gbcLayout);

            if (intIndex == 4) {
                gbcLayout.gridx = 1;
                JTextArea txtNote = new JTextArea(
                        "*With outright Payment Option of Php 2,500 for\n" +
                        "Plans 1500 & 2500, and Php 1,250 for Plan 3500.\n" +
                        "Waived Installation Fee for Plans 4500 and 7000.\n" +
                        "*Prices are VAT Inclusive"
                );
                txtNote.setFont(FontUtil.getOutfitFont(14f));
                txtNote.setEditable(false);
                txtNote.setOpaque(false);
                pnlPlan.add(txtNote, gbcLayout);
            }
        }

        String strSavedPlans = UserApplicationData.get("strSelectedPlans");
        String strSavedPlanIDs = UserApplicationData.get("strSelectedPlanIDs");
        
        if (!strSavedPlanIDs.isEmpty()) {
            String[] arrSelectedPlanIDs = strSavedPlanIDs.split(",");
            for (SelectablePlanPanel pnlPlanItem : lstPlanBoxes) {
                for (String strSelectedID : arrSelectedPlanIDs) {
                    if (pnlPlanItem.getPlanID().equalsIgnoreCase(strSelectedID.trim())) {
                        pnlPlanItem.setSelected(true);
                        break;
                    }
                }
            }
        }

        gbcLayout.gridx = 0;
        gbcLayout.gridy++;
        gbcLayout.gridwidth = 2;
        pnlInnerContent.add(pnlPlan);

        pnlInnerContent.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel pnlButton = new JPanel();
        pnlButton.setOpaque(false);
        pnlButton.setLayout(new BorderLayout());
        pnlButton.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton cmdNext = new RoundedComponents.RoundedButton("NEXT", 25);
        cmdNext.setPreferredSize(new Dimension(148, 41));
        cmdNext.setBackground(Color.decode("#2A0243"));
        cmdNext.setForeground(Color.WHITE);
        cmdNext.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdNext.setBorderColor(Color.decode("#2A0243"));

        RoundedComponents.RoundedButton cmdBack = new RoundedComponents.RoundedButton("BACK", 25);
        cmdBack.setPreferredSize(new Dimension(148, 41));
        cmdBack.setBackground(Color.decode("#FFF1FF"));
        cmdBack.setForeground(Color.decode("#2B0243"));
        cmdBack.setFont(FontUtil.getOutfitBoldFont(16f));
        cmdBack.setBorderColor(Color.decode("#2B0243"));

        pnlButton.add(cmdBack, BorderLayout.WEST);
        pnlButton.add(cmdNext, BorderLayout.EAST);

        pnlInnerContent.add(pnlButton);

        cmdNext.addActionListener(evtAction -> {
            ArrayList<String> lstSelectedPlans = new ArrayList<>();
            ArrayList<String> lstSelectedPlanIDs = new ArrayList<>();

            for (SelectablePlanPanel pnlPlanItem : lstPlanBoxes) {
                if (pnlPlanItem.isSelected()) {
                    lstSelectedPlans.add(pnlPlanItem.getPlanTitle());
                    lstSelectedPlanIDs.add(pnlPlanItem.getPlanID());
                }
            }

            if (lstSelectedPlans.isEmpty()) {
                CustomDialogUtil.showStyledErrorDialog(SignUp3.this, "No Plan Selected", "Please select at least one plan to proceed.");
            } else {
                String strJoinedPlans = String.join(",", lstSelectedPlans);
                String strJoinedPlanIDs = String.join(",", lstSelectedPlanIDs);
                
                UserApplicationData.set("strSelectedPlans", strJoinedPlans);
                UserApplicationData.set("strSelectedPlanIDs", strJoinedPlanIDs);
                
                new SignUp5();
                dispose();
            }
        });

        cmdBack.addActionListener(evtAction -> {
            ArrayList<String> lstSelectedPlans = new ArrayList<>();
            ArrayList<String> lstSelectedPlanIDs = new ArrayList<>();
        
            for (SelectablePlanPanel pnlPlanItem : lstPlanBoxes) {
                if (pnlPlanItem.isSelected()) {
                    lstSelectedPlans.add(pnlPlanItem.getPlanTitle());
                    lstSelectedPlanIDs.add(pnlPlanItem.getPlanID());
                }
            }
        
            String strJoinedPlans = String.join(",", lstSelectedPlans);
            String strJoinedPlanIDs = String.join(",", lstSelectedPlanIDs);
            
            UserApplicationData.set("strSelectedPlans", strJoinedPlans);
            UserApplicationData.set("strSelectedPlanIDs", strJoinedPlanIDs);
        
            new SignUp2();
            dispose();
        });        

        pnlContainer.add(pnlInnerContent);

        setVisible(true);
        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());
    }


    class SelectablePlanPanel extends JPanel {
        private boolean mBoolSelected = false;
        private final Color mClrBorderDefault = Color.LIGHT_GRAY;
        private final Color mClrBorderHover = Color.GRAY;
        private final Color mClrBorderSelected = Color.decode("#7E4CA5");
        private final Color mClrSquareSelected = Color.decode("#2B0243");
        private final Color mClrSquareUnselected = Color.WHITE;
        private final int mIntBorderRadius = 12;

        private final JPanel mPnlCheckbox;
        private final String mStrPlanID;
        private final String mStrPlanTitle;
        private final String mStrPlanPrice;
        private final String mStrPlanFee;

        public SelectablePlanPanel(String strPlanID, String strTitle, String strPrice, String strFee) {
            this.mStrPlanID = strPlanID;
            this.mStrPlanTitle = strTitle;
            this.mStrPlanPrice = strPrice;
            this.mStrPlanFee = strFee;
            
            setLayout(new BorderLayout(10, 0));
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(370, 75));
            setBorder(createRoundedBorder(mClrBorderDefault, 1));

            mPnlCheckbox = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(mBoolSelected ? mClrSquareSelected : mClrSquareUnselected);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.setColor(mClrBorderSelected);
                    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
            };
            mPnlCheckbox.setPreferredSize(new Dimension(50, 50));
            mPnlCheckbox.setOpaque(false);

            JPanel pnlCheckboxWrapper = new JPanel(new GridBagLayout());
            pnlCheckboxWrapper.setOpaque(false);
            pnlCheckboxWrapper.setPreferredSize(new Dimension(70, 70));
            pnlCheckboxWrapper.add(mPnlCheckbox);

            add(pnlCheckboxWrapper, BorderLayout.WEST);

            JPanel pnlContent = new JPanel();
            pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
            pnlContent.setOpaque(false);
            pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 15));

            JPanel pnlTitlePriceRow = new JPanel();
            pnlTitlePriceRow.setLayout(new BoxLayout(pnlTitlePriceRow, BoxLayout.X_AXIS));
            pnlTitlePriceRow.setOpaque(false);

            JLabel lblTitle = new JLabel(strTitle);
            lblTitle.setFont(FontUtil.getOutfitBoldFont(16f));
            pnlTitlePriceRow.add(lblTitle);
            pnlTitlePriceRow.add(Box.createRigidArea(new Dimension(70, 0)));
            pnlTitlePriceRow.add(Box.createHorizontalGlue());

            JLabel lblPrice = new JLabel(strPrice);
            lblPrice.setFont(FontUtil.getInterFont(14f));
            lblPrice.setForeground(Color.decode("#1E1E1E"));
            pnlTitlePriceRow.add(lblPrice);

            JPanel pnlFeeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pnlFeeRow.setOpaque(false);
            JLabel lblFee = new JLabel(strFee);
            lblFee.setFont(FontUtil.getInterFont(14f));
            lblFee.setForeground(Color.decode("#1E1E1E"));
            pnlFeeRow.add(lblFee);

            pnlContent.add(pnlTitlePriceRow);
            pnlContent.add(Box.createVerticalStrut(5));
            pnlContent.add(pnlFeeRow);

            add(pnlContent, BorderLayout.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evtMouse) {
                    setBorder(createRoundedBorder(mClrBorderHover, 2));
                }

                @Override
                public void mouseExited(MouseEvent evtMouse) {
                    setBorder(mBoolSelected ? createRoundedBorder(mClrBorderSelected, 2) : createRoundedBorder(mClrBorderDefault, 1));
                }

                @Override
                public void mouseClicked(MouseEvent evtMouse) {
                    mBoolSelected = !mBoolSelected;
                    setBorder(mBoolSelected ? createRoundedBorder(mClrBorderSelected, 2) : createRoundedBorder(mClrBorderDefault, 1));
                    repaint();
                }
            });
        }

        private Border createRoundedBorder(Color clrBorder, int intVisualThickness) {
            return new Border() {
                @Override
                public void paintBorder(Component ctlComponent, Graphics g, int intX, int intY, int intWidth, int intHeight) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(clrBorder);
                    g2d.setStroke(new BasicStroke(1.5f));
                    for (int intIndex = 0; intIndex < intVisualThickness; intIndex++) {
                        g2d.drawRoundRect(intX + intIndex, intY + intIndex, intWidth - 1 - 2 * intIndex, intHeight - 1 - 2 * intIndex, mIntBorderRadius, mIntBorderRadius);
                    }
                    g2d.dispose();
                }

                @Override
                public Insets getBorderInsets(Component ctlComponent) {
                    return new Insets(5, 5, 5, 5);
                }

                @Override
                public boolean isBorderOpaque() {
                    return false;
                }
            };
        }

        public boolean isSelected() {
            return mBoolSelected;
        }

        public void setSelected(boolean boolValue) {
            this.mBoolSelected = boolValue;
            setBorder(mBoolSelected ? createRoundedBorder(mClrBorderSelected, 2) : createRoundedBorder(mClrBorderDefault, 1));
            repaint();
        }

        public String getPlanID() {
            return mStrPlanID;
        }

        public String getPlanTitle() {
            return mStrPlanTitle;
        }

        public String getPlanPrice() {
            return mStrPlanPrice;
        }

        public String getPlanFee() {
            return mStrPlanFee;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp3::new);
    }
}
