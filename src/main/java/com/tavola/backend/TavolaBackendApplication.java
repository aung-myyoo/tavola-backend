package com.tavola.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TavolaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TavolaBackendApplication.class, args);
	}

}
