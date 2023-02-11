package com.ericsson.quotationmanagement;

import com.ericsson.quotationmanagement.model.StockManagerNotificationRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.reactive.function.client.WebClient;

@EnableCaching
@SpringBootApplication
public class QuotationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationManagementApplication.class, args);
    }

}
