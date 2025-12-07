package com.example.flightreservation.service;
import com.example.flightreservation.pojo.Passenger;
import com.example.flightreservation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private  PassengerRepository passengerRepository;

    public List<Passenger> findAllPassengers() {
        return passengerRepository.findAll();
    }
    public Passenger findPassengerById(Long id) {
        return passengerRepository.findPassengerById(id);
    }
    public Passenger createPassenger(Passenger passenger){
        return passengerRepository.save(passenger);
    }
    public Passenger updatePassenger (Long id, Passenger updatedPassenger){
        if (passengerRepository.existsById(id)){
            if (updatedPassenger!=null){
                updatedPassenger.setId(id);
                updatedPassenger.setFirstName(updatedPassenger.getFirstName());
                updatedPassenger.setLastName(updatedPassenger.getLastName());
                updatedPassenger.setAddress(updatedPassenger.getAddress());
                updatedPassenger.setPassportNumber(updatedPassenger.getPassportNumber());
                passengerRepository.save(updatedPassenger);
            }else {
                throw new IllegalArgumentException("Passenger cannot be null");
            }
        }else {
            throw new IllegalArgumentException("Id does not exist!");
        }
        return updatedPassenger;
    }
    public void deletePassengerByID(Long id){
        passengerRepository.deleteById(id);
    }
}