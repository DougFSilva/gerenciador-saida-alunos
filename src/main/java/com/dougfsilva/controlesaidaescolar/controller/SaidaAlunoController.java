package com.dougfsilva.controlesaidaescolar.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.controlesaidaescolar.dto.CancelamentoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.dto.ConfirmacaoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.dto.SaidaAlunoResponse;
import com.dougfsilva.controlesaidaescolar.dto.SolicitacaoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.service.saidaaluno.BuscaSaidaAlunoService;
import com.dougfsilva.controlesaidaescolar.service.saidaaluno.CancelaSaidaService;
import com.dougfsilva.controlesaidaescolar.service.saidaaluno.ConfirmaSaidaService;
import com.dougfsilva.controlesaidaescolar.service.saidaaluno.DeletaSaidaAlunoService;
import com.dougfsilva.controlesaidaescolar.service.saidaaluno.SolicitaSaidaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saidas")
@RequiredArgsConstructor
@Tag(name = "Saídas", description = "Endpoints para gerenciamento do fluxo de saída dos alunos")
public class SaidaAlunoController {

	private final SolicitaSaidaService solicitaSaidaService;
	private final ConfirmaSaidaService confirmaSaidaService;
	private final CancelaSaidaService cancelaSaidaService;
	private final DeletaSaidaAlunoService deletaSaidaAlunoService;
	private final BuscaSaidaAlunoService buscaSaidaAlunoService;

	@PostMapping
	@Operation(summary = "Solicita saída", description = "Registra uma nova solicitação de saída para um aluno")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Saída solicitada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados de solicitação inválidos") })
	public ResponseEntity<SaidaAlunoResponse> solicitar(@Valid @RequestBody SolicitacaoSaidaAlunoForm form) {
		SaidaAluno saidaSolicitada = solicitaSaidaService.solicitar(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saidaSolicitada.getId())
				.toUri();
		return ResponseEntity.created(uri).body(SaidaAlunoResponse.toDto(saidaSolicitada));
	}

	@PatchMapping("/confirmar-saida")
	@Operation(summary = "Confirma saída", description = "Altera o status da saída para confirmado mediante validação")
	@ApiResponse(responseCode = "200", description = "Saída confirmada com sucesso")
	public ResponseEntity<SaidaAlunoResponse> confirmar(@Valid @RequestBody ConfirmacaoSaidaAlunoForm form) {
		SaidaAluno saidaConfirmada = confirmaSaidaService.confirmar(form);
		return ResponseEntity.ok().body(SaidaAlunoResponse.toDto(saidaConfirmada));
	}

	@PatchMapping("/cancelar-saida")
	@Operation(summary = "Cancela saída", description = "Registra o cancelamento de uma solicitação de saída ativa")
	@ApiResponse(responseCode = "200", description = "Saída cancelada com sucesso")
	public ResponseEntity<SaidaAlunoResponse> cancelar(@Valid @RequestBody CancelamentoSaidaAlunoForm form) {
		SaidaAluno saidaCancelada = cancelaSaidaService.cancelar(form);
		return ResponseEntity.ok().body(SaidaAlunoResponse.toDto(saidaCancelada));
	}

	@DeleteMapping("/deletar-saidas/aluno/{alunoId}")
	@Operation(summary = "Remove histórico", description = "Exclui permanentemente todos os registros de saída de um aluno específico, considerando a configuração de tempo de retenção")
	@ApiResponse(responseCode = "204", description = "Histórico removido com sucesso")
	public ResponseEntity<Void> deletar(@PathVariable Long alunoId) {
		deletaSaidaAlunoService.deletarTodasSaidas(alunoId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca por ID", description = "Retorna os detalhes de um registro de saída específico")
	@ApiResponse(responseCode = "200", description = "Registro encontrado")
	public ResponseEntity<SaidaAlunoResponse> buscarPeloId(@PathVariable Long id) {
		SaidaAluno saida = buscaSaidaAlunoService.buscarPeloId(id);
		return ResponseEntity.ok().body(SaidaAlunoResponse.toDto(saida));
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Lista por status", description = "Retorna registros de saída filtrados pelo status (SOLICITADO, CONFIRMADO, etc)")
	public ResponseEntity<Page<SaidaAlunoResponse>> buscarPeloStatus(@PathVariable StatusSaida status,
			Pageable paginacao) {
		Page<SaidaAluno> saidas = buscaSaidaAlunoService.buscarPeloStatus(status, paginacao);
		return ResponseEntity.ok().body(saidas.map(SaidaAlunoResponse::toDto));
	}

	@GetMapping("/aluno/{alunoId}")
	@Operation(summary = "Lista por aluno", description = "Retorna o histórico de saídas de um aluno específico")
	public ResponseEntity<Page<SaidaAlunoResponse>> buscarPeloAluno(@PathVariable Long alunoId, Pageable paginacao) {
		Page<SaidaAluno> saidas = buscaSaidaAlunoService.buscarPeloAluno(alunoId, paginacao);
		return ResponseEntity.ok().body(saidas.map(SaidaAlunoResponse::toDto));
	}

	@GetMapping
	@Operation(summary = "Listar todas", description = "Retorna todos os registros de saída com paginação")
	public ResponseEntity<Page<SaidaAlunoResponse>> buscarTodas(Pageable paginacao) {
		Page<SaidaAluno> saidas = buscaSaidaAlunoService.buscarTodas(paginacao);
		return ResponseEntity.ok().body(saidas.map(SaidaAlunoResponse::toDto));
	}

}
