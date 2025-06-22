package com.group_9.project.session;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class UserApplicationData {
    private static final Map<String, String> mapData = new HashMap<>();

    public static void set(String strKey, String strValue) {
        String strCleanValue = (strValue != null) ? strValue.trim() : "";
        mapData.put(strKey, strCleanValue);
        System.out.println("UserApplicationData.set: " + strKey + " = '" + strCleanValue + "'");
    }

    public static String get(String strKey) {
        String strValue = mapData.getOrDefault(strKey, "");
        return strValue;
    }

    public static void clear() {
        System.out.println("Clearing UserApplicationData...");
        mapData.clear();
    }

    public static Map<String, String> getAll() {
        return new HashMap<>(mapData);
    }

    public static void printAllData() {
        System.out.println("=== ALL USER APPLICATION DATA ===");
        if (mapData.isEmpty()) {
            System.out.println("No data stored");
        } else {
            for (Map.Entry<String, String> entData : mapData.entrySet()) {
                System.out.println(entData.getKey() + " = '" + entData.getValue() + "'");
            }
        }
        System.out.println("===================================");
    }

    // validation methods with detailed logging
    public static boolean hasRequiredCustomerData() {
        boolean usernameValid = !get("strUsername").isEmpty();
        boolean customerNameValid = !get("strCustomerName").isEmpty();
        boolean passwordValid = !get("strPassword").isEmpty();
        boolean emailValid = !get("strEmail").isEmpty();
        boolean contactValid = !get("strMobile").isEmpty();
        boolean birthdateValid = !get("strBirthday").isEmpty();
        boolean genderValid = !get("strGender").isEmpty();
        boolean civilStatusValid = !get("strCivilStatus").isEmpty();
        boolean motherMnValid = !get("strMaidenName").isEmpty();
        boolean nationalityValid = !get("strNationality").isEmpty();
        boolean residenceTypeValid = !get("strHomeOwnership").isEmpty();
        boolean residenceYrsValid = !get("intYearsOfResidency").isEmpty();
        boolean compPaidValid = !get("strCompanyPaid").isEmpty();

        System.out.println("Customer Data Validation:");
        System.out.println("  Username: " + usernameValid + " ('" + get("strUsername") + "')");
        System.out.println("  Password: " + passwordValid + " ('" + get("strPassword") + "')");
        System.out.println("  Customer Name: " + customerNameValid + " ('" + get("strCustomerName") + "')");
        System.out.println("  Email: " + emailValid + " ('" + get("strEmail") + "')");
        System.out.println("  Contact: " + contactValid + " ('" + get("strMobile") + "')");
        System.out.println("  Birthdate: " + birthdateValid + " ('" + get("strBirthday") + "')");
        System.out.println("  Gender: " + genderValid + " ('" + get("strGender") + "')");
        System.out.println("  Civil Status: " + civilStatusValid + " ('" + get("strCivilStatus") + "')");
        System.out.println("  Mother's Maiden Name: " + motherMnValid + " ('" + get("strMaidenName") + "')");
        System.out.println("  Nationality: " + nationalityValid + " ('" + get("strNationality") + "')");
        System.out.println("  Residence Type: " + residenceTypeValid + " ('" + get("strHomeOwnership") + "')");
        System.out.println("  Residence Years: " + residenceYrsValid + " ('" + get("intYearsOfResidency") + "')");
        System.out.println("  Company Paid: " + compPaidValid + " ('" + get("strCompanyPaid") + "')");

        return usernameValid && customerNameValid && passwordValid && emailValid && contactValid && 
               birthdateValid && genderValid && civilStatusValid && motherMnValid && 
               nationalityValid && residenceTypeValid && residenceYrsValid && compPaidValid;
    }

    public static boolean hasRequiredResidenceData() {
        boolean ownerNameValid = !get("strNameOfOwner").isEmpty();
        boolean ownerContactValid = !get("strContactNumber").isEmpty();
        boolean residenceAddValid = !get("strResidenceAddress").isEmpty();

        System.out.println("Residence Data Validation:");
        System.out.println("  Owner Name: " + ownerNameValid + " ('" + get("strNameOfOwner") + "')");
        System.out.println("  Owner Contact: " + ownerContactValid + " ('" + get("strContactNumber") + "')");
        System.out.println("  Residence Address: " + residenceAddValid + " ('" + get("strResidenceAddress") + "')");

        return ownerNameValid && ownerContactValid && residenceAddValid;
    }

    public static boolean hasSelectedPlan() {
        boolean planValid = !get("strSelectedPlans").isEmpty();
        boolean paymentValid = !get("strPaymentOption").isEmpty();

        System.out.println("Plan Data Validation:");
        System.out.println("  Plan ID: " + planValid + " ('" + get("strSelectedPlans") + "')");
        System.out.println("  Payment Option: " + paymentValid + " ('" + get("strPaymentOption") + "')");

        return planValid && paymentValid;
    }

    // convenience setters with validation
    public static void setCustomerInfo(String strUsername, String strPassword, String strCustomerName, String strBirthdate, String strGender,
                                     String strCivilStatus, String strMotherMn, String strSpouseName,
                                     String strNationality, String strContactNo, String strEmailAdd,
                                     String strResidenceType, int intResidenceYrs, String strCompPaid) {
        System.out.println("Setting customer info...");
        set("strUsername", strUsername);
        set("strPassword", strPassword);
        set("strCustomerName", strCustomerName);
        set("strBirthday", strBirthdate);
        set("strGender", strGender);
        set("strCivilStatus", strCivilStatus);
        set("strMaidenName", strMotherMn);
        set("strSpouse", strSpouseName);
        set("strNationality", strNationality);
        set("strMobile", strContactNo);
        set("strEmail", strEmailAdd);
        set("strHomeOwnership", strResidenceType);
        set("intYearsOfResidency", String.valueOf(intResidenceYrs));
        set("strCompanyPaid", strCompPaid);
    }

    public static void setResidenceInfo(String strOwnerName, String strOwnerContact, String strResidenceAdd) {
        System.out.println("Setting residence info...");
        set("strNameOfOwner", strOwnerName);
        set("strContactNumber", strOwnerContact);
        set("strResidenceAddress", strResidenceAdd);
    }

    public static void setPlanInfo(String strPlanId, String strPaymentOption) {
        System.out.println("Setting plan info...");
        set("strSelectedPlanIDs", strPlanId);
        set("strSelectedPlans", strPlanId);
        set("strPaymentOption", strPaymentOption);
    }

    // set multiple plan IDs at once
    public static void setPlanInfo(List<String> lstPlanIds, String strPaymentOption) {
        System.out.println("Setting multiple plan info...");
        String strPlanIds = String.join(",", lstPlanIds);
        set("strSelectedPlanIDs", strPlanIds);
        set("strSelectedPlans", strPlanIds);
        set("strPaymentOption", strPaymentOption);
        System.out.println("Set " + lstPlanIds.size() + " plan(s): " + strPlanIds);
    }

    public static void setPlanInfo(String[] arrPlanIds, String strPaymentOption) {
        setPlanInfo(Arrays.asList(arrPlanIds), strPaymentOption);
    }

    public static void setPaymentInfo(String strCardNumber, String strExpiryDate, String strCvv, String strCardholderName) {
        System.out.println("Setting payment info...");
        set("strCardNumber", strCardNumber);
        set("strExpiryDate", strExpiryDate);
        set("strCVV", strCvv);
        set("strCardholderName", strCardholderName);
    }
}
