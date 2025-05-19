package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.SponsorStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "total_sponsored_orphans", nullable = false)
    private int totalSponsoredOrphans = 0;

    @Column(name = "total_donations", nullable = false)
    private double totalDonations = 0.00;

    @Column(name = "sponsorship_start_date")
    private LocalDate sponsorshipStartDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sponsor_status", nullable = false)
    private SponsorStatus sponsorStatus = SponsorStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "sponsor")
    @JsonManagedReference("sponsor-orphans")
    private List<Orphan> orphans;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getTotalSponsoredOrphans() { return totalSponsoredOrphans; }
    public void setTotalSponsoredOrphans(int totalSponsoredOrphans) { this.totalSponsoredOrphans = totalSponsoredOrphans; }

    public double getTotalDonations() { return totalDonations; }
    public void setTotalDonations(double totalDonations) { this.totalDonations = totalDonations; }

    public LocalDate getSponsorshipStartDate() { return sponsorshipStartDate; }
    public void setSponsorshipStartDate(LocalDate sponsorshipStartDate) { this.sponsorshipStartDate = sponsorshipStartDate; }

    public SponsorStatus getSponsorStatus() { return sponsorStatus; }
    public void setSponsorStatus(SponsorStatus sponsorStatus) { this.sponsorStatus = sponsorStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Orphan> getOrphans() { return orphans; }
    public void setOrphans(List<Orphan> orphans) { this.orphans = orphans; }
}
