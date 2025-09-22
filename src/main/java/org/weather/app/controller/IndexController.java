package org.weather.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.weather.app.dto.SearchRequest;
import org.weather.app.service.WeatherService;

@Controller
public class IndexController {

    private WeatherService weatherService;

    public IndexController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());

        weatherService.getWeatherByCity("Novosibirsk");
        return "pages/index";
    }
}
