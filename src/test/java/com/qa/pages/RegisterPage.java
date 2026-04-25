package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_name\")")
    @iOSXCUITFindBy(accessibility = "input_name")
    private WebElement fullNameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_phone\")")
    @iOSXCUITFindBy(accessibility = "input_phone")
    private WebElement phoneField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"radio_Male\")")
    @iOSXCUITFindBy(accessibility = "radio_Male")
    private WebElement maleGender;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"radio_Female\")")
    @iOSXCUITFindBy(accessibility = "radio_Female")
    private WebElement femaleGender;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_username\")")
    @iOSXCUITFindBy(accessibility = "input_username")
    private WebElement usernameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_password\")")
    @iOSXCUITFindBy(accessibility = "input_password")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_confirm_password\")")
    @iOSXCUITFindBy(accessibility = "input_confirm_password")
    private WebElement confirmPasswordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.View\").instance(1)")
    @iOSXCUITFindBy(accessibility = "btn_register")
    private WebElement registerButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Already have an account? Login\")")
    @iOSXCUITFindBy(accessibility = "btn_go_login")
    private WebElement loginLink;

    public RegisterPage() {
    }

    public void inputFullName(String username) throws InterruptedException {
        sendKeys(fullNameField, username);
    }

    public void inputPhone(String phone) {
        sendKeys(phoneField, phone);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            maleGender.click();
        } else if (gender.equalsIgnoreCase("Female")) {
            femaleGender.click();
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
        registerButton.click();
    }

    public void clickLoginLink() {
        loginLink.click();
    }

}
