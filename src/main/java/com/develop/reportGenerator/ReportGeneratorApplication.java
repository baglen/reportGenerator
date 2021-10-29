package com.develop.reportGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ReportGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGeneratorApplication.class, args);
	}
}
