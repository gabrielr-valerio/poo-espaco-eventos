package br.edu.fagammon.dominio;

import br.edu.fagammon.dominio.cancelamento.PoliticaCancelamento;
import br.edu.fagammon.dominio.preco.CalculoPrecoStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva {
    private final String id;
    private final Participante organizador;
    private final EspacoEvento espaco;
    private final Periodo periodo;
    private final int quantidadeConvidados;
    private StatusReserva status;

    public Reserva(String id, Participante organizador, EspacoEvento espaco, Periodo periodo, int quantidadeConvidados) {
        this(id, organizador, espaco, periodo, quantidadeConvidados, LocalDateTime.now());
    }

    public Reserva(String id, Participante organizador, EspacoEvento espaco, Periodo periodo, int quantidadeConvidados, LocalDateTime dataSolicitacao) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID da reserva é obrigatório.");
        }
        this.organizador = Objects.requireNonNull(organizador, "Organizador é obrigatório.");
        this.espaco = Objects.requireNonNull(espaco, "Espaço é obrigatório.");
        this.periodo = Objects.requireNonNull(periodo, "Período é obrigatório.");

        if (!espaco.isAtivo()) {
            throw new EspacoInativoException(espaco.getId());
        }
        if (quantidadeConvidados > espaco.getCapacidadeMaxima() || quantidadeConvidados <= 0) {
            throw new CapacidadeExcedidaException(espaco.getCapacidadeMaxima(), quantidadeConvidados);
        }
        if (dataSolicitacao != null && periodo.inicio().isBefore(dataSolicitacao.plusHours(48))) {
            throw new AntecedenciaInsuficienteException(periodo.inicio(), 48L);
        }

        this.id = id;
        this.quantidadeConvidados = quantidadeConvidados;
        this.status = StatusReserva.PENDENTE;
    }

    public void confirmar() {
        if (this.status != StatusReserva.PENDENTE) {
            throw new EstadoReservaInvalidoException(this.status, "confirmar");
        }
        this.status = StatusReserva.CONFIRMADA;
    }

    public BigDecimal cancelar(PoliticaCancelamento politica, BigDecimal valorPago, LocalDateTime dataCancelamento) {
        if (this.status == StatusReserva.CANCELADA || this.status == StatusReserva.CONCLUIDA) {
            throw new EstadoReservaInvalidoException(this.status, "cancelar");
        }
        this.status = StatusReserva.CANCELADA;
        return politica.calcularReembolso(this, valorPago, dataCancelamento);
    }

    public void concluir() {
        if (this.status != StatusReserva.CONFIRMADA) {
            throw new EstadoReservaInvalidoException(this.status, "concluir");
        }
        this.status = StatusReserva.CONCLUIDA;
    }

    public BigDecimal calcularPreco(CalculoPrecoStrategy strategy) {
        Objects.requireNonNull(strategy, "Estratégia de preço é obrigatória.");
        return strategy.calcular(this);
    }

    public String getId() { return id; }
    public Participante getOrganizador() { return organizador; }
    public EspacoEvento getEspaco() { return espaco; }
    public Periodo getPeriodo() { return periodo; }
    public int getQuantidadeConvidados() { return quantidadeConvidados; }
    public StatusReserva getStatus() { return status; }
}