package com.example.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "Flight")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight {
    @XmlElement(name = "CarrierCode")
    private String carrierCode;
    @XmlElement(name = "FlightDesignator")
    private String flightDesignator;
    @XmlElement(name = "OriginAirport")
    private String originAirport;
    @XmlElement(name = "DestinationAirport")
    private String destinationAirport;
    @XmlElement(name = "DepartureDate")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime departureDate;
    @XmlElement(name = "ArrivalDate")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime arrivalDate;
    @XmlElement(name = "Fares")
    private Fares fares;

    public List<Fare> getFaresList() {
        return null != fares && null != fares.getFares() ? fares.getFares() : Collections.emptyList();
    }
}
