package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QOService {
    private List<Stock> stocks;
    private StockRepository stockRepository;

    /** create a new stock,
     * if stock already exists, just add more quotes
     * @param stock
     * @return Stock
     */
    public Stock createStock(Stock stock){
        Optional<Stock> existingStock = stockRepository.findByStockId(stock.getStockId());
        if(existingStock.isPresent()){
            existingStock.get().getQuotes().putAll(stock.getQuotes());
            return stockRepository.save(existingStock.get());
        }
        else {
            return stockRepository.save(stock);
        }
    }

    public Stock updateStock(Stock stock){
        return stockRepository.save(stock);
    }

    /** fetch all stocks stored in DB
     * @return List<Stock>
     */
    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    /** fetch a stock stored in DB by its stockId
     * @param stockId
     * @return Stock
     */
    public Stock getStockById(String stockId){
        Optional optional = stockRepository.findByStockId(stockId);
        if(optional.isPresent())
            return (Stock)optional.get();
        else
            return null;
    }
}
