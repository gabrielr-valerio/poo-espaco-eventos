package br.edu.fagammon.dominio;

import java.time.LocalDateTime;
import java.util.Locale;

public class AntecedenciaInsuficienteException extends RegraNegocioException {
    private final LocalDateTime inicio;
    private final long horasMinimas;

    public AntecedenciaInsuficienteException(LocalDateTime inicio, long horasMinimas) {
        super("A reserva exige pelo menos %d horas de antecedência. Início solicitado: %s.".formatted(horasMinimas, inicio));
        this.inicio = inicio;
        this.horasMinimas = horasMinimas;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public long getHorasMinimas() {
        return horasMinimas;
    }
}
