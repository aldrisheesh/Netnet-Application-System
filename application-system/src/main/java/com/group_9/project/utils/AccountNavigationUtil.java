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
     * @param frmParent the JFrame to dispose after navigation
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
            UserApplicationData.set("strUsername",        objProfile.strUsername);
            UserApplicationData.set("strPassword",        objProfile.strPassword);
            UserApplicationData.set("strCustomerName",    objProfile.strFullName);
            UserApplicationData.set("strBirthday",        objProfile.strBirthdate);
            UserApplicationData.set("strGender",          objProfile.strGender);
            UserApplicationData.set("strCivilStatus",     objProfile.strCivilStatus);
            UserApplicationData.set("strMaidenName",      objProfile.strMotherMn);
            UserApplicationData.set("strSpouse",          objProfile.strSpouseName != null ? objProfile.strSpouseName : "");
            UserApplicationData.set("strNationality",     objProfile.strNationality);
            UserApplicationData.set("strEmail",           objProfile.strEmailAdd);
            UserApplicationData.set("strMobile",          objProfile.strContactNo);
            UserApplicationData.set("strHomeOwnership",   objProfile.strResidenceType);
            UserApplicationData.set("intYearsOfResidency",String.valueOf(objProfile.intResidenceYrs));
            UserApplicationData.set("strCompanyPaid",     objProfile.strCompPaid);
            UserApplicationData.set("strNameOfOwner",     objProfile.strOwnerName);
            UserApplicationData.set("strContactNumber",   objProfile.strOwnerContact);
            UserApplicationData.set("strResidenceAddress",objProfile.strResidenceAdd);

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
