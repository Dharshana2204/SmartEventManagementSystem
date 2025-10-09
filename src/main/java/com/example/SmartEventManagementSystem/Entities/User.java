package com.example.SmartEventManagementSystem.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

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
    @Size(min = 8,max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters, include letters and digits")
    private String password;
    @Column(nullable=false)
    @NotBlank(message = "Name Cannot be empty")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable=false, columnDefinition = "VARCHAR(20)")
    private Role role=Role.USER;
    // registration is the owning side in Registration (many registrations belong to one user)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-registrations")
    private List<Registration> registrations;
    // events created/owned by this user - mappedBy 'user' in Event
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-events")
    private List<Event> events;
    // feedbacks left by this user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-feedbacks")
    private List<Feedback> feedbacks;
}
