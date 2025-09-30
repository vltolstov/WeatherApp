package org.weather.app.dto;

import java.math.BigDecimal;

public record AddLocationRequest(String name, BigDecimal longitude, BigDecimal latitude) {
}
