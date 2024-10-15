package com.pustovalov.weatherapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@RequiredArgsConstructor

@Service
public class OpenWeatherApiService {

    private static final String Q_PARAM_NAME = "q";

    private static final String APPID_PARAM_NAME = "appid";

    public static final String LATITUDE_PARAM_NAME = "lat";

    public static final String LONGITUDE_PARAM_NAME = "lon";

    private static final String UNITS_PARAM_NAME = "units";

    private static final String DEFAULT_UNITS = "metric";

    private final RestClient restClient;

    @Value("${api.key}")
    private String apiKey;

    public void getWeather(String cityName) {
        //TODO обработать ошибки api
        restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/data/2.5/weather")
                        .queryParam(Q_PARAM_NAME, cityName)
                        .queryParam(APPID_PARAM_NAME, apiKey)
                        .queryParam(UNITS_PARAM_NAME, DEFAULT_UNITS)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public void getWeather(BigDecimal latitude, BigDecimal longitude) {
        //TODO обработать ошибки api
        restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/data/2.5/weather")
                        .queryParam(LATITUDE_PARAM_NAME, latitude)
                        .queryParam(LONGITUDE_PARAM_NAME, longitude)
                        .queryParam(APPID_PARAM_NAME, apiKey)
                        .queryParam(UNITS_PARAM_NAME, DEFAULT_UNITS)
                        .build())
                .retrieve()
                .body(String.class);
    }

}