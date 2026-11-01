package br.edu.fagammon.dominio.preco;

import br.edu.fagammon.dominio.Reserva;
import java.math.BigDecimal;

public interface CalculoPrecoStrategy {
    BigDecimal calcular(Reserva reserva);
}