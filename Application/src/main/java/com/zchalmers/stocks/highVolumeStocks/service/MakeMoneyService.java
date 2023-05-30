package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.Converter.StockConverter;
import com.zchalmers.stocks.highVolumeStocks.controller.model.PortfolioRecordResponse;
import com.zchalmers.stocks.highVolumeStocks.controller.model.PortfolioValueResponse;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponseClean;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MakeMoneyService {

    private StockService stockService;
    private SearchStockService searchService;
    private PortfolioService portfolioService;
    private FinVizScraper scraper;

    public MakeMoneyService(StockService stockService, SearchStockService searchService, PortfolioService portfolioService, FinVizScraper scraper) {
        this.stockService = stockService;
        this.searchService = searchService;
        this.portfolioService = portfolioService;
        this.scraper = scraper;
    }

    public void addToPortfolio(StockRecord record){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PortfolioRecord portfolioRecord = new PortfolioRecord(record.getTicker(),
                    Double.valueOf(record.getShares()), record.getCost(),
                Double.valueOf(record.getTotalvalue()), LocalDate.parse(record.getFilingdate().substring(0,10)));

        portfolioService.addToPortfolio(portfolioRecord);
    }

    @PostConstruct
    public void addAllToPortfolio() {
        List<StockElement> elementList = scraper.scrapeLatestBuy();
        stockService.saveAllList(elementList);
        int limit = 0;
        for (StockElement e : elementList) {
            if (limit == 30){
                break;
            }
            if (e.getTotalValue() > 1000000.00d) {
                portfolioService.addToPortfolio(new PortfolioRecord(e.getTicker(),
                        Double.valueOf(e.getShares()), e.getCost(),
                        e.getTotalValue(), LocalDate.parse(e.getFilingDate().substring(0, 10))));
                limit++;
            }

        }
    }

    public PortfolioValueResponse totalValueOverTime() throws InterruptedException {
        List<PortfolioRecord> recordList = portfolioService.getAllFromPortfolio();
        List<PortfolioRecordResponse> responseList = new ArrayList<>();
        Map<LocalDate, Double> totalValueOverTime = new HashMap<>();

        Double initialTotalValue = 0.00d;
        Map<LocalDate, Double> percentageChangeByDay = new HashMap<>();

        for (PortfolioRecord e : recordList) {
            StockResponseClean clean = searchService.getStocksByTicker(e.getTicker());
            Map<LocalDate, Double> pricesOverTime = clean.getPricesOverTime();
            Double currentValue = 0.00d;
            Double currentCost = 0.00d;

            for (Map.Entry<LocalDate, Double> entry : pricesOverTime.entrySet()) {
                LocalDate key = entry.getKey();
                Double value = entry.getValue();
                if (key.isBefore(e.getOriginalDateBought())) {
                    continue;
                } else {
                    Double dayValue = e.getShares() * value;
                    if (totalValueOverTime.containsKey(key)) {
                        Double existingValue = totalValueOverTime.get(key);
                        totalValueOverTime.put(key, existingValue + dayValue);
                    } else {
                        totalValueOverTime.put(key, dayValue);
                    }
                    System.out.println(percentageChangeByDay);

                    Double percentageChange = ((dayValue-  e.getOriginalValue())/e.getOriginalValue())*100;

                    if (percentageChangeByDay.containsKey(key)){
                        Double existingPercentage = percentageChangeByDay.get(key);
                        percentageChangeByDay.put(key, existingPercentage + percentageChange);
                    }
                    else {
                        percentageChangeByDay.put(key, percentageChange);
                    }

                }

                if (entry.getKey().isEqual(e.getOriginalDateBought())) {
                    currentValue = e.getShares() * entry.getValue();
                    currentCost = entry.getValue();
                }


            }

            responseList.add(StockConverter.PortfolioRecordToResponse(e, currentValue, currentCost));
            initialTotalValue += e.getOriginalValue();
        }
        return new PortfolioValueResponse(totalValueOverTime, percentageChangeByDay, initialTotalValue, responseList);
    }

    public void checkForSales(List<PortfolioRecord> recordList){
        List<StockRecord> stockList = stockService.getAllSales();
        for (PortfolioRecord e : recordList){
            if (stockList.contains(e.getTicker())){
                StockRecord stock = stockList.get(stockList.indexOf(e.getTicker()));
            }
        }
    }
}
