package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.repositories.SearchStockRepository;
import com.zchalmers.stocks.highVolumeStocks.service.model.SearchStockRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchStockCache {


    private SearchStockRepository searchStockRepository;

    public SearchStockCache(SearchStockRepository searchStockRepository) {
        this.searchStockRepository = searchStockRepository;
    }

    public SearchStockRecord findByTicker(String ticker){
        Optional<SearchStockRecord> optional = searchStockRepository.findById(ticker);
        if (optional.isPresent()){
            return optional.get();
        }
        else {
            return null;
        }
    }
    public void addToCache(SearchStockRecord record){
        searchStockRepository.save(record);

    }

    public void deleteCache(){
        searchStockRepository.deleteAll();

    }
}
