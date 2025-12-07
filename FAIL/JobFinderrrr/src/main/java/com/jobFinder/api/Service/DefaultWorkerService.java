package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Address;
import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Entity.Worker;
import com.jobFinder.api.Pojo.Input.UserInput;
import com.jobFinder.api.Pojo.Input.WorkerInput;
import com.jobFinder.api.Repository.AddressRepository;
import com.jobFinder.api.Repository.WorkRepository;
import com.jobFinder.api.Repository.WorkerRepository;
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
