package com.group_9.project.database;

import java.sql.*;

public class AuthUtil {

    /**
     * Returns true if exactly one row matches the provided username/email AND plain‐text password.
     */
    public static boolean authenticate(String usernameOrEmail, String passwordPlain) {
        String sql =
            "SELECT COUNT(*) AS cnt " +
            "FROM tbl_customer " +
            "WHERE (username = ? OR email_add = ?) " +
            "  AND `password` = ?";

        System.out.println("→ [AuthUtil] authenticate() called with:");
        System.out.println("    usernameOrEmail = '" + usernameOrEmail + "'");
        System.out.println("    password        = '" + passwordPlain + "'");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ [AuthUtil] Connection is null or closed!");
                return false;
            }
            System.out.println("✔ [AuthUtil] Got DB connection");

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, usernameOrEmail);
                ps.setString(2, usernameOrEmail);
                ps.setString(3, passwordPlain);

                System.out.println("→ [AuthUtil] Executing SQL: " + ps);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("cnt");
                        System.out.println("→ [AuthUtil] Query returned count = " + count);
                        return count == 1;  // ✅ only success if exactly one match
                    } else {
                        System.out.println("→ [AuthUtil] ResultSet had no rows!");
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("✗ [AuthUtil] SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("→ [AuthUtil] Falling through, returning false");
        return false;
    }

    // Optional quick CLI smoke test
    public static void main(String[] args) {
        System.out.println("Test (correct creds): " +
            authenticate("testuser", "testpass"));  // adjust to your test data
        System.out.println("Test (wrong creds):   " +
            authenticate("testuser", "wrongpass"));
    }
}
