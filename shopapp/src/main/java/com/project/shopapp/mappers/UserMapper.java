package com.project.shopapp.mappers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper extends GenericMapper<UserDTO, User> {
}
