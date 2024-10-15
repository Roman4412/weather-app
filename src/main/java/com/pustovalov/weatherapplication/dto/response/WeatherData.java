package com.pustovalov.weatherapplication.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pustovalov.weatherapplication.config.WeatherDataDeserializer;

@JsonDeserialize(using = WeatherDataDeserializer.class)
public record WeatherData(
        String locationName,
        String temperature,
        String windSpeed,
        String humidity,
        String pressure
) {

}
