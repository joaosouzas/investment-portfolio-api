package com.portifolio.investment_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponse {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    // Sem User aqui para manter a segurança e evitar loops de serialização
}