# Decisões de Arquitetura — Investment Portfolio API

Este documento registra as principais decisões de arquitetura do projeto, o raciocínio por trás delas e as alternativas que foram consideradas.

---

## ADR-001 — Autenticação Stateless com JWT

**Decisão:** Usar JWT (JSON Web Token) com Spring Security em vez de sessões HTTP.

**Contexto:** APIs REST devem seguir o princípio de statelessness — o servidor não deve armazenar estado entre requisições.

**Razão:** Com sessões HTTP, o servidor mantém estado na memória. Ao escalar horizontalmente (múltiplas instâncias), as sessões precisam ser compartilhadas via Redis ou sticky sessions — adicionando complexidade operacional. Com JWT, cada requisição carrega sua própria autenticação no token, e qualquer instância pode validá-lo independentemente.

**Consequências:**
- ✅ Escala horizontal simples sem sincronização de sessões
- ✅ Token pode ser validado por qualquer instância sem consultar banco
- ⚠️ Tokens emitidos não podem ser invalidados antes de expirar (sem blocklist)

---

## ADR-002 — DTOs para todas as camadas de entrada e saída

**Decisão:** Nunca expor entidades JPA diretamente na API. Usar DTOs (Data Transfer Objects) para requests e responses.

**Razão:**
1. **Segurança:** Expor `User` diretamente retornaria o campo `password` (mesmo hasheado) no JSON de resposta.
2. **Desacoplamento:** Mudanças na entidade não quebram o contrato da API e vice-versa.
3. **Controle:** O DTO define exatamente quais campos o cliente pode enviar e receber.

**Padrão usado:**
- `XxxRequest` → body da requisição (entrada)
- `XxxResponse` → body da resposta (saída)
- Conversão no `Service` via método `toResponse()` privado

---

## ADR-003 — Verificação de propriedade em todas as operações

**Decisão:** Usar `findByIdAndUserId()` no repository em vez de `findById()` para buscar recursos do usuário.

**Contexto:** Sem verificação, qualquer usuário autenticado poderia acessar o portfólio de outro simplesmente enviando um UUID válido.

**Razão:** Esta vulnerabilidade chama-se IDOR (Insecure Direct Object Reference) e está no [OWASP Top 10 API Security](https://owasp.org/API-Security/editions/2023/en/0xa3-broken-object-property-level-authorization/). A solução mais simples é incluir `userId` na query de busca — se o recurso não pertencer ao usuário, retorna 404 (em vez de 403, para não confirmar que o recurso existe).

---

## ADR-004 — BigDecimal para todos os valores monetários

**Decisão:** Usar `BigDecimal` para preços, quantidades e variações financeiras. Nunca `double` ou `float`.

**Razão:** Representação binária de ponto flutuante:
```java
double resultado = 0.1 + 0.2;
// resultado = 0.30000000000000004  ← ERRADO em sistemas financeiros
```

`BigDecimal` usa representação decimal exata, com precisão configurável. No banco, mapeado como `NUMERIC(18, 8)`.

---

## ADR-005 — OpenFeign para chamadas HTTP externas

**Decisão:** Usar Spring Cloud OpenFeign em vez de `RestTemplate` ou `WebClient`.

**Razão:** OpenFeign transforma a chamada HTTP em uma interface Java declarativa — sem código boilerplate de configuração de cliente, parsing de URL ou tratamento de erros de conexão. O código fica com 10x menos linhas e é mais legível.

**Comparação:**
```java
// Com RestTemplate (verboso)
String url = baseUrl + "/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
ResponseEntity<AlphaVantageResponse> response = restTemplate.getForEntity(url, AlphaVantageResponse.class);

// Com OpenFeign (declarativo)
alphaVantageClient.getQuote("GLOBAL_QUOTE", symbol, apiKey);
```

---

## ADR-006 — H2 em memória para testes de integração

**Decisão:** Usar H2 (banco em memória) nos testes de integração em vez do PostgreSQL real.

**Razão:**
- Testes devem ser rápidos, determinísticos e independentes de infraestrutura externa
- H2 sobe em milissegundos, sem Docker, sem configuração
- O Hibernate abstrai as diferenças de SQL entre H2 e PostgreSQL na maioria dos casos

**Risco mitigado:** Para garantir paridade com PostgreSQL, os tipos de coluna e constraints são testados também em pipeline CI com PostgreSQL real via GitHub Actions service.

---

## ADR-007 — UUID como chave primária

**Decisão:** Usar UUID gerado pelo banco (`GenerationType.UUID`) como PK em todas as entidades.

**Alternativa considerada:** `BIGSERIAL` (auto-increment).

**Razão:**
1. **Segurança:** UUIDs não revelam volume de dados. `id=12345` indica ao atacante que existem pelo menos 12.345 registros.
2. **Distribuição:** UUIDs podem ser gerados pelo cliente antes de persistir, sem consultar o banco.
3. **Migração:** Facilita a fusão de dados de múltiplos bancos sem colisão de IDs.

**Trade-off:** UUIDs ocupam mais espaço (16 bytes vs 8 bytes para BIGINT) e índices são levemente mais lentos. Aceitável para o volume esperado desta aplicação.
