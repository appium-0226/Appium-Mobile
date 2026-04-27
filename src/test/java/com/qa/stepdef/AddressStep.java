package com.qa.stepdef;

import com.qa.pages.AddressPage;
import com.qa.utils.ScenarioContext;
import com.qa.utils.TestUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

public class AddressStep {

    @And("Verify Address deleted from database")
    public void verifyAddressDeletedFromDatabase() throws Exception {
        String street = ScenarioContext.getLastStreet();
        String query = "SELECT * FROM addresses WHERE street = '" + street + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(query);

        // Assert bahwa data TIDAK ditemukan (rs.next() harus false)
        Assert.assertFalse(rs.next(), "Data alamat dengan jalan '" + street + "' masih ditemukan di database (gagal hapus)!");

        System.out.println("==========================================");
        System.out.println("VALIDASI DELETE ALAMAT SUCCESS (DB)");
        System.out.println("==========================================");
        System.out.println("Address with street '" + street + "' is NOT found in DB as expected.");
        System.out.println("==========================================");
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
        String userQuery = "SELECT * FROM addresses WHERE street = '" + street + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(userQuery);

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

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA ALAMAT BARU (UI vs INPUT DATA)");
        System.out.println("==========================================");
        System.out.println("Field       | UI Value           | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Label       | " + uiData.get("label") + " | " + expectedLabel);
        System.out.println("Street      | " + uiData.get("street") + " | " + expectedStreet);
        System.out.println("City        | " + uiData.get("city") + " | " + expectedCity);
        System.out.println("Postal Code | " + uiData.get("postalCode") + " | " + expectedPostalCode);
        System.out.println("==========================================");

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
    public void clickEditButton() throws InterruptedException {
        new AddressPage().clickEditFirstAddress();
    }

    @And("Verify New Address data in database")
    public void verifyNewAddressInDatabase() throws Exception {
        String street = ScenarioContext.getLastStreet();
        String userQuery = "SELECT * FROM addresses WHERE street = '" + street + "'";
        java.sql.ResultSet rs = com.qa.utils.DBManager.executeQuery(userQuery);

        Assert.assertTrue(rs.next(), "Address with street '" + street + "' not found in database!");

        Assert.assertEquals(rs.getString("label"), ScenarioContext.getlastAddressLabel());
        Assert.assertEquals(rs.getString("street"), ScenarioContext.getLastStreet());
        Assert.assertEquals(rs.getString("city"), ScenarioContext.getLastCity());
        Assert.assertEquals(rs.getString("postal_code"), ScenarioContext.getLastPostalCode());

        TestUtils.log().info("Database validation successful for address: " + street);
    }

    @Then("Verify New Address show in Address List with correct data")
    public void verifyNewAddressInList() throws InterruptedException {
        Map<String, String> uiData = new AddressPage().getNewAddressData();
        
        String expectedLabel = ScenarioContext.getlastAddressLabel();
        String expectedStreet = ScenarioContext.getLastStreet();
        String expectedCity = ScenarioContext.getLastCity();
        String expectedPostalCode = ScenarioContext.getLastPostalCode();

        System.out.println("==========================================");
        System.out.println("VALIDASI DATA ALAMAT BARU (UI vs INPUT DATA)");
        System.out.println("==========================================");
        System.out.println("Field       | UI Value           | Expected Value");
        System.out.println("------------------------------------------");
        System.out.println("Label       | " + uiData.get("label") + " | " + expectedLabel);
        System.out.println("Street      | " + uiData.get("street") + " | " + expectedStreet);
        System.out.println("City        | " + uiData.get("city") + " | " + expectedCity);
        System.out.println("Postal Code | " + uiData.get("postalCode") + " | " + expectedPostalCode);
        System.out.println("==========================================");

        Assert.assertEquals(uiData.get("label"), expectedLabel);
        Assert.assertEquals(uiData.get("street"), expectedStreet);
        Assert.assertEquals(uiData.get("city"), expectedCity);
        Assert.assertEquals(uiData.get("postalCode"), expectedPostalCode);
    }

    @And("Click Add Address Button")
    public void clickAddAddressButton() throws InterruptedException {
        new AddressPage().clickAddAddress();
    }

    @And("Input Postal Code with {string}")
    public void inputPostalCode(String postalCode) throws InterruptedException {
        if (postalCode.equalsIgnoreCase("RandomPostalCode")) {
            postalCode = new AddressPage().generateRandomPostalCode();
        }
        ScenarioContext.setLastPostalCode(postalCode);
        new AddressPage().inputPostalCode(postalCode);
    }

    @And("Input City with {string}")
    public void inputCity(String city) throws InterruptedException {
        if (city.equalsIgnoreCase("RandomCity")) {
            city = new AddressPage().generateRandomCity();
        }
        ScenarioContext.setLastCity(city);
        new AddressPage().inputCity(city);
    }

    @And("Input Street with {string}")
    public void inputStreet(String street) throws InterruptedException {
        if (street.equalsIgnoreCase("RandomStreet")) {
            street = new AddressPage().generateRandomStreet();
        }
        ScenarioContext.setLastStreet(street);
        new AddressPage().inputStreet(street);
    }

    @When("Input Label with {string}")
    public void inputLabel(String label) throws InterruptedException {
        if (label.equalsIgnoreCase("RandomLabel")) {
            label = "Address-" + new AddressPage().generateRandomNumber(3);
        }
        ScenarioContext.setlastAddressLabel(label);
        new AddressPage().inputLabel(label);
    }

    @Given("Access Address Page")
    public void accessAddress() throws InterruptedException {
        new AddressPage().toAddressPage();
    }
}
