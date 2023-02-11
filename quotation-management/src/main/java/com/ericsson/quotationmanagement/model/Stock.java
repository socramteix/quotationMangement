package com.ericsson.quotationmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
public class Stock {
    String id;
    String stockId;
    List<Map<String,String>> quotes;
}
