package com.example.SmartEventManagementSystem.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class User {
    public enum Role {USER, ADMIN}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    @NotBlank(message = "Email Cannot be empty")
    @Email(message = "Invalid Email formate")
    private String email;
    @Column(nullable=false)
    @NotBlank(message = "Password Cannot be empty")
    private String password;
    @Column(nullable=false)
    @NotBlank(message = "Name Cannot be empty")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role=Role.USER;
    @ManyToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Registration> registrations;
    @ManyToMany(mappedBy = "event",cascade=CascadeType.ALL)
    private List<Event> events;




}
