package com.example.cities.services;

import com.example.cities.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {
    Page<City> findAllCities(Pageable pageable);

    City findById(Long id);

    Page<City> findCities(String query, Pageable pageable);
}
