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

import com.qa.utils.DataGeneratorUtil;

import java.util.Map;

public class ContactStep {

    @And("Verify Contact deleted from database")
    public void verifyContactDeletedFromDatabase() throws Exception {
        String name = ScenarioContext.getLastContactName();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getContactByName(name);
        Assert.assertFalse(rs.next(), "Data kontak dengan nama '" + name + "' masih ditemukan di database (gagal hapus)!");
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
        String name = ScenarioContext.getLastContactName();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getContactByName(name);
        Assert.assertTrue(rs.next(), "Data kontak dengan nama '" + name + "' tidak ditemukan di database!");
        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastContactName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhoneContact());
        Assert.assertEquals(rs.getString("email"), ScenarioContext.getLastEmailContact());
    }

    @Then("Verify Updated Contact show in Contact List with correct data")
    public void verifyUpdatedContactInList() {
        Map<String, String> uiData = new ContactPage().getContactDetails();
        String expectedName = ScenarioContext.getLastContactName();
        String expectedPhone = ScenarioContext.getLastPhoneContact();
        String expectedEmail = ScenarioContext.getLastEmailContact();
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
        String name = ScenarioContext.getLastContactName();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getContactByName(name);
        Assert.assertTrue(rs.next(), "Data kontak dengan nama '" + name + "' tidak ditemukan di database!");
        Assert.assertEquals(rs.getString("name"), ScenarioContext.getLastContactName());
        Assert.assertEquals(rs.getString("phone"), ScenarioContext.getLastPhoneContact());
        Assert.assertEquals(rs.getString("email"), ScenarioContext.getLastEmailContact());
    }

    @Then("Verify New Contact show in Contact List with correct data")
    public void verifyNewContactInList() {
        Map<String, String> uiData = new ContactPage().getContactDetails();
        String expectedName = ScenarioContext.getLastContactName();
        String expectedPhone = ScenarioContext.getLastPhoneContact();
        String expectedEmail = ScenarioContext.getLastEmailContact();
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
    public void inputEmail(String email)  {
        ScenarioContext.setLastEmailContact(email);
        new ContactPage().inputContactEmail(email);
    }

    @And("Input a random Contact Email")
    public void inputRandomEmail()  {
        String email = DataGeneratorUtil.generateRandomEmail();
        ScenarioContext.setLastEmailContact(email);
        new ContactPage().inputContactEmail(email);
    }

    @And("Input Contact Phone Number as {string}")
    public void inputPhoneNumber(String phoneNumber)  {
        ScenarioContext.setLastPhoneContact(phoneNumber);
        new ContactPage().inputContactPhone(phoneNumber);
    }

    @And("Input a random Contact Phone Number")
    public void inputRandomPhoneNumber()  {
        String phoneNumber = DataGeneratorUtil.generateRandomPhone();
        ScenarioContext.setLastPhoneContact(phoneNumber);
        new ContactPage().inputContactPhone(phoneNumber);
    }

    @When("Input Contact Name with {string}")
    public void inputContactName(String contactName)  {
        ScenarioContext.setLastContactName(contactName);
        new ContactPage().inputContactName(contactName);
    }

    @When("Input a random Contact Name")
    public void inputRandomContactName()  {
        String contactName = "Contact-" + DataGeneratorUtil.generateRandomNumber(3);
        ScenarioContext.setLastContactName(contactName);
        new ContactPage().inputContactName(contactName);
    }

    @When("Access Contact Page")
    public void inputUsername()  {
        new ContactPage().accessContactPage();
    }

}
