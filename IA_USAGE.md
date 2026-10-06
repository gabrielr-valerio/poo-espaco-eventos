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