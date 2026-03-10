package com.dougfsilva.controlesaidaescolar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
public class SolicitacaoSaida {

	@Column(name = "data_hora_solicitacao", nullable = false, updatable = false)
	private LocalDateTime dataHoraSolicitacao;
	
	@ManyToOne
	@JoinColumn(name = "solicitada_por", nullable = false, updatable = false)
	private Usuario solicitadaPor;
	
	@Column(name = "obs_solicitacao", length = 255)
	private String obsSolicitacao;
}
