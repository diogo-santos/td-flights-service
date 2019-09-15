package com.example.rest.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public LocalDateTime unmarshal(String value) {
        return LocalDateTime.parse(value, ISO_DATE_TIME);
    }

    public String marshal(LocalDateTime value) {
        return value.toString();
    }
}