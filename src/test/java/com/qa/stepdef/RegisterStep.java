package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.RegisterPage;
import com.qa.utils.TestUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.qa.utils.ScenarioContext;
import org.testng.Assert;

import com.qa.utils.DataGeneratorUtil;

public class RegisterStep {

    @When("Input Full Name with {string}")
    public void inputFullname(String fullName)  {
        ScenarioContext.setLastFullName(fullName);
        new RegisterPage().inputFullName(fullName);
    }

    @When("Input a random Full Name")
    public void inputRandomFullname()  {
        String fullName = DataGeneratorUtil.generateRandomName();
        ScenarioContext.setLastFullName(fullName);
        new RegisterPage().inputFullName(fullName);
    }

    @And("Input Phone Number with {string}")
    public void inputPhone(String phone)  {
        ScenarioContext.setLastPhone(phone);
        new RegisterPage().inputPhone(phone);
    }

    @And("Input a random Phone Number")
    public void inputRandomPhone()  {
        String phone = DataGeneratorUtil.generateRandomPhone();
        ScenarioContext.setLastPhone(phone);
        new RegisterPage().inputPhone(phone);
    }

    @And("Select {string} as Gender")
    public void selectGender(String gender)  {
        ScenarioContext.setLastGender(gender);
        new RegisterPage().selectGender(gender);
    }

    @And("Select a random Gender")
    public void selectRandomGender()  {
        String gender = DataGeneratorUtil.generateRandomGender();
        ScenarioContext.setLastGender(gender);
        new RegisterPage().selectGender(gender);
    }

    @And("Input Username with {string}")
    public void inputUsername(String username)  {
        ScenarioContext.setLastUsername(username);
        new RegisterPage().inputUsername(username);
    }

    @And("Input a random Username")
    public void inputRandomUsername()  {
        String username = DataGeneratorUtil.generateRandomUsername();
        ScenarioContext.setLastUsername(username);
        new RegisterPage().inputUsername(username);
    }

    @And("Input Password with {string}")
    public void inputPassword(String password)  {
        ScenarioContext.setLastPassword(password);
        new RegisterPage().inputPassword(password);
    }

    @And("Input a random Password")
    public void inputRandomPassword()  {
        String password = DataGeneratorUtil.generateRandomPassword();
        ScenarioContext.setLastPassword(password);
        new RegisterPage().inputPassword(password);
    }

    @And("Input Confirm Password with {string}")
    public void inputConfirmPassword(String confirmPassword)  {
        new RegisterPage().inputConfirmPassword(confirmPassword);
    }

    @And("Input a random Confirm Password")
    public void inputRandomConfirmPassword()  {
        String confirmPassword = ScenarioContext.getLastPassword();
        new RegisterPage().inputConfirmPassword(confirmPassword);
    }

    @And("Click Register Button")
    public void clickRegister()  {
        new RegisterPage().clickRegister();
    }

    @Then("Verify user registration data in database")
    public void verifyUserInDB() throws Exception {
        java.sql.ResultSet rs = com.qa.utils.DBManager.getUserByUsername(ScenarioContext.getLastUsername());
        Assert.assertTrue(rs.next(),
                "User with username '" + ScenarioContext.getLastUsername() + "' not found in database!");
        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastFullName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhone());
        Assert.assertEquals(rs.getString("gender"), ScenarioContext.getLastGender());
        Assert.assertEquals(rs.getString("username"), ScenarioContext.getLastUsername());
        Assert.assertEquals(rs.getString("password"), ScenarioContext.getLastPassword());
        TestUtils.log().info(
                "Database validation successful! All fields (name, phone, gender, username, password) match for user: "
                        + ScenarioContext.getLastUsername());
    }
}
