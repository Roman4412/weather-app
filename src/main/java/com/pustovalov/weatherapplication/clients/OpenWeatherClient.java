package com.pustovalov.weatherapplication.clients;

import com.pustovalov.weatherapplication.dto.response.WeatherApiDataResponse;
import com.pustovalov.weatherapplication.dto.response.WeatherApiGeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "openWeatherClient")
public interface OpenWeatherClient {

    @GetMapping(value = ("${open-weather-api.geo-path}"), params = "q", produces = "application/json")
    List<WeatherApiGeoResponse> getLocations(@RequestParam("q") String cityName);

    @GetMapping(value = "${open-weather-api.weather-path}", params = {"lat", "lon", "units=${open-weather-api.units}"},
                produces = "application" + "/json")
    WeatherApiDataResponse getWeather(@RequestParam("lat") BigDecimal lat, @RequestParam("lon") BigDecimal lon);
}