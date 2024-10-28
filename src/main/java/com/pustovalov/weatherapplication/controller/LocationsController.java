package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.service.LocationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/location")
@Validated
public class LocationsController {

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    @GetMapping
    public String findLocations(@RequestParam @NotNull String cityName, Model model) {
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
        return "redirect:/weather";
    }

    @DeleteMapping
    public String deleteLocation(@RequestParam @NotNull Long id) {
        locationService.delete(id);
        return "weather";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException() {
        return "/error/503";
    }

}