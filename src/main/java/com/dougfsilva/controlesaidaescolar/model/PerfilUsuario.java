package com.dougfsilva.controlesaidaescolar.model;

public enum PerfilUsuario {
	MASTER, // criado automaticamente na inicialização, acesso total
	ADMIN, // gerencia turmas, alunos, usuários
	FUNCIONARIO // apenas solicita/confirma/cancela saídas
}
