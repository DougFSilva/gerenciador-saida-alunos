package com.dougfsilva.controlesaidaescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dougfsilva.controlesaidaescolar.model.Aluno;

@RestController
@RequestMapping("/alunos")
public class AlunoFotoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private FileStorageService fileStorageService;

    // Upload da foto
    @PutMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile foto) {

        Aluno aluno = alunoService.buscarPorId(id);

        // Remove foto antiga se existir
        if (aluno.getFotoUrl() != null) {
            fileStorageService.deletarFoto(aluno.getFotoUrl());
        }

        String nomeArquivo = fileStorageService.salvarFoto(foto, id);
        alunoService.atualizarFoto(id, nomeArquivo);

        return ResponseEntity.ok("Foto atualizada com sucesso");
    }

    // Download/visualização da foto
    @GetMapping("/{id}/foto")
    public ResponseEntity<Resource> getFoto(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno.getFotoUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource foto = fileStorageService.carregarFoto(aluno.getFotoUrl());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "inline; filename=\"" + foto.getFilename() + "\"")
                .body(foto);
    }

    // Deletar foto
    @DeleteMapping("/{id}/foto")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno.getFotoUrl() != null) {
            fileStorageService.deletarFoto(aluno.getFotoUrl());
            alunoService.atualizarFoto(id, null);
        }

        return ResponseEntity.noContent().build();
    }
}
