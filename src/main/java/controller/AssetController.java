package com.portifolio.investment_api.controller;

import com.portifolio.investment_api.model.dto.AssetRequest;
import com.portifolio.investment_api.model.dto.AssetResponse;
import com.portifolio.investment_api.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetResponse> create(
            @Valid @RequestBody AssetRequest request) {
        // Criação de um ativo no catálogo global
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> findAll() {
        // Lista todos os ativos disponíveis (PETR4, BTC, etc)
        return ResponseEntity.ok(assetService.findAll());
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<AssetResponse> findByTicker(
            @PathVariable String ticker) {
        // Busca detalhada por ticker (ex: /api/assets/AAPL)
        return ResponseEntity.ok(assetService.findByTicker(ticker));
    }
}