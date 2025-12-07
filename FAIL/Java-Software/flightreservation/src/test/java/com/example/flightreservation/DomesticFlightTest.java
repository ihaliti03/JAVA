package com.example.flightreservation;

import com.example.flightreservation.pojo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DomesticFlightTest {
    private DomesticFlight domesticFlight;

    @BeforeEach
    public void setUp() {
        Airline airline = new Airline("Norwegian Airline", 5);
        Airport departureAirport = new Airport("Oslo International Airport", "Oslo", "Norway");
        Airport arrivalAirport = new Airport("Trondheim Airport", "Trondheim", "Norway");
        domesticFlight = new DomesticFlight(airline, "NA1234", departureAirport, arrivalAirport,
                "2023-05-01 09:00", "2023-05-01 11:00", 0, 100);
    }

    @Test
    public void testGetFlightType() {
        String flightType = domesticFlight.getFlightType();
        Assertions.assertEquals("Domestic", flightType);
    }

    @Test
    public void testGetFlightNumber() {
        String flightNumber = domesticFlight.getFlightNumber();
        Assertions.assertEquals("NA1234", flightNumber);
    }

    @Test
    public void testGetDepartureAirport() {
        Airport departureAirport = domesticFlight.getDepartureAirport();
        Assertions.assertEquals("Oslo International Airport", departureAirport.getName());
        Assertions.assertEquals("Oslo", departureAirport.getCity());
        Assertions.assertEquals("Norway", departureAirport.getCountry());
    }

    @Test
    public void testGetArrivalAirport() {
        Airport arrivalAirport = domesticFlight.getArrivalAirport();
        Assertions.assertEquals("Trondheim Airport", arrivalAirport.getName());
        Assertions.assertEquals("Trondheim", arrivalAirport.getCity());
        Assertions.assertEquals("Norway", arrivalAirport.getCountry());
    }

    @Test
    public void testGetCapacity() {
        int capacity = domesticFlight.getCapacity();
        Assertions.assertEquals(100, capacity);
    }

    @Test
    public void testGetNumOfPassengers() {
        int numOfPassengers = domesticFlight.getNumOfPassengers();
        Assertions.assertEquals(0, numOfPassengers);
    }
}

