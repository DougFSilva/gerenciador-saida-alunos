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
public class CancelamentoSaida {

	@Column(name = "data_hora_cancelamento")
	private LocalDateTime dataHoraCancelamento;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario canceladaPor;
	
	@Column(name = "obs_confirmacao", length = 255)
	private String ObsCancelamento;
}
