package com.gerenciador_tarefas.service;

import com.gerenciador_tarefas.entity.Usuario;
import com.gerenciador_tarefas.repository.IRoleRepository;
import com.gerenciador_tarefas.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
@Transactional // Indica que os métodos desta classe devem ser executados dentro de uma transação no banco de dados.
public class UsuarioService {

    @Autowired // Injeta automaticamente a implementação do IUsuarioRepository.
    private IUsuarioRepository iUsuarioRepository;

    @Autowired // Injeta automaticamente a implementação do IRoleRepository.
    private IRoleRepository iRoleRepository;

    @Autowired // Injeta automaticamente a implementação do PasswordEncoder.
    private PasswordEncoder passwordEncoder;

    /**
     * Salva um novo usuário no banco de dados.
     * Criptografa a senha antes de salvar e atualiza as roles do usuário.
     * @param usuario O usuário a ser salvo.
     * @return O usuário salvo no banco de dados.
     */
    public Usuario salvarUsuario(Usuario usuario) {
        // Atualiza as roles do usuário no banco de dados ao invés de criar novas.
        usuario.setRoles(usuario.getRoles()
                .stream() // Inicia um fluxo para processar as roles do usuário.
                .map(role -> iRoleRepository.findByNome(role.getNome())) // Mapeia cada role para a role correspondente no banco de dados.
                .toList()); // Coleta o resultado em uma lista.

        // Criptografa a senha antes de salvar.
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Salva o usuário no banco de dados e retorna o usuário salvo.
        return this.iUsuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuário existente no banco de dados.
     * Criptografa a
     * senha antes de atualizar e atualiza as roles do usuário.
     * @param usuario O usuário a ser atualizado.
     * @return O usuário atualizado no banco de dados.
     */
    public Usuario atualizarUsuario(Usuario usuario){
        // Atualiza as roles do usuário no banco de dados ao invés de criar novas.
        usuario.setRoles(usuario.getRoles()
                .stream() // Inicia um fluxo para processar as roles do usuário.
                .map(role -> iRoleRepository.findByNome(role.getNome())) // Mapeia cada role para a role correspondente no banco de dados.
                .toList()); // Coleta o resultado em uma lista.

        // Criptografa a senha antes de atualizar.
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Atualiza o usuário no banco de dados e retorna o usuário atualizado.
        return this.iUsuarioRepository.save(usuario);
    }

    /**
     * Obtém um usuário pelo seu ID.
     * @param usuarioId O ID do usuário a ser obtido.
     * @return Um Optional contendo o usuário, se encontrado.
     */
    public Optional<Usuario> obterUsuarioId(Long usuarioId) {
        // Busca o usuário pelo ID e retorna um Optional contendo o usuário, se encontrado.
        return this.iUsuarioRepository.findById(usuarioId);
    }

    /**
     * Exclui um usuário do banco de dados.
     * @param usuario O usuário a ser excluído.
     */
    public void excluirUsuarioPorId(Long id) {
        Usuario usuario = iUsuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Remover associações de roles
        usuario.getRoles().clear();
        iUsuarioRepository.save(usuario);

        // Agora pode excluir o usuário
        iUsuarioRepository.deleteById(id);

    }

    /**
     * Obtém todos os usuários cadastrados no sistema.
     * @return Uma lista de todos os usuários cadastrados.
     */
    public List<Usuario> obtemUsuarios(){
        // Retorna uma lista de todos os usuários cadastrados.
        return this.iUsuarioRepository.findAll();
    }
}
