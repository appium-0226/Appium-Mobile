package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {

    TestUtils utils = new TestUtils();

    private final By fullNameField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_name\")"),
            AppiumBy.accessibilityId("input_name")
    );

    private final By phoneField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_phone\")"),
            AppiumBy.accessibilityId("input_phone")
    );

    private final By maleGender = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"radio_Male\")"),
            AppiumBy.accessibilityId("radio_Male")
    );

    private final By femaleGender = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"radio_Female\")"),
            AppiumBy.accessibilityId("radio_Female")
    );

    private final By usernameField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_username\")"),
            AppiumBy.accessibilityId("input_username")
    );

    private final By passwordField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_password\")"),
            AppiumBy.accessibilityId("input_password")
    );

    private final By confirmPasswordField = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_confirm_password\")"),
            AppiumBy.accessibilityId("input_confirm_password")
    );

    private final By registerButton = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(1)"),
            AppiumBy.accessibilityId("btn_register")
    );

    private final By loginLink = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Already have an account? Login\")"),
            AppiumBy.accessibilityId("btn_go_login")
    );

    public RegisterPage() {
    }

    public void inputFullName(String username)  {
        sendKeys(fullNameField, username);
    }

    public void inputPhone(String phone) {
        sendKeys(phoneField, phone);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            click(maleGender);
        } else if (gender.equalsIgnoreCase("Female")) {
            click(femaleGender);
        }
    }

    public void inputUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void inputPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void inputConfirmPassword(String confirmPassword) {
        sendKeys(confirmPasswordField, confirmPassword);
    }

    public void clickRegister() {
        click(registerButton);
    }

    public void clickLoginLink() {
        click(loginLink);
    }

}
