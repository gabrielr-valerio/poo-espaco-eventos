package br.edu.fagammon.dominio;

public class EspacoInativoException extends RegraNegocioException {
    private final String espacoId;

    public EspacoInativoException(String espacoId) {
        super("Não é possível reservar o espaço '%s' pois ele se encontra inativo.".formatted(espacoId));
        this.espacoId = espacoId;
    }

    public String getEspacoId() {
        return espacoId;
    }
}
