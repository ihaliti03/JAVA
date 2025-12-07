package com.example.flightreservation;

import com.example.flightreservation.pojo.Airline;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AirlineTest {

    @Test
    public void testGetters() {
        Airline airline = new Airline("Norwegian Airline", 5);
        assertEquals("Norwegian Airline", airline.getName());
        assertEquals(5, airline.getNumOfFlights());
    }

    @Test
    public void testSetters() {
        Airline airline = new Airline("Norwegian Airline", 5);
        airline.setNumOfFlights(10);
        assertEquals(10, airline.getNumOfFlights());
    }
}
