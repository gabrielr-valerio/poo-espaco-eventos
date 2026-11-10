package br.edu.fagammon.dominio.preco;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class CalculoPrecoPorHora implements CalculoPrecoStrategy {
    private final BigDecimal valorPorHora;

    public CalculoPrecoPorHora(BigDecimal valorPorHora) {
        if (valorPorHora == null || valorPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor por hora deve ser positivo.");
        }
        this.valorPorHora = valorPorHora;
    }

    @Override
    public BigDecimal calcular(Reserva reserva) {
        long horas = Duration.between(reserva.getPeriodo().inicio(), reserva.getPeriodo().fim()).toHours();
        if (horas == 0) {
            horas = 1; // Cobrança mínima de 1 hora
        }
        return valorPorHora.multiply(BigDecimal.valueOf(horas)).setScale(2, RoundingMode.HALF_UP);
    }
}