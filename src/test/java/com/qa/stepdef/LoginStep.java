package com.qa.stepdef;

import com.qa.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class LoginStep {


    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) throws InterruptedException {
        new LoginPage().enterUserName(username);
    }

    @When("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        new LoginPage().enterPassword(password);
    }

    @And("I click login button")
    public void  iClickLoginButton() {
        new LoginPage().clickLogin();
    }

}
