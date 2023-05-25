package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.Converter.StockConverter;
import com.zchalmers.stocks.highVolumeStocks.service.model.SearchStockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponse;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockResponseClean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class SearchStockService {

    private RestTemplate restTemplate = new RestTemplate();

    private SearchStockCache cache;

    public SearchStockService(SearchStockCache cache){
        this.cache = cache;
    }

    @GetMapping
    public StockResponseClean getStocksByTicker(String ticker) throws InterruptedException {
        SearchStockRecord record = cache.findByTicker(ticker);
        if (record != null){
            return StockConverter.cacheToClean(record);
        }
        else {
            String url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&apikey=5DA1NYHTSAKVQ99Z", ticker);
            System.out.println("SEARCHSTOCKSERVICE TICKER: " + ticker);
            StockResponse stockResponse = restTemplate.getForObject(url, StockResponse.class);
            StockResponseClean clean = StockConverter.responseToClean(stockResponse);
            cache.addToCache(StockConverter.cleanToCache(clean));
            Thread.sleep(20000);
            return clean;
        }
    }

    @GetMapping("/Name")
    public String getStockNameByTicker(String ticker) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&apikey=5DA1NYHTSAKVQ99Z&symbol=" + ticker;

        Map<String, String> response = restTemplate.getForObject(url, Map.class);

        return response.get("Name");
    }


}
