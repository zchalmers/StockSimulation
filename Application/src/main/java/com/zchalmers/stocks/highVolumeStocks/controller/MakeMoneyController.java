package com.zchalmers.stocks.highVolumeStocks.controller;


import com.zchalmers.stocks.highVolumeStocks.controller.model.PortfolioValueResponse;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.MakeMoneyService;
import com.zchalmers.stocks.highVolumeStocks.service.SearchStockService;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponseClean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/makeMoney")
public class MakeMoneyController {

    private MakeMoneyService makeMoneyService;
    private SearchStockService searchStockService;

    public MakeMoneyController(MakeMoneyService makeMoneyService, SearchStockService searchStockService) {
        this.makeMoneyService = makeMoneyService;
        this.searchStockService = searchStockService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addToPortfolio(@RequestBody StockRecord stockRecord){
        makeMoneyService.addToPortfolio(stockRecord);
        return ResponseEntity.ok("ALL GOOD");
    }

    @GetMapping
    public ResponseEntity<PortfolioValueResponse> getValuesOverTime() throws InterruptedException {
        PortfolioValueResponse response = makeMoneyService.totalValueOverTime();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stockData/{ticker}")
    public ResponseEntity<StockResponseClean> getStockData(@PathVariable("ticker") String ticker)  {
        try {
            StockResponseClean clean = searchStockService.getStocksByTicker(ticker);
            return ResponseEntity.ok(clean);
        }
        catch (InterruptedException e){
            return ResponseEntity.notFound().build();
        }


    }





}
