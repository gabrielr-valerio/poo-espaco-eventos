package br.edu.fagammon.dominio;

import java.util.Objects;

public class Participante {
    private final String id;
    private final String nome;
    private final String email;

    public Participante(String id, String nome, String email) {
        this.id = Objects.requireNonNull(id, "ID do participante não pode ser nulo.");
        this.nome = Objects.requireNonNull(nome, "Nome do participante não pode ser nulo.");
        this.email = Objects.requireNonNull(email, "E-mail do participante não pode ser nulo.");
    }

    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
}