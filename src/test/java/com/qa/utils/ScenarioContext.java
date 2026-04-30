package com.qa.utils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> contextData = ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        contextData.get().put(key, value);
    }

    public static Object get(String key) {
        return contextData.get().get(key);
    }

    public static String getString(String key) {
        Object value = get(key);
        return value != null ? String.valueOf(value) : null;
    }

    public static void clear() {
        contextData.get().clear();
    }

    public static void setRandomNumber(String value) { set("randomNumber", value); }
    public static String getRandomNumber() { return getString("randomNumber"); }
    public static void setLastFullName(String value) { set("lastFullName", value); }
    public static String getLastFullName() { return getString("lastFullName"); }
    public static void setLastPhone(String value) { set("lastPhone", value); }
    public static String getLastPhone() { return getString("lastPhone"); }
    public static void setLastGender(String value) { set("lastGender", value); }
    public static String getLastGender() { return getString("lastGender"); }
    public static void setLastUsername(String value) { set("lastUsername", value); }
    public static String getLastUsername() { return getString("lastUsername"); }
    public static void setLastPassword(String value) { set("lastPassword", value); }
    public static String getLastPassword() { return getString("lastPassword"); }
    public static void setLastAddressLabel(String value) { set("lastAddressLabel", value); }
    public static String getlastAddressLabel() { return getString("lastAddressLabel"); }
    public static void setLastStreet(String value) { set("lastStreet", value); }
    public static String getLastStreet() { return getString("lastStreet"); }
    public static void setLastCity(String value) { set("lastCity", value); }
    public static String getLastCity() { return getString("lastCity"); }
    public static void setLastPostalCode(String value) { set("lastPostalCode", value); }
    public static String getLastPostalCode() { return getString("lastPostalCode"); }
    public static void setLastContactName(String value) { set("lastContactName", value); }
    public static String getLastContactName() { return getString("lastContactName"); }
    public static void setLastPhoneContact(String value) { set("lastPhoneContact", value); }
    public static String getLastPhoneContact() { return getString("lastPhoneContact"); }
    public static void setLastEmailContact(String value) { set("lastEmailContact", value); }
    public static String getLastEmailContact() { return getString("lastEmailContact"); }
}
