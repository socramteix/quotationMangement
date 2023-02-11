package com.ericsson.quotationmanagement.configuration;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.net.http.HttpClient;

@Configuration
public class QOConfiguration {

    @Value("${stock-manager.uri}")
    private String stockManagerUri;
    @Bean
    public WebClient webClient() throws SSLException{
        return WebClient.builder().build();
    }

    @Bean
    public String getStockManagerUri(){return stockManagerUri;}

}
