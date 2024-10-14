package com.pustovalov.weatherapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.pustovalov.weatherapplication.dto.WeatherApiGeoResponse;
import com.pustovalov.weatherapplication.dto.response.WeatherData;
import com.pustovalov.weatherapplication.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor

@Service
public class OpenWeatherApiService {


    private static final String Q_PARAM_NAME = "q";

    private static final String APPID_PARAM_NAME = "appid";

    public static final String LATITUDE_PARAM_NAME = "lat";

    public static final String LONGITUDE_PARAM_NAME = "lon";

    private static final String UNITS_PARAM_NAME = "units";

    private static final String DEFAULT_UNITS = "metric";

    private static final String LIMIT_PARAM_NAME = "limit";

    private static final String GEO_PATH = "/geo/1.0/direct";

    private static final String WEATHER_PATH = "/data/2.5/weather";

    private final RestClient restClient;

    private final ObjectMapper objectMapper;

    @Value("${api.key}")
    private String apiKey;

    public List<WeatherApiGeoResponse> getLocations(String cityName) {
        //TODO обработать ошибки api
        //TODO нужны повторные попытки соединения при неудачной попытке
        List<WeatherApiGeoResponse> result;

        String body = restClient.get().uri(uriBuilder -> uriBuilder.path(GEO_PATH)
                        .queryParam(Q_PARAM_NAME, cityName)
                        .queryParam(LIMIT_PARAM_NAME, 15)
                        .queryParam(APPID_PARAM_NAME, apiKey)
                        .build())
                .retrieve()
                .body(String.class);

        CollectionType targetType = objectMapper.getTypeFactory().constructCollectionType(List.class,
                                                                                          WeatherApiGeoResponse.class);

        try {
            result = Collections.unmodifiableList(objectMapper.readValue(body, targetType));
        } catch (JsonProcessingException e) {
            result = Collections.emptyList();
            e.printStackTrace();
        }
        return result;
    }

    public WeatherData getWeather(Location location) {
        //TODO обработать ошибки api
        //TODO нужны повторные попытки соединения при неудачной попытке
        return restClient.get().uri(uriBuilder -> uriBuilder.path(WEATHER_PATH)
                .queryParam(LATITUDE_PARAM_NAME, location.getLatitude())
                .queryParam(LONGITUDE_PARAM_NAME, location.getLongitude())
                        .queryParam(APPID_PARAM_NAME, apiKey)
                        .queryParam(UNITS_PARAM_NAME, DEFAULT_UNITS)
                        .build()).retrieve().body(WeatherData.class);
    }
}