package com.example.flightreservation.repository;
import com.example.flightreservation.pojo.InternationalFlight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InternationalFlightRepository extends CrudRepository<InternationalFlight, Long> {
    List<InternationalFlight> findAll();
    InternationalFlight findInternationalFlightById(Long id);
}