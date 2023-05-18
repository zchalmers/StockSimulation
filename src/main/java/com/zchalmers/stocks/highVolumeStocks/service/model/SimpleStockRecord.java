package com.zchalmers.stocks.highVolumeStocks.service.model;




public class SimpleStockRecord {

    public String ticker;

    public int shares;

    public double totalvalue;

    public SimpleStockRecord(String ticker, int shares, double totalvalue) {
        this.ticker = ticker;
        this.shares = shares;
        this.totalvalue = totalvalue;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(double totalvalue) {
        this.totalvalue = totalvalue;
    }
}
