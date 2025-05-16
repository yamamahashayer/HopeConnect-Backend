package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Payment;
import com.example.HopeConnect.Models.PaymentRequest;
import com.example.HopeConnect.Models.SponsorActivity;
import com.example.HopeConnect.Repositories.PaymentRepository;
import com.example.HopeConnect.Repositories.SponsorActivityRepository;
import com.example.HopeConnect.Services.StripeCheckoutService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeCheckoutService stripeCheckoutService;
    private final PaymentRepository paymentRepository;
    @Autowired
    private SponsorActivityRepository sponsorActivityRepository;  // Inject repo


    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public PaymentController(PaymentRepository paymentRepository, StripeCheckoutService stripeCheckoutService) {
        this.paymentRepository = paymentRepository;
        this.stripeCheckoutService = stripeCheckoutService;
    }

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestBody PaymentRequest paymentRequest) {
        try {
            long amount = paymentRequest.getAmount();
            Long sponsorActivityId = paymentRequest.getSponsorActivityId();

            Optional<SponsorActivity> sponsorActivityOptional = sponsorActivityRepository.findById(sponsorActivityId);
            if (!sponsorActivityOptional.isPresent()) {
                return "Invalid sponsorActivityId";
            }

            SponsorActivity sponsorActivity = sponsorActivityOptional.get();

            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setPaymentStatus("pending");
            payment.setSponsorActivity(sponsorActivity);  // اربطه

            paymentRepository.save(payment);

            long amountInCents = amount * 100;
            String checkoutUrl = stripeCheckoutService.createCheckoutSession(amountInCents, payment);

            return checkoutUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating checkout session";
        }
    }



    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId) {
        try {
            Stripe.apiKey = stripeApiKey;
            Session session = Session.retrieve(sessionId);

            String customerEmail = session.getCustomerDetails() != null ? session.getCustomerDetails().getEmail() : null;

            Optional<Payment> optionalPayment = paymentRepository.findByStripeSessionId(sessionId);
            if (optionalPayment.isPresent()) {
                Payment payment = optionalPayment.get();
                payment.setPaymentStatus("completed");
                payment.setCustomerEmail(customerEmail);
                paymentRepository.save(payment);
            }

            return "Thank you for your donation! Your payment was successful. Session ID: " + sessionId + ", Email: " + customerEmail;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving payment details.";
        }
    }

    @GetMapping("/payment-cancel")
    public String paymentCancel() {
        return "Payment was canceled. Please try again.";
    }
}
