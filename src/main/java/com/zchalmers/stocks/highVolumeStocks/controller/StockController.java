package com.zchalmers.stocks.highVolumeStocks.controller;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.FinVizScraper;
import com.zchalmers.stocks.highVolumeStocks.service.StockService;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private StockService stockService;
    private FinVizScraper scraper;

    public StockController(StockService stockService, FinVizScraper scraper) {
        this.stockService = stockService;
        this.scraper = scraper;
    }

    @PostMapping
    public ResponseEntity<String> saveStockRecord(@RequestBody StockRecord stockRecord){

        System.out.println(stockRecord.toString());
        stockService.saveRecord(stockRecord);

        return ResponseEntity.ok("AlL GOOD");
    }

    @GetMapping("/saveAllLatest")
    public ResponseEntity<String> addAllLatestStocks(){
        List<StockElement> stockElementListBuy = scraper.scrapeLatestBuy();
        List<StockElement> stockElementListSell = scraper.scrapeLatestSell();

        stockService.saveAllLatest(stockElementListBuy);
        stockService.saveAllLatest(stockElementListSell);
        return ResponseEntity.ok("SAVED ALL LATEST STOCKS");
    }

    @GetMapping("table/{option}")
    public ResponseEntity<List<StockRecord>> getTableData(@PathVariable("option") String option){
        if (option.equals("All")) {
            return ResponseEntity.ok(stockService.getAll());
        } else if (option.equals("Buys")) {
            return ResponseEntity.ok(stockService.getAllBuys());
        } else if (option.equals("Sales")) {
            return ResponseEntity.ok(stockService.getAllSales());
        }
        else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
