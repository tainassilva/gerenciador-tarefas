package com.gerenciador_tarefas.service;

import com.gerenciador_tarefas.entity.Tarefa;
import com.gerenciador_tarefas.excecoes.NaoPermitirExcluirException;
import com.gerenciador_tarefas.excecoes.NaoPermitoAlterarStatusException;
import com.gerenciador_tarefas.excecoes.TarefaExistenteException;
import com.gerenciador_tarefas.repository.GerenciadorTarefasRepository;
import com.gerenciador_tarefas.request.AtualizarTarefaRequest;
import com.gerenciador_tarefas.request.CadastrarTarefaRequest;
import com.gerenciador_tarefas.status.TarefaStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Recebe a requisição, faz a tarefa e manda para o repository
// Na volta manda a resposta do repository para o controller encerrar
@Service
@Transactional // Trabalhar com o banco de dados
public class GerenciadorTarefasService {

    @Autowired
    private GerenciadorTarefasRepository gerenciadorTarefasRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Espera um request
    public Tarefa salvarTarefa(CadastrarTarefaRequest request) {

        Tarefa tarefaValidacao = gerenciadorTarefasRepository.findByTituloOrDescricao(request.getTitulo(), request.getDescricao());

        // Lançando exceção caso a tarefa tenha o mesmo nome
        if (tarefaValidacao != null) {
            throw new TarefaExistenteException("Já existe uma tarefa com o mesmo titulo ou descrição");
        }

        // Passando os dados da tarefa
        Tarefa tarefa = Tarefa.builder()
                .quantidadeHorasEstimadas(request.getQuantidadeHorasEstimadas())
                .status(TarefaStatusEnum.CRIADA) // Assim que criar a tarefa entra esse status
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .criador(usuarioService.obterUsuarioId(request.getCriadorId()).get())
                .build();

        // Criando o serviço de salvar tarefa
        return this.gerenciadorTarefasRepository.save(tarefa);
    }

    //
    public Page<Tarefa> obtemTarefasPorTitulo(String titulo, Pageable pegeable){
        // Ordernado por data mais recente
       return this.gerenciadorTarefasRepository.findByTituloContainingOrderByDataAtualizacaoDesc(titulo, pegeable);
    }

    public Page<Tarefa> obtemTodasTarefas(Pageable pegeable){
        // Ordernado por data mais recente
        return this.gerenciadorTarefasRepository.findAllByOrderByDataAtualizacaoDesc(pegeable);
    }

    public Tarefa atualizarTarefa(Long id, AtualizarTarefaRequest request){

        Tarefa tarefa = this.gerenciadorTarefasRepository.findById(id).get();

        // Fazendo as validações e podendo passar qualquer mensagem dentro do exception

        // Não pode mudar status de tarefa finalizada
        if (tarefa.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            throw new NaoPermitoAlterarStatusException("Não permitido mover tarefa que está FINALIZADA");
        }

        if (tarefa.getStatus().equals(TarefaStatusEnum.CRIADA) && request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
          throw new NaoPermitoAlterarStatusException("Não permitido mover a tarefa para FINALIZADA se a mesma estiver com status de CRIADA");
        }

        if (tarefa.getStatus().equals(TarefaStatusEnum.BLOQUEADA) && request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
           throw new NaoPermitoAlterarStatusException("Não permitido mover a tarefa para FINALIZADA se a mesma estiver com status de BLOQUEADA");
        }

        // Passando os parâmetros do AtualizarTarefaRequest
        tarefa.setQuantidadeHorasEstimadas(request.getQuantidadeHorasEstimadas());
        tarefa.setStatus(request.getStatus());
        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setResponsavel(usuarioService.obterUsuarioId(request.getResponsavelId()).get());
        tarefa.setQuantidadeHorasRealizada(request.getQuantidadeHorasRealizada());

        this.gerenciadorTarefasRepository.save(tarefa);

        return tarefa;
    }

    public void excluirTarefa(Long id){

        Tarefa tarefa = this.gerenciadorTarefasRepository.findById(id).get();

        // Se a tarefa tiver um status diferente de criada, lança a exceção que não pode excuir
        if (!TarefaStatusEnum.CRIADA.equals(tarefa.getStatus())) {
            throw new NaoPermitirExcluirException();
        }

        this.gerenciadorTarefasRepository.deleteById(id);
    }
}
