package br.edu.fagammon.dominio;

public class CapacidadeExcedidaException extends RegraNegocioException {
    private final int capacidadeMaxima;
    private final int solicitada;

    public CapacidadeExcedidaException(int capacidadeMaxima, int solicitada) {
        super("Quantidade de convidados (%d) excede a capacidade máxima do espaço (%d).".formatted(solicitada, capacidadeMaxima));
        this.capacidadeMaxima = capacidadeMaxima;
        this.solicitada = solicitada;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    public int getSolicitada() {
        return solicitada;
    }
}
