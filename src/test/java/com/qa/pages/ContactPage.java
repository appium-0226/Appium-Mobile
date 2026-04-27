package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class ContactPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_contact_name\")")
    @iOSXCUITFindBy(accessibility = "input_contact_name")
    private WebElement nameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_contact_phone\")")
    @iOSXCUITFindBy(accessibility = "input_contact_phone")
    private WebElement phoneField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_contact_email\")")
    @iOSXCUITFindBy(accessibility = "input_contact_email")
    private WebElement emailField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Add Contact\")")
    @iOSXCUITFindBy(accessibility = "btn_add_contact")
    private WebElement addContactBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"nav_contact\")")
    @iOSXCUITFindBy(accessibility = "nav_contact")
    private WebElement contactNavbar;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*contact_name_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'contact_name_.*'`]")
    private WebElement contactNameValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*contact_phone_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'contact_phone_.*'`]")
    private WebElement contactPhoneValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*contact_email_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name MATCHES 'contact_email_.*'`]")
    private WebElement contactEmailValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*btn_edit_contact_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeAny[`name BEGINSWITH 'btn_edit_contact_'`][1]")
    private WebElement editContactButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\".*btn_delete_contact_.*\")")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeAny[`name BEGINSWITH 'btn_delete_contact_'`][1]")
    private WebElement deleteContactButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"No contacts found\")")
    @iOSXCUITFindBy(accessibility = "No contacts found")
    private WebElement noContactFoundMessage;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Update\")")
    @iOSXCUITFindBy(accessibility = "btn_update_contact")
    private WebElement updateContactButton;

    public ContactPage() {
    }

    public void clickUpdateContactButton(){
        updateContactButton.click();
    }

    public void noContactFoundShow(){
        noContactFoundMessage.isDisplayed();
    }

    public void clickDeleteContact() {
        deleteContactButton.click();
    }

    public void clickEditContact() {
        editContactButton.click();
    }

    public Map<String, String> getContactDetails() {
        Map<String, String> data = new HashMap<>();
        data.put("name", getText(contactNameValue, "Contact Name"));
        data.put("phone", getText(contactPhoneValue, "Contact Phone}"));
        data.put("email", getText(contactEmailValue, "Contact Email}"));
        System.out.println("name: " + data.get("name") + " phone: " + data.get("phone") + " email: " + data.get("email"));
        return data;
    }

    public void clickAddContact() {
        addContactBtn.click();
    }

    public void inputContactEmail(String email) {
        sendKeys(emailField, email);
    }

    public void inputContactPhone(String phone) {
        sendKeys(phoneField, phone);
    }

    public void inputContactName(String name) throws InterruptedException {
        sendKeys(nameField, name);
    }

    public void accessContactPage() {
        contactNavbar.click();
    }


}
