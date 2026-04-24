package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    TestUtils utils = new TestUtils();

    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(id = "test-Username")
    private WebElement usernameTxtFld;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(id = "test-Password")
    private WebElement passwordTxtFld;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(id = "test-LOGIN")
    private WebElement loginBtn;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
    private WebElement errTxt;

    public LoginPage() {
    }

    public void enterUserName(String username) throws InterruptedException {
        clear(usernameTxtFld);
        sendKeys(usernameTxtFld, username, "login with " + username);
    }

    public void enterPassword(String password) {
        clear(passwordTxtFld);
        sendKeys(passwordTxtFld, password, "password is " + password);
    }

    public void clickLogin() {
        loginBtn.click();

    }

}
