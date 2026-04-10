package com.portifolio.investment_api.repository;

import com.portifolio.investment_api.model.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    Optional<Asset> findByTicker(String ticker);
    boolean existsByTicker(String ticker);
}