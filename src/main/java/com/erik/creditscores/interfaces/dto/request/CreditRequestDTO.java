package com.erik.creditscores.interfaces.dto.request;

import com.erik.creditscores.entity.request.CreditRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreditRequestDTO {

    @NotBlank(message = "O nome deve ser preenchido!")
    private String nome;

    @Min(value = 1, message = "A idade deve ser maior que zero!")
    @NotNull(message = "A idade deve ser preenchida!")
    private Integer idade;

    @DecimalMin(value = "0.01", message = "A renda mensal deve ser maior que zero!")
    @NotNull(message = "A renda mensal deve ser preenchida!")
    private Double rendaMensal;

    @NotBlank(message = "A cidade deve ser preenchida!")
    private String cidade;

    @NotBlank(message = "O cpf deve ser preenchido!")
    private String cpf;

    public CreditRequest toEntity() {
        CreditRequest req = new CreditRequest();
        req.setNome(nome);
        req.setIdade(idade);
        req.setRendaMensal(rendaMensal);
        req.setCidade(cidade);
        req.setCpf(cpf);

        return req;
    }

}