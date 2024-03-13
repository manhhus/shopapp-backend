package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(User user) throws DataNotFoundException {
        String phoneNumber = user.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        user.setRole(role);
        if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
            String password = user.getPassword();
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
