@simple @account @test
Feature: Account

  Scenario: Verify Account Information correctly displayed
    Given Verify Login Success with redirect to My Account
    When Get Account data in My Account Page
    Then Verify Account data in My Account Page with registered data

    Scenario: Update Account Information
      Given Verify Login Success with redirect to My Account
      When Update Full Name with "UpdatedName"
      And Update Phone Number with "updatedPhone"
      And Update Gender with "Male"
      And Update Username with "UpdatedUsername"
      And Update Password with "updatedPassword"
      And Update Confirm Password with "updatedConfirmPassword"
      And Click Save Changes Button
      Then Verify Update Account Success with message "Account updated successfully"
      And Verify updated Account data in My Account Page with updated data