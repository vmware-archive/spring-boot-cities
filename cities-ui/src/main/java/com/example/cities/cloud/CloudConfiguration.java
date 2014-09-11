package com.example.cities.cloud;

import com.example.cities.repository.CityRepository;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfiguration extends AbstractCloudConfig {
    @Bean
    public CityRepository cityRepository() {
        return connectionFactory().service(CityRepository.class);
    }
}
