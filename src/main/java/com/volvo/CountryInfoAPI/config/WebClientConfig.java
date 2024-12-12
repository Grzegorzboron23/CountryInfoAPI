package com.volvo.CountryInfoAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${COUNTRY_API_URL}")
    private String countryApiUrl;

    @Bean
    public WebClient countryWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(countryApiUrl).build();
    }
}