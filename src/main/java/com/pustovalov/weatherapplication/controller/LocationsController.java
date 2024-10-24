package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.service.LocationService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("location")
@Validated
public class LocationsController {

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    @GetMapping
    public String findLocations(@RequestParam @NotBlank String cityName, Model model) {
        model.addAttribute("locations", openWeatherClient.getLocations(cityName));
        return "locations";
    }

    @PostMapping
    public String saveLocation(@NotNull LocationSaveDto locationSaveDto) {
        locationService.save(locationSaveDto);
        return "redirect:/weather";
    }

    @DeleteMapping
    public String deleteLocation(@RequestParam @NotNull Long id) {
        locationService.delete(id);
        return "weather";
    }
}
