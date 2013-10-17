package com.example.cities.services;

import com.example.cities.domain.City;
import com.example.cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RepositoryCityService implements CityService {

    private CityRepository repository;

    @Autowired
    public RepositoryCityService(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<City> findAllCities(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public City findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<City> findCities(String query, Pageable pageable) {
        if (StringUtils.isEmpty(query)) {
            return findAllCities(pageable);
        }

        String[] parts = query.split("=");
        if (parts[0].equalsIgnoreCase("name")) {
            return repository.findByNameIgnoreCase(parts[1], pageable);
        }
        if (parts[0].equalsIgnoreCase("state")) {
            return repository.findByStateCodeIgnoreCase(parts[1], pageable);
        }
        if (parts[0].equalsIgnoreCase("postalCode")) {
            return repository.findByPostalCode(parts[1], pageable);
        }

        return null;
    }
}
