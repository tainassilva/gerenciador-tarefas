package com.gerenciador_tarefas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gerenciador_tarefas.permissoes.PermissaoEnum;
import jakarta.persistence.*;  // Importa as classes e anotações JPA
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;  // Importa a interface Serializable
import java.util.List;  // Importa a interface List

@Entity  // Marca a classe como uma entidade JPA
@Table(name="roles")  // Define o nome da tabela no banco de dados
@Data  // Gera automaticamente getters, setters, toString, equals e hashCode
@Getter  // Gera automaticamente getters
@Setter  // Gera automaticamente setters
public class Role implements Serializable {

    @Id  // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // Define a estratégia de geração de chave primária (Sequencial)
    private Long id;  // Chave primária da entidade

    @Column  // Mapeia o campo para uma coluna na tabela
    @Enumerated(EnumType.STRING)  // Define que o campo é uma enumeração e será armazenado como uma string no banco de dados
    // Mapeando qual vai ser as permissões enumeradas
    private PermissaoEnum nome;

    @ManyToMany(mappedBy = "roles")  // Define um relacionamento muitos-para-muitos com a entidade Usuario,
    // onde "roles" é o nome do campo na classe Usuario que faz o mapeamento inverso

    // Isso poderia virar um loop infinito.. ai morre na notação
    @JsonBackReference  // Anotação do Jackson para evitar loop infinito na serialização JSON
    // Tem que fazer isso quando é ManyToMany
    // Isso poderia virar um loop infinito.. ai morre na notação
    private List<Usuario> usuarios;  // Lista de usuários associados a esta permissão (role)
}
