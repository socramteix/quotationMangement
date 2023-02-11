package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QOService {
    private List<Stock> stocks;
    private StockRepository stockRepository;

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    public Stock getStockById(String stockId){
        Optional optional = stockRepository.findByStockId(stockId);
        if(optional.isPresent())
            return (Stock)optional.get();
        else
            return null;
    }
}
