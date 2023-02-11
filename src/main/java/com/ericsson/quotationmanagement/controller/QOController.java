package com.ericsson.quotationmanagement.controller;

import com.ericsson.quotationmanagement.model.Stock;
import com.ericsson.quotationmanagement.model.StockDTO;
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
    public List<Stock> get(){
        return qoService.getAllStock();
    }

    @PostMapping
    public Stock create(@RequestBody Stock stock){
        return qoService.createStock(stock);
    }
}
