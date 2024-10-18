package com.pustovalov.weatherapplication.dto.response;

import java.util.List;

public record WeatherApiDataResponse(
        Coord coord,
        List<Weather> weather,
        Main main,
        Wind wind,
        Rain rain,
        Clouds clouds,
        Sys sys,
        int timezone,
        int id,
        String name,
        int cod
) {
    public record Coord(double lon, double lat) {}

    public record Weather(int id, String main, String description, String icon) {}

    public record Main(
            double temp,
            double feels_like,
            double temp_min,
            double temp_max,
            int pressure,
            int humidity,
            int sea_level,
            int grnd_level
    ) {}

    public record Wind(double speed, int deg, double gust) {}

    public record Rain(double _1h) {}

    public record Clouds(int all) {}

    public record Sys(
            int type,
            int id,
            String country,
            long sunrise,
            long sunset
    ) {}
}
