package com.example.cities.controller;

import com.example.cities.model.City;
import com.example.cities.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CitiesController {
    private static final Logger logger = LoggerFactory.getLogger(CitiesController.class);

    @Autowired
    private CityRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<City> list(Pageable pageable) {
        return repository.findAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PagedResources<City> search(@RequestParam("name") String name, Pageable pageable) {
        return repository.findByNameContains(name, pageable.getPageNumber(), pageable.getPageSize());
    }
}
