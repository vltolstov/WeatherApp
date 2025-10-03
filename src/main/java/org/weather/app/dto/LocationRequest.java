package org.weather.app.dto;

import java.math.BigDecimal;

public record LocationRequest(String location, BigDecimal longitude, BigDecimal latitude) {
}
