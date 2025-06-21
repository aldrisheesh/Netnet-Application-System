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
    public static void openAccountPageByApplication(JFrame parentFrame) {
        String appNo = UserApplicationData.get("ApplicationNo");
        if (appNo == null || appNo.isEmpty()) {
            CustomDialogUtil.showStyledErrorDialog(
                parentFrame,
                "No Application",
                "No application number found in session."
            );
            return;
        }

        try {
            var profile = AccountService.getCustomerInfoByApplication(appNo);
            if (profile == null) {
                CustomDialogUtil.showStyledErrorDialog(
                    parentFrame,
                    "Load Error",
                    "No account found for application #" + appNo
                );
                return;
            }

            // Populate session with all needed fields
            UserApplicationData.set("Username",        profile.username);
            UserApplicationData.set("Password",        profile.password);
            UserApplicationData.set("CustomerName",    profile.fullName);
            UserApplicationData.set("Birthday",        profile.birthdate);
            UserApplicationData.set("Gender",          profile.gender);
            UserApplicationData.set("CivilStatus",     profile.civilStatus);
            UserApplicationData.set("MaidenName",      profile.motherMn);
            UserApplicationData.set("Spouse",          profile.spouseName != null ? profile.spouseName : "");
            UserApplicationData.set("Nationality",     profile.nationality);
            UserApplicationData.set("Email",           profile.emailAdd);
            UserApplicationData.set("Mobile",          profile.contactNo);
            UserApplicationData.set("HomeOwnership",   profile.residenceType);
            UserApplicationData.set("YearsOfResidency",String.valueOf(profile.residenceYrs));
            UserApplicationData.set("CompanyPaid",     profile.compPaid);
            UserApplicationData.set("NameOfOwner",     profile.ownerName);
            UserApplicationData.set("ContactNumber",   profile.ownerContact);
            UserApplicationData.set("ResidenceAddress",profile.residenceAdd);

            // Launch the details page
            new AccountDetailsPage().setVisible(true);
            parentFrame.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            CustomDialogUtil.showStyledErrorDialog(
                parentFrame,
                "Database Error",
                "Failed to load account details for application #" + appNo
            );
        }
    }
}
