package org.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class WeatherResponseDto {

    private int id;

    private String name;

    @JsonProperty("coord")
    private Coordinate coordinate;

    private List<Weather> weather;

    private Main main;

    private Sys sys;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Coordinate {
        @JsonProperty("lon")
        private BigDecimal longitude;
        @JsonProperty("lat")
        private BigDecimal latitude;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Weather {
        private String description;
        private String icon;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Main {
        double temp;
        @JsonProperty("feels_like")
        double feelsLike;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Sys {
        String country;
    }
}
