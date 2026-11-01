package br.edu.fagammon.dominio.cancelamento;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class PoliticaCancelamentoRigorosa implements PoliticaCancelamento {

    @Override
    public BigDecimal calcularReembolso(Reserva reserva, BigDecimal valorPago, LocalDateTime dataCancelamento) {
        long horasAntecedencia = Duration.between(dataCancelamento, reserva.getPeriodo().inicio()).toHours();

        if (horasAntecedencia >= 72) {
            return valorPago.multiply(new BigDecimal("0.80")).setScale(2, RoundingMode.HALF_UP); // 80% de reembolso (multa 20%)
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP); // Retenção total se < 72h
    }
}