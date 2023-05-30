package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.repositories.PortfolioRepository;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class StartupLoadCache {

    private PortfolioRepository portfolioRepository;
    private SearchStockCache cache;
    private SearchStockService searchStockService;

    public StartupLoadCache(PortfolioRepository portfolioRepository, SearchStockService searchStockService) {
        this.portfolioRepository = portfolioRepository;
        this.searchStockService = searchStockService;
    }

    @PostConstruct
    @Async
    public void loadPortfolioCache() throws InterruptedException {
        List<PortfolioRecord> recordList = portfolioRepository.findAll();
        for (PortfolioRecord e : recordList){
            searchStockService.getStocksByTicker(e.getTicker());
        }
    }

    @Scheduled(cron = "0 0 12 * * 1-5")
    public void deleteCache(){
        cache.deleteCache();
    }

}
