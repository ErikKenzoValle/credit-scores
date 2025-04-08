package com.erik.creditscores.interfaces.repository;

import com.erik.creditscores.interfaces.dto.response.CreditResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreditAnalysisRepositoryTest {

    private CreditAnalysisRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CreditAnalysisRepository();
    }

    @Test
    @DisplayName("Should return empty list when no analysis exists for given CPF")
    void shouldReturnEmptyListIfNoAnalysisExists() {
        List<CreditResponseDTO> result = repository.findAllByCpf("12345678900");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should save and retrieve analysis successfully for a given CPF")
    void shouldSaveAndRetrieveAnalysisSuccessfully() {
        String cpf = "12345678900";
        CreditResponseDTO response1 = new CreditResponseDTO(250, true);
        CreditResponseDTO response2 = new CreditResponseDTO(180, false);

        String cpfTwo = "98765432100";
        CreditResponseDTO response3 = new CreditResponseDTO(210, true);

        repository.save(cpf, response1);
        repository.save(cpf, response2);
        repository.save(cpfTwo, response3);

        List<CreditResponseDTO> result = repository.findAllByCpf(cpf);

        assertEquals(2, result.size());
        assertEquals(250, result.get(0).getScore());
        assertTrue(result.get(0).getAprovado());
        assertEquals(180, result.get(1).getScore());
        assertFalse(result.get(1).getAprovado());
    }
}
