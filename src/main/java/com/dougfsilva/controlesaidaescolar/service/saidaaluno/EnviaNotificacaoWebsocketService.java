package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.dto.SaidaAlunoNotificacaoWebsocket;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviaNotificacaoWebsocketService {

	private final SimpMessagingTemplate messagingTemplate;
	
	public void enviar(SaidaAluno saida) {
		SaidaAlunoNotificacaoWebsocket notificacaoWebsocket = new SaidaAlunoNotificacaoWebsocket(
				saida.getId(), 
				saida.getAluno().getId(), 
				saida.getAluno().getNome(),
				saida.getAluno().getFoto(), 
				saida.getAluno().getTurma().getNome(), 
				saida.getStatus(), 
				saida.getSolicitacao().getDataHoraSolicitacao());
		messagingTemplate.convertAndSend("/topic/saida-aluno", notificacaoWebsocket);
	}

}
