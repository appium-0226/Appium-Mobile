package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.qa.utils.ScenarioContext;

public class LoginStep {

    @When("Input Username as {string}")
    public void inputUsername(String username) throws InterruptedException {
        if (username.equalsIgnoreCase("RegisteredAccount")) {
            username = ScenarioContext.getLastUsername();
        }
        new LoginPage().enterUserName(username);
    }

    @When("Input Password as {string}")
    public void inputPassword(String password) {
        if (password.equalsIgnoreCase("RegisteredAccount")) {
            password = ScenarioContext.getLastPassword();
        }
        new LoginPage().enterPassword(password);
    }

    @And("Click Login Button")
    public void clickLoginButton() {
        new LoginPage().clickLogin();
    }

    @When("Access Register Page")
    public void accessRegisterPage() {
        new LoginPage().clickRegisterLink();
    }

    @And("Verify Register Success with redirect to Login Page")
    public void toLoginPage() throws InterruptedException {
        new LoginPage().toLoginPage();
    }

}
