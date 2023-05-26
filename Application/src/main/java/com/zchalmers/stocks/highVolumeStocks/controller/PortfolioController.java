package com.zchalmers.stocks.highVolumeStocks.controller;

import com.zchalmers.stocks.highVolumeStocks.controller.model.StockRequest;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.service.PortfolioHelper;
import com.zchalmers.stocks.highVolumeStocks.service.PortfolioService;
import com.zchalmers.stocks.highVolumeStocks.service.SearchStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private PortfolioService portfolioService;
    private SearchStockService searchStock;

    private PortfolioHelper helper;

    public PortfolioController(PortfolioService portfolioService, SearchStockService searchStock) {
        this.portfolioService = portfolioService;
        this.searchStock = searchStock;

    }




}
