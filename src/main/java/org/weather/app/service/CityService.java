package org.weather.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.weather.app.dto.CityDto;
import org.weather.app.exception.CityJsonProcessingException;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final ObjectMapper objectMapper;
    private final Resource cityResource;
    private Map<String, List<CityDto>> citiesByName;

    public CityService(@Value("${city.list.json.path}") Resource cityResource, ObjectMapper objectMapper) {
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
        } catch (Exception e) {
            throw new CityJsonProcessingException("Could not parse json city list file");
        }
    }

    public List<CityDto> getCities(String cityName) {
        System.out.println(cityName.toLowerCase());
        List<CityDto> cities = citiesByName.get(cityName.toLowerCase());
        return cities != null ? cities : Collections.emptyList();
    }

    public boolean isCityExist(String cityName) {
        return citiesByName.containsKey(cityName.toLowerCase());
    }
}
