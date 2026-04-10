package com.portifolio.investment_api.service;

import com.portifolio.investment_api.model.entity.User;
import com.portifolio.investment_api.model.dto.AuthResponse;
import com.portifolio.investment_api.model.dto.LoginRequest;
import com.portifolio.investment_api.model.dto.RegisterRequest;
import com.portifolio.investment_api.repository.UserRepository;
import com.portifolio.investment_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // No NOC, segurança em primeiro lugar: encode na senha!
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                // .role(Role.USER) // Certifique-se de ter um Enum Role ou String
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getUsername());
    }
}
