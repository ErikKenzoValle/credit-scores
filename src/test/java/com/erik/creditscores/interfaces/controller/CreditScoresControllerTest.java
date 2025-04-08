package com.erik.creditscores.interfaces.controller;

import com.erik.creditscores.interfaces.dto.request.CreditRequestDTO;
import com.erik.creditscores.interfaces.repository.CreditAnalysisRepository;
import com.erik.creditscores.usecases.CalculateCreditScoreUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.erik.creditscores.builder.CreditRequestDTOBuilder.generateCreditRequestDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditScoresController.class)
class CreditScoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateCreditScoreUsecase calculateCreditScoreUsecase;

    @MockBean
    private CreditAnalysisRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("Should return score and approval successfully")
    @Test
    void shouldReturnScoreAndApprovalSuccessfully() throws Exception {
        CreditRequestDTO dto = generateCreditRequestDTO();

        Mockito.when(calculateCreditScoreUsecase.calculateScore(any())).thenReturn(250);
        Mockito.when(calculateCreditScoreUsecase.isApproved(any())).thenReturn(true);

        mockMvc.perform(post("/v1/credit-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(250))
                .andExpect(jsonPath("$.aprovado").value(true));
    }

    @DisplayName("Should return bad request when fields are null")
    @Test
    void shouldReturnBadRequestWhenFieldsAreNull() throws Exception {
        CreditRequestDTO dto = new CreditRequestDTO();

        mockMvc.perform(post("/v1/credit-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("O nome deve ser preenchido!"))
                .andExpect(jsonPath("$.idade").value("A idade deve ser preenchida!"))
                .andExpect(jsonPath("$.rendaMensal").value("A renda mensal deve ser preenchida!"))
                .andExpect(jsonPath("$.cidade").value("A cidade deve ser preenchida!"))
                .andExpect(jsonPath("$.cpf").value("O cpf deve ser preenchido!"));
    }

}
