package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.UserDTO;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public String createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists: " + dto.getEmail());
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());

        userRepository.save(user);
        return "User created successfully with email: " + user.getEmail() + "\nUser Id: " + user.getId();
    }

    // GET ALL USERS
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getRoles()))
                .collect(Collectors.toList());
    }

    //GET USER BY ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
    }
    //Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with Email: " + email));
    }

    //UPDATE USER
    public UserDTO updateUser(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setRoles(dto.getRoles());
        userRepository.save(existing);

        return new UserDTO(existing.getId(), existing.getName(), existing.getEmail(), existing.getPassword(), existing.getRoles());
    }

    // DELETE USER
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        return "User with ID " + id + " has been deleted successfully.";
    }
}
