@simple @address @test
Feature: Address

  Scenario: Add New Address
    Given Access Address Page
    When Input a random Label
    And Input a random Street
    And Input a random City
    And Input a random Postal Code
    And Click Add Address Button
    Then Verify New Address show in Address List with correct data
    And Verify New Address data in database

  Scenario: Update Address
    Given Access Address Page
    When Click Edit Button on first address in Address List
    And Input a random Label
    And Input a random Street
    And Input a random City
    And Input a random Postal Code
    And Click Update Button
    Then Verify Updated Address show in Address List with correct data
    And Verify Updated Address data in database

  Scenario: Delete Address
    Given Access Address Page
    When Click Delete Button on first address in Address List
    Then Verify Address deleted from Address List
    And Verify Address deleted from database
