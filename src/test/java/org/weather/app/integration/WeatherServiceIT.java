package org.weather.app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.weather.app.dto.WeatherResponseDto;
import org.weather.app.service.WeatherService;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceIT {

    @Mock
    private WebClient mockWebClient;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersUriSpec mockRequest;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec mockRequestHeaders;

    @Mock
    private WebClient.ResponseSpec mockResponseSpec;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        weatherService = new WeatherService(mockWebClient, objectMapper, "test-api-key");
    }

    private void mockWebClientBase() {
        when(mockWebClient.get()).thenReturn(mockRequest);
        when(mockRequest.uri(anyString())).thenReturn(mockRequestHeaders);
        when(mockRequestHeaders.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.onStatus(any(), any())).thenReturn(mockResponseSpec);
    }

    @Test
    @DisplayName("Получение погоды по названию города")
    public void testGetWeatherRequest_ShouldReturnWeatherByCity() {
        String city = "Новосибирск";
        String jsonResponse = """
                    {
                        "timezone": 25200,
                        "id": 1496747,
                        "name": "Новосибирск",
                        "cod": 200
                    }
                """;
        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

        WeatherResponseDto weatherResponseDto = weatherService.getWeatherByCity(city);
        Assertions.assertEquals(city, weatherResponseDto.getName());
        Assertions.assertEquals(1496747, weatherResponseDto.getId());
    }

    @Test
    @DisplayName("Получение необходимых данных о погоде по названию города")
    public void testGetWeatherRequest_ShouldReturnWeatherDataByCity() {
        String city = "Новосибирск";
        String jsonResponse = """
                    {
                        "coord": {
                        "lon": 82.9344,
                        "lat": 55.0411
                        },
                        "weather": [
                        {
                          "id": 500,
                          "main": "Rain",
                          "description": "небольшой дождь",
                          "icon": "10d"
                        }
                        ],
                        "main": {
                        "temp": 16.61,
                        "feels_like": 15.66,
                        "temp_min": 16.61,
                        "temp_max": 16.61,
                        "pressure": 1016,
                        "humidity": 51,
                        "sea_level": 1016,
                        "grnd_level": 996
                        },
                        "visibility": 10000,
                        "wind": {
                        "speed": 6,
                        "deg": 310
                        },
                        "rain": {
                        "1h": 0.39
                        },
                        "clouds": {
                        "all": 40
                        },
                        "dt": 1756974406,
                        "sys": {
                        "type": 1,
                        "id": 8958,
                        "country": "RU",
                        "sunrise": 1756942798,
                        "sunset": 1756991711
                        },
                        "timezone": 25200,
                        "id": 1496747,
                        "name": "Новосибирск",
                        "cod": 200
                    }
                """;
        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

        WeatherResponseDto weatherResponseDto = weatherService.getWeatherByCity(city);
        Assertions.assertEquals(city, weatherResponseDto.getName());
        Assertions.assertNotNull(weatherResponseDto.getCoordinate().getLatitude());
        Assertions.assertNotNull(weatherResponseDto.getCoordinate().getLongitude());
        Assertions.assertNotNull(weatherResponseDto.getWeather().get(0).getDescription());
        Assertions.assertNotNull(weatherResponseDto.getWeather().get(0).getIcon());
        Assertions.assertNotNull(weatherResponseDto.getMain().getTemp());
        Assertions.assertNotNull(weatherResponseDto.getMain().getFeelsLike());
    }

    @Test
    @DisplayName("Получение погоды по координатам")
    public void testGetWeatherRequest_ShouldReturnWeatherByCoordinate() {
        Double longitude = 82.9344;
        Double latitude = 55.0411;
        String jsonResponse = """
                    {
                        "coord": {
                            "lon": 82.9344,
                            "lat": 55.0411
                        }
                    }
                """;
        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

        WeatherResponseDto weatherResponseDto = weatherService.getWeatherByCoordinates(longitude, latitude);
        Assertions.assertEquals(longitude, weatherResponseDto.getCoordinate().getLongitude());
        Assertions.assertEquals(latitude, weatherResponseDto.getCoordinate().getLatitude());
    }

    @Test
    @DisplayName("Получение необходимых данных о погоде по координатам")
    public void testGetWeatherRequest_ShouldReturnWeatherDataByCoordinate() {
        Double longitude = 82.9344;
        Double latitude = 55.0411;
        String jsonResponse = """
                    {
                        "coord": {
                        "lon": 82.9344,
                        "lat": 55.0411
                        },
                        "weather": [
                        {
                          "id": 500,
                          "main": "Rain",
                          "description": "небольшой дождь",
                          "icon": "10d"
                        }
                        ],
                        "main": {
                        "temp": 16.61,
                        "feels_like": 15.66,
                        "temp_min": 16.61,
                        "temp_max": 16.61,
                        "pressure": 1016,
                        "humidity": 51,
                        "sea_level": 1016,
                        "grnd_level": 996
                        },
                        "visibility": 10000,
                        "wind": {
                        "speed": 6,
                        "deg": 310
                        },
                        "rain": {
                        "1h": 0.39
                        },
                        "clouds": {
                        "all": 40
                        },
                        "dt": 1756974406,
                        "sys": {
                        "type": 1,
                        "id": 8958,
                        "country": "RU",
                        "sunrise": 1756942798,
                        "sunset": 1756991711
                        },
                        "timezone": 25200,
                        "id": 1496747,
                        "name": "Новосибирск",
                        "cod": 200
                    }
                """;

        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

        WeatherResponseDto weatherResponseDto = weatherService.getWeatherByCoordinates(latitude, longitude);
        Assertions.assertNotNull(weatherResponseDto.getCoordinate().getLatitude());
        Assertions.assertNotNull(weatherResponseDto.getCoordinate().getLongitude());
        Assertions.assertNotNull(weatherResponseDto.getWeather().get(0).getDescription());
        Assertions.assertNotNull(weatherResponseDto.getWeather().get(0).getIcon());
        Assertions.assertNotNull(weatherResponseDto.getMain().getTemp());
        Assertions.assertNotNull(weatherResponseDto.getMain().getFeelsLike());
    }

    @Test
    @DisplayName("API - Возвращает код ошибки 403 при некорректных данных")
    public void testApiRequest_ShouldReturn403Forbidden() {
        String city = "unknownCity";

        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("403 Access denied")));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertEquals("403 Access denied", runtimeException.getMessage());
    }

    @Test
    @DisplayName("API - Возвращает код ошибки 404 при отсутствии данных")
    public void testApiRequest_ShouldReturn404NotFound() {
        String city = "notExistingCity";

        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("404 Not Found")));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertEquals("404 Not Found", exception.getMessage());
    }

    @Test
    @DisplayName("API - Возвращает код ошибки 500 при некорректной работе сервера")
    public void testApiRequest_ShouldReturn500ServerError() {
        String city = "unknownCity";

        mockWebClientBase();
        when(mockResponseSpec.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("500 Internal Server Error")));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertEquals("500 Internal Server Error", runtimeException.getMessage());
    }

}
