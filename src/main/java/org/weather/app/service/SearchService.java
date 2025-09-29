package org.weather.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.weather.app.dto.CityDto;
import org.weather.app.exception.CityJsonProcessingException;
import org.weather.app.model.Coord;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final ObjectMapper objectMapper;
    private final Resource cityResource;
    private Map<String, List<CityDto>> citiesByName;
    private Map<Coord, List<CityDto>> citiesByCoordinates;
    private static final Pattern COORD_PATTERN = Pattern.compile("^\\s*(-?\\d+(?:\\.\\d+)?)\\s+(-?\\d+(?:\\.\\d+)?)\\s*$");

    public SearchService(@Value("${city.list.json.path}") Resource cityResource, ObjectMapper objectMapper) {
        this.cityResource = cityResource;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init() {
        try (InputStream inputStream = cityResource.getInputStream()) {
            List<CityDto> cities = objectMapper.readValue(inputStream, new TypeReference<List<CityDto>>() {
            });
            this.citiesByName = cities.stream()
                    .collect(Collectors.groupingBy(
                            city -> city.getName().toLowerCase(),
                            HashMap::new,
                            Collectors.toList()
                    ));
            this.citiesByCoordinates = cities.stream()
                    .collect(Collectors.groupingBy(
                            city -> new Coord(city.getCoord().getLongitude(), city.getCoord().getLatitude()),
                            HashMap::new,
                            Collectors.toList()
                    ));
        } catch (Exception e) {
            throw new CityJsonProcessingException("Could not parse json city list file");
        }
    }

    private Optional<Coord> parseCoordinates(String query) {
        Matcher matcher = COORD_PATTERN.matcher(query.trim());
        if (matcher.matches()) {
            BigDecimal longitude = new BigDecimal(matcher.group(1));
            BigDecimal latitude = new BigDecimal(matcher.group(2));
            return Optional.of(new Coord(longitude, latitude));
        }
        return Optional.empty();
    }

    public boolean isLocationExist(String query) {
        return parseCoordinates(query)
                .map(coord -> citiesByCoordinates.containsKey(coord))
                .orElseGet(() -> citiesByName.containsKey(query.toLowerCase()));
    }

    public List<CityDto> getLocations(String query) {
        return parseCoordinates(query)
                .map(coord -> searchByCoordinates(coord.longitude(), coord.latitude()))
                .orElseGet(() -> searchByCity(query));
    }

    public List<CityDto> searchByCoordinates(BigDecimal longitude, BigDecimal latitude) {
        return citiesByCoordinates.getOrDefault(new Coord(longitude, latitude), Collections.emptyList());
    }

    public List<CityDto> searchByCity(String cityName) {
        List<CityDto> cities = citiesByName.get(cityName.toLowerCase());
        return cities != null ? cities : Collections.emptyList();
    }
}
