package com.example.flightreservation.controller;
import com.example.flightreservation.pojo.DomesticFlight;
import com.example.flightreservation.service.DomesticFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/domestic-flights")
public class DomesticFlightController {

    @Autowired
    private DomesticFlightService domesticFlightService;

    @GetMapping
    public List<DomesticFlight> getAllDomesticFlights() {
        return domesticFlightService.findAllDomesticFlights();
    }

    @GetMapping("/{id}")
    public DomesticFlight getDomesticFlightById(@PathVariable Long id) {
        return domesticFlightService.findDomesticFlightById(id);
    }

    @PostMapping
    public DomesticFlight createDomesticFlight(@RequestBody DomesticFlight domesticFlight) {
        return domesticFlightService.createDomesticFlight(domesticFlight);
    }

    @PutMapping("/{id}")
    public DomesticFlight updateDomesticFlight(@PathVariable Long id, @RequestBody DomesticFlight updatedDomesticFlight) {
        return domesticFlightService.updateDomesticFlight(id,updatedDomesticFlight);
    }

    @DeleteMapping("/{id}")
    public void deleteDomesticFlight(@PathVariable Long id) {
        domesticFlightService.deleteDomesticFlightByID(id);
    }
}