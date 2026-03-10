package com.dougfsilva.controlesaidaescolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class ControlesaidaescolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlesaidaescolarApplication.class, args);
	}

}
