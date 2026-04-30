package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    TestUtils utils = new TestUtils();

    private final By usernameTxtFld = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_login_username\")"),
            AppiumBy.accessibilityId("input_login_username"));

    private final By passwordTxtFld = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"input_login_password\")"),
            AppiumBy.accessibilityId("input_login_password"));

    private final By loginBtn = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(1)"),
            AppiumBy.accessibilityId("btn_login"));

    private final By registerLink = getLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"Don't have an account? Register\")"),
            AppiumBy.accessibilityId("btn_go_register"));

    public LoginPage() {
    }

    public void enterUserName(String username) {
        sendKeys(usernameTxtFld, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordTxtFld, password);
    }

    public void clickLogin() {
        click(loginBtn);

    }

    public void clickRegisterLink() {
        click(registerLink);
    }

    public void toLoginPage() {
        waitForVisibility(usernameTxtFld);
        waitForVisibility(passwordTxtFld);
    }

}
