package com.example.SmartEventManagementSystem.DTO;

import com.example.SmartEventManagementSystem.Entities.eventMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCountDTO
{
    @NotNull(message = "Event ID cannot be null")
    private Long eventId;
    @NotBlank(message = "Event title cannot be blank")
    @Size(max = 150, message = "Event title cannot exceed 150 characters")
    private String eventTitle;
    @NotBlank(message = "Event MODE cannot be blank")
    private eventMode mode;
    private long registrationCount;
}
