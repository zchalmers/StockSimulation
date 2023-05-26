package com.zchalmers.stocks.highVolumeStocks.service;

import com.zchalmers.stocks.highVolumeStocks.Converter.InsiderTradeConverter;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class FinVizScraper {
    public String latestUrl = "https://finviz.com/insidertrading.ashx";
    public String topUrl = "https://finviz.com/insidertrading.ashx?or=-10&tv=100000&tc=7&o=-transactionValue";
    public String latestBuyUrl = "https://finviz.com/insidertrading.ashx?tc=1";
    public String latestSellUrl = "https://finviz.com/insidertrading.ashx?tc=2";

    public String topBuyUrl = "https://finviz.com/insidertrading.ashx?or=-10&tv=100000&tc=1&o=-transactionvalue";

    public String topSellUrl = "https://finviz.com/insidertrading.ashx?or=-10&tv=100000&tc=2&o=-transactionvalue";
    public FinVizScraper() {}

    public List<StockElement> scrapeLatest(){
        List<String> scrapes = new ArrayList<>();
        List<StockElement> stockElementList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(latestUrl).get();
            Elements rows = doc.select("table.table-insider tr.insider-buy-row-1, " +
                    "table.table-insider tr.insider-buy-row-2, " +
                    "table.table-insider tr.insider-light-row-cp-h, " +
                    "table.table-insider tr.insider-option-row, " +
                    "table.table-insider tr.insider-row, " +
                    "table.table-insider tr.insider-sale-row-1, " +
                    "table.table-insider tr.insider-sale-row-2");
            for (Element e : rows){
                stockElementList.add(InsiderTradeConverter.convert(e));
            }
        }
        catch (IOException e){
            System.out.println("COULD NOT CONNECT TO FINVIZ");
        }

 stockElementList.sort(new Comparator<StockElement>() {
    @Override
    public int compare(StockElement o1, StockElement o2) {
        return o1.getFilingDate().compareTo(o2.getFilingDate());
    }
});
    return stockElementList;
    }

public List<StockElement> scrapeTop(){
    List<String> scrapes = new ArrayList<>();
    List<StockElement> stockElementList = new ArrayList<>();

    try {
        Document doc = Jsoup.connect(topUrl).get();
        Elements rows = doc.select("table.table-insider tr.insider-buy-row-1, " +
                "table.table-insider tr.insider-buy-row-2, " +
                "table.table-insider tr.insider-light-row-cp-h, " +
                "table.table-insider tr.insider-option-row, " +
                "table.table-insider tr.insider-row, " +
                "table.table-insider tr.insider-sale-row-1, " +
                "table.table-insider tr.insider-sale-row-2");

        for (Element e : rows){
            stockElementList.add(InsiderTradeConverter.convert(e));
        }
    }
    catch (IOException e){
        System.out.println("COULD NOT CONNECT TO FINVIZ");
    }

    stockElementList.sort(new Comparator<StockElement>() {
        @Override
        public int compare(StockElement o1, StockElement o2) {
            return o1.getFilingDate().compareTo(o2.getFilingDate());
        }
    });
    return stockElementList;


}
    public List<StockElement> scrapeLatestBuy(){
        List<String> scrapes = new ArrayList<>();
        List<StockElement> stockElementList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(latestBuyUrl).get();
            Elements rows = doc.select("table.table-insider tr.insider-buy-row-1, " +
                    "table.table-insider tr.insider-buy-row-2, " +
                    "table.table-insider tr.insider-buy-row-1.cursor-pointer.align-top, " +
                    "table.table-insider tr.insider-buy-row-2.cursor-pointer.align-top");

            for (Element e : rows){
                stockElementList.add(InsiderTradeConverter.convert(e));
            }
        }
        catch (IOException e){
            System.out.println("COULD NOT CONNECT TO FINVIZ");
        }

        stockElementList.sort(new Comparator<StockElement>() {
            @Override
            public int compare(StockElement o1, StockElement o2) {
                return o1.getFilingDate().compareTo(o2.getFilingDate());
            }
        });
        return stockElementList;


    }

    public List<StockElement> scrapeLatestSell(){
        List<String> scrapes = new ArrayList<>();
        List<StockElement> stockElementList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(latestSellUrl).get();
            Elements rows = doc.select("table.table-insider tr.insider-sale-row-1, " +
                    "table.table-insider tr.insider-sale-row-2, " +
                    "table.table-insider tr.insider-sale-row-1.cursor-pointer.align-top, " +
                    "table.table-insider tr.insider-sale-row-2.cursor-pointer.align-top");

            for (Element e : rows){
                stockElementList.add(InsiderTradeConverter.convert(e));
            }
        }
        catch (IOException e){
            System.out.println("COULD NOT CONNECT TO FINVIZ");
        }

        stockElementList.sort(new Comparator<StockElement>() {
            @Override
            public int compare(StockElement o1, StockElement o2) {
                return o1.getFilingDate().compareTo(o2.getFilingDate());
            }
        });
        return stockElementList;


    }



}
