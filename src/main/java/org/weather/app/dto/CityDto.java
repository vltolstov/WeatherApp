package org.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CityDto {

    private Integer id;

    private String name;

    private String country;

    private CoordDto coord;

    private List<Map<String, String>> langs;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    public static class CoordDto {
        @JsonProperty("lon")
        private BigDecimal longitude;

        @JsonProperty("lat")
        private BigDecimal latitude;

        public BigDecimal getLongitude() {
            return normalize(longitude);
        }

        public BigDecimal getLatitude() {
            return normalize(latitude);
        }

        private BigDecimal normalize(BigDecimal value) {
            return value == null ? null : value.setScale(4, RoundingMode.DOWN);
        }
    }
}