package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.Address;
import com.example.JobFinderApi.Pojo.Entity.Worker;
import com.example.JobFinderApi.Pojo.Input.WorkerInput;
import com.example.JobFinderApi.Repository.AddressRepository;
import com.example.JobFinderApi.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkerService implements WorkerService{

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Worker createWorker(WorkerInput workerInput) {
        Address address = new Address(workerInput.getAddress().getCity(),workerInput.getAddress().getCountry());
        addressRepository.save(address);
        Worker worker = new Worker(workerInput.getUsername(), address);
        return workerRepository.save(worker);
    }

    @Override
    public Worker updateWorker(WorkerInput workerInput, int worker_id) {
        Worker worker = workerRepository.findWorkerById(worker_id);
        if (workerInput.getAddress() != null){
            Address address = new Address(workerInput.getAddress().getCity(),workerInput.getAddress().getCountry());
            addressRepository.save(address);
            worker.setAddress(address);
        }
        worker.setUsername(workerInput.getUsername());
        return workerRepository.save(worker);
    }
}

