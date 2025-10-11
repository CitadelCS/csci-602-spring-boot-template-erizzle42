Feature: Status Endpoint

  Scenario: Client requests server health
    When the client requests the server health
    Then the client receives a status of ok

  Scenario: Client requests server info
    When the client requests the server info
    Then the client receives server info
