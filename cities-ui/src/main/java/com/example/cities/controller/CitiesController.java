package com.example.cities.controller;

import com.example.cities.client.CityRepository;
import com.example.cities.client.model.PagedCities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CitiesController {
    private CityRepository repository;

    @Autowired
    public CitiesController(CityRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public PagedCities list(Pageable pageable) {
        return repository.findAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PagedCities search(@RequestParam("name") String name, Pageable pageable) {
        return repository.findByNameContains(name, pageable.getPageNumber(), pageable.getPageSize());
    }
}
