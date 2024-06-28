package com.gerenciador_tarefas.entity;

import com.gerenciador_tarefas.status.TarefaStatusEnum;
import jakarta.persistence.*;  // Importa as classes e anotações JPA
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalTime;

// Após a criação do Controller, Service e Repository
// Mapear para o banco de dados
@Entity  // Marca a classe como uma entidade JPA
@Table(name = "tarefas")  // Define o nome da tabela no banco de dados
@Data  // Gera automaticamente getters, setters, toString, equals e hashCode
@Getter  // Gera automaticamente getters
@Setter  // Gera automaticamente setters
@Builder  // Gera um padrão de construção de objetos com o padrão do Lombok(Não funcionou tão bem)
public class Tarefa implements Serializable {

   private static final long serialVersionUID = 1L;  // Define um identificador de versão para a serialização

   // Construtor padrão
   public Tarefa() {
   }

   // Construtor com parâmetros para inicializar todos os campos da classe
   public Tarefa(Long id, String titulo, String descricao, TarefaStatusEnum status, Usuario responsavel, Usuario criador,
                 int quantidadeHorasEstimadas, Integer quantidadeHorasRealizada, LocalTime dataCadasto, LocalTime dataAtualizacao) {
      this.id = id;
      this.titulo = titulo;
      this.descricao = descricao;
      this.status = status;
      this.responsavel = responsavel;
      this.criador = criador;
      this.quantidadeHorasEstimadas = quantidadeHorasEstimadas;
      this.quantidadeHorasRealizada = quantidadeHorasRealizada;
      this.dataCadasto = dataCadasto;
      this.dataAtualizacao = dataAtualizacao;
   }

   @Id  // Marca o campo como chave primária
   @GeneratedValue(strategy = GenerationType.SEQUENCE)  // Define a estratégia de geração de chave primária (Sequencial)
   private Long id;  // Chave primária da entidade

   @Column(nullable = false)
   private String titulo;

   @Column(nullable = false)
   private String descricao;

   @Column(nullable = false)
   // Enum para o status da tarefa
   @Enumerated(EnumType.STRING)  // Define que o campo é uma enumeração e será armazenado como uma string no banco de dados
   private TarefaStatusEnum status;

   @Column
   private Usuario responsavel;

   @Column(nullable = false)
   private Usuario criador;

   @Column(nullable = false)
   private int quantidadeHorasEstimadas;  // Quantidade estimada de horas para concluir a tarefa

   @Column
   private Integer quantidadeHorasRealizada;  // Quantidade de horas realizadas na tarefa

   @Column
   @CreationTimestamp  // Anotação do Hibernate para gerar automaticamente a data de cadastro da tarefa
   private LocalTime dataCadasto;  // Data de cadastro da tarefa

   @Column
   @UpdateTimestamp  // Anotação do Hibernate para gerar automaticamente a data de atualização da tarefa
   private LocalTime dataAtualizacao;

}
