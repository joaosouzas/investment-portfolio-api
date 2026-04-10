package com.portifolio.investment_api.service;

import com.portifolio.investment_api.client.AlphaVantageClient;
import com.portifolio.investment_api.model.dto.AlphaVantageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockQuoteService {

    private final AlphaVantageClient stockClient;
    private final String API_KEY = "RL5KZKEMAYNLYOC6";

    public Double getLatestPrice(String ticker) {
        try {
            AlphaVantageResponse response = stockClient.getQuote("GLOBAL_QUOTE", ticker, API_KEY);

            if (response != null && response.getQuote() != null && response.getQuote().getPrice() != null) {
                // CORREÇÃO: Converte a String que vem do DTO para Double
                String precoString = response.getQuote().getPrice();
                Double preco = Double.parseDouble(precoString);

                System.out.println("[NOC LOG] Cotação recuperada: " + ticker + " = " + preco);
                return preco;
            }

            System.out.println("[NOC ALERT] Resposta da API veio vazia ou com erro de limite para: " + ticker);
            return 0.0;
        } catch (Exception e) {
            System.err.println("[ERROR] Falha na integração com Alpha Vantage: " + e.getMessage());
            return 0.0;
        }
    }
}