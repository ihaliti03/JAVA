package com.example.JobFinderApi.Controller;

import com.example.JobFinderApi.Pojo.Entity.Work;
import com.example.JobFinderApi.Pojo.Input.WorkInput;
import com.example.JobFinderApi.Repository.WorkRepository;
import com.example.JobFinderApi.Service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkRepository workRepository;

    @GetMapping("/works")
    public Iterable<Work> getWorks(){
        return workRepository.findAll();
    }

    @GetMapping("/work/{work_id}")
    public Work getWork(@PathVariable int work_id){
        return workRepository.findById(work_id);
    }

    @GetMapping("/work/user/{user_id}")
    public List<Work> getWorkByUserId(@PathVariable int user_id){
        return workRepository.findAllByUser_id(user_id);
    }

    @PostMapping("/work/{id}")
    public Work addWork(@RequestBody WorkInput _work, @PathVariable Integer id){
        return workService.saveWork(_work,id);
    }

    @PutMapping("/work/{work_id}")
    public Work updateWork(@RequestBody WorkInput workInput,@PathVariable int work_id){
        return workService.updateWork(workInput,work_id);
    }

    @DeleteMapping("/work/{work_id}")
    public void deleteWork(@PathVariable int work_id){
        workRepository.deleteById(work_id);
    }
}
