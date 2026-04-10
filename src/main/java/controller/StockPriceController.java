package com.portifolio.investment_api.controller;

import com.portifolio.investment_api.model.dto.StockPriceResponse;
import com.portifolio.investment_api.service.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class StockPriceController {

    private final StockPriceService stockPriceService;

    // GET /api/prices/AAPL
    @GetMapping("/{symbol}")
    public ResponseEntity<StockPriceResponse> getPrice(@PathVariable String symbol) {
        // O Service já faz o toUpperCase(), então aqui passamos direto
        return ResponseEntity.ok(stockPriceService.getPrice(symbol));
    }

    // GET /api/prices?symbols=AAPL,MSFT,TSLA
    @GetMapping
    public ResponseEntity<List<StockPriceResponse>> getPrices(@RequestParam List<String> symbols) {

        // No NOC, isso seria como um "batch ping": monitorando vários hosts de uma vez
        List<StockPriceResponse> prices = symbols.stream()
                .map(stockPriceService::getPrice)
                .toList();

        return ResponseEntity.ok(prices);
    }
}