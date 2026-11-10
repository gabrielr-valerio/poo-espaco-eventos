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

## Checkpoint 3 — 10/11/2026
- **Persistência em JSON Aberto (RF9):** Implementação do `ServicoPersistenciaJson` utilizando a biblioteca Jackson para gravar e restaurar o estado completo das reservas em arquivos JSON inspecionáveis.
- **Padrão Adapter (`ReservaDTO`):** Utilização de DTO para desacoplar a estrutura do arquivo JSON do modelo de domínio interno.
- **Invariante 8 Preservada:** A restauração dos dados passa obrigatoriamente pelos construtores das entidades (`Reserva`, `Periodo`, `EspacoEvento`, `Participante`), garantindo que dados inválidos ou alterados externamente no JSON sejam rejeitados com exceções de negócio.
- **Padrão Factory (`ReservaFactory`):** Centralização da instanciação de reservas válidas.
- **Relatório Síntese (RF10):** Agregação de estatísticas operacionais e previsão de receita financeira utilizando Java Streams (`GeradorRelatorioEspacos`).