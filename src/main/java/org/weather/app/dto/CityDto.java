package org.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CityDto {

    private String name;

    private String country;

    private CoordDto coord;

    private List<Map<String, String>> langs;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class CoordDto {

        @JsonProperty("lat")
        private Double latitude;

        @JsonProperty("lon")
        private Double longitude;
    }
}