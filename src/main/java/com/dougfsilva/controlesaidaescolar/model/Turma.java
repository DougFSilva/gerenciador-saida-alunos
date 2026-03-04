package com.dougfsilva.controlesaidaescolar.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Turno turno;
	
	private Integer anoLetivo;
	
	private boolean ativa = true;
}
