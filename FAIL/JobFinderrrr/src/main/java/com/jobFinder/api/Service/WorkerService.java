package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Entity.Worker;
import com.jobFinder.api.Pojo.Input.UserInput;
import com.jobFinder.api.Pojo.Input.WorkInput;
import com.jobFinder.api.Pojo.Input.WorkerInput;

public interface WorkerService {
    Worker createWorker(WorkerInput workerInput);
    Worker updateWorker(WorkerInput workerInput,int worker_id);

}
