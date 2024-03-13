package com.project.shopapp.services;

import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;

public interface IUserService {
    User createUser(User user) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
