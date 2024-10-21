package com.pustovalov.weatherapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiDataResponse {

    private Long locationId;

    private String LocationName;

    private Coord coord;

    private Main main;

    private Wind wind;
}

@NoArgsConstructor
@Getter
@Setter
class Coord {

    private BigDecimal lon;

    private BigDecimal lat;
}

@NoArgsConstructor
@Getter
@Setter
class Main {

    private Integer temp;

    private Integer humidity;

    private Integer pressure;
}

@NoArgsConstructor
@Getter
@Setter
class Wind {

    private Double speed;

}
