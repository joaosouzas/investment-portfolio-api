package com.portifolio.investment_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portifolio.investment_api.model.dto.LoginRequest;
import com.portifolio.investment_api.model.dto.RegisterRequest;
import com.portifolio.investment_api.model.entity.Role;
import com.portifolio.investment_api.model.entity.User;
import com.portifolio.investment_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// Imports estáticos (essenciais para o MockMvc funcionar)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("registro com dados válidos deve retornar 201 e token")
    void shouldRegisterSuccessfully() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("joao");
        request.setEmail("joao@email.com");
        request.setPassword("senha123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("login com credenciais válidas deve retornar 200 e token")
    void shouldLoginSuccessfully() throws Exception {
        User user = User.builder()
                .username("joao_nick")
                .email("joao@email.com")
                .password(passwordEncoder.encode("senha123"))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("joao@email.com");
        loginRequest.setPassword("senha123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("rota protegida com token válido deve retornar 200")
    void shouldAllowProtectedRouteWithToken() throws Exception {
        RegisterRequest reg = new RegisterRequest();
        reg.setUsername("joao_teste");
        reg.setEmail("teste@email.com");
        reg.setPassword("senha123");

        String response = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        mockMvc.perform(get("/api/portfolios")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}