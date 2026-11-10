package br.edu.fagammon.persistencia;

import br.edu.fagammon.dominio.Reserva;
import br.edu.fagammon.repositorio.RepositorioReserva;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ServicoPersistenciaJson {
    private final ObjectMapper mapper;

    public ServicoPersistenciaJson() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void salvar(RepositorioReserva repositorio, Path caminhoArquivo) throws IOException {
        List<ReservaDTO> dtos = repositorio.listarTodos().stream()
                .map(ReservaDTO::deEntidade)
                .toList();
        mapper.writeValue(caminhoArquivo.toFile(), dtos);
    }

    public void restaurar(RepositorioReserva repositorioDestino, Path caminhoArquivo) throws IOException {
        File file = caminhoArquivo.toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException("Arquivo de persistência não encontrado: " + caminhoArquivo);
        }

        List<ReservaDTO> dtos = mapper.readValue(file, new TypeReference<List<ReservaDTO>>() {});
        for (ReservaDTO dto : dtos) {
            Reserva reserva = dto.paraEntidade();
            repositorioDestino.salvar(reserva);
        }
    }
}