package br.edu.fagammon.dominio;

import br.edu.fagammon.dominio.cancelamento.PoliticaCancelamentoFlexivel;
import br.edu.fagammon.dominio.cancelamento.PoliticaCancelamentoRigorosa;
import br.edu.fagammon.dominio.preco.CalculoPrecoPorHora;
import br.edu.fagammon.dominio.preco.CalculoPrecoPorParticipante;
import br.edu.fagammon.repositorio.RepositorioReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservaCheckpoint2Test {

    private Participante organizador;
    private EspacoEvento espaco;
    private Periodo periodoFuturo;
    private LocalDateTime agora;

    @BeforeEach
    void setUp() {
        agora = LocalDateTime.of(2026, 11, 1, 10, 0);
        organizador = new Participante("P1", "Gabriel Resende", "gabriel@email.com");
        espaco = new EspacoEvento("E1", "Auditório Principal", 100, true);
        periodoFuturo = new Periodo(agora.plusDays(5), agora.plusDays(5).plusHours(4));
    }

    @Test
    @DisplayName("Deve calcular preço por hora corretamente com BigDecimal")
    void deveCalcularPrecoPorHora() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        CalculoPrecoPorHora strategy = new CalculoPrecoPorHora(new BigDecimal("150.00"));

        BigDecimal total = reserva.calcularPreco(strategy);
        assertEquals(new BigDecimal("600.00"), total);
    }

    @Test
    @DisplayName("Deve calcular preço por participante corretamente com BigDecimal")
    void deveCalcularPrecoPorParticipante() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        CalculoPrecoPorParticipante strategy = new CalculoPrecoPorParticipante(new BigDecimal("200.00"), new BigDecimal("10.00"));

        BigDecimal total = reserva.calcularPreco(strategy);
        assertEquals(new BigDecimal("700.00"), total);
    }

    @Test
    @DisplayName("Deve realizar transição de estados válida")
    void deveRealizarTransicaoEstadoValida() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        assertEquals(StatusReserva.PENDENTE, reserva.getStatus());

        reserva.confirmar();
        assertEquals(StatusReserva.CONFIRMADA, reserva.getStatus());

        reserva.concluir();
        assertEquals(StatusReserva.CONCLUIDA, reserva.getStatus());
    }

    @Test
    @DisplayName("Deve rejeitar transição de estado inválida")
    void deveRejeitarTransicaoInvalida() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        assertThrows(EstadoReservaInvalidoException.class, reserva::concluir);
    }

    @Test
    @DisplayName("Deve aplicar política de cancelamento flexível com reembolso de 100%")
    void deveAplicarCancelamentoFlexivelTotal() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        reserva.confirmar();

        BigDecimal reembolso = reserva.cancelar(new PoliticaCancelamentoFlexivel(), new BigDecimal("500.00"), agora);
        assertEquals(new BigDecimal("500.00"), reembolso);
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus());
    }

    @Test
    @DisplayName("Deve aplicar política de cancelamento rigorosa com taxa")
    void deveAplicarCancelamentoRigorosoComTaxa() {
        Reserva reserva = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        reserva.confirmar();

        BigDecimal reembolso = reserva.cancelar(new PoliticaCancelamentoRigorosa(), new BigDecimal("500.00"), agora);
        assertEquals(new BigDecimal("400.00"), reembolso);
    }

    @Test
    @DisplayName("Deve recusar sobreposição de reservas para o mesmo espaço")
    void deveRecusarSobreposicao() {
        RepositorioReserva repo = new RepositorioReserva();
        Reserva r1 = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        repo.salvar(r1);

        Reserva r2 = new Reserva("R2", organizador, espaco, periodoFuturo, 30, agora);
        assertThrows(SobreposicaoReservaException.class, () -> repo.salvar(r2));
    }

    @Test
    @DisplayName("Deve filtrar reservas por organizador usando Streams")
    void deveFiltrarPorOrganizador() {
        RepositorioReserva repo = new RepositorioReserva();
        Reserva r1 = new Reserva("R1", organizador, espaco, periodoFuturo, 50, agora);
        repo.salvar(r1);

        List<Reserva> resultado = repo.buscarPorOrganizador("P1");
        assertEquals(1, resultado.size());
        assertEquals("R1", resultado.get(0).getId());
    }
}