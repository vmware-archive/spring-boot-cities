package com.example.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableSpringDataWebSupport
public class CitiesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CitiesApplication.class, args);
    }
}
