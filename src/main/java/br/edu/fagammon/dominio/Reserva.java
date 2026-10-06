package br.edu.fagammon.dominio;

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
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo.");
        this.organizador = Objects.requireNonNull(organizador, "Organizador é obrigatório.");
        this.espaco = Objects.requireNonNull(espaco, "Espaço é obrigatório.");
        this.periodo = Objects.requireNonNull(periodo, "Período é obrigatório.");

        if (!espaco.isAtivo()) {
            throw new EspacoInativoException(espaco.getId());
        }

        if (quantidadeConvidados <= 0 || quantidadeConvidados > espaco.getCapacidadeMaxima()) {
            throw new CapacidadeExcedidaException(espaco.getCapacidadeMaxima(), quantidadeConvidados);
        }

        if (periodo.inicio().isBefore(LocalDateTime.now().plusHours(48))) {
            throw new AntecedenciaInsuficienteException(periodo.inicio(), 48);
        }

        this.quantidadeConvidados = quantidadeConvidados;
        this.status = StatusReserva.CONFIRMADA;
    }

    public void cancelar() {
        if (this.status == StatusReserva.CONCLUIDA) {
            throw new IllegalStateException("Não é possível cancelar uma reserva já concluída.");
        }
        this.status = StatusReserva.CANCELADA;
    }

    public String getId() {
        return id;
    }
    public Participante getOrganizador() {
        return organizador;
    }
    public EspacoEvento getEspaco() {
        return espaco;
    }
    public Periodo getPeriodo() {
        return periodo;
    }
    public StatusReserva getStatus() {
        return status;
    }
    public int getQuantidadeConvidados() {
        return quantidadeConvidados;
    }
}
