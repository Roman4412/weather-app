package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.exception.UnauthorizedLocationAccessException;
import com.pustovalov.weatherapplication.service.LocationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    @GetMapping
    public String findLocation(@RequestParam @NotNull String cityName, Model model) {
        if (cityName.isEmpty()) {
            cityName = " ";
        }
        model.addAttribute("locations", openWeatherClient.getLocations(cityName));
        return "locations";
    }

    @PostMapping
    public String saveLocation(@NotNull LocationSaveDto locationSaveDto, @RequestAttribute @NotNull Long userId) {
        locationSaveDto.setUserId(userId);
        locationService.save(locationSaveDto);
        return "redirect:/locations/weather";
    }

    @DeleteMapping
    public String deleteLocation(@RequestParam @NotNull Long id, @RequestAttribute @NotNull Long userId) {
        locationService.delete(id, userId);
        return "redirect:/locations/weather";
    }

    @GetMapping("/weather")
    public String getForecast(Model model, @RequestAttribute @NotNull Long userId) {
        model.addAttribute("weatherData", locationService.getForecast(userId));
        return "main";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException() {
        return "/error/503";
    }

    @ExceptionHandler(UnauthorizedLocationAccessException.class)
    public String handleUnauthorizedLocationAccessException(Model model) {
        model.addAttribute("message",
                "You do not have the rights to delete this location, as it belongs to another user.");
        return "/error/403";
    }
}
