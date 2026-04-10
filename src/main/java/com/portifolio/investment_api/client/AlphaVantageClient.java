package com.portifolio.investment_api.client;

import com.portifolio.investment_api.model.dto.AlphaVantageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alphaVantageClient", url = "${alphavantage.base-url}")
public interface AlphaVantageClient {

    @GetMapping("/query")
    AlphaVantageResponse getQuote(
            @RequestParam("function") String function, // Valor: "GLOBAL_QUOTE"
            @RequestParam("symbol") String symbol,     // Valor: "PETR4.SAO"
            @RequestParam("apikey") String apikey      // Sua API KEY
    );
}