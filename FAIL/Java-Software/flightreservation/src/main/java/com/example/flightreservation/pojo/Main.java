package com.example.flightreservation.pojo;

public class Main {
    public static void main(String[] args) {
        // Create an airline
        Airline airline = new Airline("Skopje FlightReservation.Airline", 100);

        // Create airports
        Airport departureAirport1 = new Airport("Dortmund International FlightReservation.Airport", "Dortmund", "Germany");
        Airport arrivalAirport1 = new Airport("Hamburg FlightReservation.Airport", "Hamburg", "Germany");

        Airport departureAirport2 = new Airport("Skopje International FlightReservation.Airport", "Skopje", "North Macedonia");
        Airport arrivalAirport2 = new Airport("London Heathrow FlightReservation.Airport", "London", "United Kingdom");

        // Create a domestic flight
        DomesticFlight domesticFlight = new DomesticFlight(airline, "ABC123", departureAirport1,
                arrivalAirport1, "2024-05-01 08:00", "2024-05-01 09:00", 100, 155);

        // Create an international flight
        InternationalFlight internationalFlight = new InternationalFlight(airline, "XYZ456",
                departureAirport2, arrivalAirport2, "2024-05-01 14:00", "2024-05-01 16:00", "Visa Required",80,100);

        // Create an airline reservation system
        AirlineReservationSystem system = new AirlineReservationSystem();

        // Add the flights to the system
        system.addFlight(domesticFlight);
        system.addFlight(internationalFlight);

        // Display the flights in the system
        for (AbstractFlight flight : system.getFlights()) {
            System.out.println(flight.getFlightType() + " Flight");
            System.out.println("FlightReservation.Airline: " + flight.getAirline().getName());
            System.out.println("Flight Number: " + flight.getFlightNumber());
            System.out.println("Departure FlightReservation.Airport: " + flight.getDepartureAirport().getName());
            System.out.println("Arrival FlightReservation.Airport: " + flight.getArrivalAirport().getName());
            System.out.println("Departure Time: " + flight.getDepartureTime());
            System.out.println("Arrival Time: " + flight.getArrivalTime());

            if (flight instanceof DomesticFlight) {
                System.out.println("Number of Passengers: " + ((DomesticFlight) flight).getNumOfPassengers());
            } else if (flight instanceof InternationalFlight) {
                System.out.println("Visa Requirement: " + ((InternationalFlight) flight).getVisaRequirement());
            }

            System.out.println();
        }
    }
}
