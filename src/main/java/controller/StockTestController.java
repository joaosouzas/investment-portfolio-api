package com.portifolio.investment_api.controller;

import com.portifolio.investment_api.service.StockQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockTestController {

    private final StockQuoteService stockQuoteService;

    // Essa rota permite que você teste qualquer ação pelo navegador
    @GetMapping("/api/test/quote/{ticker}")
    public String testPrice(@PathVariable String ticker) {
        Double preco = stockQuoteService.getLatestPrice(ticker);

        if (preco == 0.0) {
            return "Falha ao obter preço para " + ticker + ". Verifique o console do IntelliJ (Rate Limit?)";
        }

        return "Conexão OK! O preço atual de " + ticker + " é: $" + preco;
    }
}