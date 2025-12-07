package com.example.JobFinderApi.Repository;

import com.example.JobFinderApi.Pojo.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}
