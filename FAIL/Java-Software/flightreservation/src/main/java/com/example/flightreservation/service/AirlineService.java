package com.example.flightreservation.service;
import com.example.flightreservation.pojo.Airline;
import com.example.flightreservation.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    public List<Airline> findAllAirlines() {
        return airlineRepository.findAll();
    }
    public Airline findAirlineById(Long id) {
        return airlineRepository.findAirlineById(id);
    }
    public Airline createAirline(Airline airline){
        return airlineRepository.save(airline);
    }
    public Airline updateAirline (Long id, Airline updatedAirline){
        if (airlineRepository.existsById(id)){
            if (updatedAirline!=null){
                updatedAirline.setId(id);
                updatedAirline.setName(updatedAirline.getName());
                updatedAirline.setNumOfFlights(updatedAirline.getNumOfFlights());
                airlineRepository.save(updatedAirline);
            }else {
                throw new IllegalArgumentException("Airline cannot be null");
            }
        }else {
            throw new IllegalArgumentException("Id does not exist!");
        }
        return updatedAirline;
    }
    public void deleteAirlineByID(Long id){
        airlineRepository.deleteById(id);
    }
    public Airline getBusiestAirline() {
        List<Airline> airlines = findAllAirlines();
        Airline busiestAirline = null;
        int maxFlights = 0;
        for (Airline airline : airlines) {
            int numFlights = airline.getNumOfFlights();
            if (numFlights > maxFlights) {
                maxFlights = numFlights;
                busiestAirline = airline;
            }
        }
        return busiestAirline;
    }
}