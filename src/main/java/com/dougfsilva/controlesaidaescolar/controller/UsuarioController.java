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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.controlesaidaescolar.dto.AlteraSenhaForm;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioResponse;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioForm;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.service.usuario.AlteraSenhaService;
import com.dougfsilva.controlesaidaescolar.service.usuario.BuscaUsuarioService;
import com.dougfsilva.controlesaidaescolar.service.usuario.CriaUsuarioService;
import com.dougfsilva.controlesaidaescolar.service.usuario.DeletaUsuarioService;
import com.dougfsilva.controlesaidaescolar.service.usuario.EditaUsuarioService;
import com.dougfsilva.controlesaidaescolar.service.usuario.ResetaSenhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários e segurança")
public class UsuarioController {
	
	private final CriaUsuarioService criaUsuarioService;
	private final DeletaUsuarioService deletaUsuarioService;
	private final EditaUsuarioService editaUsuarioService;
	private final BuscaUsuarioService buscaUsuarioService;
	private final AlteraSenhaService alteraSenhaService;
	private final ResetaSenhaService resetaSenhaService;
	
	@PostMapping
	@Operation(summary = "Cadastra um novo usuário", description = "Cria um usuário e retorna seus dados públicos via DTO")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
	})
	public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioForm form) {
		Usuario usuario = criaUsuarioService.criar(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(UsuarioResponse.toDto(usuario));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Remove um usuário", description = "Exclui permanentemente um usuário pelo ID")
	@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		deletaUsuarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um usuário", description = "Atualiza informações básicas (exceto senha) de um usuário existente")
	@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
	public ResponseEntity<UsuarioResponse> editar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateForm form) {
		Usuario usuario = editaUsuarioService.editar(id, form);
		return ResponseEntity.ok().body(UsuarioResponse.toDto(usuario));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca por ID", description = "Retorna os dados públicos de um usuário específico")
	@ApiResponse(responseCode = "200", description = "Usuário encontrado")
	public ResponseEntity<UsuarioResponse> buscarPeloId(@PathVariable Long id) {
		Usuario usuario = buscaUsuarioService.buscarPeloId(id);
		return ResponseEntity.ok().body(UsuarioResponse.toDto(usuario));
	}
	
	@GetMapping("/nome")
	@Operation(summary = "Busca por nome", description = "Lista usuários cujo nome contenha o termo pesquisado")
	public ResponseEntity<Page<UsuarioResponse>> buscarPeloNome(@RequestParam String nome, Pageable paginacao) {
		Page<UsuarioResponse> usuarios = buscaUsuarioService.buscarPeloNome(nome, paginacao).map(UsuarioResponse::toDto);
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/perfil")
	@Operation(summary = "Busca por perfil", description = "Filtra usuários por tipo de permissão (ADMIN, PROFESSOR, etc)")
	public ResponseEntity<Page<UsuarioResponse>> buscarPeloPerfil(@RequestParam PerfilUsuario perfil, Pageable paginacao) {
		Page<UsuarioResponse> usuarios = buscaUsuarioService.buscarPeloPerfil(perfil, paginacao).map(UsuarioResponse::toDto);
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping
	@Operation(summary = "Listar todos", description = "Retorna todos os usuários cadastrados de forma paginada")
	public ResponseEntity<Page<UsuarioResponse>> buscarTodos(Pageable paginacao) {
		Page<UsuarioResponse> usuarios = buscaUsuarioService.buscarTodos(paginacao).map(UsuarioResponse::toDto);
		return ResponseEntity.ok().body(usuarios);
	}
	
	@PatchMapping("/alterar-senha")
	@Operation(summary = "Alterar própria senha", description = "Permite que o usuário autenticado altere sua própria senha de acesso")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Senha atual incorreta ou nova senha inválida")
	})
    public ResponseEntity<Void> alterarSenha(@RequestBody @Valid AlteraSenhaForm form) {
        alteraSenhaService.alterar(form);
        return ResponseEntity.ok().build();
    }
	
	@PatchMapping("/resetar-senha/{id}")
	@Operation(summary = "Resetar senha de um usuário", description = "Redefine a senha de um usuário específico para uma senha padrão.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Senha resetada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	public ResponseEntity<Void> resetarSenha(@PathVariable Long id) {
		resetaSenhaService.resetar(id);
		return ResponseEntity.ok().build();
	}

}
