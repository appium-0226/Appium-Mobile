@simple @login @test @registerLogin
Feature: Login
  Scenario: Login with valis credentials
    Given Input registered Username
    And Input registered Password
    And Click Login Button
    Then Verify Login Success with redirect to My Account