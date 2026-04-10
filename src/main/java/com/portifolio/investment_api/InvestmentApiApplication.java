package com.portifolio.investment_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Adicione este import

@SpringBootApplication
@EnableFeignClients // <--- ESTA LINHA VAI RESOLVER O ERRO DE BEAN NOT FOUND
public class InvestmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentApiApplication.class, args);
	}
}