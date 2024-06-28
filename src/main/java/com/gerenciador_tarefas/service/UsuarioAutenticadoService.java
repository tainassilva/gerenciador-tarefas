package com.gerenciador_tarefas.service;

import com.gerenciador_tarefas.entity.Usuario;
import com.gerenciador_tarefas.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional // Finaliza com o banco de dados depois que encerrar o método
public class UsuarioAutenticadoService implements UserDetailsService {

    // Lida com o banco de dados
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    // Recuperando usuários
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Usuario usuario = iUsuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário "+ username + " não foi encontrado"));

       // Recuperando as permissões
       List<SimpleGrantedAuthority> roles = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome().toString()))
                .toList();

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
