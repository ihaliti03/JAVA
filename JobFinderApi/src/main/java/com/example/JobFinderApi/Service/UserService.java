package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.User;
import com.example.JobFinderApi.Pojo.Input.UserInput;

public interface UserService {
    User createUser(UserInput userInput);
    User updateUser(UserInput userInput,int user_id);
}
