package com.portifolio.investment_api.model.dto;

import com.portifolio.investment_api.model.entity.AssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {
    private UUID id;
    private String ticker;
    private String name;
    private AssetType assetType;
    private String exchange;
}