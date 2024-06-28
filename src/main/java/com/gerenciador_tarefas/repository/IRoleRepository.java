package com.gerenciador_tarefas.repository;
import com.gerenciador_tarefas.entity.Role;
import com.gerenciador_tarefas.permissoes.PermissaoEnum;
// Importa a interface JpaRepository do Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;

// Importa a anotação Repository do Spring Framework
import org.springframework.stereotype.Repository;

// Anotação que indica que essa interface é um repositório Spring
@Repository

// Declaração de uma interface que estende JpaRepository para a entidade Role e o tipo de ID Long
public interface IRoleRepository extends JpaRepository<Role, Long> {

    // Declaração de um método que encontra uma entidade Role com base no nome (que é do tipo PermissaoEnum)
    // O Spring Data JPA automaticamente gera a implementação desse método
    Role findByNome(PermissaoEnum nome);

    /*
    * A classe repository evita que coloque um monte de operações do banco de dados
    * O spring faz isso automaticamente com a classe repository*/
}
