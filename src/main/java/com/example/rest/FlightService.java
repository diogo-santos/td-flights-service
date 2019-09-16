package com.example.rest;

import com.example.rest.domain.Availability;
import com.example.rest.domain.Fare;
import com.example.rest.domain.Flight;
import com.example.rest.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class FlightService {
    private Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final WebClient webClient;

    public FlightService(Environment environment, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(environment.getRequiredProperty("url.get.availability")).build();
    }

    Map getAvailability(String origin, String destination,
                        LocalDate start, LocalDate end, int pax) {
        logger.debug("In getAvailability with {} {} {} {} {}", origin, destination, start, end, pax);
        final Availability availability = webClient.get()
                                            .uri("/{origin}/{destination}/{start}/{end}/{pax}",
                                                origin, destination, start, end, pax)
                                            .retrieve()
                                            .bodyToMono(Availability.class).block();

        final Map availabilityMap = map(availability);
        logger.debug("Out getAvailability with {}", availabilityMap);
        return availabilityMap;
    }

    Map<String, List<Map<String, FlightDto>>> map(Availability availability) {
        List<Map<String, FlightDto>> flights = (new ArrayList<>());
        if (null != availability && null != availability.getFlights()) {
            for (Flight flight : availability.getFlights()) {
                FlightDto flightDto = new FlightDto();
                flightDto.setOperator(flight.getCarrierCode());
                flightDto.setFlightNumber(flight.getFlightDesignator());
                flightDto.setDepartsFrom(flight.getOriginAirport());
                flightDto.setArrivesAt(flight.getDestinationAirport());
                flightDto.setDepartsOn(FlightDateTimeDto.from(flight.getDepartureDate()));
                flightDto.setArrivesOn(FlightDateTimeDto.from(flight.getArrivalDate()));

                long hours = 0, minutes = 0;
                if (null != flight.getDepartureDate() && null != flight.getArrivalDate()) {
                    hours = HOURS.between(flight.getDepartureDate(), flight.getArrivalDate());
                    minutes = MINUTES.between(flight.getDepartureDate().plusHours(hours), flight.getArrivalDate());
                }
                flightDto.setFlightTime(String.format("%02d:%02d", hours, minutes));

                FarePricesDto farePricesDto = new FarePricesDto();
                for (Fare fare : flight.getFaresList()) {
                    FarePriceDto farePriceDto = new FarePriceDto();
                    farePriceDto.setTicket(PriceDto.from(fare.getBasePrice()));
                    farePriceDto.setBookingFee(PriceDto.from(fare.getFees()));
                    farePriceDto.setTax(PriceDto.from(fare.getTax()));

                    switch (fare.getFareClass()) {
                        case "FIF": farePricesDto.setFirst(farePriceDto);
                        case "CIF": farePricesDto.setBusiness(farePriceDto);
                        case "YIF": farePricesDto.setEconomy(farePriceDto);
                    }
                }
                flightDto.setFarePrices(farePricesDto);

                flights.add(new HashMap<String, FlightDto>(){{put("flight", flightDto);}});
            }
        }
        return new HashMap<String, List<Map<String, FlightDto>>>(){{put("availability", flights);}};
    }
}