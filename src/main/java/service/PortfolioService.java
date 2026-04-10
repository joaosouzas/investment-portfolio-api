package com.portifolio.investment_api.service;

import com.portifolio.investment_api.model.dto.PortfolioRequest;
import com.portifolio.investment_api.model.dto.PortfolioResponse;
import com.portifolio.investment_api.model.entity.Portfolio;
import com.portifolio.investment_api.model.entity.User;
import com.portifolio.investment_api.repository.PortfolioRepository;
import com.portifolio.investment_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Transactional // Garante integridade da transação
    public PortfolioResponse create(PortfolioRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Portfolio portfolio = Portfolio.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();

        Portfolio saved = portfolioRepository.save(portfolio);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PortfolioResponse> findAllByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return portfolioRepository.findAllByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PortfolioResponse findById(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Regra de Firewall: Só busca se bater o ID do portfólio E o ID do dono
        Portfolio portfolio = portfolioRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Portfólio não encontrado ou acesso negado"));

        return toResponse(portfolio);
    }

    @Transactional
    public void delete(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Portfolio portfolio = portfolioRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Portfólio não encontrado"));

        portfolioRepository.delete(portfolio);
    }

    private PortfolioResponse toResponse(Portfolio p) {
        return PortfolioResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .createdAt(p.getCreatedAt())
                .build();
    }
}