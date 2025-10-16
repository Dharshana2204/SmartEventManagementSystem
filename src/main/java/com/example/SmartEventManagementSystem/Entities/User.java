package com.example.SmartEventManagementSystem.Entities;

import com.example.SmartEventManagementSystem.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@Table(name="users")
public class User
{
@NoArgsConstructor
@AllArgsConstructor

public class User {
    public enum Role {USER, ADMIN}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(nullable=false)
    @NotBlank(message = "Password Cannot be empty")
    @Size(min = 8,max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters, include letters and digits")
    private String password;
    @Column(name="ROLES")
    private String roles;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Registration> registrations;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Event> events;

}
