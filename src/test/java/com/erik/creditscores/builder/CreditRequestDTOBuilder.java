package com.erik.creditscores.builder;

import com.erik.creditscores.interfaces.dto.request.CreditRequestDTO;

public class CreditRequestDTOBuilder {

    public static CreditRequestDTO generateCreditRequestDTO() {
        CreditRequestDTO dto = new CreditRequestDTO();
        dto.setNome("João da Silva");
        dto.setIdade(30);
        dto.setRendaMensal(5000.0);
        dto.setCidade("São Paulo");
        dto.setCpf("123.456.789-00");

        return dto;
    }

}
