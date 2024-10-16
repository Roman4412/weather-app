package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.service.OpenWeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor

@Controller
@RequestMapping("/location")
public class LocationController {

    private final OpenWeatherApiService openWeatherApiService;

    @GetMapping("/search")
    public String findLocation(@RequestParam String cityName, Model model) {
        model.addAttribute("locations", openWeatherApiService.getLocations(cityName));
        return "/locations";
    }

}
