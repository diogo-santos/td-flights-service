package com.example.rest;

import com.example.rest.domain.Availability;
import com.example.rest.domain.Fare;
import com.example.rest.domain.Fares;
import com.example.rest.domain.Flight;
import com.example.rest.dto.FlightDateTimeDto;
import com.example.rest.dto.FlightDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FlightServiceTests {
	@Autowired
	private FlightService service;

	@Test
	public void mapAvailabilityTest() {
		//Given mock domain flight instance
		Flight flight = new Flight();
		LocalDateTime departureDate = LocalDateTime.of(2014,1,2, 1, 5, 0);
		LocalDateTime arrivalDate = LocalDateTime.of(2014,1, 2, 2, 10, 0);
		flight.setCarrierCode("AA");
		flight.setFlightDesignator("1234AA");
		flight.setOriginAirport("IST");
		flight.setDestinationAirport("DUB");
		flight.setDepartureDate(departureDate);
		flight.setArrivalDate(arrivalDate);
		flight.setFares(new Fares());
		flight.getFares().setFares(new ArrayList<>());

		Fare fareFirstClass = new Fare();
		fareFirstClass.setFareClass("FIF");
		fareFirstClass.setBasePrice("EUR 10.00");
		fareFirstClass.setFees("EUR 11.00");
		fareFirstClass.setTax("EUR 12.00");

		Fare fareEconomy = new Fare();
		fareEconomy.setFareClass("FIF");
		fareEconomy.setBasePrice("EUR 20.00");
		fareEconomy.setFees("EUR 21.00");
		fareEconomy.setTax("EUR 22.00");

		Fare fareBusiness = new Fare();
		fareBusiness.setFareClass("FIF");
		fareBusiness.setBasePrice("EUR 30.00");
		fareBusiness.setFees("EUR 31.00");
		fareBusiness.setTax("EUR 32.00");

		flight.getFaresList().add(fareFirstClass);
		flight.getFaresList().add(fareBusiness);
		flight.getFaresList().add(fareEconomy);

		Availability availability = new Availability();
		availability.setFlights(Collections.singletonList(flight));

		// When convert to Dto is invoked
		Map<String, List<Map<String, FlightDto>>> availabilityMap = service.map(availability);

		//Then returns domain converted to dto model
		List<FlightDto> flightsDto = availabilityMap.get("availability").stream().map(flightMap -> flightMap.get("flight")).collect(toList());
		assertThat(flightsDto).extracting(FlightDto::getOperator).contains(flight.getCarrierCode());
		assertThat(flightsDto).extracting(FlightDto::getFlightNumber).contains(flight.getFlightDesignator());
		assertThat(flightsDto).extracting(FlightDto::getDepartsFrom).contains(flight.getOriginAirport());
		assertThat(flightsDto).extracting(FlightDto::getArrivesAt).contains(flight.getDestinationAirport());
		assertThat(flightsDto).extracting(FlightDto::getDepartsOn).contains(FlightDateTimeDto.from(flight.getDepartureDate()));
		assertThat(flightsDto).extracting(FlightDto::getArrivesOn).contains(FlightDateTimeDto.from(flight.getArrivalDate()));
		assertThat(flightsDto).extracting(FlightDto::getFlightTime).contains("01:05");
	}
}