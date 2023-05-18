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

    public MakeMoneyService(StockService stockService, SearchStockService searchService, PortfolioService portfolioService) {
        this.stockService = stockService;
        this.searchService = searchService;
        this.portfolioService = portfolioService;
    }

    public void addToPortfolio(StockRecord record){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PortfolioRecord portfolioRecord = new PortfolioRecord(record.getTicker(),
                    Double.valueOf(record.getShares()), record.getCost(),
                Double.valueOf(record.getTotalvalue()), LocalDate.parse(record.getFilingdate().substring(0,10)));

        portfolioService.addToPortfolio(portfolioRecord);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void addAllToPortfolio() {
        List<StockElement> elementList = scraper.scrapeLatestBuy();
        stockService.saveAllList(elementList);

        for (StockElement e : elementList) {
            if (e.getTotalValue() > 1000000.00d) {
                portfolioService.addToPortfolio(new PortfolioRecord(e.getTicker(),
                        Double.valueOf(e.getShares()), e.getCost(),
                        e.getTotalValue(), LocalDate.parse(e.getFilingDate().substring(0, 10))));
            }

        }

    }  
    public PortfolioValueResponse totalValueOverTime() throws InterruptedException {
        List<PortfolioRecord> recordList = portfolioService.getAllFromPortfolio();
        List<PortfolioRecordResponse> responseList = new ArrayList<>();
//        List<Double> valueOverTime = new ArrayList<>();
        Map<LocalDate, Double> valueOverTime = new HashMap<>();
        // AMKE THIS A MAP SO I CAN STORE TOTAL VALUE OF PORTFOLIO WITH DATA AND VALUE
        System.out.println("MAKEMONEYSERVICE PORTFOLIORECORDLIST:" + recordList.toString());
        Double originalTotalValue = 0.00d;
        for (PortfolioRecord e : recordList){
            StockResponseClean clean = searchService.getStocksByTicker(e.getTicker());
//            System.out.println("TOTALVALUEOVERTIME STOCKRESPONSECLEAN: " + clean.toString());
            Map<LocalDate, Double> pricesOverTime = clean.getPricesOverTime();
            Double currentValue = 0.00d;
            Double currentCost = 0.00d;
//            int index = 0;
//            int size = pricesOverTime.entrySet().size();
//            System.out.println("SIZE: " + size);
            for (Map.Entry<LocalDate, Double> entry : pricesOverTime.entrySet()){
//                index++;

                if (entry.getKey().isBefore(e.getOriginalDateBought())){
                    continue;
                }

                else {
                    Double dayValue = e.getShares() * entry.getValue();
//                    System.out.println("DAYVALUE: " + dayValue);
                    if (valueOverTime.containsKey(entry.getKey())) {
//                        System.out.println("ENTRYVALUE: " + entry.getValue() + "ENTRYKEY: " + entry.getKey());
                        Double existingValue = valueOverTime.get(entry.getKey());
                        valueOverTime.put(entry.getKey(), existingValue + dayValue);
                    }
                    else {
                        valueOverTime.put(entry.getKey(), dayValue);
                    }
                    }
//                System.out.println(index + " INDEX");
                if (entry.getKey().isEqual(e.getOriginalDateBought())){
//                    System.out.println("IN IF LOOP THAT I WANT ");
                    currentValue = e.getShares() * entry.getValue();
                    currentCost = entry.getValue();
//                    System.out.println("CURRENT VALUE: " + currentValue);
                }
//                System.out.println(valueOverTime.toString());

            }
            responseList.add(StockConverter.PortfolioRecordToResponse(e, currentValue, currentCost));
            originalTotalValue += e.getOriginalValue();
//            Thread.sleep(2000);
        }
        // GET ORIGINAL VALUE AND ADD THEM ALL UP TO PUT IN MAP AS OWN KEY
//        valueOverTime.put("originalValue", originalTotalValue);
        System.out.println("TOTALVALUEOVERTIME MAP: " + valueOverTime.toString());

        Map<LocalDate, Double> sortedMap = valueOverTime.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return new PortfolioValueResponse(sortedMap, originalTotalValue, responseList);
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
