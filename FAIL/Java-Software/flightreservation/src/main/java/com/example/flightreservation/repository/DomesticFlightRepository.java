package com.example.flightreservation.repository;
import com.example.flightreservation.pojo.DomesticFlight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DomesticFlightRepository extends CrudRepository<DomesticFlight, Long> {
    DomesticFlight findDomesticFlightById(Long id);
    List<DomesticFlight> findAll();
}