package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.qa.utils.ScenarioContext;

public class LoginStep {

    @When("Input Username as {string}")
    public void inputUsername(String username)  {
        new LoginPage().enterUserName(username);
    }

    @Given("Input registered Username")
    public void inputRegisteredUsername()  {
        String username = ScenarioContext.getLastUsername();
        new LoginPage().enterUserName(username);
    }

    @When("Input Password as {string}")
    public void inputPassword(String password) {
        new LoginPage().enterPassword(password);
    }

    @Given("Input registered Password")
    public void inputRegisteredPassword() {
        String password = ScenarioContext.getLastPassword();
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
    public void toLoginPage()  {
        new LoginPage().toLoginPage();
    }

}
