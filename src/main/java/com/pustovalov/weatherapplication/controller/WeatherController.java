package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.response.WeatherApiDataResponse;
import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/weather")
public class WeatherController {

    private static final Long USER_ID = 1L;

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    @GetMapping
    public String getPage(Model model) {
        List<Location> locations = locationService.getAll(USER_ID);
        List<WeatherApiDataResponse> allWeather =
                locations.stream()
                         .map(l -> {
                             WeatherApiDataResponse weather = openWeatherClient.getWeather(l.getLatitude(), l.getLongitude());
                             weather.setLocationId(l.getId());
                             weather.setLocationName(l.getName());
                             return weather;
                         })
                         .toList();
        model.addAttribute("weatherData", allWeather);
        return "weather";
    }

}
