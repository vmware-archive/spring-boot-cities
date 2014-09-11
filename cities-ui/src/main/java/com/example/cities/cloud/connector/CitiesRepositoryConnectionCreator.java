package com.example.cities.cloud.connector;

import com.example.cities.cloud.WebServiceInfo;
import com.example.cities.repository.CityRepository;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

public class CitiesRepositoryConnectionCreator extends AbstractServiceConnectorCreator<CityRepository, WebServiceInfo> {
    @Override
    public CityRepository create(WebServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(CityRepository.class, serviceInfo.getUri());
    }
}
