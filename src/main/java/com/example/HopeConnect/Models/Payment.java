package com.example.HopeConnect.Models;

import jakarta.persistence.*;


@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;
    private String paymentStatus;
    private String stripeSessionId;
    private String customerEmail;
    @ManyToOne
    @JoinColumn(name = "sponsor_activity_id", nullable = false)  // نربطه بالعمود
    private SponsorActivity sponsorActivity;


    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }
    public SponsorActivity getSponsorActivity() {
        return sponsorActivity;
    }

    public void setSponsorActivity(SponsorActivity sponsorActivity) {
        this.sponsorActivity = sponsorActivity;
    }
}
