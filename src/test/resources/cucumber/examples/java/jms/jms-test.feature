Feature: Asynchronous JMS

  Scenario: Asynchronous message Confirmation
    Given the test is connected to the JMS Server
    And I create a simple Application to Receive the input
    And the receiver is configured to process the output.
    And a collection of Trades is loaded from the "testTrades.xlsx" sheet
    When The Trades are sent
    And we wait until all trades have been processed
    Then the trade sent and the trade received should be equal
    And the trade received should be executed
