package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class ContactPage extends BasePage {

    TestUtils utils = new TestUtils();

    private final By nameField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_contact_name\")"),
            AppiumBy.accessibilityId("input_contact_name")
    );

    private final By phoneField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_contact_phone\")"),
            AppiumBy.accessibilityId("input_contact_phone")
    );

    private final By emailField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_contact_email\")"),
            AppiumBy.accessibilityId("input_contact_email")
    );

    private final By addContactBtn = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Add Contact\")"),
            AppiumBy.accessibilityId("btn_add_contact")
    );

    private final By contactNavbar = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"nav_contact\")"),
            AppiumBy.accessibilityId("nav_contact")
    );

    private final By contactNameValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*contact_name_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'contact_name_.*'`]")
    );

    private final By contactPhoneValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*contact_phone_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'contact_phone_.*'`]")
    );

    private final By contactEmailValue = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*contact_email_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name MATCHES 'contact_email_.*'`]")
    );

    private final By editContactButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*btn_edit_contact_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeAny[`name BEGINSWITH 'btn_edit_contact_'`][1]")
    );

    private final By deleteContactButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*btn_delete_contact_.*\")"),
            AppiumBy.iOSClassChain("**/XCUIElementTypeAny[`name BEGINSWITH 'btn_delete_contact_'`][1]")
    );

    private final By noContactFoundMessage = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"No contacts found\")"),
            AppiumBy.accessibilityId("No contacts found")
    );

    private final By updateContactButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Update\")"),
            AppiumBy.accessibilityId("btn_update_contact")
    );

    public ContactPage() {
    }

    public void clickUpdateContactButton(){
        click(updateContactButton);
    }

    public void noContactFoundShow(){
        waitForVisibility(noContactFoundMessage);
    }

    public void clickDeleteContact() {
        click(deleteContactButton);
    }

    public void clickEditContact() {
        click(editContactButton);
    }

    public Map<String, String> getContactDetails() {
        Map<String, String> data = new HashMap<>();
        data.put("name", getText(contactNameValue, "Contact Name"));
        data.put("phone", getText(contactPhoneValue, "Contact Phone")); // Fixed trailing bracket '}' from original code
        data.put("email", getText(contactEmailValue, "Contact Email")); // Fixed trailing bracket '}' from original code
        System.out.println("name: " + data.get("name") + " phone: " + data.get("phone") + " email: " + data.get("email"));
        return data;
    }

    public void clickAddContact() {
        click(addContactBtn);
    }

    public void inputContactEmail(String email) {
        sendKeys(emailField, email);
    }

    public void inputContactPhone(String phone) {
        sendKeys(phoneField, phone);
    }

    public void inputContactName(String name)  {
        sendKeys(nameField, name);
    }

    public void accessContactPage() {
        click(contactNavbar);
    }


}
