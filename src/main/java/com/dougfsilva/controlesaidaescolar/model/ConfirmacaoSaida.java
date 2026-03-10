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
public class ConfirmacaoSaida {

	@Column(name = "data_hora_confirmacao")
	private LocalDateTime dataHoraConfirmacao;
	
	@ManyToOne
	@JoinColumn(name = "confirmada_por")
	private Usuario confirmadaPor;
	
	@Column(name = "obs_confirmacao", length = 255)
	private String obsConfirmacao;
}
