Feature: Create User

  Scenario: Create a new user successfully
    When I create a user with name "john" and job "leader"
    Then the response status should be 201
    And the response should have name "john", job "leader", and a createdAt field

   Scenario: Create a user with missing name
     When I create a user with name "" and job "leader"
     Then the response status should be 400
     And the error message should contain "name cannot be empty"

   Scenario: Create a user with missing job
    When I create a user with name "john" and job ""
    Then the response status should be 400
    And the error message should contain "job cannot be empty"

    Scenario: Create a user with valid fields but empty request body
     When I create a user with name "" and job ""
     Then the response status should be 400
     And the error message should contain "name and job cannot be empty"