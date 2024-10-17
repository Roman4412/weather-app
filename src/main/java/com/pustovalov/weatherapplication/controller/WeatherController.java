package com.pustovalov.weatherapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @GetMapping
    public String getPage() {
        return "weather";
    }

}
