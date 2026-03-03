package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
