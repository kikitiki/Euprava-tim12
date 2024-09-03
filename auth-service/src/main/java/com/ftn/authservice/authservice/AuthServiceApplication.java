package com.ftn.authservice.authservice;

import com.ftn.authservice.authservice.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com/ftn/authservice/authservice/repository")
public class AuthServiceApplication {

	@Autowired
	private DataService dataService;
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		dataService.korisnici();
//	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
