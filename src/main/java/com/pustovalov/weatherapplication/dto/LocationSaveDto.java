package com.pustovalov.weatherapplication.dto;

import java.math.BigDecimal;

public record LocationSaveDto(
        String name,
        Long userId,
        BigDecimal latitude,
        BigDecimal longitude
) {

}
