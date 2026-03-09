package com.dougfsilva.controlesaidaescolar.service.aluno;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dougfsilva.controlesaidaescolar.exceptions.ProcessamentoFotoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;

@Service
public class AlunoFotoService {

	private final Path storageLocation;
	private final AlunoRepository alunoRepository;

	public AlunoFotoService(@Value("${app.upload.dir:uploads/fotos}") String uploadDir,
			AlunoRepository alunoRepository) {

		this.alunoRepository = alunoRepository;
		this.storageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.storageLocation);
		} catch (IOException e) {
			throw new ProcessamentoFotoException("Não foi possível criar o diretório de upload", e);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	public void atualizarFoto(Long id, MultipartFile arquivo) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(id);

		validarArquivo(arquivo);

		if (aluno.getFoto() != null) {
			deletarFotoDoStorage(aluno.getFoto());
		}

		String nomeArquivo = salvarFotoNoStorage(arquivo, id);
		aluno.setFoto(nomeArquivo);
		alunoRepository.save(aluno);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Resource getFoto(Long id) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(id);

		if (aluno.getFoto() == null) {
			throw new ProcessamentoFotoException("Foto de aluno não encontrada");
		}

		return carregarFotoDoStorage(aluno.getFoto());
	}

	@PreAuthorize("hasRole('ADMIN')")
	public void removerFoto(Long id) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(id);

		if (aluno.getFoto() != null) {
			deletarFotoDoStorage(aluno.getFoto());
			aluno.setFoto(null);
			alunoRepository.save(aluno);
		}
	}

	private String salvarFotoNoStorage(MultipartFile arquivo, Long alunoId) {
		String extensao = StringUtils.getFilenameExtension(arquivo.getOriginalFilename());
		String nomeArquivo = "aluno_" + alunoId + "_" + System.currentTimeMillis() + "." + extensao;

		try {
			Path destino = this.storageLocation.resolve(nomeArquivo);
			Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
			return nomeArquivo;
		} catch (IOException e) {
			throw new ProcessamentoFotoException("Falha ao salvar o arquivo", e);
		}
	}

	private Resource carregarFotoDoStorage(String nomeArquivo) {
		try {
			Path caminho = this.storageLocation.resolve(nomeArquivo).normalize();
			Resource resource = new UrlResource(caminho.toUri());

			if (resource.exists()) {
				return resource;
			}

			throw new ProcessamentoFotoException("Arquivo não encontrado: " + nomeArquivo);
		} catch (MalformedURLException e) {
			throw new ProcessamentoFotoException("Arquivo não encontrado: " + nomeArquivo, e);
		}
	}

	private void deletarFotoDoStorage(String nomeArquivo) {
		try {
			Path caminho = this.storageLocation.resolve(nomeArquivo).normalize();
			Files.deleteIfExists(caminho);
		} catch (IOException e) {
			throw new ProcessamentoFotoException("Erro ao deletar arquivo", e);
		}
	}

	private void validarArquivo(MultipartFile arquivo) {
		if (arquivo.isEmpty()) {
			throw new ProcessamentoFotoException("Arquivo vazio");
		}

		String contentType = arquivo.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new ProcessamentoFotoException("Apenas imagens são permitidas");
		}

		if (arquivo.getSize() > 5 * 1024 * 1024) {
			throw new ProcessamentoFotoException("Arquivo muito grande. Máximo 5MB");
		}
	}

}
