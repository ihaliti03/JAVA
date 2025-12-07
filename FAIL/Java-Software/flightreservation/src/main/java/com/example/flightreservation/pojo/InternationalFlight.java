package com.example.flightreservation.pojo;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "international_flight")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class InternationalFlight extends AbstractFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String visaRequirement;
    @Column(name = "num_of_passengers")
    private int numOfPassengers;
    @OneToMany
    @JoinColumn(name = "passengers_id")
    private List<Passenger> passengers;
    private int capacity;

    public InternationalFlight() {
    }

    public InternationalFlight(Airline airline, String flightNumber, Airport departureAirport,
                               Airport arrivalAirport, String departureTime, String arrivalTime,
                               String visaRequirement, int numOfPassengers, int capacity) {
        super(airline, flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime);
        this.visaRequirement = visaRequirement;
        this.numOfPassengers = numOfPassengers;
        this.capacity = capacity;
        passengers = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getVisaRequirement() {
        return visaRequirement;
    }

    public void setVisaRequirement(String visaRequirement) {
        this.visaRequirement = visaRequirement;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getFlightType() {
        return "International";
    }
}
