package com.group_9.project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles login + customer lookups.
 */
public final class LoginAuth {
    private LoginAuth() { }

    /**
     * Does a username/email exist?
     */
    public static boolean userExists(String id) throws SQLException {
        String sql = "SELECT 1 FROM tbl_customer WHERE username = ? OR email_add = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, id);
            return ps.executeQuery().next();
        }
    }

    /**
     * Checks password for that username/email.
     */
    public static boolean authenticate(String id, String pwd) throws SQLException {
        String sql = """
            SELECT 1
              FROM tbl_customer
             WHERE (username = ? OR email_add = ?)
               AND password = ?
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, id);
            ps.setString(3, pwd);
            return ps.executeQuery().next();
        }
    }

    /**
     * **NEW**  
     * Fetches the internal customer_ID for a given username/email.
     * Call this *after* authenticate() succeeds.
     */
    public static String getCustomerId(String id) throws SQLException {
        String sql = "SELECT customer_ID FROM tbl_customer WHERE username = ? OR email_add = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("customer_ID");
                } else {
                    return null;
                }
            }
        }
    }
}
