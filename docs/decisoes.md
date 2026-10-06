# Decisões de Arquitetura e Modelagem

## Checkpoint 1 — 06/10/2026

- **Exceções de Domínio Personalizadas:** Optou-se por criar uma hierarquia com a classe base `RegraNegocioException` estendendo `RuntimeException`. As exceções específicas carregam atributos imutáveis com o contexto da falha.
- **Objeto de Valor `Periodo`:** Encapsula o intervalo de tempo e valida a invariante de que a data de início deve ser anterior à de término.
- **Invariantes nos Construtores:** As entidades e records realizam validação de não-nulidade e regras de capacidade/antecedência diretamente no momento da instanciação.