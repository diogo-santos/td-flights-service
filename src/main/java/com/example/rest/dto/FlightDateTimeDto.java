package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightDateTimeDto {
    private String date;
    private String time;

    public static FlightDateTimeDto from(LocalDateTime dateTime) {
        FlightDateTimeDto flightDateTimeDto = new FlightDateTimeDto();
        if (null != dateTime) {
            flightDateTimeDto.setDate(dateTime.format(ofPattern("dd-MM-yyyy")));
            flightDateTimeDto.setTime(dateTime.format(ofPattern("hh:mm a")));
        }
        return flightDateTimeDto;
    }
}