package com.gerenciador_tarefas.excecoes;

public class TarefaExistenteException extends RuntimeException {

    public TarefaExistenteException(){
        super();
    }

    public TarefaExistenteException(String mensagem){
        super(mensagem);
    }
}