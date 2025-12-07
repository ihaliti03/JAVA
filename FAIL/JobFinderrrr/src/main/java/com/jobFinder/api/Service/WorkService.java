package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Input.WorkInput;

public interface WorkService {
    Work saveWork(WorkInput work, Integer user_id);
    Work updateWork(WorkInput work, int work_id);
}
