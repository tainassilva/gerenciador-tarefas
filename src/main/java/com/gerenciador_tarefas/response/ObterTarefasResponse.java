package com.gerenciador_tarefas.response;

import com.gerenciador_tarefas.status.TarefaStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ObterTarefasResponse {

    // Tem as mesmas tarefas que cadastrar tarefa ms não mostra informações sensíveis para o usuário
    // Serve para o get
    private Long id;
    private String titulo;
    private String descricao;
    private TarefaStatusEnum status;
    private String responsavel;
    private String criador;
    private int quantidadeHorasEstimadas;
    private Integer quantidadeHorasRealizada;
    private LocalTime dataCadasto;
    private LocalTime dataAtualizacao;
}
