package com.group_9.project.database;

import com.group_9.project.session.UserApplicationData;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ApplicationService {

    public boolean processApplication() {
        System.out.println("\n=== STARTING APPLICATION PROCESSING ===");
        
        Connection connDb = null;
        try {
            // test database connection first
            System.out.println("Testing database connection...");
            if (!DatabaseConnection.testConnection()) {
                System.err.println("Database connection test failed!");
                return false;
            }

            connDb = DatabaseConnection.getConnection();
            if (connDb == null) {
                System.err.println("Failed to get database connection");
                return false;
            }

            System.out.println("Database connection established successfully");
            connDb.setAutoCommit(false); // Start transaction
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
            String strResidenceId = generateResidenceId(connDb);
            String strCustomerId = generateCustomerId(connDb);
            String strApplicationNo = generateApplicationNo(connDb);
            
            System.out.println("Generated IDs:");
            System.out.println("  Residence ID: " + strResidenceId);
            System.out.println("  Customer ID: " + strCustomerId);
            System.out.println("  Application No: " + strApplicationNo);

            System.out.println("Inserting data into database...");
            
            System.out.println("1. Inserting residence data...");
            insertResidence(connDb, strResidenceId);
            
            System.out.println("2. Inserting customer data...");
            insertCustomer(connDb, strCustomerId, strResidenceId);
            
            System.out.println("3. Inserting application data...");
            insertApplication(connDb, strApplicationNo, strCustomerId);
            
            System.out.println("4. Inserting payment data...");
            insertPayment(connDb, strApplicationNo);

            System.out.println("All data inserted successfully, committing transaction...");
            connDb.commit();
            
            System.out.println("✓ Transaction committed successfully!");
            System.out.println("✓ Application processed successfully. Application No: " + strApplicationNo);
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred:");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            
            try {
                if (connDb != null) {
                    System.out.println("Rolling back transaction...");
                    connDb.rollback();
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
                if (connDb != null) {
                    System.out.println("Rolling back transaction due to unexpected error...");
                    connDb.rollback();
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
                if (connDb != null) {
                    connDb.setAutoCommit(true);
                    connDb.close();
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

    private String generateResidenceId(Connection connDb) throws SQLException {
        String strSql = "SELECT MAX(CAST(SUBSTRING(residence_ID, 2) AS UNSIGNED)) as max_id FROM tbl_residence";
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql);
             ResultSet rs = psStmt.executeQuery()) {
            int intNextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                intNextId = rs.getInt("max_id") + 1;
            }
            String strId = String.format("R%05d", intNextId);
            System.out.println("Generated residence ID: " + strId);
            return strId;
        }
    }

    private String generateCustomerId(Connection connDb) throws SQLException {
        String strSql = "SELECT MAX(CAST(SUBSTRING(customer_ID, 2) AS UNSIGNED)) as max_id FROM tbl_customer";
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql);
             ResultSet rs = psStmt.executeQuery()) {
            int intNextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                intNextId = rs.getInt("max_id") + 1;
            }
            String strId = String.format("C%05d", intNextId);
            System.out.println("Generated customer ID: " + strId);
            return strId;
        }
    }

    private String generateApplicationNo(Connection connDb) throws SQLException {
        String strSql = "SELECT MAX(CAST(SUBSTRING(application_no, 2) AS UNSIGNED)) as max_id FROM tbl_application";
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql);
             ResultSet rs = psStmt.executeQuery()) {
            int intNextId = 1;
            if (rs.next() && rs.getObject("max_id") != null) {
                intNextId = rs.getInt("max_id") + 1;
            }
            String strId = String.format("A%05d", intNextId);
            System.out.println("Generated application no: " + strId);
            return strId;
        }
    }

    private void insertResidence(Connection connDb, String strResidenceId) throws SQLException {
        String strSql = "INSERT INTO tbl_residence (residence_ID, owner_name, owner_contact, residence_add) VALUES (?, ?, ?, ?)";
        
        System.out.println("Executing residence insert with values:");
        System.out.println("  residence_ID: " + strResidenceId);
        System.out.println("  owner_name: '" + UserApplicationData.get("NameOfOwner") + "'");
        System.out.println("  owner_contact: '" + UserApplicationData.get("ContactNumber") + "'");
        System.out.println("  residence_add: '" + UserApplicationData.get("ResidenceAddress") + "'");
        
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql)) {
            psStmt.setString(1, strResidenceId);
            psStmt.setString(2, UserApplicationData.get("NameOfOwner"));
            psStmt.setString(3, UserApplicationData.get("ContactNumber"));
            psStmt.setString(4, UserApplicationData.get("ResidenceAddress"));
            
            int rowsAffected = psStmt.executeUpdate();
            System.out.println("✓ Residence inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert residence - no rows affected");
            }
        }
    }

    private void insertCustomer(Connection connDb, String strCustomerId, String strResidenceId) throws SQLException {
        String strSql = "INSERT INTO tbl_customer (customer_ID, username, password, customer_name, birthdate, gender, " +
                     "civil_status, mother_mn, spouse_name, nationality, contact_no, email_add, " +
                     "residence_ID, residence_type, residence_yrs, comp_paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("Executing customer insert with values:");
        System.out.println("  customer_ID: " + strCustomerId);
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
        System.out.println("  residence_ID: " + strResidenceId);
        System.out.println("  residence_type: '" + UserApplicationData.get("HomeOwnership") + "'");
        System.out.println("  residence_yrs: '" + UserApplicationData.get("YearsOfResidency") + "'");
        System.out.println("  comp_paid: '" + UserApplicationData.get("CompanyPaid") + "'");
        
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql)) {
            psStmt.setString(1, strCustomerId);
            psStmt.setString(2, UserApplicationData.get("Username"));
            psStmt.setString(3, UserApplicationData.get("Password"));
            psStmt.setString(4, UserApplicationData.get("CustomerName"));
            
            // Handle birthdate - convert from MM/dd/yyyy to yyyy-MM-dd format
            String birthdate = UserApplicationData.get("Birthday");
            try {
                // Assuming input format is MM/dd/yyyy (like "07/11/2005")
                String[] parts = birthdate.split("/");
                if (parts.length == 3) {
                    String formattedDate = parts[2] + "-" + String.format("%02d", Integer.parseInt(parts[0])) + "-" + String.format("%02d", Integer.parseInt(parts[1]));
                    psStmt.setDate(5, Date.valueOf(formattedDate));
                } else {
                    // If already in yyyy-MM-dd format
                    psStmt.setDate(5, Date.valueOf(birthdate));
                }
            } catch (Exception e) {
                throw new SQLException("Invalid birthdate format: " + birthdate + ". Expected format: MM/dd/yyyy or yyyy-MM-dd");
            }
            
            psStmt.setString(6, UserApplicationData.get("Gender"));
            psStmt.setString(7, UserApplicationData.get("CivilStatus"));
            psStmt.setString(8, UserApplicationData.get("MaidenName"));

            String spouseName = UserApplicationData.get("Spouse");
            if (spouseName == null || spouseName.trim().isEmpty()) {
                psStmt.setNull(9, Types.VARCHAR);
            } else {
                psStmt.setString(9, spouseName);
            }

            psStmt.setString(10, UserApplicationData.get("Nationality"));
            psStmt.setString(11, UserApplicationData.get("Mobile"));
            psStmt.setString(12, UserApplicationData.get("Email"));
            psStmt.setString(13, strResidenceId);
            psStmt.setString(14, UserApplicationData.get("HomeOwnership"));
            
            try {
                int intResidenceYrs = Integer.parseInt(UserApplicationData.get("YearsOfResidency"));
                psStmt.setInt(15, intResidenceYrs);
            } catch (NumberFormatException e) {
                throw new SQLException("Invalid residence years: " + UserApplicationData.get("YearsOfResidency"));
            }
            
            psStmt.setString(16, UserApplicationData.get("CompanyPaid"));
            
            int rowsAffected = psStmt.executeUpdate();
            System.out.println("✓ Customer inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert customer - no rows affected");
            }
        }
    }

    private void insertApplication(Connection connDb, String strApplicationNo, String strCustomerId) throws SQLException {
        String strSql = "INSERT INTO tbl_application (application_no, application_date, customer_ID) VALUES (?, ?, ?)";
        
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Executing application insert with values:");
        System.out.println("  application_no: " + strApplicationNo);
        System.out.println("  application_date: " + now);
        System.out.println("  customer_ID: " + strCustomerId);
        
        try (PreparedStatement psStmt = connDb.prepareStatement(strSql)) {
            psStmt.setString(1, strApplicationNo);
            psStmt.setTimestamp(2, Timestamp.valueOf(now));
            psStmt.setString(3, strCustomerId);
            
            int rowsAffected = psStmt.executeUpdate();
            System.out.println("✓ Application inserted: " + rowsAffected + " row(s)");
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert application - no rows affected");
            }
        }
    }

    private void insertPayment(Connection connDb, String strApplicationNo) throws SQLException {
        String strSql = "INSERT INTO tbl_payment (application_no, plan_ID, payment_option) VALUES (?, ?, ?)";

        // Get the selected plan IDs (comma-separated string)
        String strSelectedPlanIDs = UserApplicationData.get("selectedPlanIDs");
        String strPaymentOption = UserApplicationData.get("paymentOption");

        System.out.println("Executing payment insert with values:");
        System.out.println("  application_no: " + strApplicationNo);
        System.out.println("  plan_IDs: '" + strSelectedPlanIDs + "'");
        System.out.println("  payment_option: '" + strPaymentOption + "'");

        if (strSelectedPlanIDs == null || strSelectedPlanIDs.trim().isEmpty()) {
            throw new SQLException("No plan IDs selected for payment");
        }

        // split the plan IDs by comma and insert each one separately
        String[] arrPlanIds = strSelectedPlanIDs.split(",");
        int intTotalRowsAffected = 0;

        try (PreparedStatement psStmt = connDb.prepareStatement(strSql)) {
            for (String strPlanId : arrPlanIds) {
                strPlanId = strPlanId.trim();
                if (!strPlanId.isEmpty()) {
                    System.out.println("  Inserting payment record for plan_ID:'" + strPlanId + "'");

                    psStmt.setString(1, strApplicationNo);
                    psStmt.setString(2, strPlanId);
                    psStmt.setString(3, strPaymentOption);

                    int rowsAffected = psStmt.executeUpdate();
                    intTotalRowsAffected += rowsAffected;

                    if (rowsAffected == 0) {
                        throw new SQLException("Failed to insert payment for plan ID: " + strPlanId + " - no rows affected");
                    }

                    System.out.println("    ✓ Payment record inserted for plan " + strPlanId + ": " + rowsAffected + " row(s)");
                }
            }
        }

        System.out.println("✓ All payment records inserted: " + intTotalRowsAffected + " total row(s) for " + arrPlanIds.length + " plan(s)");

        if (intTotalRowsAffected == 0) {
            throw new SQLException("Failed to insert any payment records - no rows affected");
        }
    }

    /**
     * Get available service plans for display in the UI
     */
    public ResultSet getServicePlans() throws SQLException {
        Connection connDb = DatabaseConnection.getConnection();
        if (connDb == null) {
            throw new SQLException("Cannot get database connection");
        }

        String strSql = "SELECT * FROM tbl_service ORDER BY service_fee";
        PreparedStatement psStmt = connDb.prepareStatement(strSql);
        return psStmt.executeQuery();
    }

    // db struct verification
    public void testDatabaseStructure() {
        Connection connDb = null;
        try {
            connDb = DatabaseConnection.getConnection();
            if (connDb == null) {
                System.err.println("Cannot connect to database for structure test");
                return;
            }

            System.out.println("=== TESTING DATABASE STRUCTURE ===");
            
            // Test each table
            String[] arrTables = {"tbl_residence", "tbl_customer", "tbl_application", "tbl_payment", "tbl_service"};

            for (String table : arrTables) {
                try {
                    String strSql = "SELECT COUNT(*) FROM " + table;
                    PreparedStatement psStmt = connDb.prepareStatement(strSql);
                    ResultSet rs = psStmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("✓ Table " + table + " exists (rows: " + rs.getInt(1) + ")");
                    }
                    psStmt.close();
                } catch (SQLException e) {
                    System.err.println("✗ Table " + table + " - Error: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error testing database structure: " + e.getMessage());
        } finally {
            if (connDb != null) {
                try {
                    connDb.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public static class ApplicationInfo {
        public final String applicationNo;
        public final String applicationDate; // formatted M/d/yyyy

        public ApplicationInfo(String no, String date) {
            this.applicationNo   = no;
            this.applicationDate = date;
        }
    }

    /**
     * Fetches the most recent application for the given username.
     * Returns null if the user or any application row is not found.
     */
    public static ApplicationInfo getLatestApplicationFor(String strUsername) {
        String strFindCustomer = "SELECT customer_ID FROM tbl_customer WHERE username = ?";
        String strFindApp      = "SELECT application_no, application_date"
                            + "  FROM tbl_application"
                            + " WHERE customer_ID = ?"
                            + " ORDER BY application_date DESC"
                            + " LIMIT 1";

        try (Connection connDb = DatabaseConnection.getConnection();
             PreparedStatement psCust = connDb.prepareStatement(strFindCustomer)) {

            psCust.setString(1, strUsername);
            try (ResultSet rsCust = psCust.executeQuery()) {
                if (!rsCust.next()) {
                    return null;
                }
                String strCustId = rsCust.getString("customer_ID");

                try (PreparedStatement psApp = connDb.prepareStatement(strFindApp)) {
                    psApp.setString(1, strCustId);
                    try (ResultSet rsApp = psApp.executeQuery()) {
                        if (rsApp.next()) {
                            String no = rsApp.getString("application_no");
                            Timestamp ts = rsApp.getTimestamp("application_date");
                            String date = LocalDateTime
                                .ofInstant(ts.toInstant(), ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));
                            return new ApplicationInfo(no, date);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}