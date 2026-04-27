package com.qa.utils;

public class ScenarioContext {
    private static final ThreadLocal<String> randomNumber = new ThreadLocal<>();
    private static final ThreadLocal<String> lastFullName = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPhone = new ThreadLocal<>();
    private static final ThreadLocal<String> lastGender = new ThreadLocal<>();
    private static final ThreadLocal<String> lastUsername = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPassword = new ThreadLocal<>();
    private static final ThreadLocal<String> lastAddressLabel = new ThreadLocal<>();
    private static final ThreadLocal<String> lastStreet = new ThreadLocal<>();
    private static final ThreadLocal<String> lastCity = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPostalCode = new ThreadLocal<>();
    private static final ThreadLocal<String> lastContactName = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPhoneContact = new ThreadLocal<>();
    private static final ThreadLocal<String> lastEmailContact = new ThreadLocal<>();

    public static void setRandomNumber(String value) {
        randomNumber.set(value);
    }

    public static String getRandomNumber() {
        return randomNumber.get();
    }

    public static void setLastFullName(String value) {
        lastFullName.set(value);
    }

    public static String getLastFullName() {
        return lastFullName.get();
    }

    public static void setLastPhone(String value) {
        lastPhone.set(value);
    }

    public static String getLastPhone() {
        return lastPhone.get();
    }

    public static void setLastGender(String value) {
        lastGender.set(value);
    }

    public static String getLastGender() {
        return lastGender.get();
    }

    public static void setLastUsername(String value) {
        lastUsername.set(value);
    }

    public static String getLastUsername() {
        return lastUsername.get();
    }

    public static void setLastPassword(String value) {
        lastPassword.set(value);
    }

    public static String getLastPassword() {
        return lastPassword.get();
    }

    public static void setlastAddressLabel(String value) {
        lastAddressLabel.set(value);
    }

    public static String getlastAddressLabel() {
        return lastAddressLabel.get();
    }

    public static void setLastStreet(String value) {
        lastStreet.set(value);
    }

    public static String getLastStreet() {
        return lastStreet.get();
    }

    public static void setLastCity(String value) {
        lastCity.set(value);
    }

    public static String getLastCity() {
        return lastCity.get();
    }

    public static void setLastPostalCode(String value) {
        lastPostalCode.set(value);
    }

    public static String getLastPostalCode() {
        return lastPostalCode.get();
    }

    public static void setLastContactName(String value) {
        lastContactName.set(value);
    }

    public static String getLastPContactName() {
        return lastContactName.get();
    }

    public static void setLastPhoneContact(String value) {
        lastPhoneContact.set(value);
    }

    public static String getLastPhoneContact() {
        return lastPhoneContact.get();
    }

    public static void setLastEmailContact(String value) {
        lastEmailContact.set(value);
    }

    public static String getLastEmailContact() {
        return lastEmailContact.get();
    }
}
