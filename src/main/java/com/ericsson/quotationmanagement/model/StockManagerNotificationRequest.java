package com.ericsson.quotationmanagement.model;

import lombok.Data;

@Data
public class StockManagerNotificationRequest {
    String host;
    Integer port;
}
