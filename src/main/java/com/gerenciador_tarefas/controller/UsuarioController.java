package com.gerenciador_tarefas.controller; // Define o pacote onde esta classe está localizada.

import com.gerenciador_tarefas.entity.Usuario;
import com.gerenciador_tarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta classe é um controlador REST do Spring.
@RequestMapping("/usuarios") // Define o mapeamento base para todas as solicitações deste controlador.
public class UsuarioController {

    @Autowired // Injeta automaticamente a instância do UsuarioService.
    private UsuarioService usuarioService;

    /**
     * Endpoint para salvar um novo usuário.
     * @param usuario O usuário a ser salvo.
     * @return Uma ResponseEntity contendo uma mensagem e o status HTTP CREATED.
     */
    @PostMapping // Mapeia solicitações HTTP POST para este método.
    public ResponseEntity<String> salvarUsuario(@RequestBody Usuario usuario) {
        // Salva o usuário utilizando o serviço e armazena o usuário salvo.
        Usuario usuarioSalvo  = usuarioService.salvarUsuario(usuario);
        // Retorna uma ResponseEntity com uma mensagem de sucesso e status HTTP CREATED.
        return new ResponseEntity<>("Novo usuário criado: " + usuarioSalvo.getUsername(), HttpStatus.CREATED);//201
    }

    /**
     * Endpoint para atualizar um usuário existente.
     * @param usuario O usuário a ser atualizado.
     * @return Uma ResponseEntity contendo uma mensagem e o status HTTP OK.
     */
    @PutMapping // Mapeia solicitações HTTP PUT para este método.
    public ResponseEntity<String> atualizarUsuario(@RequestBody Usuario usuario){
        // Atualiza o usuário utilizando o serviço e armazena o usuário atualizado.
        Usuario usuarioSalvo  = usuarioService.salvarUsuario(usuario);
        // Retorna uma ResponseEntity com uma mensagem de sucesso e status HTTP OK.
        return new ResponseEntity<>("Usuário atualizado " + usuarioSalvo.getUsername(), HttpStatus.OK);//200
    }

    /**
     * Endpoint para obter todos os usuários.
     * @return Uma ResponseEntity contendo a lista de usuários e o status HTTP OK.
     */
    @GetMapping // Mapeia solicitações HTTP GET para este método.
    public ResponseEntity<List<Usuario>> obtemUsuarios(){
        // Retorna uma ResponseEntity com a lista de usuários e status HTTP OK.
        return new ResponseEntity<>(usuarioService.obtemUsuarios(), HttpStatus.OK);//200
    }
    /**
     * Endpoint para excluir um usuário.
     * @param usuario O usuário a ser excluído.
     */
    @DeleteMapping
    public void excluirUsuario(@RequestBody Usuario usuario) {
        usuarioService.excluirUsuarioPorId(usuario.getId());

    }

}
