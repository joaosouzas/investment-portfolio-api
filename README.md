# 📈 Investment Portfolio API

> API REST profissional para gerenciamento de portfólios de investimentos com cotações em tempo real.

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/Auth-JWT-black?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-blue?style=flat-square&logo=githubactions)](https://github.com/features/actions)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Stack Técnica](#-stack-técnica)
- [Funcionalidades](#-funcionalidades)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Como Rodar](#-como-rodar)
- [Endpoints da API](#-endpoints-da-api)
- [Testes](#-testes)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Decisões de Arquitetura](#-decisões-de-arquitetura)
- [Contribuindo](#-contribuindo)

---

## 🎯 Sobre o Projeto

A **Investment Portfolio API** é uma API REST de produção que permite aos usuários gerenciar seus portfólios de investimentos e consultar cotações em tempo real de ações, ETFs, FIIs e criptomoedas.

O projeto foi construído com foco em **boas práticas de engenharia de software**: separação de responsabilidades em camadas, autenticação stateless via JWT, tratamento global de exceções, testes automatizados e documentação completa.

### O que este projeto demonstra

- Arquitetura em camadas (Controller → Service → Repository)
- Autenticação e autorização com Spring Security + JWT
- Integração com API externa (Alpha Vantage) via OpenFeign
- Prevenção de IDOR (Insecure Direct Object Reference)
- Tratamento global de erros com `@RestControllerAdvice`
- Testes unitários com Mockito e de integração com MockMvc
- Uso de variáveis de ambiente para configurações sensíveis
- CI/CD com GitHub Actions

---

## 🏗 Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                        Cliente                          │
│                  (Postman / Front-end)                  │
└──────────────────────────┬──────────────────────────────┘
                           │ HTTP + Bearer Token
┌──────────────────────────▼──────────────────────────────┐
│              JwtAuthenticationFilter                    │
│         (intercepta e valida o token JWT)               │
└──────────────────────────┬──────────────────────────────┘
                           │
┌──────────────────────────▼────────────────────────────────────────────────────────┐
│                     Controller                                                    │
│     AuthController │ PortfolioController │ AssetController │ StockPriceController │
└──────────────────────────┬────────────────────────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────┐
│                      Service                            │
│     AuthService │ PortfolioService │ StockPriceService  │
└────────────┬─────────────────────────────┬──────────────┘
             │                             │
┌────────────▼───────────┐   ┌─────────────▼──────────────┐
│      Repository        │   │      AlphaVantageClient    │
│  (Spring Data JPA)     │   │      (OpenFeign)           │
└────────────┬───────────┘   └─────────────┬──────────────┘
             │                             │
┌────────────▼───────────┐   ┌─────────────▼──────────────┐
│     PostgreSQL 16      │   │    Alpha Vantage API       │
│   (banco de dados)     │   │    (cotações em tempo real)│
└────────────────────────┘   └────────────────────────────┘
```

---

## 🛠 Stack Técnica

| Camada | Tecnologia | Versão | Motivo da escolha |
|---|---|---|---|
| Linguagem | Java | 21 LTS | Virtual Threads, última LTS |
| Framework | Spring Boot | 3.3.x | Padrão de mercado para APIs Java |
| Segurança | Spring Security + JJWT | 6.x / 0.12.6 | Autenticação stateless com JWT |
| Banco de dados | PostgreSQL | 16 | Robusto, ACID, open source |
| ORM | Spring Data JPA + Hibernate | 6.x | Abstrações de banco maduras |
| HTTP Client | OpenFeign | 4.x | Zero boilerplate para chamadas HTTP |
| Build | Maven | 3.x | Gerenciamento de dependências |
| Testes | JUnit 5 + Mockito + MockMvc | 5.x | Stack de testes padrão Spring |
| CI/CD | GitHub Actions | - | Integração nativa com GitHub |
| Banco de testes | H2 | 2.x | Banco em memória para testes rápidos |

---

## ✅ Funcionalidades

### Autenticação
- [x] Registro de usuário com validação de email único
- [x] Login com geração de token JWT (24h de validade)
- [x] Todas as rotas protegidas por Bearer Token

### Portfólios
- [x] Criar, listar, buscar e deletar portfólios
- [x] Isolamento por usuário — cada usuário vê apenas seus portfólios
- [x] Proteção contra IDOR (verificação de propriedade antes de qualquer operação)

### Ativos
- [x] Cadastrar ativos financeiros (ações, ETFs, FIIs, cripto)
- [x] Listar e buscar ativos por ticker
- [x] Suporte a múltiplas bolsas (NASDAQ, B3, etc.)

### Cotações em Tempo Real
- [x] Preço atual de qualquer ativo via Alpha Vantage
- [x] Variação do dia (change e change percent)
- [x] Consulta de múltiplos tickers em uma única requisição

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

| Ferramenta | Versão mínima | Link |
|---|---|---|
| JDK | 21 | [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21) |
| Docker | 24+ | [Docker Desktop](https://www.docker.com/products/docker-desktop/) |
| Git | 2.x | [git-scm.com](https://git-scm.com/) |

> **IDE recomendada:** IntelliJ IDEA Community Edition — [download](https://www.jetbrains.com/idea/download/)

---

## ⚙️ Configuração do Ambiente

### 1. Clone o repositório

```bash
git clone https://github.com/joaosouzas/investment-portfolio-api.git
cd investment-portfolio-api
```

### 2. Configure as variáveis de ambiente

Copie o arquivo de exemplo e preencha com seus valores:

```bash
cp .env.example .env
```

Edite o `.env` com suas credenciais:

```env
# Banco de dados
DB_URL=jdbc:postgresql://localhost:5432/portfolio_db
DB_USERNAME=portfolio_user
DB_PASSWORD=portfolio_pass

# JWT — gere uma chave segura com: openssl rand -hex 32
JWT_SECRET=sua_chave_secreta_aqui_minimo_256_bits
JWT_EXPIRATION=86400000

# Alpha Vantage — obtenha sua key gratuita em:
# https://www.alphavantage.co/support/#api-key
ALPHAVANTAGE_KEY=sua_api_key_aqui
```

> ⚠️ **Nunca** commite o arquivo `.env`. Ele já está no `.gitignore`.

### 3. Suba o banco de dados com Docker

```bash
# Windows (cmd — comando em uma linha só)
docker run --name portfolio-db -e POSTGRES_DB=portfolio_db -e POSTGRES_USER=portfolio_user -e POSTGRES_PASSWORD=portfolio_pass -p 5432:5432 -d postgres:16

# Mac / Linux
docker run --name portfolio-db \
  -e POSTGRES_DB=portfolio_db \
  -e POSTGRES_USER=portfolio_user \
  -e POSTGRES_PASSWORD=portfolio_pass \
  -p 5432:5432 \
  -d postgres:16
```

Confirme que o banco está rodando:

```bash
docker exec -it portfolio-db psql -U portfolio_user -d portfolio_db -c "SELECT 'conexao ok';"
# Resultado esperado: conexao ok
```

---

## 🚀 Como Rodar

### Via IntelliJ IDEA

1. Abra o projeto: **File → Open** → selecione a pasta do projeto
2. Instale o plugin **EnvFile**: `Settings → Plugins → buscar "EnvFile"` → Instalar
3. Configure o plugin: **Run → Edit Configurations → EnvFile** → habilite e aponte para o arquivo `.env`
4. Clique no botão ▶ verde para rodar

### Via linha de comando (Maven Wrapper)

```bash
# Linux / Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### Verificando que subiu corretamente

Acesse: `http://localhost:8080/api/auth/register`

Você deve receber `405 Method Not Allowed` (precisa de POST) — isso confirma que a API está respondendo.

---

## 📡 Endpoints da API

Base URL: `http://localhost:8080`

### 🔓 Autenticação (rotas públicas)

#### Registrar usuário

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "joao",
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "joao@email.com",
  "username": "joao"
}
```

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "joao@email.com",
  "username": "joao"
}
```

---

### 🔐 Portfólios (autenticado — necessita Bearer Token)

> Adicione o header `Authorization: Bearer SEU_TOKEN` em todas as requisições abaixo.

#### Criar portfólio

```http
POST /api/portfolios
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Carteira de Longo Prazo",
  "description": "Foco em ações de crescimento"
}
```

**Resposta (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Carteira de Longo Prazo",
  "description": "Foco em ações de crescimento",
  "createdAt": "2025-04-08T17:00:00"
}
```

#### Listar meus portfólios

```http
GET /api/portfolios
Authorization: Bearer {token}
```

#### Buscar portfólio por ID

```http
GET /api/portfolios/{id}
Authorization: Bearer {token}
```

#### Deletar portfólio

```http
DELETE /api/portfolios/{id}
Authorization: Bearer {token}
```

**Resposta: 204 No Content**

---

### 📊 Ativos

#### Cadastrar ativo

```http
POST /api/assets
Authorization: Bearer {token}
Content-Type: application/json

{
  "ticker": "AAPL",
  "name": "Apple Inc.",
  "assetType": "STOCK",
  "exchange": "NASDAQ"
}
```

#### Listar todos os ativos

```http
GET /api/assets
Authorization: Bearer {token}
```

#### Buscar por ticker

```http
GET /api/assets/AAPL
Authorization: Bearer {token}
```

---

### 💹 Cotações em Tempo Real

#### Preço de um ativo

```http
GET /api/prices/AAPL
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
{
  "symbol": "AAPL",
  "price": 189.30,
  "change": 1.23,
  "changePercent": "0.65",
  "latestTradingDay": "2025-04-08"
}
```

#### Preço de múltiplos ativos

```http
GET /api/prices?symbols=AAPL,GOOGL,PETR4
Authorization: Bearer {token}
```

---

### ❌ Respostas de Erro

Todos os erros seguem o formato padronizado:

```json
{
  "status": 400,
  "message": "Email já cadastrado",
  "timestamp": "2025-04-08T17:00:00"
}
```

| Código | Situação |
|---|---|
| `400` | Erro de negócio (email duplicado, ativo não encontrado) |
| `403` | Sem token ou token inválido |
| `422` | Erro de validação (campo vazio, email inválido) |

---

## 🧪 Testes

### Rodar todos os testes

```bash
# Linux / Mac
./mvnw test

# Windows
mvnw.cmd test
```

### Rodar com relatório de cobertura

```bash
./mvnw test jacoco:report
# Relatório gerado em: target/site/jacoco/index.html
```

### Estrutura dos testes

```
src/test/
├── java/
│   ├── security/
│   │   └── JwtServiceTest.java          # 5 testes unitários (Mockito)
│   └── controller/
│       └── AuthControllerTest.java      # 6 testes de integração (MockMvc)
└── resources/
    └── application.yml                   # configuração H2 para testes
```

| Tipo | Ferramenta | Velocidade | O que testa |
|---|---|---|---|
| Unitário | JUnit 5 + Mockito | ~0.5s | `JwtService` isolado |
| Integração | MockMvc + H2 | ~5-8s | Fluxo HTTP completo |

---

## 🔑 Variáveis de Ambiente

| Variável | Obrigatória | Padrão | Descrição |
|---|---|---|---|
| `DB_URL` | Não | `jdbc:postgresql://localhost:5432/portfolio_db` | URL de conexão do PostgreSQL |
| `DB_USERNAME` | Não | `portfolio_user` | Usuário do banco |
| `DB_PASSWORD` | **Sim** | — | Senha do banco |
| `JWT_SECRET` | **Sim** | — | Chave de assinatura JWT (mín. 256 bits) |
| `JWT_EXPIRATION` | Não | `86400000` | Expiração do token em ms (padrão: 24h) |
| `ALPHAVANTAGE_KEY` | **Sim** | — | API Key da Alpha Vantage |

Para gerar uma `JWT_SECRET` segura:

```bash
# Linux / Mac
openssl rand -hex 32

# Windows PowerShell
[System.Convert]::ToBase64String([System.Security.Cryptography.RandomNumberGenerator]::GetBytes(32))
```

---

## 📁 Estrutura do Projeto

```
investment-portfolio-api/
├── .github/
│   ├── workflows/
│   │   └── ci.yml                    # Pipeline CI/CD
│   └── ISSUE_TEMPLATE/
│       ├── bug_report.md
│       └── feature_request.md
├── docs/
│   └── ARCHITECTURE.md               # Decisões de arquitetura
├── scripts/
│   └── setup-db.sql                  # Script para setup manual do banco
├── src/
│   ├── main/
│   │   ├── java/com/portfolio/investment_api/
│   │   │   ├── client/
│   │   │   │   └── AlphaVantageClient.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── PortfolioController.java
│   │   │   │   ├── AssetController.java
│   │   │   │   └── StockPriceController.java
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── model/
│   │   │   │   ├── dto/
│   │   │   │   └── entity/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   │   ├── JwtService.java
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       ├── java/
│       └── resources/
│           └── application.yml        # Config H2 para testes
├── .env.example                       # Template de variáveis de ambiente
├── .gitignore
├── docker-compose.yml                 # Sobe o banco com um comando
├── pom.xml
└── README.md
```

---

## 🧠 Decisões de Arquitetura

### Por que JWT stateless e não sessões?

APIs REST devem ser stateless — o servidor não armazena estado entre requisições. Com JWT, cada requisição carrega sua própria autenticação no token, o que permite escalar horizontalmente sem sincronizar sessões entre instâncias.

### Por que DTOs e não expor entidades diretamente?

Expor entidades JPA na API cria acoplamento entre a camada de persistência e a camada de apresentação. Um campo adicionado na entidade aparece automaticamente na API — incluindo campos sensíveis como `password`. DTOs dão controle total sobre o contrato da API.

### Por que verificar o dono em cada operação de portfólio?

Sem verificação, buscar `GET /api/portfolios/{qualquer-uuid}` retornaria dados de qualquer usuário. Essa falha chama-se IDOR (Insecure Direct Object Reference) e está no [OWASP Top 10](https://owasp.org/Top10/). O método `findByIdAndUserId()` no repository garante que o portfólio pertence ao usuário autenticado.

### Por que BigDecimal para valores financeiros?

`double` e `float` usam representação binária de ponto flutuante — `0.1 + 0.2` resulta em `0.30000000000000004`. Em sistemas financeiros, essa imprecisão é inaceitável. `BigDecimal` garante precisão arbitrária em todas as operações.

---

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature: `git checkout -b feature/nova-funcionalidade`
3. Commit suas mudanças: `git commit -m 'feat: adiciona nova funcionalidade'`
4. Push para a branch: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<p align="center">
  Feito com Java 21 + Spring Boot 3 · Por <a href="https://github.com/joaosouzas">@joaosouzas</a>
</p>
