package com.example.flightreservation.pojo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int numOfFlights;

    public Airline() {

    }
    public Airline(String name, int numOfFlights) {
        this.name = name;
        this.numOfFlights = numOfFlights;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumOfFlights() {
        return numOfFlights;
    }
    public void setNumOfFlights(int numOfFlights) {
        this.numOfFlights = numOfFlights;
    }
}