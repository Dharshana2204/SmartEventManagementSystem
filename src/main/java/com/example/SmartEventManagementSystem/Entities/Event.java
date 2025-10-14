package com.example.SmartEventManagementSystem.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name cannot be Empty")
    @Column(nullable = false)
    private String eventTitle;
    @Lob
    private String eventDescription;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    private int capacity;
    private boolean isTrending = false;
    //Location
    @Column(nullable = false)
    private String venueName;
    @Column(nullable = false)
    private String venueCity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private eventMode mode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    //Relation-Ship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

}
