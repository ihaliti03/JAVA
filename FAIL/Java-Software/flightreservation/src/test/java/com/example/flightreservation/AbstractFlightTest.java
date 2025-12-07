package com.example.flightreservation;

import com.example.flightreservation.pojo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AbstractFlightTest {

    @Test
    public void testGetters() {
        Airline airline = new Airline("Norwegian Airline", 5);
        Airport departureAirport = new Airport("Oslo International Airport", "Oslo", "Norway");
        Airport arrivalAirport = new Airport("Trondheim Airport", "Trondheim", "Norway");
        AbstractFlight flight = new DomesticFlight(airline, "AB1234", departureAirport, arrivalAirport, "10:00", "12:00", 0, 100);
        assertEquals(airline, flight.getAirline());
        assertEquals("AB1234", flight.getFlightNumber());
        assertEquals(departureAirport, flight.getDepartureAirport());
        assertEquals(arrivalAirport, flight.getArrivalAirport());
        assertEquals("10:00", flight.getDepartureTime());
        assertEquals("12:00", flight.getArrivalTime());
    }

    @Test
    public void testGetFlightType() {
        AbstractFlight domesticFlight = new DomesticFlight(new Airline("Norwegian Airline", 5), "AB1234", new Airport("Oslo International Airport", "Oslo", "Norway"), new Airport("Trondheim Airport", "Trondheim", "Norway"), "10:00", "12:00", 0, 100);
        AbstractFlight internationalFlight = new InternationalFlight(new Airline("British Airways", 10), "BA5678", new Airport("Heathrow Airport", "London", "United Kingdom"), new Airport("JFK Airport", "New York", "USA"), "14:00", "18:00", "Visa required",80,100);
        assertEquals("Domestic", domesticFlight.getFlightType());
        assertEquals("International", internationalFlight.getFlightType());
    }
}
