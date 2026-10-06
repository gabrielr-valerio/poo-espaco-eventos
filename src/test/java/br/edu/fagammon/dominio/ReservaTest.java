package br.edu.fagammon.dominio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    @DisplayName("Deve criar uma reserva válida quando todos os dados estiverem corretos")
    void deveCriarReservaValida() {
        Participante org = new Participante("P1", "Gabriel Valério", "gabriel@email.com");
        EspacoEvento espaco = new EspacoEvento("E1", "Salão de Festas", 100);

        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(4));

        Reserva reserva = new Reserva("R1", org, espaco, periodo, 80);

        assertNotNull(reserva);
        assertEquals(StatusReserva.CONFIRMADA, reserva.getStatus());
    }

    @Test
    @DisplayName("Deve lançar PeriodoInvalidoException com contexto quando o início for posterior ao fim")
    void deveRejeitarPeriodoInvalido() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        LocalDateTime fimInvalido = inicio.minusHours(2);

        PeriodoInvalidoException ex = assertThrows(
                PeriodoInvalidoException.class,
                () -> new Periodo(inicio, fimInvalido)
        );

        assertEquals(inicio, ex.getInicio());
        assertEquals(fimInvalido, ex.getFim());
    }

    @Test
    @DisplayName("Deve lançar CapacidadeExcedidaException informando a capacidade máxima e o valor solicitado")
    void deveRejeitarCapacidadeExcedida() {
        Participante org = new Participante("P1", "Pedro Azevedo", "pedro@email.com");
        EspacoEvento espaco = new EspacoEvento("E1", "Auditório Pequeno", 50);

        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(4));

        CapacidadeExcedidaException ex = assertThrows(
                CapacidadeExcedidaException.class,
                () -> new Reserva("R2", org, espaco, periodo, 60)
        );

        assertEquals(50, ex.getCapacidadeMaxima());
        assertEquals(60, ex.getSolicitada());
    }

    @Test
    @DisplayName("Deve lançar AntecedenciaInsuficienteException ao solicitar reserva com menos de 48 horas")
    void deveRejeitarAntecedenciaInsuficiente() {
        Participante org = new Participante("P1", "Gabriel Valério", "gabriel@email.com");
        EspacoEvento espaco = new EspacoEvento("E1", "Salão Nobre", 100);

        LocalDateTime inicio = LocalDateTime.now().plusHours(12);
        Periodo periodo = new Periodo(inicio, inicio.plusHours(4));

        AntecedenciaInsuficienteException ex = assertThrows(
                AntecedenciaInsuficienteException.class,
                () -> new Reserva("R3", org, espaco, periodo, 30)
        );

        assertEquals(48, ex.getHorasMinimas());
        assertEquals(inicio, ex.getInicio());
    }
}