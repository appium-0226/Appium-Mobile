@simple @account @test
Feature: Account

  Scenario: Verify Account Information correctly displayed
    Given Verify Login Success with redirect to My Account
    When Get Account data in My Account Page
    Then Verify Account data in My Account Page with registered data

    Scenario: Update Account Information
      Given Verify Login Success with redirect to My Account
      When Update with a random Full Name
      And Update with a random Phone Number
      And Update with a random Gender
      And Update with a random Username
      And Update with a random Password
      And Update with a random Confirm Password
      And Click Save Changes Button
      Then Verify Update Account Success with message "Account updated successfully"
      And Verify updated Account data in My Account Page with updated data