package org.weather.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weather.app.dto.LocationDeleteRequest;
import org.weather.app.dto.LocationRequest;
import org.weather.app.dto.LocationResponse;
import org.weather.app.model.User;
import org.weather.app.service.LocationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/add")
    public LocationResponse add(@RequestBody LocationRequest locationRequest, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        return locationService.addLocation(user, locationRequest);
    }

    @PostMapping("/delete")
    public LocationResponse delete(@RequestBody LocationDeleteRequest locationDeleteRequest) {
        return locationService.deleteLocationById(locationDeleteRequest.id());
    }
}