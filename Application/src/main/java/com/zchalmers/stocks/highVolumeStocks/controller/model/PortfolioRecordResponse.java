package com.zchalmers.stocks.highVolumeStocks.controller.model;

import java.time.LocalDate;

public class PortfolioRecordResponse {

    private String ticker;

    private Double shares;

    private Double originalCost;

    private Double currentCost;
    private Double originalTotalValue;

    private Double currentTotalValue;

    private LocalDate originalDateBought;

    private Double percentChange;

    public PortfolioRecordResponse() {
    }


    public PortfolioRecordResponse(String ticker, Double shares, Double originalCost, Double currentCost, Double originalTotalValue, Double currentTotalValue, LocalDate originalDateBought, Double percentChange) {
        this.ticker = ticker;
        this.shares = shares;
        this.originalCost = originalCost;
        this.currentCost = currentCost;
        this.originalTotalValue = originalTotalValue;
        this.currentTotalValue = currentTotalValue;
        this.originalDateBought = originalDateBought;
        this.percentChange = percentChange;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getShares() {
        return shares;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }

    public Double getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(Double originalCost) {
        this.originalCost = originalCost;
    }

    public Double getOriginalTotalValue() {
        return originalTotalValue;
    }

    public void setOriginalTotalValue(Double originalTotalValue) {
        this.originalTotalValue = originalTotalValue;
    }

    public Double getCurrentTotalValue() {
        return currentTotalValue;
    }

    public void setCurrentTotalValue(Double currentTotalValue) {
        this.currentTotalValue = currentTotalValue;
    }

    public LocalDate getOriginalDateBought() {
        return originalDateBought;
    }

    public void setOriginalDateBought(LocalDate originalDateBought) {
        this.originalDateBought = originalDateBought;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public Double getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(Double currentCost) {
        this.currentCost = currentCost;
    }
}
