@simple @register @test @registerLogin
Feature: Register
  Scenario: Register New Account
    Given Access Register Page
    When Input a random Full Name
    And Input a random Phone Number
    And Select a random Gender
    And Input a random Username
    And Input a random Password
    And Input a random Confirm Password
    And Click Register Button
    Then Verify Register Success with redirect to Login Page
    And Verify user registration data in database