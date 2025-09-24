package org.weather.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CityDto {


    private String name;

    private String country;

    private CoordDto coord;

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
