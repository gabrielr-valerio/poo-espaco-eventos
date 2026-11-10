package br.edu.fagammon.dominio.cancelamento;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class PoliticaCancelamentoFlexivel implements PoliticaCancelamento {

    @Override
    public BigDecimal calcularReembolso(Reserva reserva, BigDecimal valorPago, LocalDateTime dataCancelamento) {
        long horasAntecedencia = Duration.between(dataCancelamento, reserva.getPeriodo().inicio()).toHours();

        if (horasAntecedencia >= 24) {
            return valorPago.setScale(2, RoundingMode.HALF_UP); // 100% de reembolso
        } else if (horasAntecedencia > 0) {
            return valorPago.multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP); // 50% de reembolso
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP); // Sem reembolso se já iniciou
    }
}