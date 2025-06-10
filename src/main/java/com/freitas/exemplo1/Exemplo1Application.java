package com.freitas.exemplo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.freitas.exemplo1")
@EntityScan(basePackages = "com.freitas.exemplo1.model")
@EnableJpaRepositories(basePackages = "com.freitas.exemplo1.repository")
public class Exemplo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Exemplo1Application.class, args);
    }
}
