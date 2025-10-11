Feature: Account Endpoints

  Scenario: Client creates a new account
    When the client creates a new account with username "testuser", email "test@test.com", and password "password"
    Then the account is created successfully

  Scenario: Client requests an account by username
    Given an account exists with username "testuser2", email "test2@test.com", and password "password"
    When the client requests the account with username "testuser2"
    Then the client receives the account details for "testuser2"
