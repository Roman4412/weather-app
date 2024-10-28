package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.response.WeatherApiDataResponse;
import com.pustovalov.weatherapplication.service.LocationService;
import com.pustovalov.weatherapplication.service.SessionService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    private final SessionService sessionService;

    @GetMapping
    public String getPage(Model model, @RequestAttribute @NotNull Long userId) {
        List<WeatherApiDataResponse> allWeather =
            locationService.getAll(userId).stream()
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

    @PostMapping
    public String logout(@RequestAttribute @NotNull String sessionId) {
        sessionService.delete(UUID.fromString(sessionId));
        return "redirect:/login";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException(Model model) {
        model.addAttribute("errorMessage", "Data could not be received from an external service");
        return "weather";
    }
}
