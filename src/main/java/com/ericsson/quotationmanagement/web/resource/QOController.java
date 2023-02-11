package com.ericsson.quotationmanagement.web.resource;

import com.ericsson.quotationmanagement.model.Stock;
import com.ericsson.quotationmanagement.service.QOService;
import com.ericsson.quotationmanagement.web.error.StockNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/stock")
@AllArgsConstructor
public class QOController {

    QOService qoService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks(){
        return ResponseEntity.ok().body(qoService.getAllStock());
    }

    @ResponseBody
    @GetMapping(path = "/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable(name = "stockId") String stockId){
        Stock stock = qoService.getStockById(stockId);
        if(stock != null)
            return ResponseEntity.ok().body(stock);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Stock> create(@RequestBody Stock stock) throws URISyntaxException {
        Stock createdStock = qoService.createStock(stock);
        if(createdStock != null){
            return ResponseEntity.created(new URI("/stock/" + createdStock.getStockId()))
                    .body(createdStock);
        }
        else {
            throw new StockNotFoundException("Stock "+stock.getStockId()+" not found in stock-manager application");
        }
    }

    /*@ResponseBody
    @GetMapping(path = "/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable(name = "stockId") String stockId){
        Stock stock = qoService.getStockById(stockId);
        if(stock != null)
            return ResponseEntity.ok().body(stock);
        else
            return ResponseEntity.notFound().build();
    }*/

}
