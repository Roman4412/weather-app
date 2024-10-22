package com.pustovalov.weatherapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationSaveDto {

    private String name;

    private Long userId;

    private BigDecimal latitude;

    private BigDecimal longitude;

}
