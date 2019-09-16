package com.example.rest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class FlightController {
    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @GetMapping("/flights/{origin}/{destination}/{start}/{end}/{pax}")
    @ResponseStatus(OK)
    public Map getAvailability(@PathVariable String origin,
                                               @PathVariable String destination,
                                               @PathVariable @DateTimeFormat(iso = DATE) LocalDate start,
                                               @PathVariable @DateTimeFormat(iso = DATE) LocalDate end,
                                               @PathVariable int pax) {
        return service.getAvailability(origin, destination, start, end, pax);
    }
}