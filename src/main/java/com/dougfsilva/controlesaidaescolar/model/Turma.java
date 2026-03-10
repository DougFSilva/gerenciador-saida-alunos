package com.dougfsilva.controlesaidaescolar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "turmas", uniqueConstraints = {
	    @UniqueConstraint(name = "uk_turma_nome_ano", columnNames = {"nome", "anoLetivo"})
	})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Turno turno;
	
	@Column(name = "ano_letivo", nullable = false)
	private Integer anoLetivo;
	
	@Column(nullable = false)
	private boolean ativa = true;
}
