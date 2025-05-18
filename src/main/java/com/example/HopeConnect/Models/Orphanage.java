package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @JsonManagedReference

    @JoinColumn(name = "manager_id")
    private User manager;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orphanage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("orphanage-project")
    private List<OrphanProject> orphanProjects = new ArrayList<>();



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }



    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentOrphans() {
        return currentOrphans;
    }

    public void setCurrentOrphans(Integer currentOrphans) {
        this.currentOrphans = currentOrphans;
    }

    public OrphanageStatus getStatus() {
        return status;
    }

    public void setStatus(OrphanageStatus status) {
        this.status = status;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}