package org.weather.app.exception;

public class CityJsonProcessingException extends RuntimeException {
    public CityJsonProcessingException(String message) {
        super(message);
    }
}
