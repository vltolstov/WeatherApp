package org.weather.app.exception;

public class WeatherJsonProcessingException extends RuntimeException {
    public WeatherJsonProcessingException(String message) {
        super(message);
    }
}
