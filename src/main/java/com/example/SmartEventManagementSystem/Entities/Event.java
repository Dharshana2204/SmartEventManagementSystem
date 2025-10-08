package com.example.SmartEventManagementSystem.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String venueAddress;
    @Column(nullable = false)
    private String venueCity;
    @Column(nullable = false)
    private String venueState;
    @Column(nullable = false)
    private String venueCountry;
    @Column(nullable = false)
    private String venueZipCode;

    //Relation-Ship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

}
