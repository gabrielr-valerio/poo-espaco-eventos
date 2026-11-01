package br.edu.fagammon.dominio.cancelamento;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PoliticaCancelamento {
    BigDecimal calcularReembolso(Reserva reserva, BigDecimal valorPago, LocalDateTime dataCancelamento);
}