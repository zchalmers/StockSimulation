package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.Converter.InsiderTradeConverter;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.repositories.StockRepository;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StockService {

    public StockRepository stockRepository;
    public FinVizScraper scraper;
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void saveRecord(StockRecord record){
        stockRepository.save(record);
    }

    public void saveAllLatest(List<StockElement> stockElementList){
        for (StockElement e : stockElementList){
            if (e.getTransaction().equals("Option Exercise")){
                continue;
            }
            else {
                stockRepository.save(InsiderTradeConverter.elementToRecord(e));
            }
        }
    }

    public void saveAllLatestBuys(){
        List<StockElement> stockElementList = scraper.scrapeLatestBuy();
        for (StockElement e : stockElementList){
            stockRepository.save(InsiderTradeConverter.elementToRecord(e));
        }

    }

    public void saveAllList(List<StockElement> elementList){
        for (StockElement e : elementList){
            if (e.getTransaction().equals("Option Exercise")){
                continue;
            }
            else {
                stockRepository.save(InsiderTradeConverter.elementToRecord(e));
            }
        }

    }
    public List<StockRecord> scrapeAllLatestBuys(){
        List<StockElement> stockElementList = scraper.scrapeLatestBuy();

        return InsiderTradeConverter.elementListToRecord(stockElementList);
    }

    public List<StockRecord> getAllBuys(){
        return stockRepository.findByTransaction("Buy");
    }
    public List<StockRecord> getAllBuysOver1Mil(){
        return stockRepository.findByTransactionAndTotalvalueGreaterThan("Buy", 1000000.00d);

    }
    public List<StockRecord> getAllSales(){
        return stockRepository.findByTransaction("Sale");
    }

    public List<StockRecord> getAll(){
        return stockRepository.findAll();
    }


}
