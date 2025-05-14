package com.example.HopeConnect.Models;
public class PaymentRequest {
    private long amount;
    private Long sponsorActivityId;


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
