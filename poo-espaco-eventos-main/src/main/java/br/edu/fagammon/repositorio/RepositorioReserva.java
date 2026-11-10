package br.edu.fagammon.repositorio;

import br.edu.fagammon.dominio.Periodo;
import br.edu.fagammon.dominio.Reserva;
import br.edu.fagammon.dominio.SobreposicaoReservaException;
import br.edu.fagammon.dominio.StatusReserva;

import java.util.Comparator;
import java.util.List;

public class RepositorioReserva extends RepositorioEmMemoria<Reserva, String> {

    public RepositorioReserva() {
        super(Reserva::getId);
    }

    @Override
    public void salvar(Reserva reserva) {
        boolean sobrepoe = dados.values().stream()
                .filter(r -> r.getEspaco().getId().equals(reserva.getEspaco().getId()))
                .filter(r -> r.getStatus() != StatusReserva.CANCELADA)
                .filter(r -> !r.getId().equals(reserva.getId()))
                .anyMatch(r -> r.getPeriodo().sobrepoe(reserva.getPeriodo()));

        if (sobrepoe) {
            throw new SobreposicaoReservaException(reserva.getEspaco().getId(), reserva.getPeriodo());
        }
        super.salvar(reserva);
    }

    public List<Reserva> buscarPorOrganizador(String organizadorId) {
        return dados.values().stream()
                .filter(r -> r.getOrganizador().getId().equals(organizadorId))
                .toList();
    }

    public List<Reserva> buscarPorEspacoEStatus(String espacoId, StatusReserva status) {
        return dados.values().stream()
                .filter(r -> r.getEspaco().getId().equals(espacoId))
                .filter(r -> r.getStatus() == status)
                .toList();
    }

    public List<Reserva> listarOrdenadasPorDataInicio() {
        return dados.values().stream()
                .sorted(Comparator.comparing(r -> r.getPeriodo().inicio()))
                .toList();
    }
}