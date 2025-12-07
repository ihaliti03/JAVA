package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Input.UserInput;

public interface UserService {
    User createUser(UserInput userInput);
    User updateUser(UserInput userInput,int user_id);
}
