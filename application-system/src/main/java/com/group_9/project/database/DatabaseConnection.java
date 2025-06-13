package com.group_9.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection objConn;

    /**
     * Get database connection with proper error handling
     */
    public static Connection getConnection() {
        String strDriver = "com.mysql.cj.jdbc.Driver";  // MySQL
        String strJDBC = "jdbc:mysql://";               // driver for MySQL
        String strHost = "localhost";                   // hostname
        String strPort = "3306";                        // port number
        String strDb = "fiberxpress";                   // database 
        String strConn = strJDBC + strHost + ":" + strPort + "/" + strDb + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String strUser = "root";
        String strPass = "";

        try {
            // Load the driver
            Class.forName(strDriver);
            
            System.out.println("Attempting to connect to database: " + strDb);
            
            // Initialize and return the connection
            Connection conn = DriverManager.getConnection(strConn, strUser, strPass);
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection established successfully");
                return conn;
            } else {
                System.out.println("Failed to establish database connection");
                return null;
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Exception during connection:");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error during connection:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Test database connection
     */
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error testing connection: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing test connection: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        if (testConnection()) {
            System.out.println("✓ Database connection test successful!");
        } else {
            System.out.println("✗ Database connection test failed!");
        }
    }
}