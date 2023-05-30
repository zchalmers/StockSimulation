package com.zchalmers.stocks.highVolumeStocks;

import com.zchalmers.stocks.highVolumeStocks.Converter.InsiderTradeConverter;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.repositories.StockRepository;
import com.zchalmers.stocks.highVolumeStocks.service.FinVizScraper;


import com.zchalmers.stocks.highVolumeStocks.service.StockService;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
class HighVolumeStocksApplicationTests {

	private StockRepository stockRepository;

	private StockService stockService;


	@BeforeEach
	void setup() {
		this.stockRepository = mock(StockRepository.class);
		stockService = new StockService(stockRepository);
	}
//	@Test
//	public void scrapeFinVizLatest() {
//		FinVizScraper scraper = new FinVizScraper();
//		List<StockElement> rows = scraper.scrapeLatest();
//		System.out.println(rows.toString());
//		// Verify that the result is not empty
//		assertFalse(rows.isEmpty());
//		System.out.println(rows);
//		// Verify that the result contains a certain string
//
//	}
//	@Test
//	public void scrapeFinVizTop() {
//		FinVizScraper scraper = new FinVizScraper();
//		List<StockElement> rows = scraper.scrapeTop();
//		System.out.println(rows.toString());
//		// Verify that the result is not empty
//		assertFalse(rows.isEmpty());
//		System.out.println(rows);
//		// Verify that the result contains a certain string
//
//	}
//	@Test
//	public void dateTest(){
//		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
//				.appendPattern("MMM dd hh:mm a")
//				.parseDefaulting(ChronoField.YEAR, Year.now().getValue())
//				.toFormatter()
//				.withZone(ZoneId.systemDefault());
//
//		System.out.println("formatter: " + formatter);
//		String input = "Apr 28 11:35 PM";
//		System.out.println("input: " + input);
//		ZonedDateTime zonedDateTime = ZonedDateTime.parse(input , formatter);
//		System.out.println(zonedDateTime.toString());
//
//	}
//
//	@Test
//	public void addToMySQL(){
//		StockRecord record = new StockRecord("AAPL", "test", "testRelationship", "buy",
//				20.00d, 10, 200, InsiderTradeConverter.zoneDateTimeToTimeStamp(ZonedDateTime.now()));
//		stockService.saveRecord(record);
//
//	}
}
