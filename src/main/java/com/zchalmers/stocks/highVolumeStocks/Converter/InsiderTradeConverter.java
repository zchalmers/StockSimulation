package com.zchalmers.stocks.highVolumeStocks.Converter;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import com.zchalmers.stocks.highVolumeStocks.service.model.StockElement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

public class InsiderTradeConverter {
    public static StockElement convert(Element row) {
        StockElement trade = new StockElement();

        Elements cells = row.select("td");

        trade.setTicker(cells.get(0).text());
        trade.setOwner(cells.get(1).text());
        trade.setRelationship(cells.get(2).text());
        trade.setDate(cells.get(3).text());
        trade.setTransaction(cells.get(4).text());
        trade.setCost(Double.parseDouble(cells.get(5).text().replace(",", "")));
        trade.setShares(Integer.parseInt(cells.get(6).text().replace(",", "")));
        trade.setTotalValue(Double.valueOf(cells.get(7).text().replace(",", "")));
        trade.setTotalShares(Double.valueOf(cells.get(8).text().replace(",", "")));
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MMM dd hh:mm a")
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter()
                .withZone(ZoneId.systemDefault());
        ZonedDateTime tempTime = ZonedDateTime.parse(cells.get(9).text(), formatter);
        String newDate = tempTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        trade.setFilingDate(newDate);
        System.out.println(trade.toString());
        return trade;
    }

//    public static String zoneDateTimeToTimeStamp (String date){
//        return Timestamp.from(date.toInstant());
//    }
//
//    public static ZonedDateTime timeStampToZonedDateTime (Timestamp date){
//        return date.toLocalDateTime().atZone(ZoneOffset.UTC);
//    }
    public static StockRecord elementToRecord (StockElement element){
        StockRecord record = new StockRecord(element.getTicker(),
                element.getOwner(),element.getRelationship(), element.getTransaction()
                ,element.getCost(), element.getShares(),
                element.getTotalValue(), element.getFilingDate());
        return record;
    }

    public static List<StockRecord> elementListToRecord(List<StockElement> elementList){
        List<StockRecord> recordList = elementList.stream()
                .map(e -> new StockRecord(e.getTicker(), e.getOwner(), e.getRelationship(),
                        e.getTransaction(), e.getCost(), e.getShares(), e.getTotalValue(),
                        e.getFilingDate()))
                .collect(Collectors.toList());
        return recordList;

    }




}
