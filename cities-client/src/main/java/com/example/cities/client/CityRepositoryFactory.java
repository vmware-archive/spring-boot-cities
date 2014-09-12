package com.example.cities.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;

import java.net.URL;

public class CityRepositoryFactory {
    public CityRepository create(String url) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(CityRepository.class, url);
    }

    public CityRepository create(URL url) {
        return create(url.toString());
    }
}
