package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long>{

    boolean existsByNomeAndAnoLetivo(String nome, String anoLetivo);
    
    boolean existsByNomeAndAnoLetivoAndIdNot(String nome, String anoLetivo, Long id);
}
