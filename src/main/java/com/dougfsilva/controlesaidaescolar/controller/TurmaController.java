package com.dougfsilva.controlesaidaescolar.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.controlesaidaescolar.dto.TurmaForm;
import com.dougfsilva.controlesaidaescolar.dto.TurmaUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.service.turma.BuscaTurmaService;
import com.dougfsilva.controlesaidaescolar.service.turma.CriaTurmaService;
import com.dougfsilva.controlesaidaescolar.service.turma.DeletaTurmaService;
import com.dougfsilva.controlesaidaescolar.service.turma.EditaTurmaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Endpoints para gerenciamento de turmas escolares")
public class TurmaController {

	private final CriaTurmaService criaTurmaService;
	private final DeletaTurmaService deletaTurmaService;
	private final EditaTurmaService editaTurmaService;
	private final BuscaTurmaService buscaTurmaService;

	@PostMapping
	@Operation(summary = "Cadastra uma nova turma", description = "Cria uma turma e retorna o objeto criado com sua URI")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Turma criada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados de formulário inválidos")
		})
	public ResponseEntity<Turma> criar(@Valid @RequestBody TurmaForm form) {
		Turma turma = criaTurmaService.criar(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(turma.getId()).toUri();
		return ResponseEntity.created(uri).body(turma);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui uma turma", description = "Remove permanentemente uma turma pelo ID")
	@ApiResponse(responseCode = "204", description = "Turma deletada com sucesso")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		deletaTurmaService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza uma turma", description = "Atualiza os dados de uma turma existente")
	@ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso")
	public ResponseEntity<Turma> editar(@PathVariable Long id, @Valid @RequestBody TurmaUpdateForm form) {
		Turma turma = editaTurmaService.editar(id, form);
		return ResponseEntity.ok().body(turma);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca por ID", description = "Retorna os detalhes de uma única turma")
	@ApiResponse(responseCode = "200", description = "Turma encontrada")
	public ResponseEntity<Turma> buscarPeloId(@PathVariable Long id) {
		Turma turma = buscaTurmaService.buscarPeloId(id);
		return ResponseEntity.ok().body(turma);
	}

	@GetMapping("/nome")
	@Operation(summary = "Busca por nome", description = "Lista turmas filtradas pelo nome com paginação")
	public ResponseEntity<Page<Turma>> buscarPeloNome(@RequestParam String nome, Pageable paginacao) {
		Page<Turma> turmas = buscaTurmaService.buscarPeloNome(nome, paginacao);
		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping("/ano-letivo")
	@Operation(summary = "Busca por ano letivo", description = "Retorna uma lista de turmas de um ano específico")
	public ResponseEntity<List<Turma>> buscarPeloAnoLetivo(@RequestParam Integer anoLetivo) {
		List<Turma> turmas = buscaTurmaService.buscarPeloAnoLetivo(anoLetivo);
		return ResponseEntity.ok().body(turmas);
	}
	
	@GetMapping
	@Operation(summary = "Listar todas", description = "Retorna todas as turmas cadastradas com suporte a paginação")
	public ResponseEntity<Page<Turma>> buscarTodas(Pageable paginacao) {
		Page<Turma> turmas = buscaTurmaService.buscarTodas(paginacao);
		return ResponseEntity.ok().body(turmas);
	}

}
