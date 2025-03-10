package com.example.UberProject_AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
// to enable entity listeners
public class UberProjectAuthServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UberProjectAuthServiceApplication.class, args);
		System.out.println("running");
	}

}
