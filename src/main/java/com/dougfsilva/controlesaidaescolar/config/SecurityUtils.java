package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Component
public class SecurityUtils {

	public String getUsuarioAtual() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Usuario usuario) {
            return usuario.getUsername();
        }

        return "Anônimo";
    }
}
