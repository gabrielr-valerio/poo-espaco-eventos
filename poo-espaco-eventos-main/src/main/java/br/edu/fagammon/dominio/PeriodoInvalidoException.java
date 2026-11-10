package br.edu.fagammon.dominio;

import java.time.LocalDateTime;

public class PeriodoInvalidoException extends RegraNegocioException {
    private final LocalDateTime inicio;
    private final LocalDateTime fim;

    public PeriodoInvalidoException(LocalDateTime inicio, LocalDateTime fim) {
        super("O início do período (%s) deve ser anterior ao fim (%s).".formatted(inicio, fim));
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }
    public LocalDateTime getFim(){
        return fim;
    }
}
