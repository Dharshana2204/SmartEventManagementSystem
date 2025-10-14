package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    //Read all the user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    //Create the user
    public User createuser(User user) {
        return userRepository.save(user);
    }
    //Read User By id
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }
    //Update user
    public User updateUser(long id ,User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            return userRepository.save(existingUser);
        }).orElse(null);
    }
    //Delete user
    public void deleteuser(long id) {
        userRepository.delete(getUserById(id));
    }
}
