Feature: Asynchronous JMS

Scenario: Asynchronous message Confirmation

    	Given the test is connected to the JMS Server 
    	And I create a simple Application to Receive the input
    	And the receiver is configured to process the output.
    	When a Trade is sent. 
		And we wait until all trades have been processed
    	Then the trade sent and the trade received should be equal
    	And the trade received should be executed
    	And the session should be closed
		