package com.example.rest;

import com.example.rest.domain.Availability;
import com.example.rest.domain.Fare;
import com.example.rest.domain.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
public class FlightService {
    private Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final Environment environment;

    public FlightService(Environment environment) {
        this.environment = environment;
    }

    public Map<String, Object> getAvailability(String origin, String destination,
                                               LocalDate start, LocalDate end, int pax) {

        Availability availability = this.getSourceAvailability(origin, destination, start, end, pax);
        return convertToMap(availability);
    }

    public Availability getSourceAvailability(String origin, String destination,
                                              LocalDate start, LocalDate end, int pax) {
        Availability availability = null;
        try {
            URL urlObject = new URL(environment.getRequiredProperty("url.get.availability")
                                    + format("/%s/%s/%s/%s/%s", origin, destination, start, end, pax));
            InputStream inputStream = urlObject.openStream();
            Unmarshaller unmarshaller = JAXBContext.newInstance(Availability.class).createUnmarshaller();
            availability = (Availability) unmarshaller.unmarshal(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return availability;
    }

    Map<String, Object> convertToMap(Availability availability) {
        List<Map> flights = new ArrayList<>();
        if (availability != null) {
            for (Flight flight : availability.getFlights()) {
                Map<String, Object> flightMap = new HashMap<>();
                flightMap.put("operator", flight.getCarrierCode());
                flightMap.put("flightNumber", flight.getFlightDesignator());
                flightMap.put("departsFrom", flight.getOriginAirport());
                flightMap.put("arrivesAt", flight.getDestinationAirport());

                Map<String, Object> departsOn = new HashMap<>();
                LocalDateTime departureDate = flight.getDepartureDate();
                departsOn.put("date", departureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                departsOn.put("time", departureDate.format(DateTimeFormatter.ofPattern("hh:mm a")));
                flightMap.put("departsOn", departsOn);

                Map<String, Object> arrivesOn = new HashMap<>();
                LocalDateTime arrivalDate = flight.getArrivalDate();
                arrivesOn.put("date", arrivalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                arrivesOn.put("time", arrivalDate.format(DateTimeFormatter.ofPattern("hh:mm a")));
                flightMap.put("arrivesOn", arrivesOn);

                long hours = departureDate.until(arrivalDate, ChronoUnit.HOURS);
                long minutes = departureDate.plusHours(hours).until(arrivalDate, ChronoUnit.MINUTES);
                flightMap.put("flightTime", hours +":"+minutes);

                Map<String, Object> farePricesMap = new HashMap<>();
                for (Fare fare : flight.getFaresList()) {
                    Map<String, Object> fareMap = new HashMap<>();
                    Map<String, Object> ticketMap = new HashMap<>();
                    ticketMap.put("currency", fare.getBasePrice().split(" ")[0]);
                    ticketMap.put("amount", fare.getBasePrice().split(" ")[1]);
                    fareMap.put("ticket", ticketMap);

                    Map<String, Object> bookingFeeMap = new HashMap<>();
                    bookingFeeMap.put("currency", fare.getFees().split(" ")[0]);
                    bookingFeeMap.put("amount", fare.getFees().split(" ")[1]);
                    fareMap.put("bookingFee", bookingFeeMap);

                    Map<String, Object> taxMap = new HashMap<>();
                    taxMap.put("currency", fare.getTax().split(" ")[0]);
                    taxMap.put("amount", fare.getTax().split(" ")[1]);
                    fareMap.put("tax", taxMap);

                    String fareType = "";
                    if ("FIF".equals(fare.getFareClass())) {
                        fareType = "first";
                    } else if ("CIF".equals(fare.getFareClass())) {
                        fareType = "business";
                    } else if ("YIF".equals(fare.getFareClass())) {
                        fareType = "economy";
                    }
                    farePricesMap.put(fareType, fareMap);
                }
                flightMap.put("farePrices", farePricesMap);
                flights.add(new HashMap<String, Map>(){{put("flight", flightMap);}});
            }
        }
        Map<String, Object> availabilityMap = new HashMap<>();
        availabilityMap.put("availability", flights);
        return availabilityMap;
    }
}