package com.portifolio.investment_api.controller;

import com.portifolio.investment_api.model.dto.PortfolioRequest;
import com.portifolio.investment_api.model.dto.PortfolioResponse;
import com.portifolio.investment_api.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<PortfolioResponse> create(
            @Valid @RequestBody PortfolioRequest request,
            Authentication auth) {
        // No Spring Security, auth.getName() retorna o email (que definimos como subject do JWT)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(portfolioService.create(request, auth.getName()));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> findAll(Authentication auth) {
        return ResponseEntity.ok(portfolioService.findAllByUser(auth.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> findById(
            @PathVariable UUID id,
            Authentication auth) {
        return ResponseEntity.ok(portfolioService.findById(id, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            Authentication auth) {
        portfolioService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}