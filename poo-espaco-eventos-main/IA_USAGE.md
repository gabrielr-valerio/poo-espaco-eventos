# Registo de Utilização de Inteligência Artificial

Este documento regista a utilização de ferramentas de IA no apoio ao desenvolvimento do projeto, detalhando os objetivos, partes afetadas, verificações e decisões tomadas pela equipa.

---

## Checkpoint 1 — 06/10/2026

### 1. Modelação da Hierarquia de Exceções de Domínio
* **Objetivo do uso:** Auxílio na estruturação de exceções de negócio personalizadas que estendem uma classe base e carregam atributos de contexto consultáveis, evitando exceções genéricas.
* **Parte do projeto afetada:** Pacote `br.edu.fagammon.dominio` (`RegraNegocioException`, `PeriodoInvalidoException`, `CapacidadeExcedidaException`, `AntecedenciaInsuficienteException`, `EspacoInativoException`, `Reserva` e `Periodo`).
* **Como o resultado foi verificado:** Leitura e verificação do código gerado para garantir o cumprimento das invariantes e execução da suíte de testes unitários `ReservaTest` via `.\mvnw.cmd test`.
* **Correções ou decisões feitas pela equipa:** A equipa optou por encapsular os atributos de contexto das exceções como imutáveis (`final`) e padronizar as mensagens formatadas.

---

### 2. Resolução de Falhas de Compilação e Ajuste de Testes
* **Objetivo do uso:** Diagnóstico de erros de compilação exibidos no IntelliJ (erro de sintaxe na variável `periodo` e incompatibilidade nos parâmetros do construtor de `Participante`).
* **Parte do projeto afetada:** `Reserva.java`, `Participante.java`, `EspacoEvento.java` e `ReservaTest.java`.
* **Como o resultado foi verificado:** Execução dos testes via JUnit/Maven na IDE e no terminal, confirmando a aprovação de 100% dos testes (`Process finished with exit code 0`).
* **Correções ou decisões feitas pela equipa:** Ajuste no construtor de `Participante` para aceitar os argumentos `(id, nome, email)` e correção do nome do atributo `periodo` na classe `Reserva`.

## Checkpoint 2 — 31/10/2026

### 3. Modelação de Padrões Polimórficos, Repositorios Genéricos e Ciclo de Vida
- **Objetivo do uso:** Apoio na estruturação das estratégias polimórficas de precificação (`Strategy`), políticas de cancelamento substituíveis, abstração do repositório genérico (`Repositorio<T, ID>`) e consultas avançadas com Java Streams.
- **Parte do projeto afetada:** Pacotes `br.edu.fagammon.dominio.preco`, `br.edu.fagammon.dominio.cancelamento`, `br.edu.fagammon.repositorio` e classe de ciclo de vida `Reserva`.
- **Como o resultado foi verificado:** Execução do comando `.\mvnw.cmd test` cobrindo cálculo por hora, cálculo por participante, reembolso flexível/rigoroso e validações de sobreposição de horários.
- **Correções ou decisões feitas pela equipa:** Manutenção do uso estrito de `BigDecimal` para valores monetários e definição do estado inicial padrão da reserva como `PENDENTE`, adaptando os testes legados para refletir o ciclo de vida.

### 4. Correção da Estrutura de Diretórios de Testes e Ajustes do JUnit
- **Objetivo do uso:** Resolução de erros de compilação causados pelo arquivo `ReservaCheckpoint2Test.java` ter sido colocado na pasta de código fonte (`src/main/java`) em vez da pasta de testes (`src/test/java`).
- **Parte do projeto afetada:** Estrutura de pastas do Maven e classes de teste (`ReservaCheckpoint2Test.java` e `ReservaTest.java`).
- **Como o resultado foi verificado:** Movimentação do ficheiro via terminal PowerShell e execução de `.\mvnw.cmd test` resultando em `BUILD SUCCESS` com 12 testes aprovados.
- **Correções ou decisões feitas pela equipa:** Organização estrita do layout do Maven, mantendo todo o código de teste isolado em `src/test/java/br/edu/fagammon/dominio/`.

---

## Checkpoint 3 — 10/11/2026

### 5. Persistência JSON (RF9), Padrões Adapter/Factory e Invariantes de Restauração
- **Objetivo do uso:** Estruturação da persistência em formato JSON aberto via Jackson, implementação do padrão Adapter com `ReservaDTO` para conversão de dados, e criação do padrão Factory (`ReservaFactory`) para instanciação controlada.
- **Parte do projeto afetada:** Pacotes `br.edu.fagammon.persistencia`, `br.edu.fagammon.dominio.factory` e suíte de testes `ReservaCheckpoint3Test.java`.
- **Como o resultado foi verificado:** Teste de gravação e leitura em arquivos temporários (`@TempDir`), além de teste proposital com JSON corrompido para assegurar a rejeição de dados que violem invariantes (Invariante 8).
- **Correções ou decisões feitas pela equipa:** A equipa garantiu que a conversão de DTO para entidade passe obrigatoriamente pelos construtores de domínio originais para impor todas as regras de validação durante a restauração dos dados.

### 6. Relatório Síntese (RF10) e Correção da Estrutura do `pom.xml`
- **Objetivo do uso:** Criação do gerador de relatórios operacionais/financeiros com Java Streams (`GeradorRelatorioEspacos`) e correção do erro de sintaxe XML no `pom.xml` causado pela inserção de dependências fora da tag `<project>`.
- **Parte do projeto afetada:** `pom.xml` e pacote `br.edu.fagammon.relatorio`.
- **Como o resultado foi verificado:** Reestruturação da tag `<dependencies>` e execução da suíte completa de testes unitários (`.\mvnw.cmd test`), obtendo 100% de aprovação em todos os 16 testes (`BUILD SUCCESS`).
- **Correções ou decisões feitas pela equipa:** Formatação correta do arquivo de configuração do Maven e validação do relatório gerando métricas financeiras precisas usando `BigDecimal`.