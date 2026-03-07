package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {

	private final PasswordEncoder passwordEncoder;

    public String criptografar(String senhaPura) {
        return passwordEncoder.encode(senhaPura);
    }

    public boolean validar(String senhaPura, String senhaCriptografada) {
        return passwordEncoder.matches(senhaPura, senhaCriptografada);
    }
}
