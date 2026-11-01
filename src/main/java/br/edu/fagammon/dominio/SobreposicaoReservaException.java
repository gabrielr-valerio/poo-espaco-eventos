package br.edu.fagammon.dominio;

public class SobreposicaoReservaException extends RegraNegocioException {
    private final String espacoId;
    private final Periodo periodo;

    public SobreposicaoReservaException(String espacoId, Periodo periodo) {
        super(String.format("O espaço %s já possui reserva ativa para o período de %s a %s.",
                espacoId, periodo.inicio(), periodo.fim()));
        this.espacoId = espacoId;
        this.periodo = periodo;
    }

    public String getEspacoId() {
        return espacoId;
    }

    public Periodo getPeriodo() {
        return periodo;
    }
}