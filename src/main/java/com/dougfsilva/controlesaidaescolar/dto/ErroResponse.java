package com.dougfsilva.controlesaidaescolar.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ErroResponse {

	private OffsetDateTime timestamp;
	private int status;
	private String mensagens;
	private String path;
}
