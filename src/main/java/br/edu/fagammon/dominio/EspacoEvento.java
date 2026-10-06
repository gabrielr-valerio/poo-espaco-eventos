package br.edu.fagammon.dominio;

public class EspacoEvento {
    private final String id;
    private final String nome;
    private final int capacidadeMaxima;
    private boolean ativo;

    public EspacoEvento(String id, String nome, int capacidadeMaxima) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID inválido.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido.");
        if (capacidadeMaxima <= 0) throw new IllegalArgumentException("Capacidade deve ser maior que zero.");

        this.id = id;
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
    public void ativar() {
        this.ativo = true;
    }

    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    public boolean isAtivo() {
        return ativo;
    }
}