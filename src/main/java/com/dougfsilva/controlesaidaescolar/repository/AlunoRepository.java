package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
