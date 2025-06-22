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
        public final String strUsername;
        public final String strPassword;
        public final String strFullName;
        public final String strBirthdate;
        public final String strGender;
        public final String strCivilStatus;
        public final String strMotherMn;
        public final String strSpouseName;
        public final String strNationality;
        public final String strContactNo;
        public final String strEmailAdd;
        public final String strResidenceType;
        public final int intResidenceYrs;
        public final String strCompPaid;
        public final String strOwnerName;
        public final String strOwnerContact;
        public final String strResidenceAdd;

        public CustomerProfile(
            String strUsername,
            String strPassword,
            String strFullName,
            String strBirthdate,
            String strGender,
            String strCivilStatus,
            String strMotherMn,
            String strSpouseName,
            String strNationality,
            String strContactNo,
            String strEmailAdd,
            String strResidenceType,
            int intResidenceYrs,
            String strCompPaid,
            String strOwnerName,
            String strOwnerContact,
            String strResidenceAdd
        ) {
            this.strUsername      = strUsername;
            this.strPassword      = strPassword;
            this.strFullName      = strFullName;
            this.strBirthdate     = strBirthdate;
            this.strGender        = strGender;
            this.strCivilStatus   = strCivilStatus;
            this.strMotherMn      = strMotherMn;
            this.strSpouseName    = strSpouseName;
            this.strNationality   = strNationality;
            this.strContactNo     = strContactNo;
            this.strEmailAdd      = strEmailAdd;
            this.strResidenceType = strResidenceType;
            this.intResidenceYrs  = intResidenceYrs;
            this.strCompPaid      = strCompPaid;
            this.strOwnerName     = strOwnerName;
            this.strOwnerContact  = strOwnerContact;
            this.strResidenceAdd  = strResidenceAdd;
        }
    }

    /**
     * Fetches the full customer + residence info for a given login ID (username or email).
     */
    public static CustomerProfile getCustomerInfoByLogin(String strLoginId) throws SQLException {
        String strSql = """
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

        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strLoginId);
            objPsStmt.setString(2, strLoginId);

            try (ResultSet objRs = objPsStmt.executeQuery()) {
                if (!objRs.next()) return null;
                return new CustomerProfile(
                    objRs.getString("username"),
                    objRs.getString("password"),
                    objRs.getString("customer_name"),
                    objRs.getString("birthdate"),
                    objRs.getString("gender"),
                    objRs.getString("civil_status"),
                    objRs.getString("mother_mn"),
                    objRs.getString("spouse_name"),
                    objRs.getString("nationality"),
                    objRs.getString("contact_no"),
                    objRs.getString("email_add"),
                    objRs.getString("residence_type"),
                    objRs.getInt   ("residence_yrs"),
                    objRs.getString("comp_paid"),
                    objRs.getString("owner_name"),
                    objRs.getString("owner_contact"),
                    objRs.getString("residence_add")
                );
            }
        }
    }

    /**
     * Fetches customer + residence info based on an application number.
     */
    public static CustomerProfile getCustomerInfoByApplication(String strApplicationNo) throws SQLException {
        String strSql = """
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

        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strApplicationNo);

            try (ResultSet objRs = objPsStmt.executeQuery()) {
                if (!objRs.next()) return null;
                return new CustomerProfile(
                    objRs.getString("username"),
                    objRs.getString("password"),
                    objRs.getString("customer_name"),
                    objRs.getString("birthdate"),
                    objRs.getString("gender"),
                    objRs.getString("civil_status"),
                    objRs.getString("mother_mn"),
                    objRs.getString("spouse_name"),
                    objRs.getString("nationality"),
                    objRs.getString("contact_no"),
                    objRs.getString("email_add"),
                    objRs.getString("residence_type"),
                    objRs.getInt   ("residence_yrs"),
                    objRs.getString("comp_paid"),
                    objRs.getString("owner_name"),
                    objRs.getString("owner_contact"),
                    objRs.getString("residence_add")
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
            String   strOriginalUsername,
            String   strNewPassword,
            String   strNewName,
            Date     dtNewBirthdate,   // java.sql.Date
            String   strNewGender,
            String   strNewCivilStatus,
            String   strNewMotherMn,
            String   strNewSpouseName,
            String   strNewNationality,
            String   strNewContactNo,
            String   strNewEmailAdd
    ) throws SQLException {
        String strSql = """
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

        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strNewPassword);
            objPsStmt.setString(2, strNewName);
            objPsStmt.setDate(   3, dtNewBirthdate);
            objPsStmt.setString(4, strNewGender);
            objPsStmt.setString(5, strNewCivilStatus);
            objPsStmt.setString(6, strNewMotherMn);
            objPsStmt.setString(7, strNewSpouseName);
            objPsStmt.setString(8, strNewNationality);
            objPsStmt.setString(9, strNewContactNo);
            objPsStmt.setString(10, strNewEmailAdd);
            objPsStmt.setString(11, strOriginalUsername);

            objPsStmt.executeUpdate();
        }
    }

    // ───────────────────────────────────────────────────────────────
    // PLAN DETAILS
    // ───────────────────────────────────────────────────────────────

    /** Simple DTO for a service plan. */
    public static class PlanDetails {
        public final String strPlanId;
        public final String strServicePlan;
        public final double dblServiceFee;
        public final String strInstallFee;

        public PlanDetails(String strPlanId, String strServicePlan, double dblServiceFee, String strInstallFee) {
            this.strPlanId      = strPlanId;
            this.strServicePlan = strServicePlan;
            this.dblServiceFee  = dblServiceFee;
            this.strInstallFee  = strInstallFee;
        }
    }

    /**
     * Fetches the plan details (service_plan, service_fee, install_fee)
     * for the given application number.
     */
    public static PlanDetails getPlanDetailsByApplication(String strApplicationNo) throws SQLException {
        String strSql = """
            SELECT s.plan_ID,
                   s.service_plan,
                   s.service_fee,
                   s.install_fee
              FROM tbl_application a
              JOIN tbl_payment   p ON a.application_no = p.application_no
              JOIN tbl_service   s ON p.plan_ID        = s.plan_ID
             WHERE a.application_no = ?
            """;

        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strApplicationNo);
            try (ResultSet objRs = objPsStmt.executeQuery()) {
                if (!objRs.next()) return null;
                return new PlanDetails(
                    objRs.getString("plan_ID"),
                    objRs.getString("service_plan"),
                    objRs.getDouble("service_fee"),
                    objRs.getString("install_fee")
                );
            }
        }
    }

    public static class Subscription {
        public final String strApplicationNo;
        public final String strDateSubmitted;
        public final String strStatus;
        public final PlanDetails objPlanDetails;

        public Subscription(
            String strApplicationNo,
            String strDateSubmitted,
            String strStatus,
            PlanDetails objPlanDetails
        ) {
            this.strApplicationNo = strApplicationNo;
            this.strDateSubmitted = strDateSubmitted;
            this.strStatus       = strStatus;
            this.objPlanDetails  = objPlanDetails;
        }
    }

    /**
     * Fetches all subscriptions (applications + plan details) for a given username.
     */
    public static List<Subscription> getSubscriptionsByUsername(String strUsername) throws SQLException {
        String strSql = """
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

        List<Subscription> colSubscriptions = new ArrayList<>();
        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strUsername);
            try (ResultSet objRs = objPsStmt.executeQuery()) {
                while (objRs.next()) {
                    PlanDetails objPlanDetails = new PlanDetails(
                        objRs.getString("plan_ID"),
                        objRs.getString("service_plan"),
                        objRs.getDouble("service_fee"),
                        objRs.getString("install_fee")
                    );
                    colSubscriptions.add(new Subscription(
                        objRs.getString("application_no"),
                        objRs.getString("date_submitted"),
                        objRs.getString("status"),
                        objPlanDetails
                    ));
                }
            }
        }
        return colSubscriptions;
    }

    /** Simple DTO for your applications (one row per application). */
    public static class ApplicationInfo {
        public final String strApplicationNo;
        public final String strDateSubmitted;
        public final String strStatus;

        public ApplicationInfo(String strApplicationNo, String strDateSubmitted, String strStatus) {
            this.strApplicationNo = strApplicationNo;
            this.strDateSubmitted = strDateSubmitted;
            this.strStatus       = strStatus;
        }
    }

    /**
     * Fetches all application rows (one per application_no) for a given username.
     */
    public static List<ApplicationInfo> getApplicationsByUsername(String strUsername) throws SQLException {
        String strSql = """
            SELECT a.application_no,
                   DATE_FORMAT(a.application_date, '%m/%d/%Y') AS date_submitted,
                   p.payment_option AS status
              FROM tbl_customer c
              JOIN tbl_application a ON c.customer_ID = a.customer_ID
              JOIN tbl_payment     p ON a.application_no = p.application_no
             WHERE c.username = ?
             ORDER BY a.application_date DESC
            """;

        List<ApplicationInfo> colApplications = new ArrayList<>();
        try (Connection objConn = DatabaseConnection.getConnection();
             PreparedStatement objPsStmt = objConn.prepareStatement(strSql)) {

            objPsStmt.setString(1, strUsername);
            try (ResultSet objRs = objPsStmt.executeQuery()) {
                while (objRs.next()) {
                    colApplications.add(new ApplicationInfo(
                        objRs.getString("application_no"),
                        objRs.getString("date_submitted"),
                        objRs.getString("status")
                    ));
                }
            }
        }
        return colApplications;
    }
}
