package com.example.schoolservice.Service;

import com.example.schoolservice.POJO.School;
import com.example.schoolservice.Repository.ISchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private ISchoolRepository schoolRepository;

    public List<School> fetchSchools(){
        return schoolRepository.findAll();
    }

    public School fetchSchoolById(int id){
        return schoolRepository.findById(id).get();
    }

    public School saveSchool(School school){
        return schoolRepository.save(school);
    }

}
