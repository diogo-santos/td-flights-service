package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightDto {
    private String operator;
    private String flightNumber;
    private String departsFrom;
    private String arrivesAt;
    private FlightDateTimeDto departsOn;
    private FlightDateTimeDto arrivesOn;
    private String flightTime;
    private FarePricesDto farePrices;
}