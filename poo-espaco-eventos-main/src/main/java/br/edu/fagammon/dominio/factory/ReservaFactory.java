package br.edu.fagammon.dominio.factory;

import br.edu.fagammon.dominio.EspacoEvento;
import br.edu.fagammon.dominio.Participante;
import br.edu.fagammon.dominio.Periodo;
import br.edu.fagammon.dominio.Reserva;

import java.time.LocalDateTime;

public class ReservaFactory {

    public static Reserva criarReservaPadrao(String id, Participante organizador, EspacoEvento espaco, Periodo periodo, int convidados) {
        return new Reserva(id, organizador, espaco, periodo, convidados);
    }

    public static Reserva criarReservaComDataSolicitacao(String id, Participante organizador, EspacoEvento espaco, Periodo periodo, int convidados, LocalDateTime dataSolicitacao) {
        return new Reserva(id, organizador, espaco, periodo, convidados, dataSolicitacao);
    }
}