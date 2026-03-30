package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Component
public interface SecurityUtils {

	String getUsernameUsuarioAtual();

    Usuario getUsuarioAtual();

    boolean isFuncionario();

    boolean isAdminOuMaster();
}
