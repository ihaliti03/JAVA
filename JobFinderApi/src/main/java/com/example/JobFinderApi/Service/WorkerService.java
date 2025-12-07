package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.Worker;
import com.example.JobFinderApi.Pojo.Input.WorkerInput;

public interface WorkerService {
    Worker createWorker(WorkerInput workerInput);
    Worker updateWorker(WorkerInput workerInput,int worker_id);

}
