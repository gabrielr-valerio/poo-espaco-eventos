package br.edu.fagammon.dominio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    @DisplayName("Deve criar reserva válida com status inicial PENDENTE")
    void deveCriarReservaValida() {
        Participante p = new Participante("1", "Gabriel", "gabriel@email.com");
        EspacoEvento e = new EspacoEvento("1", "Auditório", 50, true);
        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(2));

        Reserva reserva = new Reserva("R1", p, e, periodo, 30);

        assertNotNull(reserva);
        assertEquals("R1", reserva.getId());
        assertEquals(StatusReserva.PENDENTE, reserva.getStatus());
    }

    @Test
    @DisplayName("Deve rejeitar antecedência insuficiente")
    void deveRejeitarAntecedenciaInsuficiente() {
        Participante p = new Participante("1", "Gabriel", "gabriel@email.com");
        EspacoEvento e = new EspacoEvento("1", "Auditório", 50, true);
        LocalDateTime inicio = LocalDateTime.now().plusHours(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(2));

        assertThrows(AntecedenciaInsuficienteException.class, () ->
                new Reserva("R1", p, e, periodo, 30)
        );
    }

    @Test
    @DisplayName("Deve rejeitar capacidade excedida")
    void deveRejeitarCapacidadeExcedida() {
        Participante p = new Participante("1", "Gabriel", "gabriel@email.com");
        EspacoEvento e = new EspacoEvento("1", "Auditório", 50, true);
        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(2));

        assertThrows(CapacidadeExcedidaException.class, () ->
                new Reserva("R1", p, e, periodo, 100)
        );
    }

    @Test
    @DisplayName("Deve rejeitar espaço inativo")
    void deveRejeitarEspacoInativo() {
        Participante p = new Participante("1", "Gabriel", "gabriel@email.com");
        EspacoEvento e = new EspacoEvento("1", "Auditório", 50, false);
        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(2));

        assertThrows(EspacoInativoException.class, () ->
                new Reserva("R1", p, e, periodo, 30)
        );
    }
}