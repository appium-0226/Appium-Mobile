package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class AccountPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"account_title\")")
    @iOSXCUITFindBy(accessibility = "account_title")
    private WebElement myAccountTitle;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_account_name\")")
    @iOSXCUITFindBy(accessibility = "input_account_name")
    private WebElement fullNameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_account_phone\")")
    @iOSXCUITFindBy(accessibility = "input_account_phone")
    private WebElement phoneNumberField;

    @AndroidFindBy(accessibility = "Male")
    @iOSXCUITFindBy(accessibility = "account_radio_Male")
    private WebElement maleInGender;

    @AndroidFindBy(accessibility = "Female")
    @iOSXCUITFindBy(accessibility = "account_radio_Female")
    private WebElement femaleInGender;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_account_username\")")
    @iOSXCUITFindBy(accessibility = "input_account_username")
    private WebElement usernameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_account_new_password\")")
    @iOSXCUITFindBy(accessibility = "input_account_new_password")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_account_confirm_password\")")
    @iOSXCUITFindBy(accessibility = "input_account_confirm_password")
    private WebElement confirmPasswordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Save Changes\")")
    @iOSXCUITFindBy(accessibility = "btn_update_account")
    private WebElement saveButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Logout\")")
    @iOSXCUITFindBy(accessibility = "btn_logout")
    private WebElement logoutButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"account_success_msg\")")
    @iOSXCUITFindBy(accessibility = "account_success_msg")
    private WebElement successMessage;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"nav_account\")")
    @iOSXCUITFindBy(accessibility = "account_success_msg")
    private WebElement accountNavbar;

    public AccountPage() {
    }

    public String getSuccessUpdateMessage(){
        return successMessage.getText();
    }

    public void clickSaveChanges() throws InterruptedException {
        if (isIOS()){
            saveButton.click();
        } else {
            scrollToElement(saveButton,"up");
            saveButton.click();
        }

    }

    public void updateConfirmPassword(String confirmPassword) throws InterruptedException {
        sendKeys(confirmPasswordField, confirmPassword);
    }

    public void updatePassword(String newPassword) throws InterruptedException {
        sendKeys(passwordField, newPassword);
    }

    public void updateUsername(String newUsername) throws InterruptedException {
        sendKeys(usernameField, newUsername);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            maleInGender.click();
        } else if (gender.equalsIgnoreCase("Female")) {
            femaleInGender.click();
        }
    }

    public void updatePhone(String newPhone) throws InterruptedException {
        sendKeys(phoneNumberField, newPhone);
    }

    public void updateFullName(String newFullName) throws InterruptedException {
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
        myAccountTitle.isDisplayed();
    }

}
