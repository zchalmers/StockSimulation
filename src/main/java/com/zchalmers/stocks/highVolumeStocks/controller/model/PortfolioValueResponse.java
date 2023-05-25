package com.zchalmers.stocks.highVolumeStocks.controller.model;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PortfolioValueResponse {

    private Map<LocalDate, Double> valueOverTime;
    private Map<LocalDate, Double> relativePerformanceOverTime;
    private Double totalValue;

    private List<PortfolioRecordResponse> recordList;


    public PortfolioValueResponse(Map<LocalDate, Double> valueOverTime, Map<LocalDate, Double> relativePerformanceOverTime, Double totalValue, List<PortfolioRecordResponse> recordList) {
        this.valueOverTime = valueOverTime;
        this.relativePerformanceOverTime = relativePerformanceOverTime;
        this.totalValue = totalValue;
        this.recordList = recordList;
    }

    public Map<LocalDate, Double> getValueOverTime() {
        return valueOverTime;
    }

    public void setValueOverTime(Map<LocalDate, Double> valueOverTime) {
        this.valueOverTime = valueOverTime;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<PortfolioRecordResponse> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<PortfolioRecordResponse> recordList) {
        this.recordList = recordList;
    }

    public Map<LocalDate, Double> getRelativePerformanceOverTime() {
        return relativePerformanceOverTime;
    }

    public void setRelativePerformanceOverTime(Map<LocalDate, Double> relativePerformanceOverTime) {
        this.relativePerformanceOverTime = relativePerformanceOverTime;
    }
}
