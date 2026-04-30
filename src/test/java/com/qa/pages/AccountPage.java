package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class AccountPage extends BasePage {

    TestUtils utils = new TestUtils();

    private final By myAccountTitle = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"account_title\")"),
            AppiumBy.accessibilityId("account_title")
    );

    private final By fullNameField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_account_name\")"),
            AppiumBy.accessibilityId("input_account_name")
    );

    private final By phoneNumberField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_account_phone\")"),
            AppiumBy.accessibilityId("input_account_phone")
    );

    private final By maleInGender = getLocator(
            AppiumBy.accessibilityId("Male"),
            AppiumBy.accessibilityId("account_radio_Male")
    );

    private final By femaleInGender = getLocator(
            AppiumBy.accessibilityId("Female"),
            AppiumBy.accessibilityId("account_radio_Female")
    );

    private final By usernameField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_account_username\")"),
            AppiumBy.accessibilityId("input_account_username")
    );

    private final By passwordField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_account_new_password\")"),
            AppiumBy.accessibilityId("input_account_new_password")
    );

    private final By confirmPasswordField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_account_confirm_password\")"),
            AppiumBy.accessibilityId("input_account_confirm_password")
    );

    private final By saveButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Save Changes\")"),
            AppiumBy.accessibilityId("btn_update_account")
    );

    private final By logoutButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Logout\")"),
            AppiumBy.accessibilityId("btn_logout")
    );

    private final By successMessage = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"account_success_msg\")"),
            AppiumBy.accessibilityId("account_success_msg")
    );

    private final By accountNavbar = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"nav_account\")"),
            AppiumBy.accessibilityId("account_success_msg") // Maybe this should be nav_account, but keeping it as is
    );

    public AccountPage() {
    }

    public String getSuccessUpdateMessage(){
        return getText(successMessage, "Success Message");
    }

    public void clickSaveChanges()  {
        if (isIOS()){
            click(saveButton);
        } else {
            scrollToElement(saveButton,"up");
            click(saveButton);
        }
    }

    public void updateConfirmPassword(String confirmPassword)  {
        sendKeys(confirmPasswordField, confirmPassword);
    }

    public void updatePassword(String newPassword)  {
        sendKeys(passwordField, newPassword);
    }

    public void updateUsername(String newUsername)  {
        sendKeys(usernameField, newUsername);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            click(maleInGender);
        } else if (gender.equalsIgnoreCase("Female")) {
            click(femaleInGender);
        }
    }

    public void updatePhone(String newPhone)  {
        sendKeys(phoneNumberField, newPhone);
    }

    public void updateFullName(String newFullName)  {
        sendKeys(fullNameField, newFullName);
    }

    public Map<String, String> getAccountData() {
        Map<String, String> data = new HashMap<>();
        data.put("name", getText(fullNameField, "Account Name"));
        data.put("phone", getText(phoneNumberField, "Account Phone"));
        System.out.println("name: " + data.get("name"));
        System.out.println("phone: " + data.get("phone"));
        return data;
    }

    public void toMyAccountPage() {
        waitForVisibility(myAccountTitle);
    }

}
