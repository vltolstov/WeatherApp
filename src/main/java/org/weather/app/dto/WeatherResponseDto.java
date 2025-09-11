package org.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {

    private int id;

    private String name;

    @JsonProperty("Coord")
    private Coordinate coordinate;

    private Weather weather;

    private Main main;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coordinate {
        @JsonProperty("lon")
        private double longitude;
        @JsonProperty("lat")
        private double latitude;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String description;
        private String icon;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        String temp;
        @JsonProperty("feels_like")
        String feelsLike;
    }
}
