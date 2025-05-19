package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.DonationType;

import java.math.BigDecimal;
import java.util.Date;

public class DonationDTO {
    private Long donorId;
    private Long orphanId;
    private Long orphanageId;
    private Long projectId;
    private Long emergencyCampaignId;
    private BigDecimal amount;
    private String currency;
    private String donationType;
    private String paymentStatus;
    private Date donationDate;

    // Getters and Setters
    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public Long getOrphanId() {
        return orphanId;
    }
    public Long getOrphanageId() {
        return orphanageId;
    }

    public void setOrphanId(Long orphanId) {
        this.orphanId = orphanId;
    }
    public void setOrphanageId(Long orphanageId) {
        this.orphanageId = orphanageId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEmergencyCampaignId() {
        return emergencyCampaignId;
    }

    public void setEmergencyCampaignId(Long emergencyCampaignId) {
        this.emergencyCampaignId = emergencyCampaignId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(DonationType donationType) {
        this.donationType = String.valueOf(donationType);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }
}
