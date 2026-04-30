package com.qa.utils;

public class DataGeneratorUtil {

    private static boolean isIOS() {
        return new GlobalParams().getPlatformName().equalsIgnoreCase("iOS");
    }

    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    public static String generateRandomName() {
        String randomDigits = generateRandomNumber(3);
        ScenarioContext.setRandomNumber(randomDigits);
        if (isIOS()) {
            return "Test-iOS-" + randomDigits;
        } else {
            return "Test-Android-" + randomDigits;
        }
    }

    public static String generateRandomUsername() {
        String randomDigits = ScenarioContext.getRandomNumber();
        if (randomDigits == null) {
            randomDigits = generateRandomNumber(3);
            ScenarioContext.setRandomNumber(randomDigits);
        }
        if (isIOS()) {
            return "test-ios-" + randomDigits;
        } else {
            return "test-android-" + randomDigits;
        }
    }

    public static String generateRandomPhone() {
        return "08" + generateRandomNumber(10);
    }

    public static String generateRandomPassword() {
        return generateRandomNumber(6);
    }

    public static String generateRandomStreet() {
        String[] streets = {"Sudirman", "Thamrin", "Gatot Subroto", "Rasuna Said", "Suryo"};
        int index = (int) (Math.random() * streets.length);
        return "Jalan " + streets[index] + " No. " + generateRandomNumber(2);
    }

    public static String generateRandomCity() {
        String[] cities = {"Jakarta", "Bandung", "Surabaya", "Medan", "Semarang", "Yogyakarta"};
        int index = (int) (Math.random() * cities.length);
        return cities[index];
    }

    public static String generateRandomPostalCode() {
        return generateRandomNumber(5);
    }

    public static String generateRandomEmail() {
        return "test" + generateRandomNumber(5) + "@mail.com";
    }

    public static String generateRandomGender() {
        String[] genders = {"Male", "Female"};
        int index = (int) (Math.random() * genders.length);
        return genders[index];
    }
}
