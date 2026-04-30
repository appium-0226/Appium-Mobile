@simple @contact @test
Feature: Contact

  Scenario: Add New Contact
    Given Access Contact Page
    When Input a random Contact Name
    And Input a random Contact Phone Number
    And Input a random Contact Email
    And Click Add Contact Button
    Then Verify New Contact show in Contact List with correct data
    And Verify New Contact data in database

  Scenario: Update Contact
    Given Access Contact Page
    When Click Edit Button on first contact in Contact List
    And Input a random Contact Name
    And Input a random Contact Phone Number
    And Input a random Contact Email
    And Click Update Contact Button
    Then Verify Updated Contact show in Contact List with correct data
    And Verify Updated Contact data in database

  Scenario: Delete Contact
    Given Access Contact Page
    When Click Delete Button on first contact in Contact List
    Then Verify Contact deleted from Contact List
    And Verify Contact deleted from database
