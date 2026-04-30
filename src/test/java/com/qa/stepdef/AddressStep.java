package com.qa.stepdef;

import com.qa.pages.AddressPage;
import com.qa.utils.ScenarioContext;
import com.qa.utils.TestUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import com.qa.utils.DataGeneratorUtil;

import java.util.Map;

public class AddressStep {

    @And("Verify Address deleted from database")
    public void verifyAddressDeletedFromDatabase() throws Exception {
        String street = ScenarioContext.getLastStreet();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getAddressByStreet(street);
        Assert.assertFalse(rs.next(), "Data alamat dengan jalan '" + street + "' masih ditemukan di database (gagal hapus)!");
    }

    @Then("Verify Address deleted from Address List")
    public void verifyAddressDeleted() {
        new AddressPage().noAddressFoundShow();
    }

    @When("Click Delete Button on first address in Address List")
    public void clickDeleteButton() {
        new AddressPage().deleteFirstAddress();
    }

    @And("Verify Updated Address data in database")
    public void verifyUpdatedAddressInDatabase() throws Exception {
        String street = ScenarioContext.getLastStreet();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getAddressByStreet(street);
        Assert.assertTrue(rs.next(), "Address with street '" + street + "' not found in database!");
        Assert.assertEquals(rs.getString("label"), ScenarioContext.getlastAddressLabel());
        Assert.assertEquals(rs.getString("street"), ScenarioContext.getLastStreet());
        Assert.assertEquals(rs.getString("city"), ScenarioContext.getLastCity());
        Assert.assertEquals(rs.getString("postal_code"), ScenarioContext.getLastPostalCode());
        TestUtils.log().info("Database validation successful for address: " + street);
    }

    @Then("Verify Updated Address show in Address List with correct data")
    public void verifyUpdatedAddressInList() {
        Map<String, String> uiData = new AddressPage().getNewAddressData();
        String expectedLabel = ScenarioContext.getlastAddressLabel();
        String expectedStreet = ScenarioContext.getLastStreet();
        String expectedCity = ScenarioContext.getLastCity();
        String expectedPostalCode = ScenarioContext.getLastPostalCode();
        Assert.assertEquals(uiData.get("label"), expectedLabel);
        Assert.assertEquals(uiData.get("street"), expectedStreet);
        Assert.assertEquals(uiData.get("city"), expectedCity);
        Assert.assertEquals(uiData.get("postalCode"), expectedPostalCode);
    }

    @And("Click Update Button")
    public void clickUpdateButton() {
        new AddressPage().clickUpdateButton();
    }

    @When("Click Edit Button on first address in Address List")
    public void clickEditButton()  {
        new AddressPage().clickEditFirstAddress();
    }

    @And("Verify New Address data in database")
    public void verifyNewAddressInDatabase() throws Exception {
        String street = ScenarioContext.getLastStreet();
        java.sql.ResultSet rs = com.qa.utils.DBManager.getAddressByStreet(street);
        Assert.assertTrue(rs.next(), "Address with street '" + street + "' not found in database!");
        Assert.assertEquals(rs.getString("label"), ScenarioContext.getlastAddressLabel());
        Assert.assertEquals(rs.getString("street"), ScenarioContext.getLastStreet());
        Assert.assertEquals(rs.getString("city"), ScenarioContext.getLastCity());
        Assert.assertEquals(rs.getString("postal_code"), ScenarioContext.getLastPostalCode());
        TestUtils.log().info("Database validation successful for address: " + street);
    }

    @Then("Verify New Address show in Address List with correct data")
    public void verifyNewAddressInList()  {
        Map<String, String> uiData = new AddressPage().getNewAddressData();
        String expectedLabel = ScenarioContext.getlastAddressLabel();
        String expectedStreet = ScenarioContext.getLastStreet();
        String expectedCity = ScenarioContext.getLastCity();
        String expectedPostalCode = ScenarioContext.getLastPostalCode();
        Assert.assertEquals(uiData.get("label"), expectedLabel);
        Assert.assertEquals(uiData.get("street"), expectedStreet);
        Assert.assertEquals(uiData.get("city"), expectedCity);
        Assert.assertEquals(uiData.get("postalCode"), expectedPostalCode);
    }

    @And("Click Add Address Button")
    public void clickAddAddressButton()  {
        new AddressPage().clickAddAddress();
    }

    @And("Input Postal Code with {string}")
    public void inputPostalCode(String postalCode)  {
        ScenarioContext.setLastPostalCode(postalCode);
        new AddressPage().inputPostalCode(postalCode);
    }

    @And("Input a random Postal Code")
    public void inputRandomPostalCode()  {
        String postalCode = DataGeneratorUtil.generateRandomPostalCode();
        ScenarioContext.setLastPostalCode(postalCode);
        new AddressPage().inputPostalCode(postalCode);
    }

    @And("Input City with {string}")
    public void inputCity(String city)  {
        ScenarioContext.setLastCity(city);
        new AddressPage().inputCity(city);
    }

    @And("Input a random City")
    public void inputRandomCity()  {
        String city = DataGeneratorUtil.generateRandomCity();
        ScenarioContext.setLastCity(city);
        new AddressPage().inputCity(city);
    }

    @And("Input Street with {string}")
    public void inputStreet(String street)  {
        ScenarioContext.setLastStreet(street);
        new AddressPage().inputStreet(street);
    }

    @And("Input a random Street")
    public void inputRandomStreet()  {
        String street = DataGeneratorUtil.generateRandomStreet();
        ScenarioContext.setLastStreet(street);
        new AddressPage().inputStreet(street);
    }

    @When("Input Label with {string}")
    public void inputLabel(String label)  {
        ScenarioContext.setLastAddressLabel(label);
        new AddressPage().inputLabel(label);
    }

    @When("Input a random Label")
    public void inputRandomLabel()  {
        String label = "Address-" + DataGeneratorUtil.generateRandomNumber(3);
        ScenarioContext.setLastAddressLabel(label);
        new AddressPage().inputLabel(label);
    }

    @Given("Access Address Page")
    public void accessAddress()  {
        new AddressPage().toAddressPage();
    }
}
