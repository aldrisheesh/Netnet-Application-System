package com.group_9.project.session;

import java.util.HashMap;
import java.util.Map;

public class UserApplicationData {
    private static final Map<String, String> data = new HashMap<>();

    public static void set(String key, String value) {
        String cleanValue = (value != null) ? value.trim() : "";
        data.put(key, cleanValue);
        System.out.println("UserApplicationData.set: " + key + " = '" + cleanValue + "'");
    }

    public static String get(String key) {
        String value = data.getOrDefault(key, "");
        return value;
    }

    public static void clear() {
        System.out.println("Clearing UserApplicationData...");
        data.clear();
    }

    public static Map<String, String> getAll() {
        return new HashMap<>(data);
    }

    public static void printAllData() {
        System.out.println("=== ALL USER APPLICATION DATA ===");
        if (data.isEmpty()) {
            System.out.println("No data stored");
        } else {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                System.out.println(entry.getKey() + " = '" + entry.getValue() + "'");
            }
        }
        System.out.println("===================================");
    }

    // Validation methods with detailed logging
    public static boolean hasRequiredCustomerData() {
        boolean usernameValid = !get("Username").isEmpty();
        boolean customerNameValid = !get("CustomerName").isEmpty();
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

        return usernameValid && customerNameValid && emailValid && contactValid && 
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

    // Convenience setters with validation
    public static void setCustomerInfo(String username, String customerName, String birthdate, String gender, 
                                     String civilStatus, String motherMn, String spouseName, 
                                     String nationality, String contactNo, String emailAdd,
                                     String residenceType, int residenceYrs, String compPaid) {
        System.out.println("Setting customer info...");
        set("Username", username);
        set("CustomerName", customerName);
        set("Birthday", birthdate);
        set("Gender", gender);
        set("CivilStatus", civilStatus);
        set("MaidenName", motherMn);
        set("Spouse", spouseName);
        set("Nationality", nationality);
        set("Mobile", contactNo);
        set("Email", emailAdd);
        set("HomeOwnership", residenceType);
        set("YearsOfResidency", String.valueOf(residenceYrs));
        set("CompanyPaid", compPaid);
    }

    public static void setResidenceInfo(String ownerName, String ownerContact, String residenceAdd) {
        System.out.println("Setting residence info...");
        set("NameOfOwner", ownerName);
        set("ContactNumber", ownerContact);
        set("ResidenceAddress", residenceAdd);
    }

    public static void setPlanInfo(String planId, String paymentOption) {
        System.out.println("Setting plan info...");
        set("selectedPlanIDs", planId);
        set("selectedPlans", planId);
        set("paymentOption", paymentOption);
    }

    public static void setPaymentInfo(String cardNumber, String expiryDate, String cvv, String cardholderName) {
        System.out.println("Setting payment info...");
        set("cardNumber", cardNumber);
        set("expiryDate", expiryDate);
        set("cvv", cvv);
        set("cardholderName", cardholderName);
    }
}