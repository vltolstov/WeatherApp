package org.weather.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weather.app.dto.LocationRequest;
import org.weather.app.dto.LocationResponse;
import org.weather.app.mapper.LocationMapper;
import org.weather.app.model.Location;
import org.weather.app.model.User;
import org.weather.app.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationResponse addLocation(User user, LocationRequest locationRequest) {
        boolean exists = locationRepository.existsByUserAndLongitudeAndLatitude(
                user,
                locationRequest.longitude(),
                locationRequest.latitude()
        );

        if (exists) {
            return new LocationResponse(false, "Город уже добавлен");
        }

        Location location = locationMapper.toEntity(locationRequest, user);
        locationRepository.save(location);

        return new LocationResponse(true, "Добавлен");
    }

    @Transactional
    public LocationResponse deleteLocationById(Long id) {
        try {
            locationRepository.deleteById(id);
            return new LocationResponse(true, "Город удален");
        } catch (EmptyResultDataAccessException e) {
            return new LocationResponse(false, "Город не найден");
        }
    }
}