package com.example.cities.web;

import com.example.cities.domain.City;
import com.example.cities.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/")
    public Map<String, Object> home(Pageable pageable) {
        Page<City> allCities = cityService.findAllCities(pageable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("cities", allCities);
        return response;
    }

    @RequestMapping("/city/{id}")
    public City cityById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @RequestMapping("/search")
    public Page<City> search(@RequestParam("q") String query, Pageable pageable) {
        return cityService.findCities(query, pageable);
    }
}
