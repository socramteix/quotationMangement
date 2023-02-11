package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class QOService {
    private List<Stock> stocks;

    public Stock createStock(Stock stock){
        if(stocks.isEmpty())
            stocks =new ArrayList<>();

        stocks.add(stock);
        return stock;
    }

    public List<Stock> getAllStock(){
        if(stocks.isEmpty())
            stocks = Collections.emptyList();
        return stocks;
    }
}
