package br.edu.fagammon.persistencia;

import br.edu.fagammon.dominio.EspacoEvento;
import br.edu.fagammon.dominio.Participante;
import br.edu.fagammon.dominio.Periodo;
import br.edu.fagammon.dominio.Reserva;
import br.edu.fagammon.dominio.factory.ReservaFactory;

import java.time.LocalDateTime;

public class ReservaDTO {
    public String id;
    public String organizadorId;
    public String organizadorNome;
    public String organizadorEmail;
    public String espacoId;
    public String espacoNome;
    public int espacoCapacidade;
    public boolean espacoAtivo;
    public String inicioPeriodo;
    public String fimPeriodo;
    public int quantidadeConvidados;

    public ReservaDTO() {}

    public static ReservaDTO deEntidade(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.id = reserva.getId();
        dto.organizadorId = reserva.getOrganizador().getId();
        dto.organizadorNome = reserva.getOrganizador().getNome();
        dto.organizadorEmail = reserva.getOrganizador().getEmail();
        dto.espacoId = reserva.getEspaco().getId();
        dto.espacoNome = reserva.getEspaco().getNome();
        dto.espacoCapacidade = reserva.getEspaco().getCapacidadeMaxima();
        dto.espacoAtivo = reserva.getEspaco().isAtivo();
        dto.inicioPeriodo = reserva.getPeriodo().inicio().toString();
        dto.fimPeriodo = reserva.getPeriodo().fim().toString();
        dto.quantidadeConvidados = reserva.getQuantidadeConvidados();
        return dto;
    }

    public Reserva paraEntidade() {
        Participante organizador = new Participante(organizadorId, organizadorNome, organizadorEmail);
        EspacoEvento espaco = new EspacoEvento(espacoId, espacoNome, espacoCapacidade, espacoAtivo);
        Periodo periodo = new Periodo(LocalDateTime.parse(inicioPeriodo), LocalDateTime.parse(fimPeriodo));
        return ReservaFactory.criarReservaComDataSolicitacao(id, organizador, espaco, periodo, quantidadeConvidados, null);
    }
}