package com.zchalmers.stocks.highVolumeStocks.repositories;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockRecord, Timestamp> {

    List<StockRecord> findByTicker(String ticker);

    List<StockRecord> findByOwner(String owner);

    List<StockRecord> findByTransaction(String transaction);

    List<StockRecord> findByRelationship(String relationship);

    List<StockRecord> findByTransactionAndTotalvalueGreaterThan(String transaction, Double amount);

}
