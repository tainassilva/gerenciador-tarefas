package com.gerenciador_tarefas.excecoes;

// Lança exceção quando tentar excluir uma tarefa que não pode ser excluida
public class NaoPermitirExcluirException extends RuntimeException {

    // Sobrescrever o construtor
    public NaoPermitirExcluirException(){
        super();
    }
}
