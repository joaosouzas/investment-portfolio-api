# Guia de Contribuição

Obrigado pelo interesse em contribuir com o Investment Portfolio API!

## Convenção de Commits

Este projeto segue o padrão [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>(<escopo>): <descrição curta>

[corpo opcional]

[rodapé opcional]
```

### Tipos permitidos

| Tipo | Quando usar |
|---|---|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `docs` | Mudanças apenas na documentação |
| `refactor` | Refatoração sem mudança de comportamento |
| `test` | Adição ou correção de testes |
| `chore` | Mudanças no build, CI, dependências |
| `perf` | Melhorias de performance |

### Exemplos

```bash
git commit -m "feat(auth): adiciona endpoint de refresh token"
git commit -m "fix(portfolio): corrige verificação de propriedade no delete"
git commit -m "docs(readme): atualiza instruções de setup no Windows"
git commit -m "test(jwt): adiciona teste para token com claim customizado"
```

## Fluxo de Desenvolvimento

1. Fork o repositório
2. Crie uma branch descritiva: `git checkout -b feat/refresh-token`
3. Faça commits pequenos e atômicos
4. Garanta que todos os testes passam: `./mvnw test`
5. Abra um Pull Request com o template preenchido

## Padrões de Código

- Siga a estrutura em camadas: Controller → Service → Repository
- Nunca exponha entidades JPA diretamente na API — use DTOs
- Use `BigDecimal` para todos os valores monetários
- Adicione testes para toda nova funcionalidade
- Documente decisões de arquitetura não óbvias em comentários
