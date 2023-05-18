package com.zchalmers.stocks.highVolumeStocks.controller.model;

public class StockRequest {

    public String userId;
    public String ticker;

    public int shares;

    public StockRequest(String userId, String ticker, int shares) {
        this.userId = userId;
        this.ticker = ticker;
        this.shares = shares;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "StockRequest{" +
                "userId='" + userId + '\'' +
                ", ticker='" + ticker + '\'' +
                ", shares=" + shares +
                '}';
    }
}
