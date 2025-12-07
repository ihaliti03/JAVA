package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.Work;
import com.example.JobFinderApi.Pojo.Input.WorkInput;

public interface WorkService {
    Work saveWork(WorkInput work, Integer user_id);
    Work updateWork(WorkInput work, int work_id);
}
