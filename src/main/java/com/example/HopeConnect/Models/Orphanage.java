package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.OrphanageStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orphanages")
public class Orphanage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(length = 20, unique = true)
    private String phone;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String contactPerson;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int currentOrphans;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrphanageStatus status;

    // العلاقة بين دار الأيتام والمدير (مدير واحد لكل دار أيتام)
    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false, unique = true)
    private User manager;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ التحقق من عدم تجاوز `currentOrphans` الحد الأقصى (`capacity`)
    public void setCurrentOrphans(int currentOrphans) {
        if (currentOrphans > this.capacity) {
            throw new IllegalArgumentException("Current orphans cannot exceed capacity!");
        }
        this.currentOrphans = currentOrphans;
    }

    // ✅ حساب عدد الأماكن المتاحة للأيتام حتى الامتلاء
    public int orphanCount() {
        return Math.max(capacity - currentOrphans, 0);
    }

    // ✅ تحسين `toString()` لتسهيل التصحيح أثناء التشغيل
    @Override
    public String toString() {
        return "Orphanage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", capacity=" + capacity +
                ", currentOrphans=" + currentOrphans +
                ", status=" + status +
                ", manager=" + (manager != null ? manager.getName() : "No Manager") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentOrphans() { return currentOrphans; }

    public OrphanageStatus getStatus() { return status; }
    public void setStatus(OrphanageStatus status) { this.status = status; }

    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
