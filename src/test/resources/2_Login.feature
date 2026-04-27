@simple @login @test
Feature: Login
  Scenario: Login with valis credentials
    Given Input Username as "RegisteredAccount"
    And Input Password as "RegisteredAccount"
    And Click Login Button
    Then Verify Login Success with redirect to My Account