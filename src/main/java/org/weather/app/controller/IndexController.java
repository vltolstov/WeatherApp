package org.weather.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.weather.app.dto.SearchRequest;
import org.weather.app.dto.WeatherResponseDto;
import org.weather.app.model.Location;
import org.weather.app.model.User;
import org.weather.app.repository.LocationRepository;
import org.weather.app.service.WeatherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final WeatherService weatherService;
    private final LocationRepository locationRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("searchRequest", new SearchRequest());

        User user = (User) httpServletRequest.getAttribute("user");

        boolean exists = locationRepository.existsByUser(user);

        if (!exists) {
            model.addAttribute("locationsNotFound", "Города отсутствуют. Добавьте через поиск");
            return "pages/index";
        }

        List<Location> locations = locationRepository.findByUser(user);
        List<WeatherResponseDto> userWeatherList = locations.stream()
                .map(location -> weatherService.getWeatherByCoordinates(location.getLongitude(), location.getLatitude()))
                .toList();
        model.addAttribute("userWeatherList", userWeatherList);

        return "pages/index";
    }
}
