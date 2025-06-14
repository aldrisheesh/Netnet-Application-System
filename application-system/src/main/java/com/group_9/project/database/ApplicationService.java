package com.group_9.project.database;

import com.group_9.project.session.UserApplicationData;
import java.sql.*;
import java.time.LocalDateTime;

public class ApplicationService {

    public boolean processApplication() {
        System.out.println("\n=== STARTING APPLICATION PROCESSING ===");
        
        Connection conn = null;
        try {
            // test database connection first
            System.out.println("Testing database connection...");
            if (!DatabaseConnection.testConnection()) {
                System.err.println("Database connection test failed!");
                return false;
            }

            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return false;
            }

            System.out.println("Database connection established successfully");
            conn.setAutoCommit(false); // Start transaction
            System.out.println("Transaction started (autoCommit = false)");

            // print all data before validation
            UserApplicationData.printAllData();

            // validate all required data before any database operations
            System.out.println("Validating application data...");
            if (!validateApplicationData()) {
                System.err.println("Application validation failed - rolling back");
                return false;
            }
            System.out.println("✓ Application data validation passed");

            // generate IDs
            System.out.println("Generating IDs...");
            String residenceId = generateResidenceId(conn);
            String customerId = generateCustomerId(conn);
            String applicationNo = generateApplicationNo(conn);
            
            System.out.println("Generated IDs:");
            System.out.println("  Residence ID: " + residenceId);
            System.out.println("  Customer ID: " + customerId);
            System.out.println("  Application No: " + applicationNo);

            System.out.println("Inserting data into database...");
            
            System.out.println("1. Inserting residence data...");
            insertResidence(conn, residenceId);
            
            System.out.println("2. Inserting customer data...");
            insertCustomer(conn, customerId, residenceId);
            
            System.out.println("3. Inserting application data...");
            insertApplication(conn, applicationNo, customerId);
            
            System.out.println("4. Inserting payment data...");
            insertPayment(conn, applicationNo);

            System.out.println("All data inserted successfully, committing transaction...");
            conn.commit();
            
            System.out.println("✓ Transaction committed successfully!");
            System.out.println("✓ Application processed successfully. Application No: " + applicationNo);
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred:");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            
            try {
                if (conn != null) {
                    System.out.println("Rolling back transaction...");
                    conn.rollback();
                    System.out.println("✓ Transaction rolled back successfully");
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            
            e.printStackTrace();
            return false;
            
        } catch (Exception e) {
            System.err.println("Unexpected exception occurred:");
            System.err.println("Message: " + e.getMessage());
            
            try {
                if (conn != null) {
                    System.out.println("Rolling back transaction due to unexpected error...");
                    conn.rollback();
                    System.out.println("✓ Transaction rolled back successfully");
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); 
                    conn.close();
                    System.out.println("Database connection closed");
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // validate that all required data is present before database operations
    private boolean validateApplicationData() {
        System.out.println("=== VALIDATION DETAILS ===");
        
        boolean customerValid = UserApplicationData.hasRequiredCustomerData();
        boolean residenceValid = UserApplicationData.hasRequiredResidenceData();
        boolean planValid = UserApplicationData.hasSelectedPlan();
        
        System.out.println("Validation Results:");
        System.out.println("  Customer data valid: " + customerValid);
        System.out.println("  Residence data valid: " + residenceValid);
        System.out.println("  Plan selected: " + planValid);
        
        boolean overallValid = customerValid && residenceValid && planValid;
        System.out.println("  Overall validation: " + overallValid);
        
        if (!overallValid) {
            System.err.println("VALIDATION FAILED!");
        }
        
        return overallValid;
    }

    private String generateResidenceId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(residence_ID, 2) AS UNSIGNED)) as max_id FROM tbl_residence";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int nextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                nextId = rs.getInt("max_id") + 1;
            }
            String id = String.format("R%05d", nextId);
            System.out.println("Generated residence ID: " + id);
            return id;
        }
    }

    private String generateCustomerId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(customer_ID, 2) AS UNSIGNED)) as max_id FROM tbl_customer";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int nextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                nextId = rs.getInt("max_id") + 1;
            }
            String id = String.format("C%05d", nextId);
            System.out.println("Generated customer ID: " + id);
            return id;
        }
    }

    private String generateApplicationNo(Connection conn) throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(application_no, 2) AS UNSIGNED)) as max_id FROM tbl_application";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int nextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                nextId = rs.getInt("max_id") + 1;
            }
            String id = String.format("A%05d", nextId);
            System.out.println("Generated application no: " + id);
            return id;
        }
    }

    private void insertResidence(Connection conn, String residenceId) throws SQLException {
        String sql = "INSERT INTO tbl_residence (residence_ID, owner_name, owner_contact, residence_add) VALUES (?, ?, ?, ?)";
        
        System.out.println("Executing residence insert with values:");
        System.out.println("  residence_ID: " + residenceId);
        System.out.println("  owner_name: '" + UserApplicationData.get("NameOfOwner") + "'");
        System.out.println("  owner_contact: '" + UserApplicationData.get("ContactNumber") + "'");
        System.out.println("  residence_add: '" + UserApplicationData.get("ResidenceAddress") + "'");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, residenceId);
            stmt.setString(2, UserApplicationData.get("NameOfOwner"));
            stmt.setString(3, UserApplicationData.get("ContactNumber"));
            stmt.setString(4, UserApplicationData.get("ResidenceAddress"));
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("✓ Residence inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert residence - no rows affected");
            }
        }
    }

    private void insertCustomer(Connection conn, String customerId, String residenceId) throws SQLException {
        String sql = "INSERT INTO tbl_customer (customer_ID, username, password, customer_name, birthdate, gender, " +
                     "civil_status, mother_mn, spouse_name, nationality, contact_no, email_add, " +
                     "residence_ID, residence_type, residence_yrs, comp_paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("Executing customer insert with values:");
        System.out.println("  customer_ID: " + customerId);
        System.out.println("  username: '" + UserApplicationData.get("Username") + "'");
        System.out.println("  password: '" + UserApplicationData.get("Password") + "'");
        System.out.println("  customer_name: '" + UserApplicationData.get("CustomerName") + "'");
        System.out.println("  birthdate: '" + UserApplicationData.get("Birthday") + "'");
        System.out.println("  gender: '" + UserApplicationData.get("Gender") + "'");
        System.out.println("  civil_status: '" + UserApplicationData.get("CivilStatus") + "'");
        System.out.println("  mother_mn: '" + UserApplicationData.get("MaidenName") + "'");
        System.out.println("  spouse_name: '" + UserApplicationData.get("Spouse") + "'");
        System.out.println("  nationality: '" + UserApplicationData.get("Nationality") + "'");
        System.out.println("  contact_no: '" + UserApplicationData.get("Mobile") + "'");
        System.out.println("  email_add: '" + UserApplicationData.get("Email") + "'");
        System.out.println("  residence_ID: " + residenceId);
        System.out.println("  residence_type: '" + UserApplicationData.get("HomeOwnership") + "'");
        System.out.println("  residence_yrs: '" + UserApplicationData.get("YearsOfResidency") + "'");
        System.out.println("  comp_paid: '" + UserApplicationData.get("CompanyPaid") + "'");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            stmt.setString(2, UserApplicationData.get("Username"));
            stmt.setString(3, UserApplicationData.get("Password"));
            stmt.setString(4, UserApplicationData.get("CustomerName"));
            
            // Handle birthdate - convert from MM/dd/yyyy to yyyy-MM-dd format
            String birthdate = UserApplicationData.get("Birthday");
            try {
                // Assuming input format is MM/dd/yyyy (like "07/11/2005")
                String[] parts = birthdate.split("/");
                if (parts.length == 3) {
                    String formattedDate = parts[2] + "-" + String.format("%02d", Integer.parseInt(parts[0])) + "-" + String.format("%02d", Integer.parseInt(parts[1]));
                    stmt.setDate(5, Date.valueOf(formattedDate));
                } else {
                    // If already in yyyy-MM-dd format
                    stmt.setDate(5, Date.valueOf(birthdate));
                }
            } catch (Exception e) {
                throw new SQLException("Invalid birthdate format: " + birthdate + ". Expected format: MM/dd/yyyy or yyyy-MM-dd");
            }
            
            stmt.setString(6, UserApplicationData.get("Gender"));
            stmt.setString(7, UserApplicationData.get("CivilStatus"));
            stmt.setString(8, UserApplicationData.get("MaidenName"));

            String spouseName = UserApplicationData.get("Spouse");
            if (spouseName == null || spouseName.trim().isEmpty()) {
                stmt.setNull(9, Types.VARCHAR);
            } else {
                stmt.setString(9, spouseName);
            }

            stmt.setString(10, UserApplicationData.get("Nationality"));
            stmt.setString(11, UserApplicationData.get("Mobile"));
            stmt.setString(12, UserApplicationData.get("Email"));
            stmt.setString(13, residenceId);
            stmt.setString(14, UserApplicationData.get("HomeOwnership"));
            
            try {
                int residenceYrs = Integer.parseInt(UserApplicationData.get("YearsOfResidency"));
                stmt.setInt(15, residenceYrs);
            } catch (NumberFormatException e) {
                throw new SQLException("Invalid residence years: " + UserApplicationData.get("YearsOfResidency"));
            }
            
            stmt.setString(16, UserApplicationData.get("CompanyPaid"));
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("✓ Customer inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert customer - no rows affected");
            }
        }
    }

    private void insertApplication(Connection conn, String applicationNo, String customerId) throws SQLException {
        String sql = "INSERT INTO tbl_application (application_no, application_date, customer_ID) VALUES (?, ?, ?)";
        
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Executing application insert with values:");
        System.out.println("  application_no: " + applicationNo);
        System.out.println("  application_date: " + now);
        System.out.println("  customer_ID: " + customerId);
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, applicationNo);
            stmt.setTimestamp(2, Timestamp.valueOf(now));
            stmt.setString(3, customerId);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("✓ Application inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert application - no rows affected");
            }
        }
    }

    private void insertPayment(Connection conn, String applicationNo) throws SQLException {
        String sql = "INSERT INTO tbl_payment (application_no, plan_ID, payment_option) VALUES (?, ?, ?)";
        
        // Get the selected plan IDs (comma-separated string)
        String selectedPlanIDs = UserApplicationData.get("selectedPlanIDs");
        String paymentOption = UserApplicationData.get("paymentOption");
        
        System.out.println("Executing payment insert with values:");
        System.out.println("  application_no: " + applicationNo);
        System.out.println("  plan_IDs: '" + selectedPlanIDs + "'");
        System.out.println("  payment_option: '" + paymentOption + "'");
        
        if (selectedPlanIDs == null || selectedPlanIDs.trim().isEmpty()) {
            throw new SQLException("No plan IDs selected for payment");
        }
        
        // split the plan IDs by comma and insert each one separately
        String[] planIds = selectedPlanIDs.split(",");
        int totalRowsAffected = 0;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String planId : planIds) {
                planId = planId.trim(); // Remove any whitespace
                if (!planId.isEmpty()) {
                    System.out.println("  Inserting payment record for plan_ID: '" + planId + "'");
                    
                    stmt.setString(1, applicationNo);
                    stmt.setString(2, planId);
                    stmt.setString(3, paymentOption);
                    
                    int rowsAffected = stmt.executeUpdate();
                    totalRowsAffected += rowsAffected;
                    
                    if (rowsAffected == 0) {
                        throw new SQLException("Failed to insert payment for plan ID: " + planId + " - no rows affected");
                    }
                    
                    System.out.println("    ✓ Payment record inserted for plan " + planId + ": " + rowsAffected + " row(s)");
                }
            }
        }
        
        System.out.println("✓ All payment records inserted: " + totalRowsAffected + " total row(s) for " + planIds.length + " plan(s)");
        
        if (totalRowsAffected == 0) {
            throw new SQLException("Failed to insert any payment records - no rows affected");
        }
    }

    /**
     * Get available service plans for display in the UI
     */
    public ResultSet getServicePlans() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Cannot get database connection");
        }
        
        String sql = "SELECT * FROM tbl_service ORDER BY service_fee";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }

    // db struct verification
    public void testDatabaseStructure() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Cannot connect to database for structure test");
                return;
            }

            System.out.println("=== TESTING DATABASE STRUCTURE ===");
            
            // Test each table
            String[] tables = {"tbl_residence", "tbl_customer", "tbl_application", "tbl_payment", "tbl_service"};
            
            for (String table : tables) {
                try {
                    String sql = "SELECT COUNT(*) FROM " + table;
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("✓ Table " + table + " exists (rows: " + rs.getInt(1) + ")");
                    }
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("✗ Table " + table + " - Error: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error testing database structure: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}