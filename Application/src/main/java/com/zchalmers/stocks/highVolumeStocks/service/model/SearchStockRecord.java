package com.zchalmers.stocks.highVolumeStocks.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cache")
public class SearchStockRecord {
    @Id
    @JsonProperty(value = "ticker")
    private String ticker;

    @JsonProperty(value = "pricesovertime")
    @Column(name = "pricesovertime")
    private String jsonPriceOverTime;

    public SearchStockRecord() {
    }

    public SearchStockRecord(String ticker, String jsonPriceOverTime) {
        this.ticker = ticker;
        this.jsonPriceOverTime = jsonPriceOverTime;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getJsonPriceOverTime() {
        return jsonPriceOverTime;
    }

    public void setJsonPriceOverTime(String jsonPriceOverTime) {
        this.jsonPriceOverTime = jsonPriceOverTime;
    }
}
