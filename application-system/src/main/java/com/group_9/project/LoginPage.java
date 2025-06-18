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

        BackgroundPanel background = BaseFrameSetup.createBackgroundPanel(1);
        setContentPane(background);
        BaseFrameSetup.createLogo(background);
        BaseFrameSetup.createNavigation(background, this);

        JLabel headline = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:700;'>Supercharge your home with<br>ultra-fast internet and endless entertainment.</div></html>", SwingConstants.CENTER);
        headline.setFont(FontUtil.getOutfitFont(50f));
        headline.setForeground(new Color(0x2B0243));
        headline.setBounds(112, 220, 1200, 120);
        background.add(headline);

        JLabel subHeadline = new JLabel("Enjoy faster speed, and incredible value with our plans.", SwingConstants.CENTER);
        subHeadline.setFont(FontUtil.getInterFont(16f));
        subHeadline.setBounds(420, 350, 600, 30);
        background.add(subHeadline);

        int yPosi = 435;

        JLabel letsLabel = new JLabel("<html><div style='text-align:center;color:#2B0243;font-weight:600;'>Let’s make things happen.</div></html>");
        letsLabel.setFont(FontUtil.getOutfitFont(16f));
        letsLabel.setBounds(562, yPosi, 300, 30);
        letsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(letsLabel);

        RoundedComponents.RoundedTextField emailField = new RoundedComponents.RoundedTextField("Username or email", 20);
        emailField.setFont(FontUtil.getInterFont(14f));
        emailField.setBounds(524, yPosi + 40, 375, 60);
        background.add(emailField);

        RoundedComponents.RoundedPasswordField passwordField = new RoundedComponents.RoundedPasswordField("Password", 20);
        passwordField.setFont(FontUtil.getInterFont(14f));
        passwordField.setBounds(524, yPosi + 117, 375, 60);
        background.add(passwordField);

        // ➕ Add validation
        ValidationUtil.addTextValidation(emailField, s -> !s.trim().isEmpty());
        ValidationUtil.addTextValidation(passwordField, s -> !s.trim().isEmpty());

        JButton loginBtn = new RoundedComponents.RoundedButton("LOG IN", 20);
        loginBtn.setFont(FontUtil.getOutfitFont(16f));
        loginBtn.setBounds(525, yPosi + 195, 130, 40);
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        ButtonHoverEffect.apply(
                loginBtn,
                new Color(62, 10, 118),
                Color.WHITE,
                new Color(42, 2, 67),
                Color.WHITE,
                new Color(62, 10, 118),
                new Color(42, 2, 67)
        );
        background.add(loginBtn);

        ToolTipUtil.attachCustomTooltip(emailField, "Enter your username or email");
        ToolTipUtil.attachCustomTooltip(passwordField, "Enter your password");

        // ⚠️ LOGIN VALIDATION LOGIC
        loginBtn.addActionListener(e -> {
            String userId = emailField.getText().trim();
            String pwd    = new String(passwordField.getPassword()).trim();

            // 1) Validate empty fields
            boolean valid = true;
            if (userId.isEmpty()) {
                emailField.setValidationBorderColor(Color.RED);
                valid = false;
            } else {
                emailField.setValidationBorderColor(Color.GRAY);
            }
            if (pwd.isEmpty()) {
                passwordField.setValidationBorderColor(Color.RED);
                valid = false;
            } else {
                passwordField.setValidationBorderColor(Color.GRAY);
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
                if (!LoginAuth.userExists(userId)) {
                    CustomDialogUtil.showStyledErrorDialog(
                        this,
                        "Account Not Found",
                        "No account registered with that username or email."
                    );
                    return;
                }

                // 3) Validate password
                if (!LoginAuth.authenticate(userId, pwd)) {
                    CustomDialogUtil.showStyledErrorDialog(
                        this,
                        "Invalid Password",
                        "The password you entered is incorrect. Please try again."
                    );
                    return;
                }

                String actualUsername = userId;
                if (userId.contains("@")) {
                    // they logged in with email, fetch the username
                    String sqlUser = """
                        SELECT username
                          FROM tbl_customer
                         WHERE email_add = ?
                        """;
                    try (Connection c = DatabaseConnection.getConnection();
                         PreparedStatement psUser = c.prepareStatement(sqlUser)) {
                        psUser.setString(1, userId);
                        try (ResultSet rsUser = psUser.executeQuery()) {
                            if (rsUser.next()) {
                                actualUsername = rsUser.getString("username");
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                UserApplicationData.set("Username", actualUsername);

                // 4) Retrieve internal customer_ID
                String custId = LoginAuth.getCustomerId(userId);

                // 5) Fetch the latest application and store in session
                String sql = """
                    SELECT application_no, application_date
                    FROM tbl_application
                    WHERE customer_ID = ?
                    ORDER BY application_date DESC
                    LIMIT 1
                    """;
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, custId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String appNo = rs.getString("application_no");
                            Timestamp ts = rs.getTimestamp("application_date");
                            String dateStr = new SimpleDateFormat("MM/dd/yyyy").format(ts);

                            UserApplicationData.set("ApplicationNo", appNo);
                            UserApplicationData.set("ApplicationDate", dateStr);
                        } else {
                            UserApplicationData.set("ApplicationNo", "");
                            UserApplicationData.set("ApplicationDate", "");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    UserApplicationData.set("ApplicationNo", "");
                    UserApplicationData.set("ApplicationDate", "");
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
                

        JLabel forgotLabel = new JLabel("<html><div style='color:#7E4CA5;font-weight:600;'>Forgotten your password?</div></html>");
        forgotLabel.setFont(FontUtil.getOutfitFont(16f));
        forgotLabel.setBounds(679, yPosi + 195 + 3, 200, 30);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        background.add(forgotLabel);

        JCheckBox keepSignedIn = new JCheckBox("Keep me signed in");
        keepSignedIn.setFont(FontUtil.getOutfitFont(16f));
        keepSignedIn.setForeground(new Color(140, 140, 140));
        keepSignedIn.setOpaque(false);
        keepSignedIn.setFocusPainted(false);
        keepSignedIn.setBorderPainted(false);
        keepSignedIn.setContentAreaFilled(false);

        keepSignedIn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_unchecked.png")));
        keepSignedIn.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/checkbox_checked.png")));

        keepSignedIn.setBounds(523, yPosi + 195 + 45, 250, 40);
        background.add(keepSignedIn);

        SwingUtilities.invokeLater(() -> background.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
