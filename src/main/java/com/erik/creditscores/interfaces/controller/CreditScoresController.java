package com.erik.creditscores.interfaces.controller;

import com.erik.creditscores.entity.request.CreditRequest;
import com.erik.creditscores.interfaces.dto.request.CreditRequestDTO;
import com.erik.creditscores.interfaces.dto.response.CreditResponseDTO;
import com.erik.creditscores.interfaces.repository.CreditAnalysisRepository;
import com.erik.creditscores.usecases.CalculateCreditScoreUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/v1/credit-scores")
public class CreditScoresController {

    private final CalculateCreditScoreUsecase creditScoreUsecase;
    private final CreditAnalysisRepository repository;

    @PostMapping
    public CreditResponseDTO calculateScore(@Valid @RequestBody CreditRequestDTO dto) {
        log.info("Iniciando o cálculo do score para o CPF: " + dto.getCpf());

        CreditRequest request = dto.toEntity();

        int score = creditScoreUsecase.calculateScore(request);
        boolean approved = creditScoreUsecase.isApproved(request);

        repository.save(dto.getCpf(), new CreditResponseDTO(score, approved));

        return new CreditResponseDTO(score, approved);
    }

    @GetMapping("/{cpf}")
    public List<CreditResponseDTO> getAllByCpf(@PathVariable String cpf) {
        log.info("Buscando análises de crédito do CPF: " + cpf);

        return repository.findAllByCpf(cpf);
    }

}
