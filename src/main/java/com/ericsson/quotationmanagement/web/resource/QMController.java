package com.ericsson.quotationmanagement.web.resource;

import com.ericsson.quotationmanagement.model.Stock;
import com.ericsson.quotationmanagement.service.QMService;
import com.ericsson.quotationmanagement.web.error.StockNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
public class QMController {

    QMService QMService;
    Logger logger;
    @ResponseBody
    @GetMapping(path = "/stock")
    public ResponseEntity<List<Stock>> getAllStocks(){
        return ResponseEntity.ok().body(QMService.getAllStock());
    }

    /**
     * With this resource it is possible to fetch a Stock
     * by its stockId
     * @param stockId
     * @return
     */
    @ResponseBody
    @GetMapping(path = "/stock/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable(name = "stockId") String stockId){
        Stock stock = QMService.getStockById(stockId);
        if(stock != null)
            return ResponseEntity.ok().body(stock);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Whith this resource we can create a new Stock
     * @param stock
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path =  "/stock")
    public ResponseEntity<Stock> create(@RequestBody Stock stock) throws URISyntaxException {
        Stock createdStock = QMService.createStock(stock);
        if(createdStock != null){
            return ResponseEntity.created(new URI("/stock/" + createdStock.getStockId()))
                    .body(createdStock);
        }
        else {
            throw new StockNotFoundException("Stock "+stock.getStockId()+" not found in stock-manager application");
        }
    }

    /**
     * Method that clean cache when stock-Manager
     * application invokes this andpoint.
     * CacheEvict cleans 'stocks' cache
     * @return
     */
    @CacheEvict("stocks")
    @DeleteMapping(path = "/stockcache")
    public ResponseEntity deleteCache(){
        logger.info("Cache cleaned");
        return ResponseEntity.ok().build();
    }

}
