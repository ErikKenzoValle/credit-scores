package com.erik.creditscores.interfaces.exceptions;

public class WeatherAPIException extends RuntimeException {
    public WeatherAPIException(String errorMessage) {
        super(errorMessage);
    }
}
