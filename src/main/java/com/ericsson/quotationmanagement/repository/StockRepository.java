package com.ericsson.quotationmanagement.repository;

import com.ericsson.quotationmanagement.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    Optional<Stock> findByStockId(String stockId);

}
