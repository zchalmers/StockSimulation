package com.zchalmers.stocks.highVolumeStocks.repositories.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "portfolio")
public class PortfolioRecord {
    @Id
    @Column(name = "ticker")
    public String ticker;

    @Column(name = "shares")
    public Double shares;

    @Column(name = "originalcost")
    public Double originalCost;

    @Column(name = "originalvalue")
    public Double originalValue;
    @Column(name = "originaldatebought")
    public LocalDate originalDateBought;

    public PortfolioRecord() {
    }

    public PortfolioRecord(String ticker, Double shares, Double originalCost, Double originalValue, LocalDate originalDateBought) {
        this.ticker = ticker;
        this.shares = shares;
        this.originalCost = originalCost;
        this.originalValue = originalValue;
        this.originalDateBought = originalDateBought;
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

    public Double getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Double originalValue) {
        this.originalValue = originalValue;
    }

    public LocalDate getOriginalDateBought() {
        return originalDateBought;
    }

    public void setOriginalDateBought(LocalDate originalDateBought) {
        this.originalDateBought = originalDateBought;
    }

    @Override
    public String toString() {
        return "PortfolioRecord{" +
                "ticker='" + ticker + '\'' +
                ", shares=" + shares +
                ", originalCost=" + originalCost +
                ", originalValue=" + originalValue +
                ", originalDateBought='" + originalDateBought + '\'' +
                '}';
    }
}



