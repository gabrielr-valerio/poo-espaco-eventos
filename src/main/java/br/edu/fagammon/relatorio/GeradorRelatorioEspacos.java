package br.edu.fagammon.relatorio;

import br.edu.fagammon.dominio.Reserva;
import br.edu.fagammon.dominio.StatusReserva;
import br.edu.fagammon.dominio.preco.CalculoPrecoStrategy;
import br.edu.fagammon.repositorio.RepositorioReserva;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeradorRelatorioEspacos {

    public String gerarSintese(RepositorioReserva repositorio, CalculoPrecoStrategy strategyPreco) {
        List<Reserva> todas = repositorio.listarTodos();

        long totalReservas = todas.size();
        long confirmadas = todas.stream().filter(r -> r.getStatus() == StatusReserva.CONFIRMADA).count();
        long pendentes = todas.stream().filter(r -> r.getStatus() == StatusReserva.PENDENTE).count();
        long canceladas = todas.stream().filter(r -> r.getStatus() == StatusReserva.CANCELADA).count();

        BigDecimal receitaEstimada = todas.stream()
                .filter(r -> r.getStatus() != StatusReserva.CANCELADA)
                .map(r -> r.calcularPreco(strategyPreco))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> reservasPorEspaco = todas.stream()
                .collect(Collectors.groupingBy(r -> r.getEspaco().getNome(), Collectors.counting()));

        StringBuilder sb = new StringBuilder();
        sb.append("=== SÍNTESE DE GESTÃO DE ESPAÇOS DE EVENTOS ===\n");
        sb.append("Total de Reservas Cadastradas: ").append(totalReservas).append("\n");
        sb.append("  - Confirmadas: ").append(confirmadas).append("\n");
        sb.append("  - Pendentes: ").append(pendentes).append("\n");
        sb.append("  - Canceladas: ").append(canceladas).append("\n");
        sb.append("Receita Bruta Estimada (Ativas): R$ ").append(receitaEstimada).append("\n");
        sb.append("Ocupação por Espaço:\n");
        reservasPorEspaco.forEach((espaco, qtd) ->
                sb.append("  * ").append(espaco).append(": ").append(qtd).append(" reserva(s)\n")
        );
        return sb.toString();
    }
}