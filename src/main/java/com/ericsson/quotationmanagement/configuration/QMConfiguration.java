package com.ericsson.quotationmanagement.configuration;

import com.ericsson.quotationmanagement.QuotationManagementApplication;
import com.ericsson.quotationmanagement.model.StockManagerNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.net.ssl.SSLException;
import javax.sql.DataSource;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class QMConfiguration {

    @Value("${stock-manager.uri}")
    private String stockManagerUri;
    @Value("${stock-manager.registrationUri}")
    private String stockManagerRegistrationUri;
    @Bean
    public WebClient webClient() throws SSLException{
        return WebClient.builder().build();
    }

    @Bean
    public String getStockManagerUri(){return stockManagerUri;}

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(QuotationManagementApplication.class);
    }

    /** Defined bean registerApplicationInStockManager
     * in order to run with system startup
     */
    @Bean
    @Profile("!test")
    public boolean registerApplicationInStockManager(){
        StockManagerNotificationRequest request = new StockManagerNotificationRequest();
        request.setHost("localhost");
        request.setPort(8081);
        try {
            webClient().post().uri(stockManagerRegistrationUri)
                    .body(Mono.just(request),StockManagerNotificationRequest.class)
                    .retrieve()
                    .bodyToMono(List.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(5)))
                    .block();
            getLogger().info("Registered in Stock-Manager application");
            return true;
        }
        catch (Exception e){
            getLogger().info("Error when registering in Stock-Manager");
            getLogger().info(e.getStackTrace().toString());
            return false;
        }
    }
}
