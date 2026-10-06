# Plataforma de Reservas

## Equipe

- Integrante 1:
- Integrante 2, se houver:

## Variante

- Variante:
- Regra específica:

## Requisitos atendidos

Atualize esta seção em cada marco do projeto.

## Como executar

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Estrutura

As classes de domínio serão criadas pela equipe depois da escolha da variante.
Não adicione camadas, interfaces ou padrões antes de existir um requisito que
justifique a decisão.

# Sistema de Gestão de Reservas — Espaços para Eventos

Projeto semestral da disciplina de **Programação Orientada a Objetos (POO)** da **Faculdade Presbiteriana Gammon (Fagammon)** — 2026/2.

---

## 👥 Integrantes da Equipe
- **Gabriel Resende Valério, Pedro Henrique Silva Azevedo**

---

## 📌 Variante Escolhida
- **Domínio:** Espaços para Eventos
- **Recursos Gerenciados:** Salões de festas, auditórios, estúdios e áreas externas.
- **Regras Específicas do Domínio:**
    - Validação de capacidade máxima de participantes do evento versus capacidade do espaço.
    - Validação do período mínimo de antecedência necessário para efetuar uma reserva.
    - Verificação do status de ativação do espaço antes do agendamento.

---

## 🎯 Problema e Objetivo
O objetivo do sistema é administrar o ciclo de vida de reservas de espaços físicos para eventos em um determinado período de tempo, impedindo sobreposição de horários, garantindo as regras de negócio específicas do domínio e fornecendo um histórico consistente e validado de participantes e reservas.

---

## ⚙️ Requisitos e Invariantes Implementadas (Checkpoint 1)
- **RF1 - Participantes:** Cadastro e validação de responsáveis por reservas (ID, Nome, E-mail).
- **RF2 - Recursos:** Cadastro e gerenciamento do status de ativação de espaços para eventos.
- **RF3 - Períodos:** Representação de intervalor de tempo imutáveis com validação de início anterior ao fim e detecção de sobreposição.
- **RF4 - Validações e Exceções de Domínio:** Bloqueio de reservas para recursos inativos, capacidades excedidas ou períodos inconsistentes via hierarquia de exceções customizadas (`RegraNegocioException`).

---

## 🚀 Como Executar os Testes

Certifique-se de ter o Java 25 instalado. No terminal na raiz do projeto, execute:

### Windows (PowerShell / Prompt):
```powershell
.\mvnw.cmd test