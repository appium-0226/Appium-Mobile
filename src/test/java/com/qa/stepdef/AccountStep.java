package com.qa.stepdef;

import com.qa.pages.AccountPage;
import com.qa.pages.RegisterPage;
import com.qa.utils.ScenarioContext;
import com.qa.utils.TestUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.Map;

public class AccountStep {

    @Then("Verify Login Success with redirect to My Account")
    public void toMyAccountPage() throws InterruptedException {
        new AccountPage().toMyAccountPage();
    }

    @When("Get Account data in My Account Page")
    public void getAccountData() throws InterruptedException {
        new AccountPage().getAccountData();
    }

    @Then("Verify Account data in My Account Page with registered data")
    public void verifyAccountWithScenarioContext() {
        // 1. Ambil data dari UI
        Map<String, String> uiData = new AccountPage().getAccountData();
        String uiName = uiData.get("name");
        String uiPhone = uiData.get("phone");

        // 2. Ambil data dari ScenarioContext (Data yang diinput saat Register)
        String expectedName = ScenarioContext.getLastFullName();
        String expectedPhone = ScenarioContext.getLastPhone();

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA AKUN (UI vs REGISTERED DATA)");
        System.out.println("==========================================");
        System.out.println("Field     | UI Value           | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Full Name | " + uiName + " | " + expectedName);
        System.out.println("Phone     | " + uiPhone + " | " + expectedPhone);
        System.out.println("==========================================");

        // 3. Assert
        Assert.assertEquals(uiName, expectedName, "Full Name mismatch with registered data!");
        Assert.assertEquals(uiPhone, expectedPhone, "Phone Number mismatch with registered data!");
    }

    @When("Update Full Name with {string}")
    public void updateFullName(String newFullName) throws InterruptedException {
        if (newFullName.equalsIgnoreCase("UpdatedName")) {
            newFullName = new AccountPage().generateRandomName();
        }
        ScenarioContext.setLastFullName(newFullName);
        new AccountPage().updateFullName(newFullName);
    }

    @And("Update Phone Number with {string}")
    public void updatePhone(String newPhone) throws InterruptedException {
        if (newPhone.equalsIgnoreCase("updatedPhone")) {
            newPhone = new AccountPage().generateRandomPhone();
        }
        ScenarioContext.setLastPhone(newPhone);
        new AccountPage().updatePhone(newPhone);
    }

    @And("Update Gender with {string}")
    public void updateGender(String gender) throws InterruptedException {
        ScenarioContext.setLastGender(gender);
        new AccountPage().selectGender(gender);
    }

    @And("Update Username with {string}")
    public void updateUsername(String newUsername) throws InterruptedException {
        if (newUsername.equalsIgnoreCase("updatedUsername")) {
            newUsername = new AccountPage().generateRandomUsername();
        }
        ScenarioContext.setLastUsername(newUsername);
        new AccountPage().updateUsername(newUsername);
    }

    @And("Update Password with {string}")
    public void updatePassword(String newPassword) throws InterruptedException {
        if (newPassword.equalsIgnoreCase("updatedPassword")) {
            newPassword = new AccountPage().generateRandomPassword();
        }
        ScenarioContext.setLastPassword(newPassword);
        new AccountPage().updatePassword(newPassword);
    }

    @And("Update Confirm Password with {string}")
    public void updateConfirmPassword(String newConfirmPassword) throws InterruptedException {
        if (newConfirmPassword.equalsIgnoreCase("updatedConfirmPassword")) {
            newConfirmPassword = ScenarioContext.getLastPassword();
        }
        new AccountPage().updateConfirmPassword(newConfirmPassword);
    }

    @And("Click Save Changes Button")
    public void clickSaveChanges() throws InterruptedException {
        new AccountPage().clickSaveChanges();
    }

    @Then("Verify Update Account Success with message {string}")
    public void verifyUpdateAccountSuccess(String expectedMessage) throws InterruptedException {
        Assert.assertEquals(new AccountPage().getSuccessUpdateMessage(), expectedMessage);
    }

    @And("Verify updated Account data in My Account Page with updated data")
    public void verifyUpdatedAccountWithScenarioContext() throws Exception {
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
        System.out.println("VALIDASI DATA AKUN SETELAH UPDATE (DATABASE vs SCENARIO CONTEXT)");
        System.out.println("==========================================");
        System.out.println("Field     | Database Value      | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Full Name | " + rs.getString("name") + " | " + ScenarioContext.getLastFullName());
        System.out.println("Phone     | " + rs.getString("phone") + " | " + ScenarioContext.getLastPhone());
        System.out.println("Gender    | " + rs.getString("gender") + " | " + ScenarioContext.getLastGender());
        System.out.println("Username  | " + rs.getString("username") + " | " + ScenarioContext.getLastUsername());
        System.out.println("Password  | " + rs.getString("password") + " | " + ScenarioContext.getLastPassword());
        System.out.println("==========================================");
    }

}
