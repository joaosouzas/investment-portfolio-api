package security; // 1. CONFIRA SE ESSE CAMINHO É EXATAMENTE O DA SUA PASTA

import com.portifolio.investment_api.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

// 2. ESSES IMPORTES ESTÁTICOS SÃO ESSENCIAIS PARA O assertEquals, assertNotNull, etc.
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService; // Se sua classe se chamar "TokenService", mude aqui!

    private UserDetails testUser;

    @BeforeEach
    void setUp() {
        // 3. REFLECTION: O campo no JwtService deve se chamar exatamente "secretKey" e "expiration"
        ReflectionTestUtils.setField(jwtService, "secretKey",
                "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "expiration", 86400000L);

        testUser = User.withUsername("joao@email.com")
                .password("senha")
                .authorities("ROLE_USER") // Use authorities em vez de roles para evitar confusão de prefixo
                .build();
    }

    @Test
    @DisplayName("deve gerar um token JWT não nulo")
    void shouldGenerateToken() {
        String token = jwtService.generateToken(testUser);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    @DisplayName("deve extrair o username correto do token")
    void shouldExtractUsername() {
        String token = jwtService.generateToken(testUser);
        String extracted = jwtService.extractUsername(token);
        assertEquals("joao@email.com", extracted);
    }

    @Test
    @DisplayName("deve validar token correto como verdadeiro")
    void shouldValidateCorrectToken() {
        String token = jwtService.generateToken(testUser);
        boolean isValid = jwtService.isTokenValid(token, testUser);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("deve rejeitar token de usuário diferente")
    void shouldRejectTokenForDifferentUser() {
        String token = jwtService.generateToken(testUser);

        UserDetails outroUser = User.withUsername("outro@email.com")
                .password("senha")
                .authorities("ROLE_USER")
                .build();

        boolean isValid = jwtService.isTokenValid(token, outroUser);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("token expirado deve ser inválido")
    void shouldRejectExpiredToken() {
        // Simula timeout de link (expiração negativa)
        ReflectionTestUtils.setField(jwtService, "expiration", -1L);

        String expiredToken = jwtService.generateToken(testUser);

        // O assertThrows espera que uma exceção aconteça ao validar o token
        assertThrows(Exception.class,
                () -> jwtService.isTokenValid(expiredToken, testUser));
    }
}