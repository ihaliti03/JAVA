package com.example.flightreservation.pojo;
import java.util.ArrayList;
import java.util.List;

public class AirlineReservationSystem {

    private List<AbstractFlight> flights;

    public AirlineReservationSystem() {
        flights = new ArrayList<>();
    }
    public void addFlight(AbstractFlight flight) {
        flights.add(flight);
    }
    public List<AbstractFlight> getFlights() {
        return flights;
    }
    public void removeFlight(AbstractFlight flight) {
        flights.remove(flight);
    }
    public int getNumOfFlights() {
        return flights.size();
    }
}
