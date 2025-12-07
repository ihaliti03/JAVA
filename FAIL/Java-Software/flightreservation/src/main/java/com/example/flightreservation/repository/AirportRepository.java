package com.example.flightreservation.repository;
import com.example.flightreservation.pojo.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
    Airport findAirportById(Long id);
    List<Airport> findAll();
}