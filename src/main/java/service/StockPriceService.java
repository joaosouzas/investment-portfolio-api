package com.portifolio.investment_api.service;

import com.portifolio.investment_api.client.AlphaVantageClient;
import com.portifolio.investment_api.model.dto.AlphaVantageResponse;
import com.portifolio.investment_api.model.dto.StockPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j // Adiciona suporte a logs (Essencial no NOC)
public class StockPriceService {

    private final AlphaVantageClient alphaVantageClient;

    @Value("${alphavantage.api-key}")
    private String apiKey;

    public StockPriceResponse getPrice(String symbol) {
        log.info("[NOC LOG] Solicitando cotação para o ticker: {}", symbol);

        AlphaVantageResponse response = alphaVantageClient.getQuote(
                "GLOBAL_QUOTE",
                symbol.toUpperCase(),
                apiKey
        );

        AlphaVantageResponse.GlobalQuote quote = response.getQuote();

        // Check de saúde do Link: Se o quote for nulo, a API falhou ou o ticker não existe
        if (quote == null || quote.getSymbol() == null) {
            log.error("[NOC ERROR] Falha ao obter dados para: {}. Verifique o limite da API.", symbol);
            throw new RuntimeException("Ativo não encontrado ou limite de requisições atingido.");
        }

        // Limpeza do dado: Remove o "%" para o frontend tratar apenas o valor
        String cleanChangePercent = quote.getChangePercent() != null
                ? quote.getChangePercent().replace("%", "").trim()
                : "0.0";

        return StockPriceResponse.builder()
                .symbol(quote.getSymbol())
                // Usamos 'new BigDecimal' porque o dado que vem do seu DTO agora é String
                .price(new BigDecimal(quote.getPrice()))
                .change(new BigDecimal(quote.getChange()))
                .changePercent(cleanChangePercent)
                .latestTradingDay(quote.getLatestTradingDay())
                .build();
    }
}