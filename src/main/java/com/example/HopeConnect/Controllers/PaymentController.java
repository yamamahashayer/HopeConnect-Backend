package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Payment;
import com.example.HopeConnect.Repositories.PaymentRepository;
import com.example.HopeConnect.Services.StripeCheckoutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeCheckoutService stripeCheckoutService;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public PaymentController(PaymentRepository paymentRepository, StripeCheckoutService stripeCheckoutService) {
        this.paymentRepository = paymentRepository;
        this.stripeCheckoutService = stripeCheckoutService;
    }

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestParam("amount") long amount) {
        try {
            // 1. Create the payment record
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setPaymentStatus("pending");
            paymentRepository.save(payment);

            // 2. Convert amount to cents
            long amountInCents = amount * 100;

            // 3. Call service with payment record
            String checkoutUrl = stripeCheckoutService.createCheckoutSession(amountInCents, payment);

            return checkoutUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating checkout session";
        }
    }
    // ✅ Success & Cancel URLs (Stripe redirects here)
    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId) {
        // مبدئياً فقط رسالة نجاح — لاحقاً ممكن تعمل تحقق من الجلسة باستخدام الـ sessionId لو بدك
        return "Thank you for your donation! Your payment was successful. Session ID: " + sessionId;
    }

    @GetMapping("/payment-cancel")
    public String paymentCancel() {
        return "Payment was canceled. Please try again.";
    }
}
