package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.service.LocationService;
import com.pustovalov.weatherapplication.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/location")
@Validated
public class LocationsController {
    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final LocationService locationService;

    private final OpenWeatherClient openWeatherClient;

    private final SessionService sessionService;

    @GetMapping
    public String findLocations(@RequestParam @NotBlank String cityName, Model model) {
        model.addAttribute("locations", openWeatherClient.getLocations(cityName));
        return "locations";
    }

    @PostMapping
    public String saveLocation(@NotNull LocationSaveDto locationSaveDto, HttpServletRequest request) {
        String sessionId = Arrays.stream(request.getCookies())
                .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .orElseThrow()
                .getValue();

        long userId = sessionService.findBy(UUID.fromString(sessionId))
                .getUser()
                .getId();

        locationSaveDto.setUserId(userId);
        locationService.save(locationSaveDto);

        return "redirect:/weather";
    }

    @DeleteMapping
    public String deleteLocation(@RequestParam @NotNull Long id) {
        locationService.delete(id);
        return "weather";
    }
}
