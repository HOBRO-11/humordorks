package com.mal.humordorks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// @EnableJpaAuditing
public class HumordorksApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumordorksApplication.class, args);
	}

}
