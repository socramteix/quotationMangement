package com.ericsson.quotationmanagement.controller;

import com.ericsson.quotationmanagement.model.Stock;
import com.ericsson.quotationmanagement.service.QOService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stock")
@AllArgsConstructor
public class QOController {

    QOService qoService;

    @ResponseBody
    @GetMapping
    public List<Stock> getAllStocks(){
        return qoService.getAllStock();
    }

    @ResponseBody
    @GetMapping(path = "/{stockId}")
    public Stock getStockById(@RequestParam String stockId){
        return qoService.getStockById(stockId);
    }

    @PostMapping
    public Stock create(@RequestBody Stock stock){
        return qoService.createStock(stock);
    }
}
