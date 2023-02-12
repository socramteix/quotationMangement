package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.model.StockSM;
import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.model.Stock;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QMService {
    private StockRepository stockRepository;
    private WebClient webClient;
    private Logger logger;
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
            if(existsInStockManager(stock))
                    return stockRepository.save(stock);
            else
                return null;
        }
    }

    public boolean existsInStockManager(Stock stock) {
        return fetchAllStocksFromStockManager()
                .stream().filter(s -> s.getId().equals(stock.getStockId()))
                .findFirst().isPresent();
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

    /**
     * fetchAllStocksFromStockManager method brings from
     * stock-manager application all the stocks stored.
     * However, this method is Cacheable. This means that
     * The data is fetched only in case that
     * there is no data cached
     * @return List<StockSM> with all stocks from stock-manager
     */
    @Cacheable("stocks")
    public List<StockSM> fetchAllStocksFromStockManager(){
        logger.info("stock-manager called at "+stockManagerUri);
        return webClient.get().uri(stockManagerUri)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<StockSM>>() {})
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(5)))
                .block();
    }


}
