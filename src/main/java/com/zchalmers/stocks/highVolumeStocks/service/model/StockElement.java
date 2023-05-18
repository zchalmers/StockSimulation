package com.zchalmers.stocks.highVolumeStocks.service.model;

public class StockElement {
    public String ticker;
    public String owner;
    public String relationship;
    public String date;
    public String transaction;
    public double cost;
    public int shares;
    public Double totalValue;
    public Double totalShares;
    public String filingDate;

    public StockElement(String ticker, String owner, String relationship, String date, String transaction, double cost, int shares, Double totalValue, Double totalShares, String filingDate) {
        this.ticker = ticker;
        this.owner = owner;
        this.relationship = relationship;
        this.date = date;
        this.transaction = transaction;
        this.cost = cost;
        this.shares = shares;
        this.totalValue = totalValue;
        this.totalShares = totalShares;
        this.filingDate = filingDate;
    }

    public StockElement() {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Double totalShares) {
        this.totalShares = totalShares;
    }
    public String getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    @Override
    public String toString() {
        return "StockElement{" +
                "ticker='" + ticker + '\'' +
                ", owner='" + owner + '\'' +
                ", relationship='" + relationship + '\'' +
                ", date='" + date + '\'' +
                ", transaction='" + transaction + '\'' +
                ", cost=" + cost +
                ", shares=" + shares +
                ", totalValue=" + totalValue +
                ", totalShares=" + totalShares +
                ", filingDate='" + filingDate + '\'' +
                '}';
    }
}
