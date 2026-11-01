package br.edu.fagammon.dominio;

public class EstadoReservaInvalidoException extends RegraNegocioException {
    private final StatusReserva statusAtual;
    private final String acaoTentada;

    public EstadoReservaInvalidoException(StatusReserva statusAtual, String acaoTentada) {
        super(String.format("Não é possível %s uma reserva com status %s.", acaoTentada, statusAtual));
        this.statusAtual = statusAtual;
        this.acaoTentada = acaoTentada;
    }

    public StatusReserva getStatusAtual() {
        return statusAtual;
    }

    public String getAcaoTentada() {
        return acaoTentada;
    }
}