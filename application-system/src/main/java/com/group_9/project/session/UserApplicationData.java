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
        boolean usernameValid = !get("Username").isEmpty();
        boolean customerNameValid = !get("CustomerName").isEmpty();
        boolean passwordValid = !get("Password").isEmpty();
        boolean emailValid = !get("Email").isEmpty();
        boolean contactValid = !get("Mobile").isEmpty();
        boolean birthdateValid = !get("Birthday").isEmpty();
        boolean genderValid = !get("Gender").isEmpty();
        boolean civilStatusValid = !get("CivilStatus").isEmpty();
        boolean motherMnValid = !get("MaidenName").isEmpty();
        boolean nationalityValid = !get("Nationality").isEmpty();
        boolean residenceTypeValid = !get("HomeOwnership").isEmpty();
        boolean residenceYrsValid = !get("YearsOfResidency").isEmpty();
        boolean compPaidValid = !get("CompanyPaid").isEmpty();

        System.out.println("Customer Data Validation:");
        System.out.println("  Username: " + usernameValid + " ('" + get("Username") + "')");
        System.out.println("  Password: " + passwordValid + " ('" + get("Password") + "')");
        System.out.println("  Customer Name: " + customerNameValid + " ('" + get("CustomerName") + "')");
        System.out.println("  Email: " + emailValid + " ('" + get("Email") + "')");
        System.out.println("  Contact: " + contactValid + " ('" + get("Mobile") + "')");
        System.out.println("  Birthdate: " + birthdateValid + " ('" + get("Birthday") + "')");
        System.out.println("  Gender: " + genderValid + " ('" + get("Gender") + "')");
        System.out.println("  Civil Status: " + civilStatusValid + " ('" + get("CivilStatus") + "')");
        System.out.println("  Mother's Maiden Name: " + motherMnValid + " ('" + get("MaidenName") + "')");
        System.out.println("  Nationality: " + nationalityValid + " ('" + get("Nationality") + "')");
        System.out.println("  Residence Type: " + residenceTypeValid + " ('" + get("HomeOwnership") + "')");
        System.out.println("  Residence Years: " + residenceYrsValid + " ('" + get("YearsOfResidency") + "')");
        System.out.println("  Company Paid: " + compPaidValid + " ('" + get("CompanyPaid") + "')");

        return usernameValid && customerNameValid && passwordValid && emailValid && contactValid && 
               birthdateValid && genderValid && civilStatusValid && motherMnValid && 
               nationalityValid && residenceTypeValid && residenceYrsValid && compPaidValid;
    }

    public static boolean hasRequiredResidenceData() {
        boolean ownerNameValid = !get("NameOfOwner").isEmpty();
        boolean ownerContactValid = !get("ContactNumber").isEmpty();
        boolean residenceAddValid = !get("ResidenceAddress").isEmpty();

        System.out.println("Residence Data Validation:");
        System.out.println("  Owner Name: " + ownerNameValid + " ('" + get("NameOfOwner") + "')");
        System.out.println("  Owner Contact: " + ownerContactValid + " ('" + get("ContactNumber") + "')");
        System.out.println("  Residence Address: " + residenceAddValid + " ('" + get("ResidenceAddress") + "')");

        return ownerNameValid && ownerContactValid && residenceAddValid;
    }

    public static boolean hasSelectedPlan() {
        boolean planValid = !get("selectedPlans").isEmpty();
        boolean paymentValid = !get("paymentOption").isEmpty();

        System.out.println("Plan Data Validation:");
        System.out.println("  Plan ID: " + planValid + " ('" + get("selectedPlans") + "')");
        System.out.println("  Payment Option: " + paymentValid + " ('" + get("paymentOption") + "')");

        return planValid && paymentValid;
    }

    // convenience setters with validation
    public static void setCustomerInfo(String strUsername, String strPassword, String strCustomerName, String strBirthdate, String strGender,
                                     String strCivilStatus, String strMotherMn, String strSpouseName,
                                     String strNationality, String strContactNo, String strEmailAdd,
                                     String strResidenceType, int intResidenceYrs, String strCompPaid) {
        System.out.println("Setting customer info...");
        set("Username", strUsername);
        set("Password", strPassword);
        set("CustomerName", strCustomerName);
        set("Birthday", strBirthdate);
        set("Gender", strGender);
        set("CivilStatus", strCivilStatus);
        set("MaidenName", strMotherMn);
        set("Spouse", strSpouseName);
        set("Nationality", strNationality);
        set("Mobile", strContactNo);
        set("Email", strEmailAdd);
        set("HomeOwnership", strResidenceType);
        set("YearsOfResidency", String.valueOf(intResidenceYrs));
        set("CompanyPaid", strCompPaid);
    }

    public static void setResidenceInfo(String strOwnerName, String strOwnerContact, String strResidenceAdd) {
        System.out.println("Setting residence info...");
        set("NameOfOwner", strOwnerName);
        set("ContactNumber", strOwnerContact);
        set("ResidenceAddress", strResidenceAdd);
    }

    public static void setPlanInfo(String strPlanId, String strPaymentOption) {
        System.out.println("Setting plan info...");
        set("selectedPlanIDs", strPlanId);
        set("selectedPlans", strPlanId);
        set("paymentOption", strPaymentOption);
    }

    // set multiple plan IDs at once
    public static void setPlanInfo(List<String> lstPlanIds, String strPaymentOption) {
        System.out.println("Setting multiple plan info...");
        String strPlanIds = String.join(",", lstPlanIds);
        set("selectedPlanIDs", strPlanIds);
        set("selectedPlans", strPlanIds);
        set("paymentOption", strPaymentOption);
        System.out.println("Set " + lstPlanIds.size() + " plan(s): " + strPlanIds);
    }

    public static void setPlanInfo(String[] arrPlanIds, String strPaymentOption) {
        setPlanInfo(Arrays.asList(arrPlanIds), strPaymentOption);
    }

    public static void setPaymentInfo(String strCardNumber, String strExpiryDate, String strCvv, String strCardholderName) {
        System.out.println("Setting payment info...");
        set("cardNumber", strCardNumber);
        set("expiryDate", strExpiryDate);
        set("cvv", strCvv);
        set("cardholderName", strCardholderName);
    }
}
