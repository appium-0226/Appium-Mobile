package com.qa.stepdef;

import com.qa.pages.AddressPage;
import com.qa.pages.ContactPage;
import com.qa.pages.LoginPage;
import com.qa.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.testng.Assert;

import java.util.Map;

public class ContactStep {

    @And("Verify Contact deleted from database")
    public void verifyContactDeletedFromDatabase() throws Exception {
        String name = ScenarioContext.getLastPContactName();
        String query = "SELECT * FROM contacts WHERE name = '" + name + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(query);

        // Assert bahwa data TIDAK ditemukan (rs.next() harus false)
        Assert.assertFalse(rs.next(), "Data kontak dengan nama '" + name + "' masih ditemukan di database (gagal hapus)!");

        System.out.println("==========================================");
        System.out.println("VALIDASI DELETE KONTAK SUCCESS (DB)");
        System.out.println("==========================================");
        System.out.println("Contact with name '" + name + "' is NOT found in DB as expected.");
        System.out.println("==========================================");
    }

    @Then("Verify Contact deleted from Contact List")
    public void verifyContactDeleted() {
        new ContactPage().noContactFoundShow();
    }

    @When("Click Delete Button on first contact in Contact List")
    public void clickDeletetButton() {
        new ContactPage().clickDeleteContact();
    }

    @And("Verify Updated Contact data in database")
    public void verifyUpdatedContactInDatabase() throws Exception {
        String name = ScenarioContext.getLastPContactName();
        String query = "SELECT * FROM contacts WHERE name = '" + name + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(query);

        Assert.assertTrue(rs.next(), "Data kontak dengan nama '" + name + "' tidak ditemukan di database!");

        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastPContactName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhoneContact());
        Assert.assertEquals(rs.getString("email"), ScenarioContext.getLastEmailContact());

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA KONTAK UPDATE (DB)");
        System.out.println("==========================================");
        System.out.println("Data kontak dengan nama '" + name + "' ditemukan di database dengan data yang sesuai.");
        System.out.println("==========================================");
    }

    @Then("Verify Updated Contact show in Contact List with correct data")
    public void verifyUpdatedContactInList() {
        Map<String, String> uiData = new ContactPage().getContactDetails();
        String expectedName = ScenarioContext.getLastPContactName();
        String expectedPhone = ScenarioContext.getLastPhoneContact();
        String expectedEmail = ScenarioContext.getLastEmailContact();

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA KONTAK UPDATE (UI vs INPUT DATA)");
        System.out.println("==========================================");
        System.out.println("Field     | UI Value           | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Name      | " + uiData.get("name") + " | " + expectedName);
        System.out.println("Phone     | " + uiData.get("phone") + " | " + expectedPhone);
        System.out.println("Email     | " + uiData.get("email") + " | " + expectedEmail);
        System.out.println("==========================================");

        Assert.assertEquals(uiData.get("name"), expectedName);
        Assert.assertEquals(uiData.get("phone"), expectedPhone);
        Assert.assertEquals(uiData.get("email"), expectedEmail);
    }

    @When("Click Edit Button on first contact in Contact List")
    public void clickEditButton() {
        new ContactPage().clickEditContact();
    }

    @And("Verify New Contact data in database")
    public void verifyNewContactInDatabase() throws Exception {
        String name = ScenarioContext.getLastPContactName();
        String query = "SELECT * FROM contacts WHERE name = '" + name + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(query);

        Assert.assertTrue(rs.next(), "Data kontak dengan nama '" + name + "' tidak ditemukan di database!");

        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastPContactName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhoneContact());
        Assert.assertEquals(rs.getString("email"), ScenarioContext.getLastEmailContact());

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA KONTAK BARU (DB)");
        System.out.println("==========================================");
        System.out.println("Data kontak dengan nama '" + name + "' ditemukan di database dengan data yang sesuai.");
        System.out.println("==========================================");
    }

    @Then("Verify New Contact show in Contact List with correct data")
    public void verifyNewContactInList() {
        Map<String, String> uiData = new ContactPage().getContactDetails();
        String expectedName = ScenarioContext.getLastPContactName();
        String expectedPhone = ScenarioContext.getLastPhoneContact();
        String expectedEmail = ScenarioContext.getLastEmailContact();

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA KONTAK BARU (UI vs INPUT DATA)");
        System.out.println("==========================================");
        System.out.println("Field     | UI Value           | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Name      | " + uiData.get("name") + " | " + expectedName);
        System.out.println("Phone     | " + uiData.get("phone") + " | " + expectedPhone);
        System.out.println("Email     | " + uiData.get("email") + " | " + expectedEmail);
        System.out.println("==========================================");

        Assert.assertEquals(uiData.get("name"), expectedName);
        Assert.assertEquals(uiData.get("phone"), expectedPhone);
        Assert.assertEquals(uiData.get("email"), expectedEmail);
    }

    @And("Click Update Contact Button")
    public void clickUpdateContactButton() {
        new ContactPage().clickUpdateContactButton();
    }

    @And("Click Add Contact Button")
    public void clickAddContactButton() {
        new ContactPage().clickAddContact();
    }

    @And("Input Contact Email with {string}")
    public void inputEmail(String email) throws InterruptedException {
        if (email.equalsIgnoreCase("RandomEmail")) {
            email = new ContactPage().generateRandomEmail();
        }
        ScenarioContext.setLastEmailContact(email);
        new ContactPage().inputContactEmail(email);
    }

    @And("Input Contact Phone Number as {string}")
    public void inputPhoneNumber(String phoneNumber) throws InterruptedException {
        if (phoneNumber.equalsIgnoreCase("RandomNumber")) {
            phoneNumber = new ContactPage().generateRandomPhone();
        }
        ScenarioContext.setLastPhoneContact(phoneNumber);
        new ContactPage().inputContactPhone(phoneNumber);
    }

    @When("Input Contact Name with {string}")
    public void inputContactName(String contactName) throws InterruptedException {
        if (contactName.equalsIgnoreCase("RandomContactName")) {
            contactName = "Contact-" + new ContactPage().generateRandomNumber(3);
        }
        ScenarioContext.setLastContactName(contactName);
        new ContactPage().inputContactName(contactName);
    }

    @When("Access Contact Page")
    public void inputUsername() throws InterruptedException {
        new ContactPage().accessContactPage();
    }

}
