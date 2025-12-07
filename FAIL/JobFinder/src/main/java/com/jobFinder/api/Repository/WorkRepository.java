package com.jobFinder.api.Repository;

import com.jobFinder.api.Pojo.Entity.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends CrudRepository<Work, Integer> {
    List<Work> findAllByUser_id(Integer user_id);
    Work findById(int work_id);
}
