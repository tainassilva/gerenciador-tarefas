package com.gerenciador_tarefas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String status; // 400 - BAD REQUEST
    // Quais vão ser os erros
    private List<Map<String, String>> errors; // campo que está com erro // descricão do erro
}
