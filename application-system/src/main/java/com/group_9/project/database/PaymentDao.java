package com.group_9.project.database;

import com.group_9.project.session.UserApplicationData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data-access object for writing payments into tbl_payment.
 */
public class PaymentDao {

    private static final String INSERT_SQL = """
        INSERT INTO tbl_payment(application_no, plan_ID, payment_option)
        VALUES(?, ?, ?)
        """;

    private static final String DELETE_SQL = """
        DELETE FROM tbl_payment
         WHERE application_no = ? AND plan_ID = ?
        """;

    /**
     * Inserts one payment row per plan ID for the given application.
     *
     * @param strApplicationNo   the application number (e.g. "A00001")
     * @param strPlanIdsCsv      comma-separated plan IDs (e.g. "P001,P003")
     * @param strPaymentOption   either "full" or "installment"
     * @throws SQLException   if any insert fails
     */
    public void insertPayments(String strApplicationNo, String strPlanIdsCsv, String strPaymentOption) throws SQLException {
        if (strApplicationNo == null || strApplicationNo.isBlank()) {
            throw new IllegalArgumentException("applicationNo must not be blank");
        }
        if (strPlanIdsCsv == null || strPlanIdsCsv.isBlank()) {
            throw new IllegalArgumentException("planIdsCsv must not be blank");
        }
        // Split & trim
        String[] arrPlanIds = strPlanIdsCsv.split(",");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psStmt = conn.prepareStatement(INSERT_SQL)) {

            for (String strRawId : arrPlanIds) {
                String strPlanId = strRawId.trim();
                if (strPlanId.isEmpty()) continue;

                psStmt.setString(1, strApplicationNo);
                psStmt.setString(2, strPlanId);
                psStmt.setString(3, strPaymentOption);
                psStmt.addBatch();
            }
            int[] arrResults = psStmt.executeBatch();
            // optional: verify results
            for (int intCount : arrResults) {
                if (intCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException("Batch insert failed for one or more payments");
                }
            }
        }
    }

    /**
     * Convenience: pull parameters from UserApplicationData session.
     */
    public void insertCurrentUserPayments() throws SQLException {
        String strAppNo   = UserApplicationData.get("strApplicationNo");
        String strPlanIds = UserApplicationData.get("strSelectedPlanIDs");
        String strOption  = UserApplicationData.get("strPaymentOption");
        insertPayments(strAppNo, strPlanIds, strOption);
    }

    /**
     * Deletes a payment row for the given application number and plan ID.
     *
     * @param strApplicationNo the application number
     * @param strPlanId        the plan ID to remove
     * @return true if a row was deleted
     * @throws SQLException if the delete fails
     */
    public boolean deletePayment(String strApplicationNo, String strPlanId) throws SQLException {
        if (strApplicationNo == null || strApplicationNo.isBlank()) {
            throw new IllegalArgumentException("applicationNo must not be blank");
        }
        if (strPlanId == null || strPlanId.isBlank()) {
            throw new IllegalArgumentException("planId must not be blank");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psStmt = conn.prepareStatement(DELETE_SQL)) {
            psStmt.setString(1, strApplicationNo.trim());
            psStmt.setString(2, strPlanId.trim());
            int intCount = psStmt.executeUpdate();
            return intCount > 0;
        }
    }
}
