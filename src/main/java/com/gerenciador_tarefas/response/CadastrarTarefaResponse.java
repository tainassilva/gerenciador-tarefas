package com.gerenciador_tarefas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
// Para não retornar informações do usuário
public class CadastrarTarefaResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private String criador;
}
