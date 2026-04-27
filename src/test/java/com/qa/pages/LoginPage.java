package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_login_username\")")
    @iOSXCUITFindBy(id = "input_login_username")
    private WebElement usernameTxtFld;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"input_login_password\")")
    @iOSXCUITFindBy(id = "input_login_password")
    private WebElement passwordTxtFld;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.View\").instance(1)")
    @iOSXCUITFindBy(accessibility = "btn_login")
    private WebElement loginBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Don't have an account? Register\")")
    @iOSXCUITFindBy(accessibility = "btn_go_register")
    private WebElement registerLink;

    public LoginPage() {
    }

    public void enterUserName(String username) throws InterruptedException {
        sendKeys(usernameTxtFld, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordTxtFld, password);
    }

    public void clickLogin() {
        loginBtn.click();

    }

    public void clickRegisterLink() {
        registerLink.click();
    }

    public void toLoginPage() {
        usernameTxtFld.isDisplayed();
        passwordTxtFld.isDisplayed();
     }

}
