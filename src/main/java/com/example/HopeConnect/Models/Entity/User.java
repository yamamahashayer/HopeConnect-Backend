package com.example.HopeConnect.Models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name; // User name

    @Column(nullable = false, unique = true, length = 150)
    private String email; // Unique email

    @Column(nullable = false, length = 255)
    private String password; // User password

    @Column(nullable = false, length = 20)
    private String phone; // Phone number

    @Column(nullable = false, length = 50)
    private String nationality; // Nationality

    @Column(nullable = false, length = 50)
    private String country; // Country

    @Column(nullable = false, length = 50)
    private String city; // City

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType; // User Type (ENUM: sponsor, donor, volunteer, admin)


    @Column
    private LocalDateTime lastLogin; // Timestamp of last login

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Auto-set creation date

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}