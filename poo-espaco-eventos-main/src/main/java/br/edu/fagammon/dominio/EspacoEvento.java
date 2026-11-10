package br.edu.fagammon.dominio;

public class EspacoEvento {
    private final String id;
    private final String nome;
    private final int capacidadeMaxima;
    private boolean ativo;

    public EspacoEvento(String id, String nome, int capacidadeMaxima) {
        this(id, nome, capacidadeMaxima, true);
    }

    public EspacoEvento(String id, String nome, int capacidadeMaxima, boolean ativo) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do espaço é obrigatório.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do espaço é obrigatório.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima deve ser maior que zero.");
        }
        this.id = id;
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ativo = ativo;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public boolean isAtivo() { return ativo; }

    public void ativar() { this.ativo = true; }
    public void desativar() { this.ativo = false; }
}