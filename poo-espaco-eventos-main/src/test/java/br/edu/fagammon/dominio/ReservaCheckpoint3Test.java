package br.edu.fagammon.dominio;

import br.edu.fagammon.dominio.factory.ReservaFactory;
import br.edu.fagammon.dominio.preco.CalculoPrecoPorHora;
import br.edu.fagammon.persistencia.ServicoPersistenciaJson;
import br.edu.fagammon.relatorio.GeradorRelatorioEspacos;
import br.edu.fagammon.repositorio.RepositorioReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaCheckpoint3Test {

    private Participante organizador;
    private EspacoEvento espaco;
    private Periodo periodo;
    private LocalDateTime agora;

    @BeforeEach
    void setUp() {
        agora = LocalDateTime.of(2026, 11, 10, 14, 0);
        organizador = new Participante("P10", "Carlos Silva", "carlos@email.com");
        espaco = new EspacoEvento("E10", "Salão Nobre", 200, true);
        periodo = new Periodo(agora.plusDays(10), agora.plusDays(10).plusHours(5));
    }

    @Test
    @DisplayName("Deve criar reserva através do padrão Factory")
    void deveCriarReservaViaFactory() {
        Reserva reserva = ReservaFactory.criarReservaPadrao("RF1", organizador, espaco, periodo, 150);
        assertNotNull(reserva);
        assertEquals("RF1", reserva.getId());
    }

    @Test
    @DisplayName("Deve salvar e restaurar reservas via JSON preservando Invariante 8")
    void deveSalvarERestaurarJson(@TempDir Path tempDir) throws IOException {
        Path arquivoJson = tempDir.resolve("reservas_teste.json");
        RepositorioReserva repoOriginal = new RepositorioReserva();
        Reserva r1 = ReservaFactory.criarReservaComDataSolicitacao("R100", organizador, espaco, periodo, 100, null);
        repoOriginal.salvar(r1);

        ServicoPersistenciaJson persistencia = new ServicoPersistenciaJson();
        persistencia.salvar(repoOriginal, arquivoJson);

        assertTrue(Files.exists(arquivoJson));

        RepositorioReserva repoRestaurado = new RepositorioReserva();
        persistencia.restaurar(repoRestaurado, arquivoJson);

        assertEquals(1, repoRestaurado.listarTodos().size());
        Reserva rRestaurada = repoRestaurado.buscarPorId("R100").orElseThrow();
        assertEquals("Salão Nobre", rRestaurada.getEspaco().getNome());
    }

    @Test
    @DisplayName("Deve rejeitar restauração JSON caso haja violação de invariante (ex: capacidade)")
    void deveRejeitarJsonInvalidoComInvarianteViolada(@TempDir Path tempDir) throws IOException {
        Path arquivoJson = tempDir.resolve("json_corrompido.json");

        String jsonInvalido = """
        [ {
          "id": "R999",
          "organizadorId": "P10",
          "organizadorNome": "Carlos Silva",
          "organizadorEmail": "carlos@email.com",
          "espacoId": "E10",
          "espacoNome": "Salão Nobre",
          "espacoCapacidade": 200,
          "espacoAtivo": true,
          "inicioPeriodo": "2026-11-20T14:00:00",
          "fimPeriodo": "2026-11-20T19:00:00",
          "quantidadeConvidados": 300
        } ]
        """;

        Files.writeString(arquivoJson, jsonInvalido);

        ServicoPersistenciaJson persistencia = new ServicoPersistenciaJson();
        RepositorioReserva repo = new RepositorioReserva();

        assertThrows(CapacidadeExcedidaException.class, () -> persistencia.restaurar(repo, arquivoJson));
    }

    @Test
    @DisplayName("Deve gerar relatório síntese com estatísticas e receitas válidas")
    void deveGerarRelatorioSintese() {
        RepositorioReserva repo = new RepositorioReserva();
        Reserva r1 = ReservaFactory.criarReservaComDataSolicitacao("R1", organizador, espaco, periodo, 50, null);
        r1.confirmar();
        repo.salvar(r1);

        GeradorRelatorioEspacos gerador = new GeradorRelatorioEspacos();
        String relatorio = gerador.gerarSintese(repo, new CalculoPrecoPorHora(new BigDecimal("100.00")));

        assertTrue(relatorio.contains("Total de Reservas Cadastradas: 1"));
        assertTrue(relatorio.contains("Confirmadas: 1"));
        assertTrue(relatorio.contains("Receita Bruta Estimada (Ativas): R$ 500.00"));
    }
}