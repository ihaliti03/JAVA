package com.example.flightreservation.repository;
import com.example.flightreservation.pojo.Airline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AirlineRepository extends CrudRepository<Airline, Long> {
    Airline findAirlineById(Long id);
    List<Airline> findAll();
}