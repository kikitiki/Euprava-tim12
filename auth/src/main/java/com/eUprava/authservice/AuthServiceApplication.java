package com.eUprava.authservice;

import com.eUprava.authservice.service.DataService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com/eUprava/authservice/repository")
public class AuthServiceApplication implements CommandLineRunner {

	@Autowired
	private DataService dataService;


	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {
		dataService.korisnici();
	}
}
