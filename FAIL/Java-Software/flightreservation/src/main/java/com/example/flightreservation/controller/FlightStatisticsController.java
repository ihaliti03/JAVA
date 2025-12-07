package com.example.flightreservation.controller;
import com.example.flightreservation.pojo.Airline;
import com.example.flightreservation.service.AirlineService;
import com.example.flightreservation.service.DomesticFlightService;
import com.example.flightreservation.service.InternationalFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class FlightStatisticsController {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private DomesticFlightService domesticFlightService;

    @Autowired
    private InternationalFlightService internationalFlightService;

    @GetMapping("/numAirlines")
    public int getNumberOfAirlines() {
        return airlineService.findAllAirlines().size();
    }

    @GetMapping("/numDomesticFlights")
    public int getNumberOfDomesticFlights() {
        return domesticFlightService.findAllDomesticFlights().size();
    }

    @GetMapping("/numInternationalFlights")
    public int getNumberOfInternationalFlights() {
        return internationalFlightService.findAllInternationalFlights().size();
    }

    @GetMapping("/averagePassengersPerFlight")
    public double getAveragePassengersPerFlight() {
        int totalPassengers = domesticFlightService.getTotalPassengers() + internationalFlightService.getTotalPassengers();
        int totalFlights = getNumberOfDomesticFlights() + getNumberOfInternationalFlights();
        return totalFlights > 0 ? (double) totalPassengers / totalFlights : 0;
    }

    @GetMapping("/busiestAirline")
    public Airline getBusiestAirline() {
        return airlineService.getBusiestAirline();
    }

}