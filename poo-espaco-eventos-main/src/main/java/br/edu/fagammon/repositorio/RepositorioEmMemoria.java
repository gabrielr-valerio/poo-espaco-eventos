package br.edu.fagammon.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class RepositorioEmMemoria<T, ID> implements Repositorio<T, ID> {
    protected final Map<ID, T> dados = new ConcurrentHashMap<>();
    private final Function<T, ID> idExtractor;

    public RepositorioEmMemoria(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void salvar(T entidade) {
        ID id = idExtractor.apply(entidade);
        dados.put(id, entidade);
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(ID id) {
        dados.remove(id);
    }
}