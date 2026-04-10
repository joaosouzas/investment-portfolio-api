## Descrição

Descreva claramente o que este PR faz e qual problema resolve.

Closes #(número da issue)

## Tipo de mudança

- [ ] Bug fix (correção que não quebra funcionalidades existentes)
- [ ] Nova feature (adiciona funcionalidade sem quebrar nada existente)
- [ ] Breaking change (correção ou feature que altera comportamento existente)
- [ ] Refatoração (sem mudança de comportamento, melhoria de código)
- [ ] Documentação

## Checklist

- [ ] Meu código segue o estilo do projeto
- [ ] Fiz self-review do meu próprio código
- [ ] Comentei as partes mais complexas
- [ ] Adicionei/atualizei testes para cobrir as mudanças
- [ ] Todos os testes existentes continuam passando (`./mvnw test`)
- [ ] Atualizei a documentação se necessário

## Como testar

Descreva os passos para testar suas mudanças:

1. Configure o ambiente com `.env`
2. Suba o banco: `docker compose up -d`
3. Rode a aplicação
4. Teste o endpoint `POST /api/...` com o body `{ ... }`
5. Resultado esperado: `201 Created` com `{ ... }`

## Screenshots (se aplicável)

Adicione screenshots de chamadas no Postman ou logs relevantes.
