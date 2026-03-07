package com.dougfsilva.controlesaidaescolar.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "alunos")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String matricula;
	private String nome;
	private LocalDate dataNascimento;
	private String foto;

	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;

	private Boolean ativo = true;

	public Aluno(String matricula, String nome, LocalDate dataNascimento, Turma turma) {
		this.matricula = matricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.turma = turma;
	}
	
	
}
