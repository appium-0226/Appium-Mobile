package com.qa.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductStep {

    @Given("Iam logged in")
    public void iam_logged_in() {

    }

    @Then("The product is listed with title {string} and price {string}")
    public void the_product_is_listed_with_title_and_price(String productDetails) {

    }

    @When("I click product title {string}")
    public void i_click_product_title(String productTitle) {

    }

    @Then("I should be on product detail page with title {string} and price {string} and description {string}")
    public void i_should_be_on_product_detail_page_with_title_and_price_and_description(String productDetails) {

    }

}
