package org.weather.app.exception;

public class EmptyWeatherResponseException extends RuntimeException {
    public EmptyWeatherResponseException(String message) {
        super(message);
    }
}
