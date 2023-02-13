package com.ericsson.quotationmanagement.service;

import com.ericsson.quotationmanagement.model.StockSM;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class QMIntegrationService {

    private WebClient webClient;
    private Logger logger;
    private String stockManagerUri;

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
