package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponse;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponseClean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortfolioHelper {


    private SearchStockService searchStock;

    private PortfolioService portfolioService;

    public PortfolioHelper(SearchStockService searchStock, PortfolioService portfolioService) {
        this.searchStock = searchStock;
        this.portfolioService = portfolioService;
    }


//    public PortfolioRecord buyStock(String ticker, int shares){
//        StockResponseClean response = searchStock.getStocksByTicker(ticker);
////        Map<String, HashMap<String, String>> stocksByDay = response.getStocksByDay();
////        HashMap<String, String> firstValue = (HashMap<String, String>) stocksByDay.get(stocksByDay.keySet().toArray()[0]);
//        Double cost = response.getPricesOverTime().get(response.getPricesOverTime().keySet().toArray()[0]);
//        if (cost > portfolioRecord.getCashBalance()){
//            throw new RuntimeException("Insufficient funds");
//        }
//        else {
//            portfolioRecord.buyStock(new SimpleStockRecord(ticker, 0,0), shares, cost);
////            portfolioService.updateRecord(portfolioRecord);
//        }
//        return portfolioRecord;
//    }
//
//    public PortfolioRecord sellStock(String ticker, int shares){
//        StockResponseClean response = searchStock.getStocksByTicker(ticker);
////        Map<String, HashMap<String, String>> stocksByDay = response.getStocksByDay();
////        HashMap<String, String> firstValue = (HashMap<String, String>) stocksByDay.get(stocksByDay.keySet().toArray()[0]);
//        Double cost = response.getPricesOverTime().get(response.getPricesOverTime().keySet().toArray()[0]);
//
//        portfolioRecord.sellStock(new SimpleStockRecord(ticker, 0, 0), shares, cost);
////        portfolioService.updateRecord(portfolioRecord);
//        return portfolioRecord;
//    }


}
