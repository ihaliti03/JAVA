package com.example.flightreservation.repository;
import com.example.flightreservation.pojo.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    Passenger findPassengerById(Long id);
    List<Passenger> findAll();
}