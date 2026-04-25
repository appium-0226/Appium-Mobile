@test @simple @register
Feature: Register
  Scenario: Register New Account
    Given Access Register Page
    When Input Full Name with "RANDOM"
    And Input Phone Number with "081234567890"
    And Select "Female" as Gender
    And Input Username with "RANDOM"
    And Input Password with "hasania123"
    And Input Confirm Password with "hasania123"
    And Click Register Button
    Then Verify Register Success with redirect to Login Page
    And Verify user registration in database