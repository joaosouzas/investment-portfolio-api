package com.portifolio.investment_api.repository;

import com.portifolio.investment_api.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByPortfolioId(UUID portfolioId);
    List<Transaction> findAllByPortfolioIdAndAssetId(UUID portfolioId, UUID assetId);
}