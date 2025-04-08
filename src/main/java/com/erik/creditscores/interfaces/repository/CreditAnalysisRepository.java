package com.erik.creditscores.interfaces.repository;

import com.erik.creditscores.interfaces.dto.response.CreditResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class CreditAnalysisRepository {

    private final Map<String, List<CreditResponseDTO>> storage = new HashMap<>();

    public void save(String cpf, CreditResponseDTO response) {
        log.info("Salvando análise de crédito...");

        storage.computeIfAbsent(cpf, key -> new ArrayList<>()).add(response);
    }

    public List<CreditResponseDTO> findAllByCpf(String cpf) {
        return storage.getOrDefault(cpf, Collections.emptyList());
    }

}
