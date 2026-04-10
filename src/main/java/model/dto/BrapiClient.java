package com.portifolio.investment_api.client;

import com.portifolio.investment_api.model.dto.BrapiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "brapiClient", url = "https://brapi.dev/api")
public interface BrapiClient {

    @GetMapping("/quote/{ticker}")
    BrapiResponse getQuote(
            @PathVariable("ticker") String ticker,
            @RequestParam("token") String token // Token de autenticação da API Brapi
    );
}