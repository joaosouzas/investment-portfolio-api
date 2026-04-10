package com.portifolio.investment_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // Necessário para o Jackson (JSON) conseguir ler o objeto
public class AuthResponse {
    private String token;
    private String email;
    private String username;
}