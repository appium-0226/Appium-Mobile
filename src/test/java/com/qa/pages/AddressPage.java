package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class AddressPage extends BasePage {

    TestUtils utils = new TestUtils();

    private final By labelField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_address_label\")"),
            AppiumBy.accessibilityId("input_address_label")
    );

    private final By streetField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_address_street\")"),
            AppiumBy.accessibilityId("input_address_street")
    );

    private final By cityField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_address_city\")"),
            AppiumBy.accessibilityId("input_address_city")
    );

    private final By postalCodeField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_address_postal\")"),
            AppiumBy.accessibilityId("input_address_postal")
    );

    private final By addAddressBtn = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Add Address\")"),
            AppiumBy.accessibilityId("btn_add_address")
    );

    private final By labelValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*address_label_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'address_label_.*'`]")
    );

    private final By streetValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*address_street_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'address_street_.*'`]")
    );

    private final By cityPostalCodeValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*address_city_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'address_city_.*'`]")
    );

    private final By addressNavbar = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"nav_address\")"),
            AppiumBy.accessibilityId("nav_address")
    );

    private final By editFirstAddressBtn = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*btn_edit_address_.*\").instance(0)"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeAny[`name BEGINSWITH 'btn_edit_address_'`][1]")
    );

    private final By deleteFirstAddressBtn = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*btn_delete_address_.*\").instance(0)"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeAny[`name BEGINSWITH 'btn_delete_address_'`][1]")
    );

    private final By updateButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Update\")"),
            AppiumBy.accessibilityId("btn_update_address")
    );

    private final By noAddressFoundMessage = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"No addresses found\")"),
            AppiumBy.accessibilityId("No addresses found")
    );

    public AddressPage() {
    }

    public void noAddressFoundShow(){
        waitForVisibility(noAddressFoundMessage);
    }

    public void clickUpdateButton(){
        click(updateButton);
    }

    public void deleteFirstAddress() {
        click(deleteFirstAddressBtn);
    }

    public void clickEditFirstAddress(){
        click(editFirstAddressBtn);
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
        return data;
    }

    public void clickAddAddress() {
        click(addAddressBtn);
    }

    public void inputPostalCode(String postalCode)  {
        sendKeys(postalCodeField, postalCode);
    }

    public void inputCity(String city)  {
        sendKeys(cityField, city);
    }

    public void inputStreet(String street)  {
        sendKeys(streetField, street);
    }

    public void inputLabel(String label)  {
        sendKeys(labelField, label);
    }

    public void toAddressPage()  {
        click(addressNavbar);
    }

}
