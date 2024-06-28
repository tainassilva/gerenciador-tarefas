package com.gerenciador_tarefas.repository;

import com.gerenciador_tarefas.entity.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar operações relacionadas às Tarefas.
 * Utiliza o Spring Data JPA para acesso ao banco de dados.
 */
@Repository
public interface GerenciadorTarefasRepository extends JpaRepository<Tarefa, Long> {

    /**
     * Encontra uma Tarefa pelo título ou descrição.
     * @param titulo Título da Tarefa a ser encontrada.
     * @param descricao Descrição da Tarefa a ser encontrada.
     * @return A Tarefa encontrada, se existir.
     */
    Tarefa findByTituloOrDescricao(String titulo, String descricao);

    /**
     * Encontra todas as Tarefas cujo título contenha a string especificada,
     * ordenadas pela data de atualização em ordem decrescente.
     * Suporta paginação dos resultados.
     * @param titulo String contida no título das Tarefas a serem encontradas.
     * @param pageable Objeto de paginação que define a página e tamanho dos resultados.
     * @return Página de Tarefas encontradas.
     */
    Page<Tarefa> findByTituloContainingOrderByDataAtualizacaoDesc(String titulo, Pageable pageable);

    /**
     * Encontra todas as Tarefas no sistema,
     * ordenadas pela data de atualização em ordem decrescente.
     * Suporta paginação dos resultados.
     * @param pageable Objeto de paginação que define a página e tamanho dos resultados.
     * @return Página de Tarefas encontradas.
     */
    Page<Tarefa> findAllByOrderByDataAtualizacaoDesc(Pageable pageable);
}
