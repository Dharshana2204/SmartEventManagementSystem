package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.UserDTO;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public User createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists: " + dto.getEmail());
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());

        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
    //Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with Email: " + email));
    }

    //UPDATE USER
    public User updateUser(Long id, UserDTO dto) {
        User existing = getUserById(id);
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setRoles(dto.getRoles());
        return userRepository.save(existing);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
