package com.gerenciador_tarefas.controller;

import com.gerenciador_tarefas.entity.Tarefa;
import com.gerenciador_tarefas.request.AtualizarTarefaRequest;
import com.gerenciador_tarefas.request.CadastrarTarefaRequest;
import com.gerenciador_tarefas.response.AtualizarTarefaResponse;
import com.gerenciador_tarefas.response.CadastrarTarefaResponse;
import com.gerenciador_tarefas.response.ObterTarefasPaginadaResponse;
import com.gerenciador_tarefas.response.ObterTarefasResponse;
import com.gerenciador_tarefas.service.GerenciadorTarefasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Vai mandar a requisição para o service
// Recebe do service na volta do banco de dados e manda a resposta da requisição
// de acordo com a resposta do banco de dados
@RestController
@RequestMapping("/gerenciador-tarefas")
public class GerenciadorTarefasController {

    @Autowired
    private GerenciadorTarefasService gerenciadorTarefasService;

    @PostMapping
    // Esperando uma requisição
    public ResponseEntity<CadastrarTarefaResponse> salvarTarefa(@Valid @RequestBody CadastrarTarefaRequest request) {
        // O @valid é pra entrar as validações feita na classe CadastrarTarefaRequest
        Tarefa tarefaSalva  = gerenciadorTarefasService.salvarTarefa(request);

        // Passar apenas as informações necessárias sem violar a privacidade do usuário
        // Não vai ser mais da entidade tarefa que mostra até senha do usuário
        CadastrarTarefaResponse response = CadastrarTarefaResponse
                .builder()
                .id(tarefaSalva.getId())
                .titulo(tarefaSalva.getTitulo())
                .descricao(tarefaSalva.getDescricao())
                .criador(tarefaSalva.getCriador().getUsername())
                .build();

        // Retorna o HTTP Status de criado (201)
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObterTarefasPaginadaResponse> obterTarefas(
            @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            // Geralmente é paginada por 20 ou 30
            @RequestParam(defaultValue = "3") int size){


       Page<Tarefa> tarefasPaginada = null;

       if ( titulo == null ) {
           //Pegar todas as páginas sem filtrar pelo título, sem parâmetros
           tarefasPaginada = this.gerenciadorTarefasService.obtemTodasTarefas(PageRequest.of(page, size));
       } else {
           tarefasPaginada = this.gerenciadorTarefasService.obtemTarefasPorTitulo(titulo, PageRequest.of(page, size));
       }

        List<ObterTarefasResponse> tarefas = tarefasPaginada
                .getContent()
                .stream()
                .map(tarefa -> {
                   return ObterTarefasResponse
                            .builder()
                            .id(tarefa.getId())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                           // Se o responsável estiver sem nome, entra como não atribuida
                            .responsavel(tarefa.getResponsavel() != null ?tarefa.getResponsavel().getUsername() : "NAO_ATRIBUIDA")
                            .criador(tarefa.getCriador().getUsername())
                            .status(tarefa.getStatus())
                            .quantidadeHorasEstimadas(tarefa.getQuantidadeHorasEstimadas())
                            .quantidadeHorasRealizada(tarefa.getQuantidadeHorasRealizada())
                            .dataCadasto(tarefa.getDataCadasto())
                            .dataAtualizacao(tarefa.getDataAtualizacao())
                            .build();
                })
                .toList();

       // Mostrando as páginas
        ObterTarefasPaginadaResponse response = ObterTarefasPaginadaResponse
                .builder()
                .paginaAtual(tarefasPaginada.getNumber()) // Página atual
                .totalPaginas(tarefasPaginada.getTotalPages()) // Total de páginas
                .totalItens(tarefasPaginada.getTotalElements())
                .tarefas(tarefas)
                .build();

        // Response e o status de ok
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Passando o id como parâmetro antes de atualizar
    @PutMapping(value = "/{id}")
    public ResponseEntity<AtualizarTarefaResponse> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody AtualizarTarefaRequest request) {
    // O @valid é pra entrar as validações feita na classe AtualizarTarefaRequest
        Tarefa tarefaAtualizada = gerenciadorTarefasService.atualizarTarefa(id, request);

        // O que vai retornar após atualizar tarefa
        AtualizarTarefaResponse response = AtualizarTarefaResponse
                .builder()
                .id(tarefaAtualizada.getId())
                .titulo(tarefaAtualizada.getTitulo())
                .descricao(tarefaAtualizada.getDescricao())
                .criador(tarefaAtualizada.getCriador().getUsername())
                .quantidadeHorasEstimadas(tarefaAtualizada.getQuantidadeHorasEstimadas())
                .status(tarefaAtualizada.getStatus().toString())
                .responsavel(tarefaAtualizada.getResponsavel().getUsername())
                // Se for diferente de nulo, pega a quantidade de horas realizadas, se não, vai ser nulo
                .quantidadeHorasRealizada(tarefaAtualizada.getQuantidadeHorasRealizada() != null ? tarefaAtualizada.getQuantidadeHorasRealizada() : null)
                .build();

        // OK para tarefa alterada
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Passar o id como parâmetro antes de excluir
    @DeleteMapping(value = "/{id}")
    public void excluirTarefa(@PathVariable Long id){

        //Chama o service para excluir a tarefa
        gerenciadorTarefasService.excluirTarefa(id);
    }
}
