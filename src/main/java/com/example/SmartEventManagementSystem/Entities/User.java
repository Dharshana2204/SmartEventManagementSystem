package com.example.SmartEventManagementSystem.Entities;

import com.example.SmartEventManagementSystem.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
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
