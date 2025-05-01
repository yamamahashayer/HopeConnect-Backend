package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Services.StripeCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class StripeCheckoutController {

    @Autowired
    private StripeCheckoutService stripeCheckoutService;

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestParam Double amount) throws Exception {
        return stripeCheckoutService.createCheckoutSession(amount);
    }
}
