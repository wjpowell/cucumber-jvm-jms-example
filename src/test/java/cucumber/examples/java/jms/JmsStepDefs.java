package cucumber.examples.java.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.examples.java.jms.utilities.JmsTestUtilities;
import cucumber.examples.java.jms.utilities.TradeReader;

public class JmsStepDefs {
	private JmsTestUtilities jmsTestUtilities;
	private static final int MESSAGE_TIMEOUT = 10000;
	private static final String INPUT_QUEUE = "TEST.INPUT";
	private static final String OUTPUT_QUEUE = "TEST.OUTPUT";
	private static final String JMS_SERVER_URL = "vm://localhost:61616";
	private Queue inputDestination;
	private MessageConsumer outputConsumer;
	private MessageProducer inputProducer;
	private List<Trade> receivedTrades = new ArrayList<Trade>();
	private List<Trade> expectedTrades = new ArrayList<Trade>();

	@Given("^the test is connected to the JMS Server$")
	public void the_test_is_connected_to_the_JMS_Server() throws Throwable {
		jmsTestUtilities = new JmsTestUtilities();
		jmsTestUtilities.startServerSession(JMS_SERVER_URL);
		inputDestination = jmsTestUtilities.createQueue(INPUT_QUEUE);
	    inputProducer = jmsTestUtilities.createInputProducer(inputDestination);
	    	
	  }

	@Given("^I create a simple Application to Receive the input$")
	public void I_create_a_simple_Application_to_Receive_the_input() throws Throwable {
		jmsTestUtilities.attachTestApplicationToQueue(inputDestination);
        outputConsumer = jmsTestUtilities.createOutputReceiver(OUTPUT_QUEUE);

	}

	@Given("^the receiver is configured to process the output.$")
	public void the_receiver_is_configured_to_process_the_output() throws Throwable {
    	receivedTrades = new ArrayList<Trade>();
    	MessageListener testListener = jmsTestUtilities.createTestListener(receivedTrades);
    	jmsTestUtilities.attachListenerToOutputConsumer(testListener, outputConsumer);
	}

	@When("^The Trades are sent$")
	public void a_Trade_is_sent() throws Throwable {
    	for(Trade trade : expectedTrades) {
    		jmsTestUtilities.sendMessage(trade, inputProducer);
    	}
    }

	@When("^we wait until all trades have been processed$")
	public void we_wait_until_all_trades_have_been_processed() throws Throwable {
		waitUntilAllOutputIsReceived(receivedTrades.size(), expectedTrades.size(), MESSAGE_TIMEOUT);
	}

	@Then("^the trade sent and the trade received should be equal$")
	public void the_trade_sent_and_the_trade_received_should_be_equal() throws Throwable {
		for(Trade expectedTrade : expectedTrades) {
			assertTrue(receivedTrades.contains(expectedTrade));
		}
	}

	@Then("^the trade received should be executed$")
	public void the_trade_received_should_be_executed() throws Throwable {
    	for(Trade receivedTrade : receivedTrades) {
    		assertTrue(receivedTrade.isExecuted());
    	}
	}

	@Then("^the session should be closed$")
	public void the_session_should_be_closed() throws Throwable {
		jmsTestUtilities.closeSession();
	}
	
	@Given("^a collection of Trades is loaded from the \"([^\"]*)\" sheet$")
	public void a_collection_of_Trades_is_loaded_from_the_sheet(String sheeName) throws Throwable {
	    TradeReader tradeReader = new TradeReader(sheeName);
		expectedTrades = tradeReader.getTrades();
	}

	private void waitUntilAllOutputIsReceived(int receivedTrades, int expectedTrades, int timeout) throws InterruptedException {
		int timeoutReached = 0;
		while (receivedTrades != expectedTrades && timeoutReached < timeout) {
	    		Thread.sleep(2000);
	    		timeoutReached += 2000;
	    }
	}
}
