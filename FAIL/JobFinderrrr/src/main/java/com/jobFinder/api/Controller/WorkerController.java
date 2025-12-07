package com.jobFinder.api.Controller;

import com.jobFinder.api.Pojo.Entity.Worker;
import com.jobFinder.api.Pojo.Input.WorkerInput;
import com.jobFinder.api.Repository.WorkerRepository;
import com.jobFinder.api.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/worker/{id}")
    public Worker getworker(@PathVariable int id){
        return workerRepository.findWorkerById(id);
    }

    @GetMapping("/workers")
    public Iterable<Worker> getworkers() {
        return workerRepository.findAll();
    }

    @PostMapping("/worker")
    public Worker addworker(@RequestBody WorkerInput worker){
        return workerService.createWorker(worker);
    }

    @PutMapping("/worker/{id}")
    public Worker updateworker(@RequestBody WorkerInput workerInput, @PathVariable int id) {
        return workerService.updateWorker(workerInput,id);
    }

    @DeleteMapping("/worker/{id}")
    public void deleteworker(@PathVariable int id) {
        workerRepository.deleteById(id);
    }
}
