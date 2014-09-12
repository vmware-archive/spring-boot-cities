package com.example.cities.client.cloud.connector;

import com.example.cities.client.CityRepositoryFactory;
import com.example.cities.client.cloud.WebServiceInfo;
import com.example.cities.client.CityRepository;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

public class CitiesRepositoryConnectionCreator extends AbstractServiceConnectorCreator<CityRepository, WebServiceInfo> {
    @Override
    public CityRepository create(WebServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        return new CityRepositoryFactory().create(serviceInfo.getUri());
    }
}
