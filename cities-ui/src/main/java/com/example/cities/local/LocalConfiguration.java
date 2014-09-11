package com.example.cities.local;

import com.example.cities.repository.CityRepository;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class LocalConfiguration {
    @Bean
    public CityRepository cityRepository() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(CityRepository.class, "http://localhost:8080/cities");
    }
}
