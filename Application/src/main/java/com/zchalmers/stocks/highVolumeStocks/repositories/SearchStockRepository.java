package com.zchalmers.stocks.highVolumeStocks.repositories;

import com.zchalmers.stocks.highVolumeStocks.service.model.SearchStockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchStockRepository extends JpaRepository<SearchStockRecord, String>{

}
