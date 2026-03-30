package com.dougfsilva.controlesaidaescolar.dto;

import java.time.LocalDateTime;

import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class SaidaAlunoResponse {

	private Long id;
	private Aluno aluno;
	private StatusSaida status;
	private LocalDateTime dataHoraSolicitacao;
	private UsuarioResponse solicitadaPor;
	private String ObsSolicitacao;
	private LocalDateTime dataHoraConfirmacao;
	private UsuarioResponse confirmadaPor;
	private String ObsConfirmacao;
	private LocalDateTime dataHoraCancelamento;
	private UsuarioResponse canceladaPor;
	private String ObsCancelamento;
	
	private SaidaAlunoResponse(SaidaAluno saida) {
		this.id = saida.getId();
		this.aluno = saida.getAluno();
		this.status = saida.getStatus();
		this.dataHoraSolicitacao = saida.getSolicitacao().getDataHoraSolicitacao();
		this.solicitadaPor = UsuarioResponse.toDto(saida.getSolicitacao().getSolicitadaPor());
		this.ObsSolicitacao = saida.getSolicitacao().getObsSolicitacao();
		var confirmacao = saida.getConfirmacao();
		var cancelamento = saida.getCancelamento();

		this.dataHoraConfirmacao = confirmacao != null ? confirmacao.getDataHoraConfirmacao() : null;
		this.confirmadaPor = confirmacao != null ? UsuarioResponse.toDto(confirmacao.getConfirmadaPor()) : null;
		this.ObsConfirmacao = confirmacao != null ? confirmacao.getObsConfirmacao() : null;

		this.dataHoraCancelamento = cancelamento != null ? cancelamento.getDataHoraCancelamento() : null;
		this.canceladaPor = cancelamento != null ? UsuarioResponse.toDto(cancelamento.getCanceladaPor()) : null;
		this.ObsCancelamento = cancelamento != null ? cancelamento.getObsCancelamento() : null;
	}
	
	public static SaidaAlunoResponse toDto(SaidaAluno saida) {
		return new SaidaAlunoResponse(saida);
	}
}
