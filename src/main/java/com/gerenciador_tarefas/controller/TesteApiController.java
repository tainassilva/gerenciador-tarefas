package com.gerenciador_tarefas.controller;

// Importa a anotação GetMapping, que é usada para mapear solicitações HTTP GET para métodos específicos.
import org.springframework.web.bind.annotation.GetMapping;

// Importa a anotação RequestParam, que é usada para extrair os parâmetros da solicitação da URL.
import org.springframework.web.bind.annotation.RequestParam;

// Importa a anotação RestController, que é usada para definir uma classe como um controlador RESTful
import org.springframework.web.bind.annotation.RestController;


// Define a classe como um controlador RESTful. Isso significa que cada método dentro desta classe será um endpoint HTTP.
@RestController
public class TesteApiController {

    // Mapeia solicitações HTTP GET para o caminho "/teste-api" para este método.
    @GetMapping("/teste-api")
    // Define um método que retorna uma string "Sucesso" quando a URL "/teste-api" é acessada.
    public String teste() {
        return "Sucesso";
    }

    // Mapeia solicitações HTTP GET para o caminho "/teste-api-bem-vindo" para este método.
    @GetMapping("/teste-api-bem-vindo")
    // Define um método que aceita um parâmetro de solicitação chamado "nome" e o usa para retornar uma mensagem de boas-vindas personalizada.
    public String testeBemVindo(@RequestParam(name = "nome") String nome) {
        return "Olá " + nome + ", Seja muito bem-vindo! ";
    }
}
