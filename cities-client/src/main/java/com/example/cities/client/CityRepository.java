package com.example.cities.client;

import com.example.cities.client.model.PagedCities;
import feign.RequestLine;
import org.springframework.stereotype.Repository;

import javax.inject.Named;

@Repository
public interface CityRepository {
    @RequestLine("GET /cities?page={page}&size={size}")
    public PagedCities findAll(@Named("page") Integer page, @Named("size") Integer size);

    @RequestLine("GET /cities/search/nameContains?q={name}&page={page}&size={size}")
    public PagedCities findByNameContains(@Named("name") String name, @Named("page") Integer page, @Named("size") Integer size);
}
