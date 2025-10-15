package com.example.SmartEventManagementSystem.DTO;

import com.example.SmartEventManagementSystem.Entities.eventMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCountDTO {
    private Long eventId;
    private String eventTitle;
    private eventMode mode;
    private long registrationCount;
}
