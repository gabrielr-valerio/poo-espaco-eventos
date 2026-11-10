package br.edu.fagammon.dominio;

public abstract class RegraNegocioException extends RuntimeException {
    protected RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
