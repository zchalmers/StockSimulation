package com.zchalmers.stocks.highVolumeStocks.repositories.model;


import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "stocktablelatest")
public class StockRecord {
    @JsonProperty("ticker")
    private String ticker;
    @Id
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("transaction")
    private String transaction;
    @JsonProperty("cost")
    private double cost;
    @JsonProperty("shares")
    private int shares;
    @JsonProperty("totalvalue")
    private Double totalvalue;
    @JsonProperty("filingdate")
    private String filingdate;

    public StockRecord() {
    }

    public StockRecord(String ticker, String owner, String relationship, String transaction, double cost, int shares, Double totalvalue, String filingdate) {
        this.ticker = ticker;
        this.owner = owner;
        this.relationship = relationship;
        this.transaction = transaction;
        this.cost = cost;
        this.shares = shares;
        this.totalvalue = totalvalue;
        this.filingdate = filingdate;
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

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) {
        this.totalvalue = totalvalue;
    }

    public String getFilingdate() {
        return filingdate;
    }

    public void setFilingdate(String filingdate) {
        this.filingdate = filingdate;
    }

    @Override
    public String toString() {
        return "StockRecord{" +
                "ticker='" + ticker + '\'' +
                ", owner='" + owner + '\'' +
                ", relationship='" + relationship + '\'' +
                ", cost=" + cost +
                ", shares=" + shares +
                ", totalvalue=" + totalvalue +
                ", filingdate=" + filingdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockRecord that = (StockRecord) o;
        return Objects.equals(ticker, that.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }
}
