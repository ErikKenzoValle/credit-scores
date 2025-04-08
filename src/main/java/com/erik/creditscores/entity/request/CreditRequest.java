package com.erik.creditscores.entity.request;

import jakarta.validation.Valid;
import lombok.Data;

@Valid
@Data
public class CreditRequest {

    private String nome;
    private Integer idade;
    private Double rendaMensal;
    private String cidade;
    private String cpf;

}
