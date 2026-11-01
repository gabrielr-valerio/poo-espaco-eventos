package br.edu.fagammon.dominio.preco;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoPrecoPorParticipante implements CalculoPrecoStrategy {
    private final BigDecimal taxaBase;
    private final BigDecimal valorPorParticipante;

    public CalculoPrecoPorParticipante(BigDecimal taxaBase, BigDecimal valorPorParticipante) {
        if (taxaBase == null || taxaBase.compareTo(BigDecimal.ZERO) < 0 ||
                valorPorParticipante == null || valorPorParticipante.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Os valores de taxa devem ser válidos.");
        }
        this.taxaBase = taxaBase;
        this.valorPorParticipante = valorPorParticipante;
    }

    @Override
    public BigDecimal calcular(Reserva reserva) {
        BigDecimal totalParticipantes = valorPorParticipante.multiply(BigDecimal.valueOf(reserva.getQuantidadeConvidados()));
        return taxaBase.add(totalParticipantes).setScale(2, RoundingMode.HALF_UP);
    }
}