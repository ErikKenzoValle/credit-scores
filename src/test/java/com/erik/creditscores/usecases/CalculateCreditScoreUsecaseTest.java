package com.erik.creditscores.usecases;

import com.erik.creditscores.entity.request.CreditRequest;
import com.erik.creditscores.interfaces.exceptions.CreditScoreException;
import com.erik.creditscores.interfaces.exceptions.WeatherAPIException;
import com.erik.creditscores.interfaces.provider.WeatherProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.erik.creditscores.builder.CreditRequestBuilder.generateApprovedScoreCreditRequest;
import static com.erik.creditscores.builder.CreditRequestBuilder.generateNotApprovedScoreCreditRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculateCreditScoreUsecaseTest {

    private WeatherProvider weatherProvider;
    private CalculateCreditScoreUsecase usecase;

    @BeforeEach
    void setUp() {
        weatherProvider = mock(WeatherProvider.class);
        usecase = new CalculateCreditScoreUsecase(weatherProvider);
    }

    @Test
    @DisplayName("Should calculate credit score successfully")
    void shouldCalculateCreditScoreSuccessfully() {
        CreditRequest request = generateApprovedScoreCreditRequest();

        when(weatherProvider.getTemperatureByCity("Rio de Janeiro")).thenReturn(20.0);

        Integer score = usecase.calculateScore(request);

        assertEquals(232, score);
    }

    @Test
    @DisplayName("Should approve credit score")
    void shouldApproveCreditRequest() {
        CreditRequest request = generateApprovedScoreCreditRequest();

        when(weatherProvider.getTemperatureByCity("Rio de Janeiro")).thenReturn(20.0);

        Boolean approved = usecase.isApproved(request);
        assertTrue(approved);
    }

    @Test
    @DisplayName("Should return false when score is not enough")
    void shouldRejectCreditRequest() {
        CreditRequest request = generateNotApprovedScoreCreditRequest();

        when(weatherProvider.getTemperatureByCity("Curitiba")).thenReturn(10.0);

        Boolean approved = usecase.isApproved(request);
        assertFalse(approved);
    }

    @Test
    @DisplayName("Should throw CreditScoreException when weather provider fails")
    void shouldThrowExceptionWhenWeatherProviderFails() {
        CreditRequest request = generateApprovedScoreCreditRequest();

        when(weatherProvider.getTemperatureByCity("Rio de Janeiro")).thenThrow(new WeatherAPIException("API error"));

        CreditScoreException exception = assertThrows(CreditScoreException.class, () -> {
            usecase.calculateScore(request);
        });

        assertEquals("Erro ao calcular score de crédito. Por favor, tente novamente.", exception.getMessage());
    }
}
