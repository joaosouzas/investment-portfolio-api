package com.portifolio.investment_api.service;

import com.portifolio.investment_api.model.dto.AssetRequest;
import com.portifolio.investment_api.model.dto.AssetResponse;
import com.portifolio.investment_api.model.entity.Asset;
import com.portifolio.investment_api.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    // Cadastra novo ativo — verifica se ticker já existe (DNS dos ativos)
    public AssetResponse create(AssetRequest request) {
        if (assetRepository.existsByTicker(request.getTicker().toUpperCase())) {
            throw new RuntimeException(
                    "Ativo com ticker '" + request.getTicker() + "' já cadastrado");
        }

        Asset asset = Asset.builder()
                .ticker(request.getTicker().toUpperCase()) // Padronização NOC
                .name(request.getName())
                .assetType(request.getAssetType())
                .exchange(request.getExchange())
                .build();

        Asset saved = assetRepository.save(asset);
        return toResponse(saved);
    }

    // Lista todos os ativos cadastrados no inventário global
    public List<AssetResponse> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Busca ativo pelo ticker (ex: "AAPL")
    public AssetResponse findByTicker(String ticker) {
        return assetRepository.findByTicker(ticker.toUpperCase())
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Ativo não encontrado: " + ticker));
    }

    // Conversor auxiliar Entidade -> DTO
    private AssetResponse toResponse(Asset a) {
        return AssetResponse.builder()
                .id(a.getId())
                .ticker(a.getTicker())
                .name(a.getName())
                .assetType(a.getAssetType())
                .exchange(a.getExchange())
                .build();
    }
}