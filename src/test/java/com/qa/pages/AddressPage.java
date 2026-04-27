package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class AddressPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_address_label\")")
    @iOSXCUITFindBy(accessibility = "input_address_label")
    private WebElement labelField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_address_street\")")
    @iOSXCUITFindBy(accessibility = "input_address_street")
    private WebElement streetField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_address_city\")")
    @iOSXCUITFindBy(accessibility = "input_address_city")
    private WebElement cityField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_address_postal\")")
    @iOSXCUITFindBy(accessibility = "input_address_postal")
    private WebElement postalCodeField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Add Address\")")
    @iOSXCUITFindBy(accessibility = "btn_add_address")
    private WebElement addAddressBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*address_label_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'address_label_.*'`]")
    private WebElement labelValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*address_street_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'address_street_.*'`]")
    private WebElement streetValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*address_city_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'address_city_.*'`]")
    private WebElement cityPostalCodeValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"nav_address\")")
    @iOSXCUITFindBy(accessibility = "nav_address")
    private WebElement addressNavbar;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*btn_edit_address_.*\").instance(0)")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeAny[`name BEGINSWITH 'btn_edit_address_'`][1]")
    private WebElement editFirstAddressBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*btn_delete_address_.*\").instance(0)")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeAny[`name BEGINSWITH 'btn_delete_address_'`][1]")
    private WebElement deleteFirstAddressBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Update\")")
    @iOSXCUITFindBy(accessibility = "btn_update_address")
    private WebElement updateButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"No addresses found\")")
    @iOSXCUITFindBy(accessibility = "No addresses found")
    private WebElement noAddressFoundMessage;

    public AddressPage() {
    }

    public void noAddressFoundShow(){
        noAddressFoundMessage.isDisplayed();
    }

    public void clickUpdateButton(){
        updateButton.click();
    }

    public void deleteFirstAddress() {
        deleteFirstAddressBtn.click();
    }

    public void clickEditFirstAddress(){
        editFirstAddressBtn.click();
    }

    public Map<String, String> getNewAddressData() {
        Map<String, String> data = new HashMap<>();
        data.put("label", getText(labelValue, "Label"));
        data.put("street", getText(streetValue, "Street"));
        String cityPostalText = getText(cityPostalCodeValue, "City & Postal Code");
        if (cityPostalText.contains(",")) {
            String[] parts = cityPostalText.split(",");
            data.put("city", parts[0].trim());
            data.put("postalCode", parts[1].trim());
        } else {
            data.put("city", cityPostalText);
            data.put("postalCode", "");
        }
        System.out.println("label: " + data.get("label"));
        System.out.println("street: " + data.get("street"));
        System.out.println("city: " + data.get("city"));
        System.out.println("postalCode: " + data.get("postalCode"));
        return data;
    }

    public void clickAddAddress() {
        addAddressBtn.click();
    }

    public void inputPostalCode(String postalCode) throws InterruptedException {
        sendKeys(postalCodeField, postalCode);
    }

    public void inputCity(String city) throws InterruptedException {
        sendKeys(cityField, city);
    }

    public void inputStreet(String street) throws InterruptedException {
        sendKeys(streetField, street);
    }

    public void inputLabel(String label) throws InterruptedException {
        sendKeys(labelField, label);
    }

    public void toAddressPage() throws InterruptedException {
        addressNavbar.click();
    }

}
