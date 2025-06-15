package com.group_9.project.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads a customer's profile and their residence in one shot,
 * either by username/email or by application number.
 * Also provides methods to update customer info and fetch plan details.
 */
public class AccountService {

    // ───────────────────────────────────────────────────────────────
    // CUSTOMER PROFILE
    // ───────────────────────────────────────────────────────────────

    public static class CustomerProfile {
        public final String username, password, fullName, birthdate, gender;
        public final String civilStatus, motherMn, spouseName, nationality;
        public final String contactNo, emailAdd;
        public final String residenceType, compPaid;
        public final int residenceYrs;
        public final String ownerName, ownerContact, residenceAdd;

        public CustomerProfile(String username,
                               String password,
                               String fullName,
                               String birthdate,
                               String gender,
                               String civilStatus,
                               String motherMn,
                               String spouseName,
                               String nationality,
                               String contactNo,
                               String emailAdd,
                               String residenceType,
                               int residenceYrs,
                               String compPaid,
                               String ownerName,
                               String ownerContact,
                               String residenceAdd) {
            this.username       = username;
            this.password       = password;
            this.fullName       = fullName;
            this.birthdate      = birthdate;
            this.gender         = gender;
            this.civilStatus    = civilStatus;
            this.motherMn       = motherMn;
            this.spouseName     = spouseName;
            this.nationality    = nationality;
            this.contactNo      = contactNo;
            this.emailAdd       = emailAdd;
            this.residenceType  = residenceType;
            this.residenceYrs   = residenceYrs;
            this.compPaid       = compPaid;
            this.ownerName      = ownerName;
            this.ownerContact   = ownerContact;
            this.residenceAdd   = residenceAdd;
        }
    }

