package com.example.rest;

import com.example.rest.domain.Availability;
import com.example.rest.domain.Flight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FlightServiceTests {

	@Autowired
	private FlightService service;

	@Test
	public void getSourceAvailabilityTest() {
		String origin = "IST";
		String destination = "DUB";
		LocalDate start = LocalDate.of(2014,1,1);
		LocalDate end = LocalDate.of(2014,1, 2);
		int pax = 1;
		Availability availability = service.getSourceAvailability(origin, destination, start, end, pax);
		assertThat(availability).isNotNull();
		assertThat(availability.getFlights()).extracting(Flight::getOriginAirport).contains(origin);
	}

	@Test
	public void convertToMapTest() {
		Flight flight = new Flight();
		flight.setOriginAirport("IST");
		flight.setDestinationAirport("DUB");
		Availability availability = new Availability();
		availability.setFlights(Collections.singletonList(flight));
		Map<String, Object> availabilityMap = service.convertToMap(availability);
		assertThat(availabilityMap).isNotEmpty();
		assertThat(availabilityMap).containsKey("availability");
	}
}