package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.response.WeatherApiDataResponse;
import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.service.LocationService;
import com.pustovalov.weatherapplication.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/weather")
public class WeatherController {

    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    private final SessionService sessionService;

    @GetMapping
    public String getPage(Model model, HttpServletRequest request) {
        String sessionId = Arrays.stream(request.getCookies())
                .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .orElseThrow()
                .getValue();

        long userId = sessionService.findBy(UUID.fromString(sessionId))
                .getUser()
                .getId();

        List<Location> locations = locationService.getAll(userId);
        List<WeatherApiDataResponse> allWeather = locations.stream().map(loc -> {
            WeatherApiDataResponse weather = openWeatherClient.getWeather(loc.getLatitude(), loc.getLongitude());
            weather.setLocationId(loc.getId());
            weather.setLocationName(loc.getName());
            return weather;
        }).toList();

        model.addAttribute("weatherData", allWeather);
        return "weather";
    }

    @PostMapping
    public String logout(HttpServletRequest request) {
        String sessionId = Arrays.stream(request.getCookies())
                .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .orElseThrow()
                .getValue();

        sessionService.delete(UUID.fromString(sessionId));

        return "redirect:/login";
    }

}
