package com.portifolio.investment_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceResponse {
    private String symbol;
    private BigDecimal price;
    private BigDecimal change;
    private String changePercent;
    private String latestTradingDay;
}