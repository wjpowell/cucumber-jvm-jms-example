package cucumber.examples.java.jms.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.xml.sax.SAXException;

import cucumber.examples.java.jms.Trade;
import cucumber.examples.java.jms.utilities.TradeReader;

public class DataReaderTests {

	@Test
	public void canLoadTradeFromXLS() throws InvalidFormatException, IOException, SAXException {
		List<Trade> testList = new ArrayList<Trade>();
		TradeReader tradeReader = new TradeReader("testTrades.xlsx");
		testList = tradeReader.getTrades();
		assertTrue(testList.size() > 4);

	}
}
