package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.RegisterPage;
import com.qa.utils.TestUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class RegisterStep {

    private static final ThreadLocal<String> randomNumber = new ThreadLocal<>();
    private static final ThreadLocal<String> lastFullName = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPhone = new ThreadLocal<>();
    private static final ThreadLocal<String> lastGender = new ThreadLocal<>();
    private static final ThreadLocal<String> lastUsername = new ThreadLocal<>();
    private static final ThreadLocal<String> lastPassword = new ThreadLocal<>();

    @When("Input Full Name with {string}")
    public void inputFullNameWith(String fullName) throws InterruptedException {
        if (fullName.equalsIgnoreCase("RANDOM")) {
            randomNumber.set(String.valueOf((int) (Math.random() * 900) + 100));
            if (new RegisterPage().isIOS()) {
                fullName = "Test-iOS-" + randomNumber.get();
            } else {
                fullName = "Test-Android-" + randomNumber.get();
            }
        }
        lastFullName.set(fullName);
        new RegisterPage().inputFullName(fullName);
    }

    @And("Input Phone Number with {string}")
    public void inputPhone(String phone) throws InterruptedException {
        lastPhone.set(phone);
        new RegisterPage().inputPhone(phone);
    }

    @And("Select {string} as Gender")
    public void selectGender(String gender) throws InterruptedException {
        lastGender.set(gender);
        new RegisterPage().selectGender(gender);
    }

    @And("Input Username with {string}")
    public void inputUsername(String username) throws InterruptedException {
        if (username.equalsIgnoreCase("RANDOM")) {
            if (new RegisterPage().isIOS()) {
                username = "test-ios-" + randomNumber.get();
            } else {
                username = "test-android-" + randomNumber.get();
            }
        }
        lastUsername.set(username);
        new RegisterPage().inputUsername(username);
    }

    @And("Input Password with {string}")
    public void inputPassword(String password) throws InterruptedException {
        lastPassword.set(password);
        new RegisterPage().inputPassword(password);
    }

    @And("Input Confirm Password with {string}")
    public void inputConfirmPassword(String confirmPassword) throws InterruptedException {
        new RegisterPage().inputConfirmPassword(confirmPassword);
    }

    @And("Click Register Button")
    public void clickRegister() throws InterruptedException {
        new RegisterPage().clickRegister();
    }

    @io.cucumber.java.en.Then("Verify user registration in database")
    public void verifyUserInDB() throws Exception {
        // Query to check user in database with all fields
        String userQuery = "SELECT * FROM users WHERE username = '" + lastUsername.get() + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(userQuery);

        org.testng.Assert.assertTrue(rs.next(),
                "User with username '" + lastUsername.get() + "' not found in database!");

        // Asserting all fields
        org.testng.Assert.assertEquals(rs.getString("name"), lastFullName.get(), "Name mismatch in DB!");
        org.testng.Assert.assertEquals(rs.getString("phone"), lastPhone.get(), "Phone mismatch in DB!");
        org.testng.Assert.assertEquals(rs.getString("gender"), lastGender.get(), "Gender mismatch in DB!");
        org.testng.Assert.assertEquals(rs.getString("username"), lastUsername.get(), "Username mismatch in DB!");
        org.testng.Assert.assertEquals(rs.getString("password"), lastPassword.get(), "Password mismatch in DB!");

        TestUtils.log().info(
                "Database validation successful! All fields (name, phone, gender, username, password) match for user: "
                        + lastUsername.get());

        // Printing values for comparison
        System.out.println("==========================================");
        System.out.println("DATABASE VALIDATION SUCCESSFUL");
        System.out.println("==========================================");
        System.out.println("Field     | Input Value        | DB Value");
        System.out.println("------------------------------------------");
        System.out.println("Name      | " + lastFullName.get() + " | " + rs.getString("name"));
        System.out.println("Phone     | " + lastPhone.get() + " | " + rs.getString("phone"));
        System.out.println("Gender    | " + lastGender.get() + " | " + rs.getString("gender"));
        System.out.println("Username  | " + lastUsername.get() + " | " + rs.getString("username"));
        System.out.println("Password  | " + lastPassword.get() + " | " + rs.getString("password"));
        System.out.println("==========================================");
    }
}
