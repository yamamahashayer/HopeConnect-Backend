package com.example.HopeConnect.Models;
public class PaymentRequest {
    private long amount;
    private Long sponsorActivityId;
    private Long donationId;

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;

    }

    public Long getSponsorActivityId() {
        return sponsorActivityId;
    }

    public void setSponsorActivityId(Long sponsorActivityId) {
        this.sponsorActivityId = sponsorActivityId;
    }
}
