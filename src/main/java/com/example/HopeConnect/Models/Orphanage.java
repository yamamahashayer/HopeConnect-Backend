package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orphanage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orphanage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String contactPerson;
    private Integer capacity;
    private Integer currentOrphans;

    @Enumerated(EnumType.STRING)
    private OrphanageStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    //diala
    @OneToMany(mappedBy = "orphanage")
    @JsonManagedReference("donation-orphanage")
    private List<Donation> donations;

    //diala
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orphanage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orphanage-orphans")
    private List<Orphan> orphans = new ArrayList<>();

    @OneToMany(mappedBy = "orphanage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orphanageproject")
    private List<OrphanProject> orphanProjects = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
