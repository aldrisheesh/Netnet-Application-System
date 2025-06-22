package com.group_9.project.utils;

import com.group_9.project.database.AccountService;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.AccountDetailsPage;

import javax.swing.*;
import java.sql.SQLException;

public final class AccountNavigationUtil {
    private AccountNavigationUtil() { /* no‐op: utility */ }

    /**
     * Looks up the current ApplicationNo in session, fetches
     * the customer profile, stores it in session, then opens
     * the AccountDetailsPage (or shows an error).
     *
     * @param parentFrame the JFrame to dispose after navigation
     */
    public static void openAccountPageByApplication(JFrame frmParent) {
        String strAppNo = UserApplicationData.get("ApplicationNo");
        if (strAppNo == null || strAppNo.isEmpty()) {
            CustomDialogUtil.showStyledErrorDialog(
                frmParent,
                "No Application",
                "No application number found in session."
            );
            return;
        }

        try {
            var objProfile = AccountService.getCustomerInfoByApplication(strAppNo);
            if (objProfile == null) {
                CustomDialogUtil.showStyledErrorDialog(
                    frmParent,
                    "Load Error",
                    "No account found for application #" + strAppNo
                );
                return;
            }

            // Populate session with all needed fields
            UserApplicationData.set("Username",        objProfile.username);
            UserApplicationData.set("Password",        objProfile.password);
            UserApplicationData.set("CustomerName",    objProfile.fullName);
            UserApplicationData.set("Birthday",        objProfile.birthdate);
            UserApplicationData.set("Gender",          objProfile.gender);
            UserApplicationData.set("CivilStatus",     objProfile.civilStatus);
            UserApplicationData.set("MaidenName",      objProfile.motherMn);
            UserApplicationData.set("Spouse",          objProfile.spouseName != null ? objProfile.spouseName : "");
            UserApplicationData.set("Nationality",     objProfile.nationality);
            UserApplicationData.set("Email",           objProfile.emailAdd);
            UserApplicationData.set("Mobile",          objProfile.contactNo);
            UserApplicationData.set("HomeOwnership",   objProfile.residenceType);
            UserApplicationData.set("YearsOfResidency",String.valueOf(objProfile.residenceYrs));
            UserApplicationData.set("CompanyPaid",     objProfile.compPaid);
            UserApplicationData.set("NameOfOwner",     objProfile.ownerName);
            UserApplicationData.set("ContactNumber",   objProfile.ownerContact);
            UserApplicationData.set("ResidenceAddress",objProfile.residenceAdd);

            // Launch the details page
            new AccountDetailsPage().setVisible(true);
            frmParent.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            CustomDialogUtil.showStyledErrorDialog(
                frmParent,
                "Database Error",
                "Failed to load account details for application #" + strAppNo
            );
        }
    }
}
