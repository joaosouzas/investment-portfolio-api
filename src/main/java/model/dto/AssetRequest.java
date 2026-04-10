package com.portifolio.investment_api.model.dto;

import com.portifolio.investment_api.model.entity.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequest {

    @NotBlank(message = "Ticker obrigatório")
    @Size(max = 10, message = "Ticker máximo 10 caracteres")
    private String ticker; // Ex: "AAPL", "PETR4", "BTC"

    @NotBlank(message = "Nome do ativo obrigatório")
    private String name;   // Ex: "Apple Inc.", "Petrobras"

    @NotNull(message = "Tipo do ativo obrigatório")
    private AssetType assetType; // O seu Enum: STOCK, CRYPTO, ETF, FII

    private String exchange; // Ex: "NASDAQ", "B3", "Binance"
}