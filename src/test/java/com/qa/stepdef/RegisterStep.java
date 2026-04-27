package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.RegisterPage;
import com.qa.utils.TestUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.qa.utils.ScenarioContext;
import org.testng.Assert;

public class RegisterStep {

    @When("Input Full Name with {string}")
    public void inputFullname(String fullName) throws InterruptedException {
        if (fullName.equalsIgnoreCase("RandomName")) {
            fullName = new RegisterPage().generateRandomName();
        }
        ScenarioContext.setLastFullName(fullName);
        new RegisterPage().inputFullName(fullName);
    }

    @And("Input Phone Number with {string}")
    public void inputPhone(String phone) throws InterruptedException {
        if (phone.equalsIgnoreCase("RandomPhone")) {
            phone = new RegisterPage().generateRandomPhone();
        }
        ScenarioContext.setLastPhone(phone);
        new RegisterPage().inputPhone(phone);
    }

    @And("Select {string} as Gender")
    public void selectGender(String gender) throws InterruptedException {
        ScenarioContext.setLastGender(gender);
        new RegisterPage().selectGender(gender);
    }

    @And("Input Username with {string}")
    public void inputUsername(String username) throws InterruptedException {
        if (username.equalsIgnoreCase("RandomUsername")) {
            username = new RegisterPage().generateRandomUsername();
        }
        ScenarioContext.setLastUsername(username);
        new RegisterPage().inputUsername(username);
    }

    @And("Input Password with {string}")
    public void inputPassword(String password) throws InterruptedException {
        if (password.equalsIgnoreCase("RandomPassword")) {
            password = new RegisterPage().generateRandomPassword();
        }
        ScenarioContext.setLastPassword(password);
        new RegisterPage().inputPassword(password);
    }

    @And("Input Confirm Password with {string}")
    public void inputConfirmPassword(String confirmPassword) throws InterruptedException {
        if (confirmPassword.equalsIgnoreCase("RandomConfirmPassword")) {
            confirmPassword = ScenarioContext.getLastPassword();
        }
        new RegisterPage().inputConfirmPassword(confirmPassword);
    }

    @And("Click Register Button")
    public void clickRegister() throws InterruptedException {
        new RegisterPage().clickRegister();
    }

    @Then("Verify user registration data in database")
    public void verifyUserInDB() throws Exception {
        // Query to check user in database with all fields
        String userQuery = "SELECT * FROM users WHERE username = '" + ScenarioContext.getLastUsername() + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(userQuery);

        Assert.assertTrue(rs.next(),
                "User with username '" + ScenarioContext.getLastUsername() + "' not found in database!");

        // Asserting all fields
        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastFullName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhone());
        Assert.assertEquals(rs.getString("gender"), ScenarioContext.getLastGender());
        Assert.assertEquals(rs.getString("username"), ScenarioContext.getLastUsername());
        Assert.assertEquals(rs.getString("password"), ScenarioContext.getLastPassword());

        TestUtils.log().info(
                "Database validation successful! All fields (name, phone, gender, username, password) match for user: "
                        + ScenarioContext.getLastUsername());
        System.out.println("==========================================");
    }
}
