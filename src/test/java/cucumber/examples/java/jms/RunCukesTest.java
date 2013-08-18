package cucumber.examples.java.jms;

import javax.jms.JMSException;

import cucumber.api.junit.Cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(format = {"html:target/cucumber-html-report", "json-pretty:target/cucumber-json-report.json"})
public class RunCukesTest {
	
	@BeforeClass
	public static void startTestServer() throws JMSException {
		JmsStepDefs.startUpServer();
	}

	@AfterClass
	public static void closeDownServer() throws JMSException {
		JmsStepDefs.closeDownServer();
	}
}
