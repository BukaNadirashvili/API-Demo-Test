Feature: API Tests for PUT and PATCH methods

  # Test updating user details using a PUT request
  Scenario: Update user details with PUT
    Given the API endpoint is "https://reqres.in/api/users/api/users"
    When I send a PUT request with the following data:
      | name  | job    |
      | John  | leader |
    Then I receive a response with status code 200
    And the response contains updated user data

  # Test updating user details using a PATCH request
  Scenario: Update user details with PATCH
    Given the API endpoint is "https://reqres.in/api/users/api/users"
    When I send a PATCH request with the following data:
      | name  | job    |
      | John  | leader |
    Then I receive a response with status code 200
    And the response contains updated user data

  # Test PUT request with a missing field
  Scenario: Update user with missing job field in PUT
    Given the API endpoint is "https://reqres.in/api/users/api/users"
    When I send a PUT request with the following data:
      | name  |
      | John  |
    Then I receive a response with status code 400

  # Test PATCH request with an invalid field
  Scenario: Update user with invalid name field in PATCH
    Given the API endpoint is "https://reqres.in/api/users/api/users"
    When I send a PATCH request with the following data:
      | name  | job    |
      |       | leader |
    Then I receive a response with status code 400

  # Test PUT request for a non-existent user
  Scenario: Update user with non-existent user ID in PUT
    Given the API endpoint is "https://reqres.in/api/users/api/users/999"
    When I send a PUT request with the following data:
      | name  | job    |
      | John  | leader |
    Then I receive a response with status code 404

  # Test PATCH request for a non-existent user
  Scenario: Update user with non-existent user ID in PATCH
    Given the API endpoint is "https://reqres.in/api/users/api/users/999"
    When I send a PATCH request with the following data:
      | name  | job    |
      | John  | leader |
    Then I receive a response with status code 404

  # Test to verify the presence of updatedAt field in response
  Scenario: Validate updatedAt field in PUT response
    Given the API endpoint is "https://reqres.in/api/users/api/users"
    When I send a PUT request with the following data:
      | name  | job    |
      | John  | leader |
    Then I receive a response with status code 200
    And the response contains an updatedAt field