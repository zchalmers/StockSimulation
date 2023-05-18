package com.zchalmers.stocks.highVolumeStocks.service.model;


import java.time.LocalDate;
import java.util.Map;

public class StockResponseClean {

    private String ticker;

    private Map<LocalDate, Double> pricesOverTime;


    public StockResponseClean(String ticker, Map<LocalDate, Double> pricesOverTime) {
        this.ticker = ticker;
        this.pricesOverTime = pricesOverTime;
    }


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Map<LocalDate, Double> getPricesOverTime() {
        return pricesOverTime;
    }

    public void setPricesOverTime(Map<LocalDate, Double> pricesOverTime) {
        this.pricesOverTime = pricesOverTime;
    }

    @Override
    public String toString() {
        return "StockResponseClean{" +
                "ticker='" + ticker + '\'' +
                ", pricesOverTime=" + pricesOverTime.toString() +
                '}';
    }
}
