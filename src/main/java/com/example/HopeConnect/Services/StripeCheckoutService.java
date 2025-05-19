package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Payment;
import com.example.HopeConnect.Repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeCheckoutService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final PaymentRepository paymentRepository;

    public StripeCheckoutService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String createCheckoutSession(long amountInCents, Payment paymentRecord) throws Exception {
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8066/api/payment/payment-success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8066/api/payment/payment-cancel")
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.AUTO)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amountInCents)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Donation to HopeConnect")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        paymentRecord.setStripeSessionId(session.getId());
        paymentRepository.save(paymentRecord);

        return session.getUrl();
    }
}
