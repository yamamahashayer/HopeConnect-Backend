package com.example.HopeConnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne

    @JoinColumn(name = "emergency_campaign_id", nullable = true)
    private EmergencyCampaign emergencyCampaign;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "donor_id", nullable = false)
    @JsonIgnore
    private Donor donor;
//diala
@ManyToOne
@JsonBackReference
@JoinColumn(name = "orphan_id", insertable = false, updatable = false)
private Orphan orphan;
    @ManyToOne
    @JsonBackReference
    private Orphanage orphanage;

    @Column(name = "orphan_id")
    private Long orphanId;

    @Column(name = "project_id")
    private Long projectId;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date donationDate;

    public enum DonationType { ONE_TIME, MONTHLY }
    public enum PaymentStatus { PENDING, COMPLETED, FAILED }

    public Donation() {}

    public Donation(Donor donor, Long orphanId, Long projectId, BigDecimal amount, String currency, DonationType donationType, PaymentStatus paymentStatus, Date donationDate) {
        this.donor = donor;
        this.orphanId = orphanId;
        this.projectId = projectId;
        this.amount = amount;
        this.currency = currency;
        this.donationType = donationType;
        this.paymentStatus = paymentStatus;
        this.donationDate = donationDate;
    }

    public Long getId() { return id; }
    public Donor getDonor() { return donor; }
    public Long getOrphanId() { return orphanId; }
    public Long getProjectId() { return projectId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public DonationType getDonationType() { return donationType; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public Date getDonationDate() { return donationDate; }

    public void setDonor(Donor donor) { this.donor = donor; }
    public void setOrphanId(Long orphanId) { this.orphanId = orphanId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setDonationType(DonationType donationType) { this.donationType = donationType; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setDonationDate(Date donationDate) { this.donationDate = donationDate; }

    //diala
    public Orphan getOrphan() {
        return orphan;
    }
    public Orphanage getOrphanage() {
        return orphanage;
    }

    public void setOrphanage(Orphanage orphanage) {
        this.orphanage = orphanage;
    }

}