    /**
     * Fetches the full customer + residence info for a given login ID (username or email).
     */
    public static CustomerProfile getCustomerInfoByLogin(String loginId) throws SQLException {
        String sql = """
            SELECT c.username,
                   c.password,
                   c.customer_name,
                   DATE_FORMAT(c.birthdate, '%m/%d/%Y') AS birthdate,
                   c.gender,
                   c.civil_status,
                   c.mother_mn,
                   c.spouse_name,
                   c.nationality,
                   c.contact_no,
                   c.email_add,
                   c.residence_type,
                   c.residence_yrs,
                   c.comp_paid,
                   r.owner_name,
                   r.owner_contact,
                   r.residence_add
              FROM tbl_customer c
              JOIN tbl_residence r
                ON c.residence_ID = r.residence_ID
             WHERE c.username  = ?
                OR c.email_add = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loginId);
            ps.setString(2, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new CustomerProfile(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("customer_name"),
                    rs.getString("birthdate"),
                    rs.getString("gender"),
                    rs.getString("civil_status"),
                    rs.getString("mother_mn"),
                    rs.getString("spouse_name"),
                    rs.getString("nationality"),
                    rs.getString("contact_no"),
                    rs.getString("email_add"),
                    rs.getString("residence_type"),
                    rs.getInt   ("residence_yrs"),
                    rs.getString("comp_paid"),
                    rs.getString("owner_name"),
                    rs.getString("owner_contact"),
                    rs.getString("residence_add")
                );
            }
        }
    }

    /**
     * Fetches customer + residence info based on an application number.
     */
    public static CustomerProfile getCustomerInfoByApplication(String applicationNo) throws SQLException {
        String sql = """
            SELECT c.username,
                   c.password,
                   c.customer_name,
                   DATE_FORMAT(c.birthdate, '%m/%d/%Y') AS birthdate,
                   c.gender,
                   c.civil_status,
                   c.mother_mn,
                   c.spouse_name,
                   c.nationality,
                   c.contact_no,
                   c.email_add,
                   c.residence_type,
                   c.residence_yrs,
                   c.comp_paid,
                   r.owner_name,
                   r.owner_contact,
                   r.residence_add
              FROM tbl_application a
              JOIN tbl_customer  c
                ON a.customer_ID  = c.customer_ID
              JOIN tbl_residence r
                ON c.residence_ID = r.residence_ID
             WHERE a.application_no = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, applicationNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new CustomerProfile(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("customer_name"),
                    rs.getString("birthdate"),
                    rs.getString("gender"),
                    rs.getString("civil_status"),
                    rs.getString("mother_mn"),
                    rs.getString("spouse_name"),
                    rs.getString("nationality"),
                    rs.getString("contact_no"),
                    rs.getString("email_add"),
                    rs.getString("residence_type"),
                    rs.getInt   ("residence_yrs"),
                    rs.getString("comp_paid"),
                    rs.getString("owner_name"),
                    rs.getString("owner_contact"),
                    rs.getString("residence_add")
                );
            }
        }
    }

    // ───────────────────────────────────────────────────────────────
    // UPDATE CUSTOMER INFO
    // ───────────────────────────────────────────────────────────────

    /**
     * Persists updated customer fields back to tbl_customer (keyed by username).
     */
    public static void updateCustomerInfoByUsername(
            String   originalUsername,
            String   newPassword,
            String   newName,
            Date     newBirthdate,   // java.sql.Date
            String   newGender,
            String   newCivilStatus,
            String   newMotherMn,
            String   newSpouseName,
            String   newNationality,
            String   newContactNo,
            String   newEmailAdd
    ) throws SQLException {
        String sql = """
            UPDATE tbl_customer
               SET password      = ?,
                   customer_name = ?,
                   birthdate     = ?,
                   gender        = ?,
                   civil_status  = ?,
                   mother_mn     = ?,
                   spouse_name   = ?,
                   nationality   = ?,
                   contact_no    = ?,
                   email_add     = ?
             WHERE username = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, newName);
            ps.setDate(   3, newBirthdate);
            ps.setString(4, newGender);
            ps.setString(5, newCivilStatus);
            ps.setString(6, newMotherMn);
            ps.setString(7, newSpouseName);
            ps.setString(8, newNationality);
            ps.setString(9, newContactNo);
            ps.setString(10, newEmailAdd);
            ps.setString(11, originalUsername);

            ps.executeUpdate();
        }
    }

    // ───────────────────────────────────────────────────────────────
    // PLAN DETAILS
    // ───────────────────────────────────────────────────────────────

    /** Simple DTO for a service plan. */
    public static class PlanDetails {
        public final String planId, servicePlan, installFee;
        public final double serviceFee;

        public PlanDetails(String planId, String servicePlan, double serviceFee, String installFee) {
            this.planId       = planId;
            this.servicePlan  = servicePlan;
            this.serviceFee   = serviceFee;
            this.installFee   = installFee;
        }
    }

    /**
     * Fetches the plan details (service_plan, service_fee, install_fee)
     * for the given application number.
     */
    public static PlanDetails getPlanDetailsByApplication(String applicationNo) throws SQLException {
        String sql = """
            SELECT s.plan_ID,
                   s.service_plan,
                   s.service_fee,
                   s.install_fee
              FROM tbl_application a
              JOIN tbl_payment   p ON a.application_no = p.application_no
              JOIN tbl_service   s ON p.plan_ID        = s.plan_ID
             WHERE a.application_no = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, applicationNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new PlanDetails(
                    rs.getString("plan_ID"),
                    rs.getString("service_plan"),
                    rs.getDouble("service_fee"),
                    rs.getString("install_fee")
                );
            }
        }
    }

    public static class Subscription {
        public final String applicationNo;
        public final String dateSubmitted;
        public final String status;
        public final PlanDetails planDetails;

        public Subscription(String applicationNo,
                            String dateSubmitted,
                            String status,
                            PlanDetails planDetails) {
            this.applicationNo = applicationNo;
            this.dateSubmitted = dateSubmitted;
            this.status        = status;
            this.planDetails   = planDetails;
        }
    }

    /**
     * Fetches all subscriptions (applications + plan details) for a given username.
     */
    public static List<Subscription> getSubscriptionsByUsername(String username) throws SQLException {
        String sql = """
            SELECT a.application_no,
                   DATE_FORMAT(a.application_date, '%m/%d/%Y') AS date_submitted,
                   p.payment_option      AS status,
                   s.plan_ID,
                   s.service_plan,
                   s.service_fee,
                   s.install_fee
              FROM tbl_customer  c
              JOIN tbl_application a ON c.customer_ID     = a.customer_ID
              JOIN tbl_payment     p ON a.application_no  = p.application_no
              JOIN tbl_service     s ON p.plan_ID         = s.plan_ID
             WHERE c.username = ?
            """;

        List<Subscription> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PlanDetails pd = new PlanDetails(
                        rs.getString("plan_ID"),
                        rs.getString("service_plan"),
                        rs.getDouble("service_fee"),
                        rs.getString("install_fee")
                    );
                    out.add(new Subscription(
                        rs.getString("application_no"),
                        rs.getString("date_submitted"),
                        rs.getString("status"),
                        pd
                    ));
                }
            }
        }
        return out;
    }

        /** Simple DTO for your applications (one row per application). */
        public static class ApplicationInfo {
            public final String applicationNo;
            public final String dateSubmitted;
            public final String status;
    
            public ApplicationInfo(String applicationNo, String dateSubmitted, String status) {
                this.applicationNo = applicationNo;
                this.dateSubmitted = dateSubmitted;
                this.status        = status;
            }
        }
    
        /**
         * Fetches all application rows (one per application_no) for a given username.
         */
        public static List<ApplicationInfo> getApplicationsByUsername(String username) throws SQLException {
            String sql = """
                SELECT a.application_no,
                       DATE_FORMAT(a.application_date, '%m/%d/%Y') AS date_submitted,
                       p.payment_option AS status
                  FROM tbl_customer c
                  JOIN tbl_application a ON c.customer_ID = a.customer_ID
                  JOIN tbl_payment     p ON a.application_no = p.application_no
                 WHERE c.username = ?
                 ORDER BY a.application_date DESC
                """;
    
            List<ApplicationInfo> out = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
    
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        out.add(new ApplicationInfo(
                            rs.getString("application_no"),
                            rs.getString("date_submitted"),
                            rs.getString("status")
                        ));
                    }
                }
            }
            return out;
        }    
}
