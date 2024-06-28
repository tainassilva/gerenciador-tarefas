package com.gerenciador_tarefas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AtualizarTarefaResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private String criador;
    private int quantidadeHorasEstimadas;
    private String status;
    private String responsavel;
    // Tem que ser integer porque automaticamente pega o valor zero ao invez de nulo
    // Quando for para pegar valores nulos, usa Integer
    private Integer quantidadeHorasRealizada;
}
