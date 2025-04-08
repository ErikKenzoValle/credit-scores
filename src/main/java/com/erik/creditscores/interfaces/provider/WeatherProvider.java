package com.erik.creditscores.interfaces.provider;

import com.erik.creditscores.interfaces.exceptions.WeatherAPIException;
import com.erik.creditscores.interfaces.gateway.WeatherClient;
import com.erik.creditscores.interfaces.gateway.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherProvider {

    @Value("${weather.api.key}")
    private String apiKey;

    private final WeatherClient weatherClient;

    public WeatherProvider(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public Double getTemperatureByCity(String cityName) {
        try {
            log.info("Buscando temperatura da cidade: " + cityName);
            WeatherResponse response = weatherClient.getWeather(cityName, apiKey);

            return response.getMain().getTemp() - 273.15;
        }
        catch(Exception e) {
            log.error("Ocorreu um erro na API de clima: " + e);
            throw new WeatherAPIException("Ocorreu um erro inesperado na API de clima! Tente novamente mais tarde.");
        }
    }
}