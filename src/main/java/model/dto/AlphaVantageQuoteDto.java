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
// Ignora outros campos do JSON que você não mapeou (boa prática de NOC/Redes)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageQuoteDto {

    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("05. price")
    private Double price; // Mudamos para Double para facilitar cálculos

    @JsonProperty("08. previous close")
    private Double previousClose;

    @JsonProperty("09. change")
    private Double change;

    @JsonProperty("10. change percent")
    private String changePercent; // Mantemos String por causa do símbolo '%' no JSON

    @JsonProperty("07. latest trading day")
    private String latestTradingDay;
}