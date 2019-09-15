package com.example.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "Availability")
@XmlAccessorType(XmlAccessType.FIELD)
public class Availability {
    @XmlElement(name = "Flight")
    List<Flight> flights;

    public List<Flight> getFlights() {
        return null != flights ? flights : Collections.emptyList();
    }
}