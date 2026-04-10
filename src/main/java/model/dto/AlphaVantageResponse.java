package com.portifolio.investment_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {

    @JsonProperty("Global Quote")
    private GlobalQuote quote;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GlobalQuote {

        @JsonProperty("01. symbol")
        private String symbol;

        // Recebemos como String para garantir que o Jackson não dê erro de parsing
        @JsonProperty("05. price")
        private String price;

        @JsonProperty("10. change percent")
        private String changePercent;

        @JsonProperty("09. change")
        private String change;

        @JsonProperty("07. latest trading day")
        private String latestTradingDay;
    }
}