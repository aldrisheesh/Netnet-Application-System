package com.group_9.project;

import com.group_9.project.utils.*;
import com.group_9.project.database.LoginAuth;

import java.awt.*;

import javax.swing.*;

import com.group_9.project.session.UserApplicationData;
import com.group_9.project.database.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LoginPage extends JFrame {

    public LoginPage() {
        BaseFrameSetup.applyAppIcon(this);
        BaseFrameSetup.setupFrame(this);

        BackgroundPanel pnlBackground = BaseFrameSetup.createBackgroundPanel(1);
        setContentPane(pnlBackground);
        BaseFrameSetup.createLogo(pnlBackground);
        BaseFrameSetup.createNavigation(pnlBackground, this);

        JLabel lblHeadline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        lblHeadline.setFont(FontUtil.getOutfitFont(50f));
        lblHeadline.setForeground(new Color(0x2B0243));
        lblHeadline.setBounds(112, 220, 1200, 120);
        pnlBackground.add(lblHeadline);

        JLabel lblSubHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        lblSubHeadline.setFont(FontUtil.getInterFont(16f));
        lblSubHeadline.setBounds(420, 350, 600, 30);
        pnlBackground.add(lblSubHeadline);

        int intYPos = 435;

        JLabel lblLets = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:600;'>Let’s make things happen.</div></html>");
        lblLets.setFont(FontUtil.getOutfitFont(16f));
        lblLets.setBounds(562, intYPos, 300, 30);
        lblLets.setHorizontalAlignment(SwingConstants.CENTER);
        pnlBackground.add(lblLets);

        RoundedComponents.RoundedTextField txtEmailField = new RoundedComponents.RoundedTextField("Username or email", 20);
        txtEmailField.setFont(FontUtil.getInterFont(14f));
        txtEmailField.setBounds(524, intYPos + 40, 375, 60);
        pnlBackground.add(txtEmailField);

        RoundedComponents.RoundedPasswordField txtPassword = new RoundedComponents.RoundedPasswordField("Password", 20);
        txtPassword.setFont(FontUtil.getInterFont(14f));
        txtPassword.setBounds(524, intYPos + 117, 375, 60);
        pnlBackground.add(txtPassword);

        // ➕ Add validation
        ValidationUtil.addTextValidation(txtEmailField, s -> !s.trim().isEmpty());
        ValidationUtil.addTextValidation(txtPassword, s -> !s.trim().isEmpty());

        JButton cmdLogin = new RoundedComponents.RoundedButton("LOG IN", 20);
        cmdLogin.setFont(FontUtil.getOutfitFont(16f).deriveFont(Font.BOLD));
        cmdLogin.setBounds(525, intYPos + 195, 130, 40);
        cmdLogin.setFocusPainted(false);
        cmdLogin.setFocusable(false);
        ButtonHoverEffect.apply(
                cmdLogin,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        pnlBackground.add(cmdLogin);

        ToolTipUtil.attachCustomTooltip(txtEmailField, "Enter your username or email");
        ToolTipUtil.attachCustomTooltip(txtPassword, "Enter your password");

        // ⚠️ LOGIN VALIDATION LOGIC
        cmdLogin.addActionListener(e -> {
            String strUserId = txtEmailField.getText().trim();
            String strPwd    = new String(txtPassword.getPassword()).trim();

            // 1) Validate empty fields
            boolean valid = true;
            if (strUserId.isEmpty()) {
                txtEmailField.setValidationBorderColor(Color.RED);
                valid = false;
            } else {
                txtEmailField.setValidationBorderColor(Color.GRAY);
            }
            if (strPwd.isEmpty()) {
                txtPassword.setValidationBorderColor(Color.RED);
                valid = false;
            } else {
                txtPassword.setValidationBorderColor(Color.GRAY);
            }
            if (!valid) {
                CustomDialogUtil.showStyledErrorDialog(
                    this,
                    "Missing Fields",
                    "Please fill in all required fields before logging in."
                );
                return;
            }

            try {
                // 2) Check account existence
                if (!LoginAuth.userExists(strUserId)) {
                    CustomDialogUtil.showStyledErrorDialog(
                        this,
                        "Account Not Found",
                        "No account registered with that username or email."
                    );
                    return;
                }

                // 3) Validate password
                if (!LoginAuth.authenticate(strUserId, strPwd)) {
                    CustomDialogUtil.showStyledErrorDialog(
                        this,
                        "Invalid Password",
                        "The password you entered is incorrect. Please try again."
                    );
                    return;
                }

                String strActualUsername = strUserId;
                if (strUserId.contains("@")) {
                    // they logged in with email, fetch the username
                    String strSqlUser = """
                        SELECT username
                          FROM tbl_customer
                         WHERE email_add = ?
                        """;
                    try (Connection c = DatabaseConnection.getConnection();
                         PreparedStatement psUser = c.prepareStatement(strSqlUser)) {
                        psUser.setString(1, strUserId);
                        try (ResultSet rsUser = psUser.executeQuery()) {
                            if (rsUser.next()) {
                                strActualUsername = rsUser.getString("username");
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                UserApplicationData.set("strUsername", strActualUsername);

                // 4) Retrieve internal customer_ID
                String strCustId = LoginAuth.getCustomerId(strUserId);

                // 5) Fetch the latest application and store in session
                String strSql = """
                    SELECT application_no, application_date
                    FROM tbl_application
                    WHERE customer_ID = ?
                    ORDER BY application_date DESC
                    LIMIT 1
                    """;
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(strSql)) {

                    ps.setString(1, strCustId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String strAppNo = rs.getString("application_no");
                            Timestamp ts = rs.getTimestamp("application_date");
                            String strDate = new SimpleDateFormat("MM/dd/yyyy").format(ts);

                            UserApplicationData.set("strApplicationNo", strAppNo);
                            UserApplicationData.set("strApplicationDate", strDate);
                        } else {
                            UserApplicationData.set("strApplicationNo", "");
                            UserApplicationData.set("strApplicationDate", "");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    UserApplicationData.set("strApplicationNo", "");
                    UserApplicationData.set("strApplicationDate", "");
                }

                // 6) Success → open TrackingPage
                SwingUtilities.invokeLater(() -> {
                    new TrackingPage().setVisible(true);
                    dispose();
                });

            } catch (SQLException ex) {
                ex.printStackTrace();
                CustomDialogUtil.showStyledErrorDialog(
                    this,
                    "Database Error",
                    "An error occurred while connecting to the database."
                );
            }
        });
                

        JLabel lblForgot = new JLabel("<html><div style='color:#7E4CA5;font-weight:600;'>Forgotten your password?</div></html>");
        lblForgot.setFont(FontUtil.getOutfitFont(16f));
        lblForgot.setBounds(679, intYPos + 195 + 3, 200, 30);
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlBackground.add(lblForgot);

        JCheckBox chkKeepSignedIn = new JCheckBox("Keep me signed in");
        chkKeepSignedIn.setFont(FontUtil.getOutfitFont(16f));
        chkKeepSignedIn.setForeground(new Color(140, 140, 140));
        chkKeepSignedIn.setOpaque(false);
        chkKeepSignedIn.setFocusPainted(false);
        chkKeepSignedIn.setBorderPainted(false);
        chkKeepSignedIn.setContentAreaFilled(false);

        chkKeepSignedIn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_unchecked.png")));
        chkKeepSignedIn.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_checked.png")));

        chkKeepSignedIn.setBounds(523, intYPos + 195 + 45, 250, 40);
        pnlBackground.add(chkKeepSignedIn);

        SwingUtilities.invokeLater(() -> pnlBackground.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
