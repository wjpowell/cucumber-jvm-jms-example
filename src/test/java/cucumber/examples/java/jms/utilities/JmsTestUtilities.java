package cucumber.examples.java.jms.utilities;

import java.io.Serializable;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

import cucumber.examples.java.jms.MessageForwarder;

public class JmsTestUtilities {
	private Session session;
	private Connection connection;

	public Queue createQueue(String queueName) throws JMSException {
		return session.createQueue(queueName);
	}

	private void attachTestApplicationToJMSQueue(MessageConsumer inputConsumer) throws JMSException {
		MessageForwarder messageForwarder = new MessageForwarder();
		inputConsumer.setMessageListener(messageForwarder);
	}

    
    public void sendMessage(Serializable object, MessageProducer producer) {
    	try {  
            // Create a messages
            ObjectMessage message = session.createObjectMessage(object);
            producer.send(message);
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
    
   
	public void closeSession() throws JMSException {
		session.close();
		connection.close();
	}

	public void startServerSession(String jmsServerUrl) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(jmsServerUrl);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		connection.start();
	}

	public MessageProducer createInputProducer(Queue inputDestination)
			throws JMSException {
	    MessageProducer producer = session.createProducer(inputDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		return producer;
	}

	public void attachTestApplicationToQueue(Queue inputDestination) throws JMSException {
		MessageConsumer inputConsumer = session.createConsumer(inputDestination);
    	attachTestApplicationToJMSQueue(inputConsumer);
	}

	public MessageConsumer createOutputReceiver(String outputQueue) throws JMSException {
		Queue outputDestination = session.createQueue(outputQueue);
		MessageConsumer outputConsumer = session.createConsumer(outputDestination);
		
		return outputConsumer;
	}

	public void attachListenerToOutputConsumer(MessageListener testListener, MessageConsumer outputConsumer) throws JMSException {
		outputConsumer.setMessageListener(testListener);
	}

	public <T> MessageListener createTestListener(final List<T> receivedCollection) {
		
		MessageListener testListener = new MessageListener() {

			@Override
			public void onMessage(Message received) {
	            if (received instanceof ObjectMessage) {  	
					try {
						receivedCollection.add((T)((ObjectMessage) received).getObject());
					} catch (JMSException e) {
						e.printStackTrace();
					}
	            }
			}
		};
		return testListener;
	}


 }