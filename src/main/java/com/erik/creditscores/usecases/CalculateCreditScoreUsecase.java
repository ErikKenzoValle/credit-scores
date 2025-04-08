package com.erik.creditscores.usecases;

import com.erik.creditscores.entity.request.CreditRequest;
import com.erik.creditscores.interfaces.exceptions.CreditScoreException;
import com.erik.creditscores.interfaces.provider.WeatherProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalculateCreditScoreUsecase {

    private final WeatherProvider weatherProvider;

    public CalculateCreditScoreUsecase(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public Integer calculateScore(CreditRequest request) {
        try {
            Double temp = weatherProvider.getTemperatureByCity(request.getCidade());

            Double ageComponent = request.getIdade() * 0.5;
            Double incomeComponent = (request.getRendaMensal() / 100) * 2;
            Double temperatureComponent = temp * 5;
            Integer totalScore = (int) Math.floor(ageComponent + incomeComponent + temperatureComponent);

            log.info("Cálculo do score feito com sucesso!");

            return totalScore;
        }
        catch (Exception e) {
            log.error("Erro ao calcular score de crédito: ", e);
            throw new CreditScoreException("Erro ao calcular score de crédito. Por favor, tente novamente.");
        }
    }

    public Boolean isApproved(CreditRequest request) {
        Integer score = calculateScore(request);
        Boolean approved = score >= 200 && request.getIdade() >= 18;

        log.info("Validação do score feita com sucesso!");

        return approved;
    }
}