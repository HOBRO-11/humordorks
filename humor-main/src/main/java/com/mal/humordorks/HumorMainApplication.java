package com.mal.humordorks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HumorMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumorMainApplication.class, args);
	}

}
