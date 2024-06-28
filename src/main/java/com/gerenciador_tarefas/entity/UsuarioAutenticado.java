/**Em resumo, a classe UsuarioAutenticado serve como um contêiner simples
 * para armazenar informações de autenticação de usuário e facilitar sua manipulação
 * e transmissão dentro de uma aplicação ou entre serviços. Ela é parte essencial do
 * processo de autenticação e pode ser adaptada conforme as necessidades específicas
 * de cada sistema.
 **/

package com.gerenciador_tarefas.entity;
import lombok.Getter;
import lombok.Setter;

@Getter  // Gera automaticamente getters para todos os campos da classe
@Setter  // Gera automaticamente setters para todos os campos da classe
public class UsuarioAutenticado {

    // No postman tem que ser igual senão vai dar falha
    private String username;

    private String password;
}
