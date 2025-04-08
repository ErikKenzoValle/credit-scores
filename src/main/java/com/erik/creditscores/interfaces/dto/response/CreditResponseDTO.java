package com.erik.creditscores.interfaces.dto.response;

import lombok.Data;

@Data
public class CreditResponseDTO {
    public Integer score;
    public Boolean aprovado;

    public CreditResponseDTO(Integer score, Boolean aprovado) {
        this.score = score;
        this.aprovado = aprovado;
    }
}