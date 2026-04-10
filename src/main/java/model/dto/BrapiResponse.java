package com.portifolio.investment_api.model.dto;

import lombok.Data; // Importa o Lombok para gerar Getters/Setters
import java.util.List; // Importa a estrutura de lista do Java

@Data
public class BrapiResponse {

    private List<StockResult> results;

    @Data
    public static class StockResult {
        private String symbol;             // Ticker (ex: AAPL)
        private Double regularMarketPrice; // Preço atual vindo da API
        private String longName;           // Nome da empresa
    }
}