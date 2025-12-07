package com.jobFinder.api.Repository;

import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Entity.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}
