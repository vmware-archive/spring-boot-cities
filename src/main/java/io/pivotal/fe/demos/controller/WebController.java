package io.pivotal.fe.demos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.fe.demos.domain.City;
import io.pivotal.fe.demos.repositories.CityRepository;

/**
 * Class to add optional non-HATEOS and non-pageable list of all cities
 * @author skazi
 *
 */
@RestController
public class WebController {
	@Autowired
	private CityRepository repo;

	@RequestMapping("/cities_all")
	public Iterable<City> showAllCities() {
		return repo.findAll();
	}
}
