@simple @login
Feature: Login scenarios

  Scenario Outline: Login with valis credentials
    When I enter username as "<username>"
    And I enter password as "<password>"
#    And I click login button
    Examples:
      | username | password |
      | standard_user | secret_sauce |