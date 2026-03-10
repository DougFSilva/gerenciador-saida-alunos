package com.dougfsilva.controlesaidaescolar.model;

import java.time.LocalDateTime;

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

	private LocalDateTime dataHoraCancelamento;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario canceladaPor;
	
	private String ObsCancelamento;
}
