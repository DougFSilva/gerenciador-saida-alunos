package com.dougfsilva.controlesaidaescolar.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	@ManyToOne
    @JoinColumn(name = "solicitado_por_id")
    private Usuario solicitadoPor;
    private LocalDateTime solicitadaEm;

    @ManyToOne
    @JoinColumn(name = "confirmado_por_id")
    private Usuario confirmadoPor;
    private LocalDateTime confirmadaEm;

    @ManyToOne
    @JoinColumn(name = "cancelado_por_id")
    private Usuario canceladoPor;
    private LocalDateTime canceladaEm;
	
	private String observacao;
}
