package com.jobFinder.api.Repository;

import com.jobFinder.api.Pojo.Entity.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker,Integer> {
    Worker findWorkerById(Integer id);
}
