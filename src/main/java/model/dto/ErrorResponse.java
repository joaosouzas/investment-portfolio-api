package com.portifolio.investment_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;        // Ex: 400, 404, 401
    private String message;     // Mensagem amigável do erro
    private LocalDateTime timestamp; // Data e hora do incidente
}