package com.example.flightreservation.service;
import com.example.flightreservation.pojo.Airport;
import com.example.flightreservation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AirportService {

    @Autowired
    private  AirportRepository airportRepository;

    public List<Airport> findAllAirports() {
        return airportRepository.findAll();
    }
    public Airport findAirportById(Long id) {
        return airportRepository.findAirportById(id);
    }
    public Airport createAirport(Airport airport){
        return airportRepository.save(airport);
    }
    public Airport updateAirport (Long id, Airport updatedAirport){
        if (airportRepository.existsById(id)){
            if (updatedAirport!=null){
                updatedAirport.setId(id);
                updatedAirport.setName(updatedAirport.getName());
                updatedAirport.setCity(updatedAirport.getCity());
                updatedAirport.setCountry(updatedAirport.getCountry());
                airportRepository.save(updatedAirport);
            }else {
                throw new IllegalArgumentException("Airport cannot be null");
            }
        }else {
            throw new IllegalArgumentException("Id does not exist!");
        }
        return updatedAirport;
    }
    public void deleteAirlineByID(Long id){
        airportRepository.deleteById(id);
    }
}