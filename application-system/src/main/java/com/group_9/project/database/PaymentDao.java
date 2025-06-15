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

    /**
     * Inserts one payment row per plan ID for the given application.
     *
     * @param applicationNo   the application number (e.g. "A00001")
     * @param planIdsCsv      comma-separated plan IDs (e.g. "P001,P003")
     * @param paymentOption   either "full" or "installment"
     * @throws SQLException   if any insert fails
     */
    public void insertPayments(String applicationNo, String planIdsCsv, String paymentOption) throws SQLException {
        if (applicationNo == null || applicationNo.isBlank()) {
            throw new IllegalArgumentException("applicationNo must not be blank");
        }
        if (planIdsCsv == null || planIdsCsv.isBlank()) {
            throw new IllegalArgumentException("planIdsCsv must not be blank");
        }
        // Split & trim
        String[] planIds = planIdsCsv.split(",");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            for (String rawId : planIds) {
                String planId = rawId.trim();
                if (planId.isEmpty()) continue;

                ps.setString(1, applicationNo);
                ps.setString(2, planId);
                ps.setString(3, paymentOption);
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            // optional: verify results
            for (int count : results) {
                if (count == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException("Batch insert failed for one or more payments");
                }
            }
        }
    }

    /**
     * Convenience: pull parameters from UserApplicationData session.
     */
    public void insertCurrentUserPayments() throws SQLException {
        String appNo    = UserApplicationData.get("applicationNo");
        String planIds  = UserApplicationData.get("selectedPlanIDs");
        String option   = UserApplicationData.get("paymentOption");
        insertPayments(appNo, planIds, option);
    }
}
