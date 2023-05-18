package com.zchalmers.stocks.highVolumeStocks.controller;


import com.zchalmers.stocks.highVolumeStocks.controller.model.PortfolioValueResponse;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.MakeMoneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/makeMoney")
public class MakeMoneyController {

    private MakeMoneyService makeMoneyService;


    public MakeMoneyController(MakeMoneyService makeMoneyService) {
        this.makeMoneyService = makeMoneyService;
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





}
