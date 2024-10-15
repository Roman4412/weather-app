package com.pustovalov.weatherapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${api.base-url}")
    private String url;

    @Bean
    public RestClient restClient() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS.withConnectTimeout(
                Duration.ofSeconds(10)).withReadTimeout(Duration.ofSeconds(15));

        return RestClient.builder().requestFactory(ClientHttpRequestFactories.get(settings)).baseUrl(url).build();
    }
}
