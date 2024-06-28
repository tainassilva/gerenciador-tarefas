package com.gerenciador_tarefas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ObterTarefasPaginadaResponse {

    private int paginaAtual;
    private Long totalItens;
    private int totalPaginas;
    // Retorna as tarefas
    private List<ObterTarefasResponse> tarefas;
}
