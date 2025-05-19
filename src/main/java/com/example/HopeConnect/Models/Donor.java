package com.example.HopeConnect.Models;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "donors")


public class Donor {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //diala
    @OneToMany(mappedBy = "donor")
    @JsonManagedReference("donation-donor")
    private List<Donation> donations;
//diala

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int totalSupportedProjects;
    private Date lastDonationDate;

    @Enumerated(EnumType.STRING)
    private DonorStatus donorStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public enum DonorStatus { ACTIVE, INACTIVE, PENDING }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public Donor() {}

    public Donor(User user, int totalSupportedProjects, Date lastDonationDate, DonorStatus donorStatus) {
        this.user = user;
        this.totalSupportedProjects = totalSupportedProjects;
        this.lastDonationDate = lastDonationDate;
        this.donorStatus = donorStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalSupportedProjects() {
        return totalSupportedProjects;
    }

    public void setTotalSupportedProjects(int totalSupportedProjects) {
        this.totalSupportedProjects = totalSupportedProjects;
    }

    public Date getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(Date lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public DonorStatus getDonorStatus() {
        return donorStatus;
    }

    public void setDonorStatus(DonorStatus donorStatus) {
        this.donorStatus = donorStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", user=" + user +
                ", totalSupportedProjects=" + totalSupportedProjects +
                ", lastDonationDate=" + lastDonationDate +
                ", donorStatus=" + donorStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
