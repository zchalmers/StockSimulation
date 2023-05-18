package com.zchalmers.stocks.highVolumeStocks.repositories;

import com.zchalmers.stocks.highVolumeStocks.repositories.model.PortfolioRecord;
import com.zchalmers.stocks.highVolumeStocks.repositories.model.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioRecord, String> {


}