package com.example.studentservice.Service;

import com.example.studentservice.POJO.DTO.School;
import com.example.studentservice.POJO.DTO.StudentResponse;
import com.example.studentservice.Repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentService {

    @Autowired
    private IStudentRepository studentService;
    @Autowired
    private RestTemplate restTemplate;

    public StudentResponse getStudentById(int id){
        Student student = studentRepository.findById(id).get();
        School school = restTemplate.getForObject("http://localhost:8082/school/" + student.getSchoolId(), school.class);
        StudentResponse response = new StudentResponse(student.getId(), student.getName(), student.getSurname(), student.getAge(), school);
        return response;
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }


}
