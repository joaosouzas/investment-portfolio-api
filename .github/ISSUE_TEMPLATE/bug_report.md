---
name: "Bug report"
about: "Relate um bug ou comportamento inesperado"
title: "[BUG] "
labels: ["bug", "triage"]
assignees: []
---

## Descrição do Bug

Uma descrição clara e concisa do bug encontrado.

## Como Reproduzir

Passos para reproduzir o comportamento:

1. Chame o endpoint `POST /api/...`
2. Com o body `{ ... }`
3. Observe o erro

## Comportamento Esperado

O que deveria acontecer.

## Comportamento Atual

O que está acontecendo de fato.

## Resposta da API (se aplicável)

```json
{
  "status": 500,
  "message": "..."
}
```

## Log de Erro (se aplicável)

```
Cole aqui o stack trace do console
```

## Ambiente

- **OS:** [ex: Windows 11, macOS 14, Ubuntu 22.04]
- **Java:** [ex: 21.0.3]
- **Spring Boot:** [ex: 3.3.2]
- **Banco:** [ex: PostgreSQL 16 via Docker]

## Contexto Adicional

Qualquer outra informação relevante sobre o problema.
