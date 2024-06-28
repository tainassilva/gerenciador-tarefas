package com.gerenciador_tarefas.entity;

import jakarta.persistence.*;  // Importa as classes e anotações JPA
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity  // Marca a classe como uma entidade JPA
@Table(name = "usuarios")  // Define o nome da tabela no banco de dados
@Data  // Gera automaticamente getters, setters, toString, equals e hashCode
@Getter  // Gera automaticamente getters
@Setter  // Gera automaticamente setters
public class Usuario implements Serializable {  // Implementa Serializable para permitir serialização

    private static final long serialVersionUID = 1L;  // Define um identificador de versão para a serialização

    @Id  // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // Define a estratégia de geração de chave primária(Sequencial)
    private Long id;  // Chave primária da entidade

    @Column(unique = true, length = 50)  // Define a coluna como única e com comprimento máximo de 50 caracteres
    private String username;

    @Column(length = 150)  // Define a coluna com comprimento máximo de 150 caracteres
    // Só tem esse tamanno porque a senha vai ser criptografada
    private String password;


    /**FetchType.LAZY é usado para o relacionamento @ManyToMany com Role. Isso significa que quando um Usuario é carregado,
    * sua lista de roles não será carregada imediatamente, a menos que explicitamente acessada pela aplicação.
    *Reduz consultas desnecessárias e melhorando a eficiência no uso de recursos do banco de dados.
     **/

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // Define um relacionamento muitos-para-muitos
                                                                   // com carregamento LAZY e cascata em todas as operações

    /**
     * Um usuário pode ter várias permissões
     * Também pode conter uma mesma permissão para vários usuários**/

    @JoinTable(name = "usuarios_roles",  // Define a tabela de junção para o relacionamento muitos-para-muitos(Permissão dos usuários)
            joinColumns = @JoinColumn(name = "usuario_id"),  // Define a chave estrangeira que referencia a tabela `usuarios`
            inverseJoinColumns = @JoinColumn(name = "role_id"),  // Define a chave estrangeira que referencia a tabela `roles`
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "role_id"}))  // Garante que a combinação de `usuario_id` e `role_id`
                                                                                            // seja única na tabela de junção

    private List<Role> roles;  // Lista de permissões(roles) associadas ao usuário

}
