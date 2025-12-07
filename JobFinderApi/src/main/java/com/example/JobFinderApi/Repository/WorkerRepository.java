package com.example.JobFinderApi.Repository;

import com.example.JobFinderApi.Pojo.Entity.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker,Integer> {
    Worker findWorkerById(Integer id);
}
