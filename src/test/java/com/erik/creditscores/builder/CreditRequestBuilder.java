package com.erik.creditscores.builder;

import com.erik.creditscores.entity.request.CreditRequest;

public class CreditRequestBuilder {

    public static CreditRequest generateApprovedScoreCreditRequest() {
        CreditRequest request = new CreditRequest();
        request.setNome("Maria");
        request.setIdade(25);
        request.setRendaMensal(6000.0);
        request.setCidade("Rio de Janeiro");
        request.setCpf("987.654.321-00");

        return request;
    }

    public static CreditRequest generateNotApprovedScoreCreditRequest() {
        CreditRequest request = new CreditRequest();
        request.setNome("Lucas");
        request.setIdade(18);
        request.setRendaMensal(2000.0);
        request.setCidade("Curitiba");
        request.setCpf("111.222.333-44");

        return request;
    }
}
