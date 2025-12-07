package com.example.schoolservice.Controller;

import com.example.schoolservice.POJO.School;
import com.example.schoolservice.Service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/school")
@RestController
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public List<School> getSchools() {
        return schoolService.fetchSchools();
    }

    @GetMapping("/{id}")
    public School getSchoolById(@PathVariable int id) {
        return schoolService.fetchSchoolById(id);
    }

    @PostMapping
    public School addSchool(@RequestBody School school) {
        return schoolService.saveSchool(school);
    }



}
