package com.gerenciador_tarefas.excecoes;

public class NaoPermitoAlterarStatusException extends RuntimeException {


    public NaoPermitoAlterarStatusException(){
        super();
    }

    // Esse tem o super com mensagem porque pode se alterar o status várias vezes
    public NaoPermitoAlterarStatusException(String mensagem){
        super(mensagem);
    }
}
