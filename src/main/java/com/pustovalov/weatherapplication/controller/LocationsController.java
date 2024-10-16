package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.service.LocationService;
import com.pustovalov.weatherapplication.service.OpenWeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor

@Controller
@RequestMapping("location")
public class LocationsController {

    private final LocationService locationService;

    private final OpenWeatherApiService openWeatherApiService;

    @GetMapping
    public String findLocations(@RequestParam String cityName, Model model) {
        model.addAttribute("locations", openWeatherApiService.getLocations(cityName));
        return "locations";
    }

    @PostMapping
    public String saveLocation(LocationSaveDto locationSaveDto) {
        locationService.save(locationSaveDto);
        return "redirect:/weather";
    }

}
