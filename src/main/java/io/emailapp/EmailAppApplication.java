package io.emailapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmailAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailAppApplication.class, args);
	}

}
