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
    public static boolean userExists(String strId) throws SQLException {
        String strSql = "SELECT 1 FROM tbl_customer WHERE username = ? OR email_add = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psStmt = conn.prepareStatement(strSql)) {
            psStmt.setString(1, strId);
            psStmt.setString(2, strId);
            return psStmt.executeQuery().next();
        }
    }

    /**
     * Checks password for that username/email.
     */
    public static boolean authenticate(String strId, String strPwd) throws SQLException {
        String strSql = """
            SELECT 1
              FROM tbl_customer
             WHERE (username = ? OR email_add = ?)
               AND password = ?
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psStmt = conn.prepareStatement(strSql)) {
            psStmt.setString(1, strId);
            psStmt.setString(2, strId);
            psStmt.setString(3, strPwd);
            return psStmt.executeQuery().next();
        }
    }

    /**
     * **NEW**  
     * Fetches the internal customer_ID for a given username/email.
     * Call this *after* authenticate() succeeds.
     */
    public static String getCustomerId(String strId) throws SQLException {
        String strSql = "SELECT customer_ID FROM tbl_customer WHERE username = ? OR email_add = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psStmt = conn.prepareStatement(strSql)) {
            psStmt.setString(1, strId);
            psStmt.setString(2, strId);
            try (ResultSet rs = psStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("customer_ID");
                } else {
                    return null;
                }
            }
        }
    }
}
