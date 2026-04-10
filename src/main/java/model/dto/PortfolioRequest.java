package com.portifolio.investment_api.model.dto; // MUDOU DE entity PARA dto // Ajuste para model.dto se preferir

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioRequest {

    @NotBlank(message = "Nome do portfólio é obrigatório")
    @Size(max = 100, message = "Nome muito longo")
    private String name;

    private String description; // Opcional (não precisa de @NotBlank)
}