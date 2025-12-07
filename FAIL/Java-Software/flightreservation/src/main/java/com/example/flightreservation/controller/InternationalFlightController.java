package com.example.flightreservation.controller;
import com.example.flightreservation.pojo.InternationalFlight;
import com.example.flightreservation.service.InternationalFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/international-flights")
public class InternationalFlightController {

    @Autowired
    private InternationalFlightService internationalFlightService;

    @GetMapping
    public List<InternationalFlight> getAllInternationalFlights() {
        return internationalFlightService.findAllInternationalFlights();
    }

    @GetMapping("/{id}")
    public InternationalFlight getInternationalFlightById(@PathVariable Long id) {
        return internationalFlightService.findInternationalFlightById(id);
    }

    @PostMapping
    public InternationalFlight createInternationalFlight(@RequestBody InternationalFlight internationalFlight) {
        return internationalFlightService.createInternationalFLight(internationalFlight);
    }

    @PutMapping("/{id}")
    public InternationalFlight updateInternationalFlight(@PathVariable Long id, @RequestBody InternationalFlight updatedInternationalFlight) {
        return internationalFlightService.updateInternationalFlight(id, updatedInternationalFlight);
    }

    @DeleteMapping("/{id}")
    public void deleteInternationalFlight(@PathVariable Long id) {
        internationalFlightService.deleteInternationalFlightByID(id);
    }
}