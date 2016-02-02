package io.pivotal.fe.demos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.pivotal.fe.demos.domain.City;
import io.pivotal.fe.demos.repositories.CityRepository;

/**
 * Test inspired by:
 * http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-
 * application/
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-
 * testing.html
 * 
 * @author skazi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SBootCitiesServiceApplication.class)
//@WebAppConfiguration
@WebIntegrationTest(randomPort = true)
public class TestCityRepository {

	@Autowired
	private CityRepository repo;

	private String testCity = "Birmingham";
	private int testResultSize = 10;
	private static final Logger logger = LoggerFactory.getLogger(TestCityRepository.class);

	@Test
	public void canFetchPaged() {
		Page<City> apiResponse = repo.findAll(new PageRequest(0, testResultSize));
		assertNotNull(apiResponse);
		assertTrue("Zero results returned", apiResponse.hasContent());
		assertTrue(apiResponse.getNumberOfElements() == testResultSize);
	}

	@Test
	public void canFetchSorted() {
		Page<City> apiResponse = repo.findAll(new PageRequest(0, testResultSize, new Sort(Sort.Direction.DESC, "county").and(new Sort(Sort.Direction.ASC, "name"))));
		//Work out random city to select
		int random = (int) Math.max(Math.min(1, Math.random()* 8),testResultSize-1);
		List<City> aList = apiResponse.getContent();
		
		//logger.info(aList.get(0).getName().compareTo(aList.get(random).getName()) + ",");
		assertTrue(aList.get(0).getName().compareTo(aList.get(random).getName()) < 0);
	}

	@Test
	public void canFetchBirmingham() {
		Page<City> apiResponse = repo.findByNameContainsIgnoreCase("Birmingham", null);
		City respCity = apiResponse.getContent().get(0);
		assertTrue(respCity.getName().equalsIgnoreCase(testCity));
		
		apiResponse = repo.findByNameContainsIgnoreCase("Birmingham2", null);
		assertTrue(apiResponse.getContent().isEmpty());
	}
}
