package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Address;
import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Input.WorkInput;
import com.jobFinder.api.Repository.AddressRepository;
import com.jobFinder.api.Repository.UserRepository;
import com.jobFinder.api.Repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkService implements WorkService{

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Work saveWork(WorkInput _work, Integer user_id) {
        addressRepository.save(_work.getAddress());
        User user = userRepository.findUserById(user_id);
        Work work = new Work(_work.getTitle(),_work.getDescription(),_work.getAddress(),user);
        return workRepository.save(work);
    }

    @Override
    public Work updateWork(WorkInput _work, int work_id) {
        Work work = workRepository.findById(work_id);
        work.setTitle(_work.getTitle());
        if (_work.getAddress() != null){
            addressRepository.save(_work.getAddress());
            work.setAddress(_work.getAddress());
        }
        return workRepository.save(work);
    }
}
