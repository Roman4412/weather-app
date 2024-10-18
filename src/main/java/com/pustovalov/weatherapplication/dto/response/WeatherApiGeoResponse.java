package com.pustovalov.weatherapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherApiGeoResponse(
        String name,
        String lat,
        String lon,
        String country,
        String state
) {


}
