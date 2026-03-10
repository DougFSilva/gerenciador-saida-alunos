package com.dougfsilva.controlesaidaescolar.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "alunos")
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 20)
	private String matricula;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	@Column(columnDefinition = "TEXT")
	private String foto;

	@ManyToOne
	@JoinColumn(name = "turma_id", nullable = false)
	private Turma turma;

	@Column(nullable = false)
	private Boolean ativo = true;

	public Aluno(String matricula, String nome, LocalDate dataNascimento, Turma turma) {
		this.matricula = matricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.turma = turma;
	}
	
	
}
