package com.dougfsilva.controlesaidaescolar.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "saidas_alunos")
public class SaidaAluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;

	@Enumerated(EnumType.STRING)
	private StatusSaida status;

	@Embedded
    private SolicitacaoSaida solicitacao;

    @Embedded
    private ConfirmacaoSaida confirmacao;

    @Embedded
    private CancelamentoSaida cancelamento;

	public SaidaAluno(Aluno aluno, StatusSaida status, SolicitacaoSaida solicitacao) {
		this.aluno = aluno;
		this.status = status;
		this.solicitacao = solicitacao;
	}
	
}
