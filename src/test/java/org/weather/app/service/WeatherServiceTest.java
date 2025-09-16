package org.weather.app.service;

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
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

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

        when(mockWebClient.get()).thenReturn(mockRequest);
        when(mockRequest.uri(anyString())).thenReturn(mockRequestHeaders);
        when(mockRequestHeaders.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.onStatus(any(), any())).thenReturn(mockResponseSpec);
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

        when(mockWebClient.get()).thenReturn(mockRequest);
        when(mockRequest.uri(anyString())).thenReturn(mockRequestHeaders);
        when(mockRequestHeaders.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.onStatus(any(), any())).thenReturn(mockResponseSpec);
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

    }

    @Test
    @DisplayName("Получение необходимых данных о погоде по координатам")
    public void testGetWeatherRequest_ShouldReturnWeatherDataByCoordinate() {

    }

    @Test
    @DisplayName("API - Возвращает код ошибки 403 при некорректных данных")
    public void testApiRequest_ShouldReturn403Forbidden() {

    }

    @Test
    @DisplayName("API - Возвращает код ошибки 404 при отсутствии данных")
    public void testApiRequest_ShouldReturn404NotFound() {

    }

    @Test
    @DisplayName("API - Возвращает код ошибки 500 при некорректной работе сервера")
    public void testApiRequest_ShouldReturn500ServerError() {

    }

}
