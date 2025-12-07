package com.example.schoolservice.Repository;

import com.example.schoolservice.POJO.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISchoolRepository extends JpaRepository<School, Integer> {

}
