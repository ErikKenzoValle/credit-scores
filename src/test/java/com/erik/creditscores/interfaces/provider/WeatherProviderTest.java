package com.erik.creditscores.interfaces.provider;

import com.erik.creditscores.interfaces.exceptions.WeatherAPIException;
import com.erik.creditscores.interfaces.gateway.WeatherClient;
import com.erik.creditscores.interfaces.gateway.response.WeatherMainResponse;
import com.erik.creditscores.interfaces.gateway.response.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherProviderTest {

    private WeatherClient weatherClient;
    private WeatherProvider weatherProvider;

    @BeforeEach
    void setUp() {
        weatherClient = mock(WeatherClient.class);
        weatherProvider = new WeatherProvider(weatherClient);
        weatherProvider = Mockito.spy(weatherProvider);
        ReflectionTestUtils.setField(weatherProvider, "apiKey", "test-key");
    }

    @DisplayName("Should return temperature successfully when valid response")
    @Test
    void shouldReturnTemperatureInCelsiusWhenResponseIsValid() {

        String city = "São Paulo";
        double tempKelvin = 300.15;

        WeatherMainResponse mainResponse = new WeatherMainResponse();
        setField(mainResponse, "temp", tempKelvin);

        WeatherResponse weatherResponse = new WeatherResponse();
        setField(weatherResponse, "main", mainResponse);

        when(weatherClient.getWeather(eq(city), anyString())).thenReturn(weatherResponse);

        Double result = weatherProvider.getTemperatureByCity(city);

        assertEquals(27.0, result);
    }

    @DisplayName("Should throw weather api exception when api fails")
    @Test
    void shouldThrowWeatherAPIExceptionWhenApiFails() {

        String city = "Cidade Inexistente";

        when(weatherClient.getWeather(eq(city), anyString()))
            .thenThrow(new RuntimeException("API failure"));

        assertThrows(WeatherAPIException.class, () -> weatherProvider.getTemperatureByCity(city));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject field in test", e);
        }
    }

}
