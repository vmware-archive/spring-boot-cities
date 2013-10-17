package com.example.cities.repositories;

import com.example.cities.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface CityRepository extends Repository<City, Long> {
    Page<City> findAll(Pageable pageable);

    City findById(Long id);

    Page<City> findByStateCodeIgnoreCase(String stateCode, Pageable pageable);

    Page<City> findByNameIgnoreCase(String part, Pageable pageable);

    Page<City> findByPostalCode(String postalCode, Pageable pageable);
}
