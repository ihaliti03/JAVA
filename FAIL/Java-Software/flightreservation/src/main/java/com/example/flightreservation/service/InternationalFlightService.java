package com.example.flightreservation.service;
import com.example.flightreservation.pojo.InternationalFlight;
import com.example.flightreservation.repository.InternationalFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InternationalFlightService {

    @Autowired
    private InternationalFlightRepository internationalFlightRepository;

    public List<InternationalFlight> findAllInternationalFlights() {
        return internationalFlightRepository.findAll();
    }
    public InternationalFlight findInternationalFlightById(Long id){
        return  internationalFlightRepository.findInternationalFlightById(id);
    }
    public InternationalFlight createInternationalFLight(InternationalFlight internationalFlight){
        return internationalFlightRepository.save(internationalFlight);
    }
    public InternationalFlight updateInternationalFlight (Long id, InternationalFlight updatedInternationalFlight){
        if (internationalFlightRepository.existsById(id)){
            if (updatedInternationalFlight!=null){
                updatedInternationalFlight.setId(id);
                updatedInternationalFlight.setArrivalTime(updatedInternationalFlight.getArrivalTime());
                updatedInternationalFlight.setDepartureTime(updatedInternationalFlight.getDepartureTime());
                updatedInternationalFlight.setFlightNumber(updatedInternationalFlight.getFlightNumber());
                updatedInternationalFlight.setVisaRequirement(updatedInternationalFlight.getVisaRequirement());
                updatedInternationalFlight.setAirline(updatedInternationalFlight.getAirline());
                updatedInternationalFlight.setArrivalAirport(updatedInternationalFlight.getArrivalAirport());
                updatedInternationalFlight.setDepartureAirport(updatedInternationalFlight.getDepartureAirport());
                updatedInternationalFlight.setCapacity(updatedInternationalFlight.getCapacity());
                updatedInternationalFlight.setNumOfPassengers(updatedInternationalFlight.getNumOfPassengers());
                internationalFlightRepository.save(updatedInternationalFlight);
            }else {
                throw new IllegalArgumentException("International Flight cannot be null");
            }
        }else {
            throw new IllegalArgumentException("Id does not exist!");
        }
        return updatedInternationalFlight;
    }
    public void deleteInternationalFlightByID(Long id){
        internationalFlightRepository.deleteById(id);
    }
    public int getTotalPassengers() {
        List<InternationalFlight> internationalFlights = findAllInternationalFlights();
        int totalPassengers = 0;
        for (InternationalFlight flight : internationalFlights) {
            totalPassengers += flight.getNumOfPassengers();
        }
        return totalPassengers;
    }
}