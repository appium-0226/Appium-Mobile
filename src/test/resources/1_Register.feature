@simple @register @test
Feature: Register
  Scenario: Register New Account
    Given Access Register Page
    When Input Full Name with "RandomName"
    And Input Phone Number with "RandomPhone"
    And Select "Female" as Gender
    And Input Username with "RandomUsername"
    And Input Password with "RandomPassword"
    And Input Confirm Password with "RandomConfirmPassword"
    And Click Register Button
    Then Verify Register Success with redirect to Login Page
    And Verify user registration data in database