-- =============================================================================
-- INVESTMENT PORTFOLIO API — Script de Setup do Banco de Dados
-- =============================================================================
-- Este script é executado automaticamente pelo Docker Compose ao criar
-- o banco pela primeira vez (via /docker-entrypoint-initdb.d/).
--
-- Para rodar manualmente:
--   psql -U portfolio_user -d portfolio_db -f scripts/setup-db.sql
-- =============================================================================

-- Garante que a extensão uuid-ossp está disponível (para gerar UUIDs se necessário)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =============================================================================
-- Comentário: As tabelas são criadas automaticamente pelo Hibernate
-- com ddl-auto: update. Este script serve apenas para configurações
-- adicionais que o Hibernate não gerencia.
-- =============================================================================

-- Índices adicionais para performance (criados após o Hibernate criar as tabelas)
-- Execute estes índices após a primeira inicialização da aplicação:

-- CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_portfolios_user_id
--     ON tb_portfolios(user_id);

-- CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_transactions_portfolio_id
--     ON tb_transactions(portfolio_id);

-- CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_transactions_asset_id
--     ON tb_transactions(asset_id);

-- CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_portfolio_assets_portfolio_id
--     ON tb_portfolio_assets(portfolio_id);
