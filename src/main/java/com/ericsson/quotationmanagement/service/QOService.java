package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.model.StockSM;
import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QOService {
    private List<Stock> stocks;
    private StockRepository stockRepository;
    private WebClient webClient;

    private String stockManagerUri;
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
            if(existsInStockManger(stock.getStockId()))
                return stockRepository.save(stock);
            else
                return null;
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

    private boolean existsInStockManger(String stockId){
        List<StockSM> stockSMs = webClient.get().uri(stockManagerUri)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<StockSM>>() {})
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(5)))
                .block();
        if(stockSMs.stream().filter(s -> s.getId().equals(stockId)).findFirst().isPresent())
            return true;
        else
            return false;
    }
}
