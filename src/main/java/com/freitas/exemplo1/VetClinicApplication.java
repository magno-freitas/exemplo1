package com.freitas.exemplo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.modelmapper.ModelMapper;

@SpringBootApplication
@ComponentScan(basePackages = {"com.freitas.exemplo1", "com.freitas.controller", "com.freitas.service"})
@EntityScan(basePackages = {"com.freitas.exemplo1.model", "com.freitas.model"})
@EnableJpaRepositories(basePackages = {"com.freitas.exemplo1.repository", "com.freitas.repo"})
public class VetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(VetClinicApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}