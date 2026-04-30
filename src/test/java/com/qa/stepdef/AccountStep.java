package com.qa.stepdef;

import com.qa.pages.AccountPage;
import com.qa.pages.RegisterPage;
import com.qa.utils.ScenarioContext;
import com.qa.utils.TestUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import com.qa.utils.DataGeneratorUtil;
import java.util.Map;

public class AccountStep {

    @Then("Verify Login Success with redirect to My Account")
    public void toMyAccountPage()  {
        new AccountPage().toMyAccountPage();
    }

    @When("Get Account data in My Account Page")
    public void getAccountData()  {
        new AccountPage().getAccountData();
    }

    @Then("Verify Account data in My Account Page with registered data")
    public void verifyAccountWithScenarioContext() {
        Map<String, String> uiData = new AccountPage().getAccountData();
        String uiName = uiData.get("name");
        String uiPhone = uiData.get("phone");
        String expectedName = ScenarioContext.getLastFullName();
        String expectedPhone = ScenarioContext.getLastPhone();
        Assert.assertEquals(uiName, expectedName, "Full Name mismatch with registered data!");
        Assert.assertEquals(uiPhone, expectedPhone, "Phone Number mismatch with registered data!");
    }

    @When("Update Full Name with {string}")
    public void updateFullName(String newFullName)  {
        ScenarioContext.setLastFullName(newFullName);
        new AccountPage().updateFullName(newFullName);
    }

    @When("Update with a random Full Name")
    public void updateRandomFullName()  {
        String newFullName = DataGeneratorUtil.generateRandomName();
        ScenarioContext.setLastFullName(newFullName);
        new AccountPage().updateFullName(newFullName);
    }

    @And("Update Phone Number with {string}")
    public void updatePhone(String newPhone)  {
        ScenarioContext.setLastPhone(newPhone);
        new AccountPage().updatePhone(newPhone);
    }

    @And("Update with a random Phone Number")
    public void updateRandomPhone()  {
        String newPhone = DataGeneratorUtil.generateRandomPhone();
        ScenarioContext.setLastPhone(newPhone);
        new AccountPage().updatePhone(newPhone);
    }

    @And("Update Gender with {string}")
    public void updateGender(String gender)  {
        ScenarioContext.setLastGender(gender);
        new AccountPage().selectGender(gender);
    }

    @And("Update with a random Gender")
    public void updateRandomGender()  {
        String gender = DataGeneratorUtil.generateRandomGender();
        ScenarioContext.setLastGender(gender);
        new AccountPage().selectGender(gender);
    }

    @And("Update Username with {string}")
    public void updateUsername(String newUsername)  {
        ScenarioContext.setLastUsername(newUsername);
        new AccountPage().updateUsername(newUsername);
    }

    @And("Update with a random Username")
    public void updateRandomUsername()  {
        String newUsername = DataGeneratorUtil.generateRandomUsername();
        ScenarioContext.setLastUsername(newUsername);
        new AccountPage().updateUsername(newUsername);
    }

    @And("Update Password with {string}")
    public void updatePassword(String newPassword)  {
        ScenarioContext.setLastPassword(newPassword);
        new AccountPage().updatePassword(newPassword);
    }

    @And("Update with a random Password")
    public void updateRandomPassword()  {
        String newPassword = DataGeneratorUtil.generateRandomPassword();
        ScenarioContext.setLastPassword(newPassword);
        new AccountPage().updatePassword(newPassword);
    }

    @And("Update Confirm Password with {string}")
    public void updateConfirmPassword(String newConfirmPassword)  {
        new AccountPage().updateConfirmPassword(newConfirmPassword);
    }

    @And("Update with a random Confirm Password")
    public void updateRandomConfirmPassword()  {
        String newConfirmPassword = ScenarioContext.getLastPassword();
        new AccountPage().updateConfirmPassword(newConfirmPassword);
    }

    @And("Click Save Changes Button")
    public void clickSaveChanges()  {
        new AccountPage().clickSaveChanges();
    }

    @Then("Verify Update Account Success with message {string}")
    public void verifyUpdateAccountSuccess(String expectedMessage)  {
        Assert.assertEquals(new AccountPage().getSuccessUpdateMessage(), expectedMessage);
    }

    @And("Verify updated Account data in My Account Page with updated data")
    public void verifyUpdatedAccountWithScenarioContext() throws Exception {
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
