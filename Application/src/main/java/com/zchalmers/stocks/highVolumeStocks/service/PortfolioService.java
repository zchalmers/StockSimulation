package com.zchalmers.stocks.highVolumeStocks.service;


import com.zchalmers.stocks.highVolumeStocks.repositories.PortfolioRepository;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {


    private PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public void addToPortfolio(PortfolioRecord portfolioRecord){
        portfolioRepository.save(portfolioRecord);
    }

    public List<PortfolioRecord> getAllFromPortfolio(){
        return portfolioRepository.findAll();
    }
    public void addListToPortfolio(){



    }
//    public List<PortfolioRecord> getAllBuys(){
//        return portfolioRepository.
//    }
}
