package org.weather.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.weather.app.dto.WeatherResponseDto;
import org.weather.app.exception.EmptyWeatherResponseException;
import org.weather.app.exception.WeatherJsonProcessingException;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_KEY = " ";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherService(WebClient webClient) {
        this.webClient = webClient;
    }

    public WeatherResponseDto getWeatherByCity(String city) {
        String url = buildUrl("q", city);
        String response = fetchResponse(url);
        return parseWeatherResponse(response);
    }

    public WeatherResponseDto getWeatherByCoordinates(String latitude, String longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("lang", "ru")
                .queryParam("units", "metric")
                .queryParam("appid", API_KEY)
                .toUriString();
        String response = fetchResponse(url);
        return parseWeatherResponse(response);
    }

    private String buildUrl(String param, String value) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(param, value)
                .queryParam("lang", "ru")
                .queryParam("units", "metric")
                .queryParam("appid", API_KEY)
                .toUriString();
    }

    private String fetchResponse(String url) {
        String json = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    int code = clientResponse.statusCode().value();
                    switch (code) {
                        case 403:
                            return Mono.error(new RuntimeException("403 Access denied"));
                        case 404:
                            return Mono.error(new RuntimeException("404 Not Found"));
                        case 500:
                            return Mono.error(new RuntimeException("500 Internal Server Error"));
                        default:
                            return Mono.error(new RuntimeException("Unknown Error: " + code));
                    }
                })
                .bodyToMono(String.class)
                .block();

        if (json == null || json.isEmpty()) {
            throw new EmptyWeatherResponseException("Empty weather server response");
        }

        return json;
    }

    private WeatherResponseDto parseWeatherResponse(String response) {
        try {
            return objectMapper.readValue(response, WeatherResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new WeatherJsonProcessingException("Parsing JSON error");
        }
    }

}
