package com.example.cities.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.hateoas.hal.Jackson2HalModule;

import java.net.URL;

public class CityRepositoryFactory {
    public CityRepository create(String url) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new Jackson2HalModule());

        return Feign.builder()
                .decoder(new JacksonDecoder(mapper))
                .target(CityRepository.class, url);
    }

    public CityRepository create(URL url) {
        return create(url.toString());
    }
}
