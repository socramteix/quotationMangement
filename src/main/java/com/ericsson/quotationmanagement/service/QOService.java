package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.model.Quote;
import com.ericsson.quotationmanagement.model.StockDTO;
import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class QOService {
    private List<Stock> stocks;
    private StockRepository stockRepository;

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    /*private Stock stockDtoToStock(StockDTO stockDTO) {
        Stock stock =  new Stock();
        stock.setStockId(stockDTO.getStockId());
        stock.setQuotes(new ArrayList<>());
        Quote quoteTemp;
        for(Map.Entry<String,String> entry: stockDTO.getQuotes().entrySet(){

        }
        return stock;
    }*/

    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }
}
