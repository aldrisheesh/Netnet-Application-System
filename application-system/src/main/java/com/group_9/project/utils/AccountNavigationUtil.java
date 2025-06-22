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
        String strAppNo = UserApplicationData.get("strApplicationNo");
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
            UserApplicationData.set("strUsername",        objProfile.username);
            UserApplicationData.set("strPassword",        objProfile.password);
            UserApplicationData.set("strCustomerName",    objProfile.fullName);
            UserApplicationData.set("strBirthday",        objProfile.birthdate);
            UserApplicationData.set("strGender",          objProfile.gender);
            UserApplicationData.set("strCivilStatus",     objProfile.civilStatus);
            UserApplicationData.set("strMaidenName",      objProfile.motherMn);
            UserApplicationData.set("strSpouse",          objProfile.spouseName != null ? objProfile.spouseName : "");
            UserApplicationData.set("strNationality",     objProfile.nationality);
            UserApplicationData.set("strEmail",           objProfile.emailAdd);
            UserApplicationData.set("strMobile",          objProfile.contactNo);
            UserApplicationData.set("strHomeOwnership",   objProfile.residenceType);
            UserApplicationData.set("intYearsOfResidency",String.valueOf(objProfile.residenceYrs));
            UserApplicationData.set("strCompanyPaid",     objProfile.compPaid);
            UserApplicationData.set("strNameOfOwner",     objProfile.ownerName);
            UserApplicationData.set("strContactNumber",   objProfile.ownerContact);
            UserApplicationData.set("strResidenceAddress",objProfile.residenceAdd);

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
