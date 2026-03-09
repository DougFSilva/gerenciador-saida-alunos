package com.dougfsilva.controlesaidaescolar.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.controlesaidaescolar.dto.AlunoForm;
import com.dougfsilva.controlesaidaescolar.dto.AlunoUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.service.aluno.AlunoFotoService;
import com.dougfsilva.controlesaidaescolar.service.aluno.BuscaAlunoService;
import com.dougfsilva.controlesaidaescolar.service.aluno.CriaAlunoService;
import com.dougfsilva.controlesaidaescolar.service.aluno.DeletaAlunoService;
import com.dougfsilva.controlesaidaescolar.service.aluno.EditaAlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {

	private final CriaAlunoService criaAlunoService;
	private final DeletaAlunoService deletaAlunoService;
	private final EditaAlunoService editaAlunoService;
	private final BuscaAlunoService buscaAlunoService;
    private final AlunoFotoService alunoFotoService;

	@PostMapping
	@Operation(summary = "Cadastra um novo aluno", description = "Cria um novo registro de aluno e retorna os dados salvos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
	})
	public ResponseEntity<Aluno> criar(@Valid @RequestBody AlunoForm form) {
		Aluno aluno = criaAlunoService.criar(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aluno.getId()).toUri();
		return ResponseEntity.created(uri).body(aluno);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remove um aluno", description = "Exclui permanentemente um aluno do sistema pelo ID")
	@ApiResponse(responseCode = "204", description = "Aluno removido com sucesso")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		deletaAlunoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um aluno", description = "Atualiza as informações de um aluno existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado")
	})
	public ResponseEntity<Aluno> editar(@PathVariable Long id, @Valid @RequestBody AlunoUpdateForm form) {
		Aluno aluno = editaAlunoService.editar(id, form);
		return ResponseEntity.ok().body(aluno);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca por ID", description = "Retorna os detalhes de um aluno específico")
	@ApiResponse(responseCode = "200", description = "Aluno encontrado")
	public ResponseEntity<Aluno> buscarPeloId(@PathVariable Long id) {
		Aluno aluno = buscaAlunoService.buscarPeloId(id);
		return ResponseEntity.ok().body(aluno);
	}

	@GetMapping("/matricula")
	@Operation(summary = "Busca pela matrícula", description = "Localiza um aluno utilizando seu número de matrícula único")
	@ApiResponse(responseCode = "200", description = "Aluno encontrado")
	public ResponseEntity<Aluno> buscarPelaMatricula(@RequestParam String matricula) {
		Aluno aluno = buscaAlunoService.buscarPelaMatricula(matricula);
		return ResponseEntity.ok().body(aluno);
	}

	@GetMapping("/nome")
	@Operation(summary = "Lista alunos por nome", description = "Retorna os alunos filtrados pelo nome com paginação")
	public ResponseEntity<Page<Aluno>> buscarPeloNome(@RequestParam String nome, Pageable paginacao) {
		Page<Aluno> alunos = buscaAlunoService.buscarPeloNome(nome, paginacao);
		return ResponseEntity.ok().body(alunos);
	}
	
	@GetMapping("/turma/{id}")
	@Operation(summary = "Lista alunos por turma", description = "Retorna uma página de alunos vinculados a uma turma específica")
	public ResponseEntity<Page<Aluno>> buscarPelaTurma(@PathVariable Long id, Pageable paginacao) {
		Page<Aluno> alunos = buscaAlunoService.buscarPelaTurma(id, paginacao);
		return ResponseEntity.ok().body(alunos);
	}
	
	@GetMapping
	@Operation(summary = "Listar todos os alunos", description = "Retorna todos os alunos cadastrados com suporte a paginação")
	public ResponseEntity<Page<Aluno>> buscarTodos(Pageable paginacao) {
		Page<Aluno> alunos = buscaAlunoService.buscarTodos(paginacao);
		return ResponseEntity.ok().body(alunos);
	}
	
	@PutMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFoto(
	    @PathVariable Long id,
	    @RequestParam("foto") MultipartFile foto) {

	    alunoFotoService.atualizarFoto(id, foto);
	    return ResponseEntity.ok().body("Foto atualizada com sucesso");
	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<Resource> getFoto(@PathVariable Long id) {
	    Resource foto = alunoFotoService.getFoto(id);

	    String contentType;
	    try {
	        contentType = Files.probeContentType(foto.getFile().toPath());
	    } catch (IOException e) {
	        contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "inline; filename=\"" + foto.getFilename() + "\"")
	            .body(foto);
	}

	@DeleteMapping("/{id}/foto")
	public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
	    alunoFotoService.removerFoto(id);
	    return ResponseEntity.noContent().build();
	}

}
