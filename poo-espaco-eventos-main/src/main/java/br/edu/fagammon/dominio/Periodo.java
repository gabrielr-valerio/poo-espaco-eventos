package br.edu.fagammon.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

public record Periodo(LocalDateTime inicio, LocalDateTime fim){
    public Periodo {
        Objects.requireNonNull(inicio, "A data/hora de início não pode ser nula.");
        Objects.requireNonNull(fim, "A data/hora de fim não pode ser nula.");

        if(!inicio.isBefore(fim)) {
            throw new PeriodoInvalidoException(inicio, fim);
        }
    }

    public boolean sobrepoe(Periodo outro) {
        return this.inicio.isBefore(outro.fim) && outro.inicio.isBefore(this.fim);
    }
}
