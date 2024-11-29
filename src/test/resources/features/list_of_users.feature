Feature: Get List Of Users

  Scenario: Get list of users
    Given I have a list of users with this api address "https://reqres.in/api/users"
    When I send request
    Then the response code should be 200

  Scenario: Get non empty list of users
    Given I have a list of users with this api address "https://reqres.in/api/users"
    When I send request
    Then the users list should not be empty

   Scenario: Check structure of users
     Given I have a list of users with this api address "https://reqres.in/api/users?per_page=12"
     When I send request
     Then the user should have: "id", "email", "first_name", "last_name" and "avatar"

   Scenario: Test invalid url
    Given I have used invalid api url "https://reqres.in/apis/users"
    When I send request
    Then the response code should not be 200

    Scenario: Test pagination
    Given I have a list of users with this api address "https://reqres.in/api/users?per_page=5"
    When I send request
    Then the response should return 3 pages