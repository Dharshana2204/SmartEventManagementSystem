package com.example.SmartEventManagementSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    @NotNull(message = "Registration ID cannot be null")
    private Long registrationId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "User name cannot be blank")
    @Size(max = 100, message = "User name cannot exceed 100 characters")
    private String userName;

    @NotBlank(message = "User email cannot be blank")
    @Email(message = "Invalid email format")
    private String userEmail;

    @NotNull(message = "Event ID cannot be null")
    private Long eventId;

    @NotBlank(message = "Event title cannot be blank")
    @Size(max = 150, message = "Event title cannot exceed 150 characters")
    private String eventTitle;

    @NotBlank(message = "Venue name cannot be blank")
    @Size(max = 100, message = "Venue name cannot exceed 100 characters")
    private String venueName;

    @NotBlank(message = "Venue city cannot be blank")
    @Size(max = 50, message = "Venue city cannot exceed 50 characters")
    private String venueCity;

    @NotBlank(message = "Email message cannot be blank")
    @Size(max = 500, message = "Email message cannot exceed 500 characters")
    private String emailMessage;
}
