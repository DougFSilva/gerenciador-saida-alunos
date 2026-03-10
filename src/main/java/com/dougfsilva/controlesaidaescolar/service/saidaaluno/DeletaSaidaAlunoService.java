package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeletaSaidaAlunoService {

	private final SaidaAlunoRepository saidaAlunoRepository;
	private final AlunoRepository alunoRepository;
	private final SecurityUtils securityUtils;
	
	@Value("${app.seguranca.anos-para-delecao:2}")
    private Integer anosParaDelecao;

	@Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public void deletarTodasSaidas(Long alunoId) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(alunoId);
        List<SaidaAluno> todasSaidas = saidaAlunoRepository.findByAluno(aluno);
        List<SaidaAluno> saidasParaDeletar = filtrarSaidasPermitidasParaDeletar(todasSaidas);
        saidaAlunoRepository.deleteAll(saidasParaDeletar);
        log.info("Usuário [{}] deletou {} registros antigos do Aluno {} com sucesso.", 
                 securityUtils.getUsernameUsuarioAtual(),
                 saidasParaDeletar.size(),
                 aluno.getId());
	}
	
	private List<SaidaAluno> filtrarSaidasPermitidasParaDeletar(List<SaidaAluno> saidas) {
        LocalDateTime dataLimite = LocalDateTime.now().minusYears(anosParaDelecao);
        
        return saidas.stream()
                .filter(saida -> saida.getSolicitacao().getDataHoraSolicitacao().isBefore(dataLimite))
                .toList();
    }
}
