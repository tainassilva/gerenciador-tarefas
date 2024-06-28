package com.gerenciador_tarefas.repository;
import com.gerenciador_tarefas.entity.Usuario;
// Importa a interface JpaRepository do Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*A classe Optional no Java é uma classe contida no pacote java.util que foi introduzida
no Java 8. É usada para representar um valor que pode estar presente ou ausente (null).
Ela foi projetada para reduzir o uso explícito de null e minimizar os problemas associados
ao uso de null, como NullPointerException. Evita exceções... mas pode-se colocar mensagens como:
Usuário não valido ...
Usando Optional, você pode lidar de forma segura e expressiva com a ausência de valores, evitando
NullPointerException e promovendo um código mais robusto e legível.
 */
import java.util.Optional;

// Anotação que indica que essa interface é um repositório Spring
@Repository

// Declaração de uma interface que estende JpaRepository para a entidade Usuario e o tipo de ID Long
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    // Declaração de um método que encontra um usuário pelo seu nome de usuário (username)
    // Retorna um Optional contendo o usuário encontrado, ou um Optional vazio se não encontrar
    Optional<Usuario> findByUsername(String username);

    /*Se o método Optional<Usuario> findByUsername(String username); não encontrar
     um usuário com o nome de usuário fornecido, ele retornará um Optional vazio.
     A classe Optional é projetada para lidar com a ausência de valores de forma segura
     e expressiva, evitando a necessidade de retornos nulos que podem levar a exceções
     de ponteiro nulo (NullPointerException).
     */


}
