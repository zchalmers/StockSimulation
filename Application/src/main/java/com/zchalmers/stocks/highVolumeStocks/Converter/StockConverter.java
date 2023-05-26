package com.zchalmers.stocks.highVolumeStocks.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zchalmers.stocks.highVolumeStocks.controller.model.PortfolioRecordResponse;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.SearchStockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponse;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponseClean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StockConverter {


    public static StockResponseClean cacheToClean(SearchStockRecord record){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Map<LocalDate, Double> resultMap = new HashMap<>();
        try {
            resultMap = mapper.readValue(
                    record.getJsonPriceOverTime(),
                    new TypeReference<Map<LocalDate, Double>>() {
                    }
            );
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        StockResponseClean clean = new StockResponseClean(record.getTicker(), resultMap);
        return clean;
    }
    public static SearchStockRecord cleanToCache(StockResponseClean clean){
        ObjectMapper mapper = new ObjectMapper();
        String pricesOverTime = "";
        try {
            pricesOverTime = mapper.writeValueAsString(
                    clean.getPricesOverTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        SearchStockRecord record = new SearchStockRecord(clean.getTicker(), pricesOverTime);
        return record;
    }
    public static StockResponseClean responseToClean(StockResponse response){
        String ticker = response.getMetaData().get("2. Symbol");
        Map<LocalDate, Double> pricesOverTime = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(Map.Entry<String, HashMap<String, String>> day : response.getStocksByDay().entrySet()) {
            pricesOverTime.put(LocalDate.parse(day.getKey(), formatter), Double.valueOf(day.getValue().get("4. close")));
        }
        return new StockResponseClean(ticker, pricesOverTime);
    }

    public static PortfolioRecordResponse PortfolioRecordToResponse(PortfolioRecord e, Double currentValue, Double currentCost){
        return new PortfolioRecordResponse(e.getTicker(),
                e.getShares(),
                e.getOriginalCost(),
                currentCost,
                e.getOriginalValue(),
                currentValue,
                e.getOriginalDateBought(),
                ((currentValue-e.getOriginalValue())/e.getOriginalValue()) * 100);
    }

}
