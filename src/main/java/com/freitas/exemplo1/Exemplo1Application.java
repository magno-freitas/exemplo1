package com.freitas.exemplo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.freitas")
@EntityScan("com.freitas.model")
@EnableJpaRepositories("com.freitas.repo")
public class Exemplo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Exemplo1Application.class, args);
    }
}