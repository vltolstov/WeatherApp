package org.weather.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weather.app.dto.AddLocationRequest;
import org.weather.app.dto.AddLocationResponse;
import org.weather.app.model.Location;
import org.weather.app.model.User;
import org.weather.app.repository.LocationRepository;
import org.weather.app.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @PostMapping("/add")
    public AddLocationResponse add(@RequestBody AddLocationRequest addLocationRequest, HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getAttribute("user");

        boolean exists = locationRepository.existsByUserAndLatitudeAndLongitude(user, addLocationRequest.longitude(), addLocationRequest.latitude());

        if (exists) {
            return new AddLocationResponse(false, "Город уже добавлен");
        }

        Location location = new Location();
        location.setUser(user);
        location.setName(addLocationRequest.name());
        location.setLatitude(addLocationRequest.latitude());
        location.setLongitude(addLocationRequest.longitude());
        locationRepository.save(location);

        return new AddLocationResponse(true, "Что-то добавили");

    }
    
    @PostMapping("/delete")
    public void delete() {

    }

}
