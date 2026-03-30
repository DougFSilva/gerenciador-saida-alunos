package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.dougfsilva.controlesaidaescolar.config.PasswordService;
import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CriaUsuarioMasterService implements CommandLineRunner {

	private final UsuarioRepository repository;
	private final PasswordService passwordService;

	@Value("${senha.master}")
	private String senhaMaster;
	
	@Value("${email.master}")
	private String emailMaster;

	@Override
	public void run(String... args) throws Exception {

		if (repository.findByEmail(emailMaster).isEmpty()) {
			log.info("Criando usuário Master padrão...");

			String senhaCriptografada = passwordService.criptografar(senhaMaster);

			Usuario master = new Usuario("Master do Sistema", "00000000000",
					emailMaster, senhaCriptografada, PerfilUsuario.MASTER
			);

			repository.save(master);
			log.info("Usuário Master criado com sucesso!");
		} else {
			log.info("Usuário Master já existe. Pulando inicialização.");
		}
	}

}
