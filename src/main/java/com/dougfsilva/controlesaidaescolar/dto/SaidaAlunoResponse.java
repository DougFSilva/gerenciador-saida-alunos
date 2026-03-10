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
		this.dataHoraConfirmacao = saida.getConfirmacao().getDataHoraConfirmacao();
		this.confirmadaPor = UsuarioResponse.toDto(saida.getConfirmacao().getConfirmadaPor());
		this.ObsConfirmacao = saida.getConfirmacao().getObsConfirmacao();
		this.dataHoraCancelamento = saida.getCancelamento().getDataHoraCancelamento();
		this.canceladaPor = UsuarioResponse.toDto(saida.getCancelamento().getCanceladaPor());
		this.ObsCancelamento = saida.getCancelamento().getObsCancelamento();
	}
	
	public static SaidaAlunoResponse toDto(SaidaAluno saida) {
		return new SaidaAlunoResponse(saida);
	}
}
