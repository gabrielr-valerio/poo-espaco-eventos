# Decisões de Arquitetura e Modelagem

## Checkpoint 1 — 06/10/2026

- **Exceções de Domínio Personalizadas:** Optou-se por criar uma hierarquia com a classe base `RegraNegocioException` estendendo `RuntimeException`. As exceções específicas carregam atributos imutáveis com o contexto da falha.
- **Objeto de Valor `Periodo`:** Encapsula o intervalo de tempo e valida a invariante de que a data de início deve ser anterior à de término.
- **Invariantes nos Construtores:** As entidades e records realizam validação de não-nulidade e regras de capacidade/antecedência diretamente no momento da instanciação.

## Checkpoint 2 — 31/10/2026
- **Padrão Strategy para Cancelamento e Preços:** Utilização de interfaces polimórficas (`PoliticaCancelamento` e `CalculoPrecoStrategy`) para aplicar regras de reembolso e precificação sem condicionais espalhadas no domínio.
- **Precisão Financeira com `BigDecimal`:** Uso estrito de `BigDecimal` com arredondamento `HALF_UP` para garantir precisão monetária e conformidade com as restrições técnicas.
- **Abstração Genérica de Repositório:** Interface `Repositorio<T, ID>` e implementação base `RepositorioEmMemoria<T, ID>` reaproveitável.
- **Consultas com Java Streams:** Métodos de consulta declarativos (`buscarPorOrganizador`, `listarOrdenadasPorDataInicio`) utilizando a API de Streams.