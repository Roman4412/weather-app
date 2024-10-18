package com.pustovalov.weatherapplication.clients;

import com.pustovalov.weatherapplication.dto.response.WeatherApiGeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "openWeatherClient")
public interface OpenWeatherClient {

    @GetMapping(value = ("${open-weather-api.geo-path}"), params = "q", produces = "application" + "/json")
    List<WeatherApiGeoResponse> getLocations(@RequestParam("q") String cityName);
}