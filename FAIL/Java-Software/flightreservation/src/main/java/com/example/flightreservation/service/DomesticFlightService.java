package com.example.flightreservation.service;
import com.example.flightreservation.pojo.DomesticFlight;
import com.example.flightreservation.repository.DomesticFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DomesticFlightService {

    @Autowired
    private DomesticFlightRepository domesticFlightRepository;

    public List<DomesticFlight> findAllDomesticFlights() {
        return domesticFlightRepository.findAll();
    }
    public DomesticFlight findDomesticFlightById(Long id){
        return  domesticFlightRepository.findDomesticFlightById(id);
    }
    public DomesticFlight createDomesticFlight(DomesticFlight domesticFlight){
        return domesticFlightRepository.save(domesticFlight);
    }
    public DomesticFlight updateDomesticFlight (Long id, DomesticFlight updatedDomesticFlight){
        if (domesticFlightRepository.existsById(id)){
            if (updatedDomesticFlight!=null){
                updatedDomesticFlight.setId(id);
                updatedDomesticFlight.setDepartureTime(updatedDomesticFlight.getDepartureTime());
                updatedDomesticFlight.setArrivalTime(updatedDomesticFlight.getArrivalTime());
                updatedDomesticFlight.setFlightNumber(updatedDomesticFlight.getFlightNumber());
                updatedDomesticFlight.setCapacity(updatedDomesticFlight.getCapacity());
                updatedDomesticFlight.setNumOfPassengers(updatedDomesticFlight.getNumOfPassengers());
                updatedDomesticFlight.setAirline(updatedDomesticFlight.getAirline());
                updatedDomesticFlight.setDepartureAirport(updatedDomesticFlight.getDepartureAirport());
                updatedDomesticFlight.setArrivalAirport(updatedDomesticFlight.getArrivalAirport());
                domesticFlightRepository.save(updatedDomesticFlight);
            }else {
                throw new IllegalArgumentException("Domestic Flight cannot be null");
            }
        }else {
            throw new IllegalArgumentException("Id does not exist!");
        }
        return updatedDomesticFlight;
    }
    public void deleteDomesticFlightByID(Long id){
        domesticFlightRepository.deleteById(id);
    }
    public int getTotalPassengers() {
        List<DomesticFlight> domesticFlights = findAllDomesticFlights();
        int totalPassengers = 0;
        for (DomesticFlight flight : domesticFlights) {
            totalPassengers += flight.getNumOfPassengers();
        }
        return totalPassengers;
    }
}